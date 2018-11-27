package com.networkerr.core.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySqlORM should contain logic for finding SQL annotations, executing SQL queries and connecting to the database.
 * @author Sean Kelly
 * @apiNote MySql command line interface is expected for seeding to work.
 */
@Deprecated
public class MySqlORM extends AnnotationSchema {
    /**
     * Singleton instance for ORM
     */
    private static MySqlORM instance = new MySqlORM();
    /**
     * MySQL Connection object
     */
    private Connection connection = null;

    /**
     * Private constructor to generate the instance, find annotations at runtime start, and seed database with tables.
     */
    private MySqlORM() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.getMapFromMethods();
//            this.goRaw("root", "", "networkerr", this.getSeed());
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

    /**
     * Connect to the MySql database via the command line, and execute the table seeder.
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
}
