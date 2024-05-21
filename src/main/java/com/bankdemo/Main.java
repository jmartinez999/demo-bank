package com.bankdemo;

import com.bankdemo.config.GuiceModule;
import com.bankdemo.web.JavalinApp;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new GuiceModule());
        JavalinApp app = injector.getInstance(JavalinApp.class);
        app.start();
    }
}
