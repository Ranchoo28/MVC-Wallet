module it.unical.demacs.informatica.mvcwallet {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires com.jfoenix;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.context.support;
    requires org.apache.commons.codec;
    requires spring.security.crypto;
    requires org.xerial.sqlitejdbc;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires okhttp3;

    exports it.unical.demacs.informatica.mvcwallet.controller;
    opens it.unical.demacs.informatica.mvcwallet.controller to javafx.fxml;
    exports it.unical.demacs.informatica.mvcwallet;
    opens it.unical.demacs.informatica.mvcwallet.model to javafx.base;
    opens it.unical.demacs.informatica.mvcwallet.handler to javafx.base;
}