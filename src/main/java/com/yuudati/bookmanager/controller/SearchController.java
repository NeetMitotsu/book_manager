package com.yuudati.bookmanager.controller;

import com.yuudati.bookmanager.entity.BookData;
import com.yuudati.bookmanager.util.DataLoadUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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

    /**
     * 数据列表
     */
    private Map<String, BookData> bookDataList;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTableView.prefWidthProperty().bind(parentPane.widthProperty());
        dataTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * 读取缓存数据
     * @return
     */
    public List<BookData> loadData(){
        File dir = new File("./data");
        if (!dir.exists()){
            log.info("没有本地数据");
            return null;
        }
        Map<String, HashSet<String>> attributesMap = DataLoadUtil.getXMLMap(dir, "attributes.xml");
        Map<String, HashSet<String>> charactersMap = DataLoadUtil.getXMLMap(dir, "characters.xml");


        return null;
    }
}
