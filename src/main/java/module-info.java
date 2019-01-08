module sample {
    requires javafx.controls;
    requires javafx.fxml;

    // 暴露包 sample 给javafx 的模块, 使其可以在运行时使用反射访问
    opens com.yuudati.bookmanager.sample to javafx.graphics, javafx.fxml;
}