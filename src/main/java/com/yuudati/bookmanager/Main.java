package com.yuudati.bookmanager;

import com.yuudati.bookmanager.controller.MainTableController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/8 17:00
 */
public class Main extends Application {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Book Manager V1.0");
        final Scene scene = new Scene(root, 1366, 768);

        // 传递参数给controller
        MainTableController mainTableController = fxmlLoader.getController();
        mainTableController.setPrimaryStage(stage);
        mainTableController.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        stage.setScene(scene);
        log.info("启动......");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
