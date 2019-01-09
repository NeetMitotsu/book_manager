package com.yuudati.bookmanager.entity;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell<S, T> extends TableCell<S, T> {
    private final Button previewButton;
    private ObservableValue<T> observableValue;

    public ButtonCell() {
        this.previewButton = new Button("预览");
        setGraphic(previewButton);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        }else {
            setGraphic(previewButton);
        }
    }
}
