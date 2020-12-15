package com.fabiangabor.bankjegyek;

import java.sql.*;

public class Database {
    private static final String url = "jdbc:mysql://localhost:3306/Bankjegyek";
    private static final String user = "jdbc";
    private static final String password = "jdbc";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String query;

    private final String dropTables = "drop table if exists `palya`";
    private final String showTables = "show tables";
    private final String createTable =
            "create table if not exists `palya` (" +
                    "i int not null," +
                    "j int not null," +
                    "val int not null," +
                    "constraint palya_pk primary key (i,j)" +
                    ")";

    public Database() {
    }

    public Database(String query) {
        this.query = query;
    }

    void execute() throws SQLException {
        execute(query);
    }

    void execute(String query) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        stmt = conn.createStatement();
        try (ResultSet rs = stmt.executeQuery(query)) {
            rs.first();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            System.out.println(e);
        }
        stmt.close();
        conn.close();
    }

    void insert(int[][] data) throws SQLException {
        execute(dropTables);
        execute(createTable);
        String query;
        query = "insert into `palya`" +
                "values";
        for (int i=0; i<data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                query += "(" + i + "," + j + "," + data[i][j] + ")" + ",";
            }
        }
        query = query.substring(0, query.length()-1);
        execute(query);
    }

    public static void main(String[] args ) throws SQLException {
        String dropTables = "drop table if exists `palya`";
        String showTables = "show tables";
        String createTable =
                "create table if not exists `palya` (" +
                    "i int not null," +
                    "j int not null," +
                    "val int not null," +
                    "constraint palya_pk primary key (i,j)" +
                ")";

        Database db;

        db = new Database();
        db.execute(showTables);
    }
}