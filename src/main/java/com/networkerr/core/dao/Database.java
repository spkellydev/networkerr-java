package com.networkerr.core.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

final public class Database {
    private static Database instance = new Database();
    private Connection connection = null;

    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void connect(String database, String user, String password) {
        try {
            instance.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, user, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                Runtime rt = Runtime.getRuntime();
                try {
                    Process pr = rt.exec("mysql -u " + user + " --password=\"" + password + "\"" + " -e\"create database " + database);
                    pr.destroy();
                    this.connect(database, user, password);
                } catch (IOException e1) {
                    System.out.println("Could not create database automatically");
                    System.out.println("Check that " + database + " exists in your server");
                }
            }
        }
    }

    protected void execute(String sql) {
        if (this.connection != null) {
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
                if (e.getErrorCode() == 1215) {
                    System.out.println("Foreign key can't be created, it doesn't seem the associated table is created");
                }
                if (e.getErrorCode() == 1146) {
                    System.out.println("Table has not been created. Please update your table name or database.");
                }
                if (e.getErrorCode() == 1064) {
                    System.out.println("Error in MySql Syntax");
                    System.out.println(sql);
                }
                System.out.println(e.getErrorCode());
                e.printStackTrace();
            }
        }
    }

    /**
     * Connect to the MySql database via the command line, and execute the table seeder.
     *
     * @param user
     * @param password
     * @param database
     * @param query
     */
    private void goRaw(String user, String password, String database, String query) {
        Runtime rt = Runtime.getRuntime();
        System.out.println("going raw");
        System.out.println(query);
        try {
            final String sql = String.format("mysql -u %s --password=\"%s\" -e\"create database if not exists %s; use %s; %s\"",
                    user, password, database, database, query);
            System.out.println(String.format("Executing Annotation Seeder....\r\n %s", sql));
            Process pr = rt.exec(sql);
        } catch (IOException e1) {
            System.out.println("Could not run automatically");
        }
    }

    public static Database getInstance() {
        return instance;
    }
}
