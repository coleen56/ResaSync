module fr.bts.sio.resasync {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires com.h2database;
    requires jbcrypt;
    requires com.github.librepdf.openpdf;

    opens fr.bts.sio.resasync to javafx.fxml;
    opens fr.bts.sio.resasync.controller to javafx.fxml;
    opens fr.bts.sio.resasync.model.entity to javafx.base;


    exports fr.bts.sio.resasync;
    exports fr.bts.sio.resasync.controller to javafx.fxml;


}