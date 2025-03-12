module ubb.scs.map.anar {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ubb.scs.map.anar to javafx.fxml;
    exports ubb.scs.map.anar;
}