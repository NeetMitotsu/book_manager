package com.yuudati.bookmanager.controller;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.yuudati.bookmanager.entity.Book;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * 选择路径
     * @param event
     */
    public void switchFromPath(ActionEvent event){
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
                }else {

                }
            }
        }

        final List<Book> fileList = multiset.elementSet().stream().map(file -> new Book(file.getPath(), file.getName())).collect(Collectors.toList());
        mainTableView.setItems(FXCollections.observableArrayList(fileList));
    }

    /**
     * 迭代获取目录下所有文件列表
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
     * @param event
     */
    public void switchToPath(ActionEvent event) {
        directoryChoose("选择目标路径", toPathTextField);
    }

    public File directoryChoose(String title, TextField showField){
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle(title);
        final File file = dirChooser.showDialog(primaryStage);
        if (file != null && file.isDirectory()){
            showField.setText(file.getPath());
            return file;
        }else{
            showAlert("请选择一个目录");
            return null;
        }
    }

    /**
     * 打开一个提醒框
     * @param text
     */
    public void showAlert(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(text);
        alert.contentTextProperty().set(null);
        alert.show();
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
