module ubb.scs.map.oferte {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jdk.compiler;

    opens ubb.scs.map.oferte to javafx.fxml;
    exports ubb.scs.map.oferte;
}