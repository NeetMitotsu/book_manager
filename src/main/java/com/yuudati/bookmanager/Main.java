package com.yuudati.bookmanager;

import com.yuudati.bookmanager.controller.MainController;
import com.yuudati.bookmanager.controller.MoveAndRenameController;
import com.yuudati.bookmanager.controller.SearchController;
import com.yuudati.bookmanager.util.SpringContext;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.FileCopyUtils;

import java.io.File;


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
        File datafile = new File("." + File.separator + "data" + File.separator + "book_manager.db");
        if (!datafile.exists()) {
            File database = new File(getClass().getResource(File.separator + "database" + File.separator + "book_manager.db").toURI());
            FileCopyUtils.copy(database, datafile);
        }
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        SpringContext.setApplicationContext(context);
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(File.separator + "fxml" + File.separator + "Main.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Book Manager V1.5");
        final Scene scene = new Scene(root, 1366, 800);
        final MainController mainController = fxmlLoader.getController();
        scene.getStylesheets().add(getClass().getResource(File.separator + "css" + File.separator + "application.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            mainController.saveConfig();
            context.close();
            Platform.exit();
        });
        log.info("启动......");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
