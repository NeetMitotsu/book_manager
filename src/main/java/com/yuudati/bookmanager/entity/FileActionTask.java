package com.yuudati.bookmanager.entity;

import com.yuudati.bookmanager.controller.ProgressController;
import com.yuudati.bookmanager.mapper.BookInfoMapper;
import com.yuudati.bookmanager.util.SpringContext;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 文件操作工具类
 *
 * @author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/10 9:43
 */
@Data
public class FileActionTask extends RecursiveTask<Boolean> {

    private static final long serialVersionUID = 7743951161907313240L;

//    @Resource

    private BookInfoMapper bookInfoMapper = (BookInfoMapper) SpringContext.getContext().getBean("bookInfoMapper");

    /**
     * 只移动
     */
    public static final int MOVE = 1;
    /**
     * 只重命名
     */
    public static final int RENAME = 1 << 1;
    /**
     * 移动并重命名
     */
    public static final int MOVE_RENAME = MOVE | RENAME;

    private double threshold;
    private List<Book> fileList;
    private int type;
    private ProgressController progressController;


    public FileActionTask(@NotNull List<Book> fileList, ProgressController progressController, int type) {
        this.fileList = fileList;
        this.type = type;
        this.threshold = Math.ceil(fileList.size() / Runtime.getRuntime().availableProcessors());
        this.progressController = progressController;
    }

    public FileActionTask(@NotNull List<Book> fileList, double threshold, ProgressController progressController, int type) {
        this.fileList = fileList;
        this.type = type;
        this.threshold = threshold;
        this.progressController = progressController;
    }

    @Override
    protected Boolean compute() {
        // 任务足够小
        boolean flag = false;
        if (fileList.size() <= this.getThreshold()) {
            switch (type) {
                case MOVE:
                    flag = move(fileList);
                    break;
                case RENAME:
                    flag = rename(fileList);
                    break;
                case MOVE_RENAME:
                    flag = moveAndRename(fileList);
                    break;
                default:
                    break;
            }
            return flag;
        }
        // 任务太大
        int middle = fileList.size() / 2;
        FileActionTask fileTask1 = new FileActionTask(this.fileList.subList(0, middle), this.threshold, progressController, this.type);
        FileActionTask fileTask2 = new FileActionTask(this.fileList.subList(middle, this.fileList.size()), this.threshold, progressController, this.type);
        invokeAll(fileTask1, fileTask2);
        boolean taskResult1 = fileTask1.join();
        boolean taskResult2 = fileTask2.join();
        return taskResult1 && taskResult2;
    }

    /**
     * 移动并重命名
     *
     * @param fileList 文件列表
     * @return
     * @throws IOException
     */
    private boolean moveAndRename(@NotNull List<Book> fileList) {
        int completeCount = 0;
        for (Book book :
                fileList) {
            String toPath = book.getToPath() +
                    File.separator +
                    book.getArtist() +
                    File.separator +
                    book.getParody();
            File dir = new File(toPath);
            if (!dir.exists()) {
                // 路径不存在, 创建
                if (!dir.mkdirs()) {
                    break;
                }
            }
            final String oldName = book.getOldName();
            // 扩展名
            final String fileExtension = oldName.substring(oldName.lastIndexOf("."));
            File newFile = checkExist(dir, book.getNewName(), fileExtension);
            if (copyFile(book.getOldFile(), newFile)) {
                completeCount++;
                // 设置新文件
                book.setNewFile(newFile);
                int insert = 0;
                try {
                    insert = bookInfoMapper.insert(new BookInfo(book));
                }catch (Exception e){
                    e.printStackTrace();
                }
                int count = progressController.getCount();
                progressController.setCount(count + insert);
            }
        }
        return completeCount == fileList.size();
    }

    /**
     * 移动
     *
     * @param fileList
     * @return
     */
    private boolean move(List<Book> fileList) {
        int completeCount = 0;
        for (Book book :
                fileList) {
            final File oldFile = book.getOldFile();
            String toPath = book.getToPath() +
                    File.separator +
                    book.getArtist() +
                    File.separator +
                    book.getParody();
            final File toDir = new File(toPath);
            final String oldFileName = oldFile.getName();
            File newFile = checkExist(toDir, oldFileName.substring(0, oldFileName.lastIndexOf(".")), oldFileName.substring(oldFileName.lastIndexOf(".")));
            if (copyFile(oldFile, newFile)) {
                completeCount++;
                int count = progressController.getCount();
                progressController.setCount(++count);
            }
        }
        return completeCount == fileList.size();
    }

    /**
     * 重命名
     *
     * @return
     */
    private boolean rename(@NotNull List<Book> fileList) {
        int completeCount = 0;
        for (Book book :
                fileList) {
            final File oldFile = book.getOldFile();
            final File fromDir = new File(book.getFromPath());
            final String oldFileName = oldFile.getName();
            String fileExtension = oldFileName.substring(oldFileName.lastIndexOf("."));
            File newFile = checkExist(fromDir, book.getNewName(), fileExtension);
            if (oldFile.renameTo(newFile)) {
                book.setOldFile(newFile);
                completeCount++;
                int count = progressController.getCount();
                progressController.setCount(++count);
            }
        }
        return completeCount == fileList.size();
    }


    /**
     * 检测重名
     *
     * @param dir           目标路径
     * @param fileExtension 文件后缀
     * @return 不重名的文件
     */
    private File checkExist(@NotNull File dir, String fileName, String fileExtension) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File newFile = new File(dir, fileName + fileExtension);
        for (int i = 1; newFile.exists() && i < Integer.MAX_VALUE; i++) {
            newFile = new File(dir, fileName + "(" + i + ")" + fileExtension);
        }
        return newFile;
    }

    /**
     * 拷贝文件
     */
    private boolean copyFile(@NotNull File fromFile, @NotNull File toFile) {
        boolean flag;
        try {
            if (!toFile.exists()) {
                toFile.createNewFile();
            }
            FileInputStream fileInputStream = new FileInputStream(fromFile);
            FileOutputStream fileOutputStream = new FileOutputStream(toFile);
            // 拷贝
            byte[] tempBytes = new byte[8192];
            while (fileInputStream.read(tempBytes) != -1) {
                fileOutputStream.write(tempBytes);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            fileInputStream.close();
            flag = true;
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }


}
