package com.yuudati.bookmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Administrator李新栋 [lxd3808@163.com]
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
        // 读取配置
        this.loadConfig();
    }

    /**
     * 保存配置
     */
    public void saveConfig() {
        try {
            File dataDir = new File("./config");
            if (!dataDir.exists()) {
                if (!dataDir.mkdirs()) {
                    return;
                }
            }
            File configFile = new File(dataDir, "conf.properties");
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) {
                    return;
                }
            }
            FileOutputStream outputStream = new FileOutputStream(configFile);
            Properties prop = new Properties();
            prop.setProperty("toPath", moveAndRenameTabController.getToPathTextField().getText());
            prop.store(outputStream, "ToPath config");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置
     */
    private void loadConfig() {
        try {
            File dataDir = new File("./config");
            if (!dataDir.exists()) {
                return;
            }
            File configFile = new File(dataDir, "conf.properties");
            if (!configFile.exists()) {
                return;
            }
            Properties prop = new Properties();
            FileInputStream inputStream = new FileInputStream(configFile);
            prop.load(inputStream);
            final Enumeration<?> enumeration = prop.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                //
                if ("toPath".equalsIgnoreCase(key)) {
                    moveAndRenameTabController.getToPathTextField().setText(prop.getProperty(key));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
