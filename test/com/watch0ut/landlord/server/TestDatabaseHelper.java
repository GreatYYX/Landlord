package com.watch0ut.landlord.server;

import com.watch0ut.landlord.server.database.DatabaseHelper;

public class TestDatabaseHelper {

    public static void main(String[] args) {

        DatabaseHelper db = new DatabaseHelper();
        db.login("yyx", "123456", "");
    }
}
