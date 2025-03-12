module ubb.scs.map.restaurante {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ubb.scs.map.restaurante to javafx.fxml;
    exports ubb.scs.map.restaurante;
}