module com.example.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.json;
    requires junit;
    requires javafx.swing;

    opens com.example.ejemplo.controller to javafx.fxml;
    exports com.example.ejemplo.controller;
    opens com.example.ejemplo.view to javafx.graphics;
    exports com.example.ejemplo.view;
    opens com.example.ejemplo.model to javafx.base;
    exports com.example.ejemplo.model;
    exports com.example.ejemplo.test;

}