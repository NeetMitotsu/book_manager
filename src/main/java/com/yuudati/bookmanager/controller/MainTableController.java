package com.yuudati.bookmanager.controller;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.yuudati.bookmanager.entity.Book;
import com.yuudati.bookmanager.entity.ButtonCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private Button fromPathButton;
    @FXML
    private Button toPathButton;
    @FXML
    private TextField fromPathTextField;
    @FXML
    private TextField toPathTextField;
    @FXML
    private TableView<Book> mainTableView;

    @FXML
    private TableColumn<Book, String> rowNumCol, fromPathCol, fromNameCol, toPathCol, toNameCol,
            exhibitionCol, authorCol, bookNameCol, themeCol, translateCol;
    @FXML
    private TableColumn<Book, Button> actionCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableView();
    }

    /**
     * 初始化表格
     */
    private void initTableView() {
        mainTableView.setEditable(true);
        // 行号
        rowNumCol.setCellFactory(column -> {
            TableCell<Book, String> cell = new TableCell<>() {
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
            };
            return cell;
        });
        fromPathCol.setCellValueFactory(cellData -> cellData.getValue().fromPathProperty());
        fromNameCol.setCellValueFactory(cellData -> cellData.getValue().oldNameProperty());
        toPathCol.setCellValueFactory(cellData -> cellData.getValue().toPathProperty());
        toNameCol.setCellValueFactory(cellData -> cellData.getValue().newNameProperty());
        toNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        toNameCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setNewName(event.getNewValue());
        });
        toNameCol.setOnEditCancel(toNameCol.getOnEditCommit());
        toNameCol.setOnEditStart(toNameCol.getOnEditCommit());
        exhibitionCol.setCellValueFactory(cellData -> cellData.getValue().exhibitionProperty());
        exhibitionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        exhibitionCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setExhibition(event.getNewValue());
            book.updateNewName();
        });
        exhibitionCol.setOnEditCancel(exhibitionCol.getOnEditCommit());
        authorCol.setCellValueFactory(cellData -> cellData.getValue().authorProperty());
        authorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        authorCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setAuthor(event.getNewValue());
            book.updateNewName();
        });
        authorCol.setOnEditCancel(authorCol.getOnEditCommit());
        bookNameCol.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookNameCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setBookName(event.getNewValue());
            book.updateNewName();
        });
        bookNameCol.setOnEditCancel(bookNameCol.getOnEditCommit());
        themeCol.setCellValueFactory(cellData -> cellData.getValue().themeProperty());
        themeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        themeCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setTheme(event.getNewValue());
            book.updateNewName();
        });
        themeCol.setOnEditCancel(themeCol.getOnEditCommit());
        translateCol.setCellValueFactory(cellData -> cellData.getValue().translateProperty());
        translateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        translateCol.setOnEditCommit(event -> {
            Book book = mainTableView.getSelectionModel().getSelectedItem();
            book.setTranslate(event.getNewValue());
            book.updateNewName();
        });
        translateCol.setOnEditCancel(translateCol.getOnEditCommit());
        actionCol.setCellFactory(param -> {
            ButtonCell<Book, Button> buttonCell = new ButtonCell<>();
            Button previewButton = (Button) buttonCell.getGraphic();
            previewButton.setOnAction(event -> {
                mainTableView.getSelectionModel().select(buttonCell.getIndex());
                Book selectedItem = mainTableView.getSelectionModel().getSelectedItem();
                showAlert(selectedItem.getOldName());
                System.out.println("点击了预览按钮");
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
        String patternStr = "\\(\\S+\\)(\\s*)(\\[.+])+(.)+(\\(.+\\))* *(\\[.+])* *(\\[.+])*";
        Pattern pattern = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
        File[] files = dir.listFiles();
        final HashMultiset<File> multiset = HashMultiset.create();
        for (File f : files) {
            List<File> fileList = Lists.newArrayList();
            if (f.isDirectory()) {
                getFileList(f, fileList);
                for (File file :
                        fileList) {
                    final String fileName = file.getName();
                    Matcher m = pattern.matcher(fileName);
                    if (m.find()) {
                        multiset.add(file);
                    }
                }
            }
            if (f.isFile()) {
                final String fileName = f.getName();
                Matcher m = pattern.matcher(fileName);
                if (m.find()) {
                    multiset.add(f);
                } else {

                }
            }
        }

        final List<Book> fileList = multiset.elementSet().stream().map(file -> new Book(file, file.getPath(), file.getName())).collect(Collectors.toList());
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
     *
     * @param event
     */
    public void switchToPath(ActionEvent event) {
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
            showField.getText().startsWith("目")){
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
    public Boolean showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(text);
        alert.contentTextProperty().set(null);
        AtomicBoolean flag = new AtomicBoolean(false);
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent(response -> flag.set(true));
        return flag.get();
    }

    public void showBookTable(ObservableList<Book> bookList) {

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
