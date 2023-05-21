module it.unical.demacs.informatica.mvcwallet {
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

    exports it.unical.demacs.informatica.mvcwallet.controller;
    opens it.unical.demacs.informatica.mvcwallet.controller to javafx.fxml;
    exports it.unical.demacs.informatica.mvcwallet;
}