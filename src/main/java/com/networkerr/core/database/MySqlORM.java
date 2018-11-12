package com.networkerr.core.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlORM {
    private static MySqlORM instance = new MySqlORM();
    private Connection connection = null;
    private MySqlORM() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MySqlORM getInstance() {
        return instance;
    }

    public void connect(String database, String user, String password) {
        try {
            instance.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, user, password);
        } catch (SQLException e) {
            if(e.getErrorCode() == 1049) {
                Runtime rt = Runtime.getRuntime();
                try {
                    Process pr = rt.exec("mysql -u " + user + " --password=\"" + password + "\"" + " -e\"create database " + database);
                    this.connect(database, user, password);
                } catch (IOException e1) {
                    System.out.println("Could not create database automatically");
                    System.out.println("Check that " + database + " exists in your server");
                }
            }
        }
    }

    public void execute(String sql) {
        if(this.connection != null) {
            Statement statement = null;
            try {
                statement = this.connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                assert statement != null;
                System.out.println("Executing to MySQL...");
                System.out.println(sql);
                statement.execute(sql);
            } catch (SQLException e) {
                if(e.getErrorCode() == 1215) {
                    System.out.println("Foreign key can't be created, it doesn't seem the associated table is created");
                }
                e.printStackTrace();
            }
        }
    }
}
