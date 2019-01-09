/**
 * @Author Administrator李新栋 [lxd3808@163.com]
 * @Date 2019/1/9 10:34
 */
module com.yuudati.bookmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires slf4j.api;
    requires guava;
    opens com.yuudati.bookmanager to javafx.graphics, javafx.fxml, lombok, guava;
    opens com.yuudati.bookmanager.controller to javafx.graphics, javafx.fxml, lombok;
    opens com.yuudati.bookmanager.entity to lombok;
}