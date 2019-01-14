package com.yuudati.bookmanager.controller;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.luhuiguo.chinese.ChineseUtils;
import com.yuudati.bookmanager.entity.Book;
import com.yuudati.bookmanager.entity.ButtonCell;
import com.yuudati.bookmanager.entity.FileActionTask;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

/**
 * @author  Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/8 17:03
 */
@Data
@Slf4j
public class MainController implements Initializable {


    @FXML
    private AnchorPane parentPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private AnchorPane moveAndRenameTab;
    @FXML
    private AnchorPane searchTab;
    @FXML
    private MoveAndRenameController moveAndRenameTabController;
    @FXML
    private SearchController searchTabController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moveAndRenameTabController.injectMainController(this);
        searchTabController.injectMainController(this);
        tabPane.prefWidthProperty().bind(parentPane.widthProperty());

    }
}
