module ubb.scs.map.adoptioncenter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens ubb.scs.map.adoptioncenter to javafx.fxml;
    exports ubb.scs.map.adoptioncenter;
}