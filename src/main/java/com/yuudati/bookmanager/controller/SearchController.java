package com.yuudati.bookmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/14 11:31
 */
@Data
@Slf4j
public class SearchController implements Initializable {

    private MainController mainController;

    @FXML
    private AnchorPane parentPane;
    @FXML
    private TableView dataTableView;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTableView.prefWidthProperty().bind(parentPane.widthProperty());
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}