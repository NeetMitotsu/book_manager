package com.yuudati.bookmanager.entity;

import java.io.*;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 文件操作工具类
 *
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/10 9:43
 */
public class FileActionTask extends RecursiveTask<Boolean> {

    private static final long serialVersionUID = 7743951161907313240L;

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

    private static final int THRESHOLD = 100;
    private List<Book> fileList;
    private int type;


    public FileActionTask(List<Book> fileList, int type) {
        this.fileList = fileList;
        this.type = type;
    }

    @Override
    protected Boolean compute() {
        // 任务足够小
        boolean flag = false;
        if (fileList.size() <= THRESHOLD) {
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
        FileActionTask fileTask1 = new FileActionTask(this.fileList.subList(0, middle), this.type);
        FileActionTask fileTask2 = new FileActionTask(this.fileList.subList(middle, this.fileList.size()), this.type);
        invokeAll(fileTask1, fileTask2);
        boolean taskResult1 = fileTask1.join();
        boolean taskResult2 = fileTask2.join();
        boolean result = taskResult1 && taskResult2;
        return result;
    }

    /**
     * 移动并重命名
     *
     * @param fileList
     * @return
     * @throws IOException
     */
    protected boolean moveAndRename(List<Book> fileList) {
        int completeCount = 0;
        for (Book book :
                fileList) {
            String toPath = book.getToPath() +
                    File.separator +
                    book.getAuthor() +
                    File.separator +
                    book.getTheme();
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
                book.setOldFile(newFile);
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
    protected boolean move(List<Book> fileList) {
        int completeCount = 0;
        for (Book book :
                fileList) {
            final File oldFile = book.getOldFile();
            String toPath = book.getToPath() +
                    File.separator +
                    book.getAuthor() +
                    File.separator +
                    book.getTheme();
            final File toDir = new File(toPath);
            final String oldFileName = oldFile.getName();
            File newFile = checkExist(toDir, oldFileName.substring(0, oldFileName.lastIndexOf(".")), oldFileName.substring(oldFileName.lastIndexOf(".")));
            if (copyFile(oldFile, newFile)) {
                completeCount++;
            }
        }
        return completeCount == fileList.size();
    }

    /**
     * 重命名
     *
     * @return
     */
    protected boolean rename(List<Book> fileList) {
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
    private File checkExist(File dir, String fileName, String fileExtension) {
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
    private boolean copyFile(File fromFile, File toFile) {
        boolean flag;
        try {
            if (!fromFile.exists()) {
                fromFile.createNewFile();
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
