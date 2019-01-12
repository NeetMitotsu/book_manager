package com.yuudati.bookmanager.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/11 16:18
 */
@Data
public class ProgressController implements Initializable {

    @FXML
    private ProgressBar progressBar;

    private int count;
    private int total;
    private Stage stage;

    public void init(int count, int total, Stage stage) {
        this.count = count;
        this.total = total;
        this.stage = stage;

        Service<Integer> service = new Service<Integer>() {
            @Override
            protected Task<Integer> createTask() {
                return new Task<Integer>() {
                    @Override
                    protected Integer call() {
                        while (getCount() < total) {
                            updateProgress(getCount(), total);
                        }
                        return null;
                    }
                };
            }
        };
        progressBar.setProgress(0);
        progressBar.progressProperty().bind(service.progressProperty());
        service.setOnSucceeded(event -> stage.close());
        service.restart();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    synchronized public int getCount() {
        return count;
    }

    public void setCount(int count) {
        synchronized (this) {
            this.count = count;
        }
    }
}
