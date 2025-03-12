module ubb.scs.map.faptebune {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens ubb.scs.map.faptebune to javafx.fxml;
    exports ubb.scs.map.faptebune;
}