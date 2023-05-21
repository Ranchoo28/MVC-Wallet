module com.example.progettouid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.context.support;
    requires jakarta.mail;
    requires org.apache.commons.codec;
    requires spring.security.crypto;
    requires org.xerial.sqlitejdbc;

    exports com.example.progettouid.application.controller;
    opens com.example.progettouid.application.controller to javafx.fxml;
    exports com.example.progettouid.application;
    opens com.example.progettouid.application to javafx.fxml;
}