package com.yuudati.bookmanager.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

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

    /**
     * 弹出一个提示框
     *
     * @param text
     * @return
     */
    @NotNull
    public Boolean showAlert(String text, boolean needCancel) {
        AtomicBoolean flag = new AtomicBoolean(false);
        Alert alert = null;
        if (needCancel) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
        }
        alert.titleProperty().set("信息");
        alert.headerTextProperty().set(text);
        alert.contentTextProperty().set(null);
        alert.showAndWait().filter(response -> response == ButtonType.OK)
                .ifPresent(response -> flag.set(true));
        return flag.get();
    }

    /**
     * 打开一个进度条
     *
     * @param total 进度条长度
     * @return 进度条controller
     */
    @NotNull
    public ProgressController showProgress(int total) throws IOException {
        // int count, int total
        ProgressController controller = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/ProgressBar.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        Stage progressStage = new Stage(StageStyle.TRANSPARENT);
        progressStage.setAlwaysOnTop(true);
        progressStage.setScene(new Scene(root));
        controller = fxmlLoader.getController();
        controller.init(0, total, progressStage);
        progressStage.show();
        return controller;
    }

}
