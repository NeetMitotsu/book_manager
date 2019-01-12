package com.yuudati.bookmanager.controller;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.yuudati.bookmanager.entity.Book;
import com.yuudati.bookmanager.entity.ButtonCell;
import com.yuudati.bookmanager.entity.FileActionTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/8 17:03
 */
@Data
@Slf4j
public class MainTableController implements Initializable {

    private Scene scene;
    private Stage primaryStage;

    @FXML
    private Pane pane;

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
            exhibitionCol, authorCol, bookNameCol, themeCol, translateCol;
    @FXML
    private TableColumn<Book, Button> actionCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainTableView.prefWidthProperty().bind(pane.widthProperty());
        mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mainTableView.prefWidthProperty().bind(pane.widthProperty());
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
            book.setNewName(event.getNewValue());
        });
        toNameCol.setOnEditCancel(toNameCol.getOnEditCommit());
        exhibitionCol.setCellValueFactory(cellData -> cellData.getValue().exhibitionProperty());
        exhibitionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        exhibitionCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                book.setExhibition(event.getNewValue());
                book.updateNewName();
            });

        });
        exhibitionCol.setOnEditCancel(exhibitionCol.getOnEditCommit());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                book.setAuthor(event.getNewValue());
                book.updateNewName();
            });
        });
        authorCol.setOnEditCancel(authorCol.getOnEditCommit());
        bookNameCol.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookNameCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                book.setBookName(event.getNewValue());
                book.updateNewName();
            });
        });
        bookNameCol.setOnEditCancel(bookNameCol.getOnEditCommit());
        themeCol.setCellValueFactory(cellData -> cellData.getValue().themeProperty());
        themeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        themeCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                book.setTheme(event.getNewValue());
                book.updateNewName();
            });
        });
        themeCol.setOnEditCancel(themeCol.getOnEditCommit());
        translateCol.setCellValueFactory(cellData -> cellData.getValue().translateProperty());
        translateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        translateCol.setOnEditCommit(event -> {
            List<Book> books = mainTableView.getSelectionModel().getSelectedItems();
            books.forEach(book -> {
                book.setTranslate(event.getNewValue());
                book.updateNewName();
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
     * 选择路径
     *
     * @param event
     */
    public void switchFromPath(ActionEvent event) {
        File dir = directoryChoose("选择原路径", fromPathTextField);
        assert dir != null;
        File[] files = dir.listFiles();
        final HashMultiset<File> multiset = HashMultiset.create();
        assert files != null;
        for (File f : files) {
            List<File> fileList = Lists.newArrayList();
            if (f.isDirectory()) {
                getFileList(f, fileList);
                multiset.addAll(fileList);
            }
            if (f.isFile()) {
                multiset.add(f);
            }
        }

        final List<Book> fileList = multiset.elementSet().stream().map(file -> new Book(file, file.getParent(), file.getName())).collect(Collectors.toList());
        for (Book book :
                fileList) {
            System.out.println(Lists.newArrayList(book.getNewInfo()));
        }
        mainTableView.setItems(FXCollections.observableArrayList(fileList));
    }

    /**
     * 迭代获取目录下所有文件列表
     *
     * @param file
     * @param fileList
     */
    private void getFileList(File file, List<File> fileList) {
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
        if (!toPath.exists() && showAlert("该路径不存在, 是否创建")) {
            toPath.mkdirs();
        }
        ObservableList<Book> tableItems = mainTableView.getItems();
        if (tableItems != null && mainTableView.getItems().size() > 0) {
            tableItems.forEach(book -> {
                book.setToPath(toPath.getPath());
                book.setNewName(String.format("(%s)_[%s]_%s_(%s)_[%s]",
                        book.getExhibition(), book.getAuthor(), book.getBookName(), book.getTheme(), book.getTranslate()));
            });
        }

    }

    public File directoryChoose(String title, TextField showField) {
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
     * @param text
     */
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
    private ProgressController showProgress(int count, int total) {
        // int count, int total
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/progressBar.fxml"));
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = fxmlLoader.load();
            Stage progressStage = new Stage(StageStyle.TRANSPARENT);
            progressStage.setAlwaysOnTop(true);
            progressStage.setScene(new Scene(root));
            ProgressController controller = fxmlLoader.getController();
            controller.init(count, total, progressStage);
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
    public void onlyMove() throws InterruptedException {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(0, books.size());
        new Thread(() -> {
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


        }).start();
    }

    /**
     * 只重命名
     */
    public void onlyRename() throws InterruptedException {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(0, books.size());
        new Thread(() -> {
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
        }).start();

    }

    /**
     * 移动并重命名
     *
     * @param actionEvent
     */
    public void moveAndRename(ActionEvent actionEvent) throws InterruptedException {
        final ObservableList<Book> books = mainTableView.getItems();
        // cpu核数为最大并发数
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        // 展示一个进度条
        ProgressController progressController = showProgress(0, books.size());
        new Thread(() -> {
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

        }).start();

    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
