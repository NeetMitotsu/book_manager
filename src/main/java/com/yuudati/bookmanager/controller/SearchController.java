package com.yuudati.bookmanager.controller;

import javafx.fxml.Initializable;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/14 11:31
 */
@Data
public class SearchController implements Initializable {

    private MainController mainController;

    public void injectMainController(MainController mainController){
        this.mainController = mainController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
