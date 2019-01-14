package com.yuudati.bookmanager.controller;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.luhuiguo.chinese.ChineseUtils;
import com.yuudati.bookmanager.entity.Book;
import com.yuudati.bookmanager.entity.ButtonCell;
import com.yuudati.bookmanager.entity.FileActionTask;
import com.yuudati.bookmanager.util.ThreadUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/14 11:35
 */
@Data
public class MoveAndRenameController implements Initializable {

    private MainController mainController;

    private Scene scene;
    private Stage primaryStage;

    @FXML
    private Pane parentPane;
    @FXML
    public TabPane tabPane;

    @FXML
    private Button fromPathButton;
    @FXML
    private Button toPathButton;
    @FXML
    private TextField fromPathTextField;
    @FXML
    private TextField toPathTextField;
    @FXML
    public Button moveButton;
    @FXML
    public Button renameButton;
    @FXML
    public Button moveAndRenameButton;
    @FXML
    private TableView<Book> mainTableView;

    @FXML
    private TableColumn<Book, String> rowNumCol, fromPathCol, fromNameCol, toPathCol, toNameCol,
            exhibitionCol, artistCol, bookNameCol, parodyCol, translateCol;
    @FXML
    private TableColumn<Book, Button> actionCol;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainTableView.prefWidthProperty().bind(parentPane.widthProperty());
        mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        initTableView();
    }

    /**
     * 初始化表格
     */
    private void initTableView() {
        mainTableView.setEditable(true);
        // 行号
        rowNumCol.setCellFactory(column -> new TableCell<Book, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    int rowIndex = this.getIndex() + 1;
                    this.setText(String.valueOf(rowIndex));
                }
            }
        });
        fromPathCol.setCellValueFactory(cellData -> cellData.getValue().fromPathProperty());
        fromNameCol.setCellValueFactory(cellData -> cellData.getValue().oldNameProperty());
        fromNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        toPathCol.setCellValueFactory(cellData -> cellData.getValue().toPathProperty());
        toNameCol.setCellValueFactory(cellData -> cellData.getValue().newNameProperty());
        toNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        toNameCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            final String newValue = event.getNewValue();
            if (!Strings.isNullOrEmpty(newValue)) {
                book.setNewName(newValue.trim());
            }
        });
        toNameCol.setOnEditCancel(toNameCol.getOnEditCommit());
        exhibitionCol.setCellValueFactory(cellData -> cellData.getValue().exhibitionProperty());
        exhibitionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        exhibitionCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                final String newValue = event.getNewValue();
                if (!Strings.isNullOrEmpty(newValue)) {
                    book.setExhibition(newValue.toUpperCase().trim());
                    book.updateNewName();
                }
            });

        });
        exhibitionCol.setOnEditCancel(exhibitionCol.getOnEditCommit());
        artistCol.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        artistCol.setCellFactory(TextFieldTableCell.forTableColumn());
        artistCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                final String newValue = event.getNewValue();
                if (!Strings.isNullOrEmpty(newValue)) {
                    book.setArtist(newValue.trim());
                    book.updateNewName();
                }
            });
        });
        artistCol.setOnEditCancel(artistCol.getOnEditCommit());
        bookNameCol.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookNameCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                final String newValue = event.getNewValue();
                if (!Strings.isNullOrEmpty(newValue)) {
                    book.setBookName(newValue.trim());
                    book.updateNewName();
                }
            });
        });
        bookNameCol.setOnEditCancel(bookNameCol.getOnEditCommit());
        parodyCol.setCellValueFactory(cellData -> cellData.getValue().parodyProperty());
        parodyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        parodyCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                final String newValue = event.getNewValue();
                if (!Strings.isNullOrEmpty(newValue)) {
                    book.setParody(newValue.toUpperCase().trim());
                    book.updateNewName();
                }
            });
        });
        parodyCol.setOnEditCancel(parodyCol.getOnEditCommit());
        translateCol.setCellValueFactory(cellData -> cellData.getValue().translateProperty());
        translateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        translateCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                final String newValue = event.getNewValue();
                if (!Strings.isNullOrEmpty(newValue)) {
                    book.setTranslate(ChineseUtils.toSimplified(newValue.trim()));
                    book.updateNewName();
                }
            });
        });
        translateCol.setOnEditCancel(translateCol.getOnEditCommit());
        actionCol.setCellFactory(param -> {
            ButtonCell<Book, Button> buttonCell = new ButtonCell<>();
            Button previewButton = (Button) buttonCell.getGraphic();
            previewButton.setOnAction(event -> {
                mainTableView.getSelectionModel().select(buttonCell.getIndex());
                Book selectedItem = mainTableView.getSelectionModel().getSelectedItem();
                try {
                    Desktop.getDesktop().open(selectedItem.getOldFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return buttonCell;
        });
    }

    /**
     * 选择路径, 并校验文件, 返回压缩包文件
     */
    public void switchFromPath() {
        String patternStr = ".+\\.(rar|zip|7z|tar|gz)$";
        Pattern pattern = Pattern.compile(patternStr);

        File dir = directoryChoose("选择原路径", fromPathTextField);
        assert dir != null;
        File[] files = dir.listFiles();
        final HashMultiset<File> multiset = HashMultiset.create();
        assert files != null;
        for (File f : files) {
            List<File> fileList = Lists.newArrayList();
            if (f.isDirectory()) {
                getFileList(f, fileList);
                for (File file : fileList) {
                    if (pattern.matcher(file.getName()).find()) {
                        multiset.add(file);
                    }
                }
            }
            if (f.isFile()) {
                if (pattern.matcher(f.getName()).find()) {
                    multiset.add(f);
                }
            }
        }

        final List<Book> fileList = multiset.elementSet().stream().map(file -> new Book(file, file.getParent(), file.getName())).collect(Collectors.toList());
        // 更新
        if (!Strings.isNullOrEmpty(toPathTextField.getText()) && !toPathTextField.getText().startsWith("目标")) {
            fileList.forEach(book -> {
                book.setToPath(toPathTextField.getText());
                book.setNewName(String.format("(%s)_[%s]_%s_(%s)_[%s]",
                        book.getExhibition(), book.getArtist(), book.getBookName(), book.getParody(), book.getTranslate()));
            });
        }
        mainTableView.setItems(FXCollections.observableArrayList(fileList));
    }

    /**
     * 迭代获取目录下所有文件列表
     *
     * @param file     文件路径
     * @param fileList 结果输出
     */
    private void getFileList(@NotNull File file, List<File> fileList) {
        File[] fs = file.listFiles();
        if (fs != null && fs.length > 0) {
            for (File f : fs) {
                if (f.isDirectory()) {
                    getFileList(f, fileList);
                }
                if (f.isFile()) {
                    fileList.add(f);
                }
            }
        }
    }

    /**
     * 选择目标路径
     */
    public void switchToPath() {
        File toPath = directoryChoose("选择目标路径", toPathTextField);
        assert toPath != null;
        if (!toPath.exists() && showAlert("该路径不存在, 是否创建")) {
            toPath.mkdirs();
        }
        ObservableList<Book> tableItems = mainTableView.getItems();
        if (tableItems != null && mainTableView.getItems().size() > 0) {
            tableItems.forEach(book -> {
                book.setToPath(toPath.getPath());
                book.setNewName(String.format("(%s)_[%s]_%s_(%s)_[%s]",
                        book.getExhibition(), book.getArtist(), book.getBookName(), book.getParody(), book.getTranslate()));
            });
        }

    }

    @Nullable
    private File directoryChoose(String title, TextField showField) {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle(title);
        File file = dirChooser.showDialog(primaryStage);
        if (file != null && file.isDirectory()) {
            showField.setText(file.getPath());
            return file;
        } else {
            if (Strings.isNullOrEmpty(showField.getText()) ||
                    showField.getText().startsWith("源") ||
                    showField.getText().startsWith("目")) {
                showAlert("请选择一个目录");
                return null;
            }
            file = new File(showField.getText());
            return file;
        }
    }

    /**
     * 打开一个提醒框
     *
     * @param text info
     */
    @NotNull
    private Boolean showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(text);
        alert.contentTextProperty().set(null);
        AtomicBoolean flag = new AtomicBoolean(false);
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent(response -> flag.set(true));
        return flag.get();
    }

    @NotNull
    private ProgressController showProgress(int total) {
        // int count, int total
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/ProgressBar.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            Stage progressStage = new Stage(StageStyle.TRANSPARENT);
            progressStage.setAlwaysOnTop(true);
            progressStage.setScene(new Scene(root));
            ProgressController controller = fxmlLoader.getController();
            controller.init(0, total, progressStage);
            progressStage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 只移动
     */
    public void onlyMove() {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(books.size());

        ThreadUtil.getThreadPool().submit(() -> {
            ForkJoinTask<Boolean> task = new FileActionTask(books, progressController, FileActionTask.MOVE);
            Boolean result = forkJoinPool.invoke(task);
            Platform.runLater(() -> {
                if (Boolean.TRUE.equals(result)) {
                    showAlert("移动完成");
                } else {
                    progressController.getStage().close();
                    showAlert("移动失败");
                }
            });
        });
    }

    /**
     * 只重命名
     */
    public void onlyRename() {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(books.size());
        ThreadUtil.getThreadPool().submit(() -> {
            ForkJoinTask<Boolean> task = new FileActionTask(books, progressController, FileActionTask.RENAME);
            Boolean result = forkJoinPool.invoke(task);
            Platform.runLater(() -> {
                if (Boolean.TRUE.equals(result)) {
                    showAlert("重命名完成");
                } else {
                    Platform.runLater(() -> progressController.getStage().close());
                    showAlert("重命名失败");
                }
            });
        });
    }

    /**
     * 移动并重命名
     */
    public void moveAndRename() {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(books.size());
        ThreadUtil.getThreadPool().submit(() -> {
            ForkJoinTask<Boolean> task = new FileActionTask(books, progressController, FileActionTask.MOVE_RENAME);
            Boolean result = forkJoinPool.invoke(task);
            Platform.runLater(() -> {
                if (Boolean.TRUE.equals(result)) {
                    showAlert("操作完成");
                } else {
                    Platform.runLater(() -> progressController.getStage().close());
                    showAlert("操作失败");
                }
            });
        });
    }
}
