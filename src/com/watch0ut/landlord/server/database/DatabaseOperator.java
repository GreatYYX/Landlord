package com.watch0ut.landlord.server.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GreatYYX on 2/17/16.
 *
 * DB基础类
 */
public class DatabaseOperator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseOperator.class);

    private static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/landlord";
    private static final String JDBC_USER = "landlord";
    private static final String JDBC_PWD = "123456";

    private Connection conn_ = null;
    private Statement st_ = null;
    private ResultSet rs_ = null;

    public DatabaseOperator() {
        try {
            Class.forName("org.postgresql.Driver");
            conn_ = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PWD);
            st_ = conn_.createStatement();
        } catch (ClassNotFoundException e) {
            LOGGER.error("Missing JDBC Driver: {}", e);
        } catch (SQLException e) {
            LOGGER.error("Connect database failed: {}", e);
        }
        if (conn_ == null) {
            LOGGER.error("Failed to make connection.");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (rs_ != null) {
                rs_.close();
            }
            if (st_ != null) {
                st_.close();
            }
            if (conn_ != null) {
                conn_.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Release database failed: {}", e);
        }
//        super.finalize();
    }

    public Connection getConn() {
        return conn_;
    }

    public ResultSet query(String sql) throws SQLException {
        return st_.executeQuery(sql);
    }

    public ResultSet query(PreparedStatement st) throws SQLException {
        return st.executeQuery();
    }
}
