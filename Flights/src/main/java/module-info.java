module ubb.scs.map.zboruri {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ubb.scs.map.zboruri to javafx.fxml;
    exports ubb.scs.map.zboruri;
}