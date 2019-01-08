package com.yuudati.bookmanager.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/8 17:00
 */
public class Main extends Application {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        stage.setTitle("Hello world");
        stage.setScene(new Scene(root, 1366, 768));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
