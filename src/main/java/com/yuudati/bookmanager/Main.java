package com.yuudati.bookmanager;

import com.yuudati.bookmanager.controller.MainController;
import com.yuudati.bookmanager.controller.MoveAndRenameController;
import com.yuudati.bookmanager.controller.SearchController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;


/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/8 17:00
 */
@Slf4j
public class Main extends Application {


    private MoveAndRenameController moveAndRenameController;
    private SearchController searchController;

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Book Manager V1.0");
        final Scene scene = new Scene(root, 1366, 800);
        final MainController mainController = fxmlLoader.getController();
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            mainController.saveConfig();
            Platform.exit();
        });
        log.info("启动......");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
