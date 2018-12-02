package com.networkerr.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
    private static String environment;
    private static AppProperties settings;
    private static Properties props = new Properties();
    private static InputStream in = null;
    private AppProperties() {
        settings = new AppProperties();
    }

    public static AppProperties env(String env) {
        if (env == null) {
            env = "local";
        }
        environment = env;
        return settings;
    }

    public static void setProps() {
        String filename = "config." + environment + ".properties";
        try {
            in = new FileInputStream("config" + File.separator + filename);
            props.load(in);
        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String prop(String p) {
        return props.getProperty(p);
    }
}
