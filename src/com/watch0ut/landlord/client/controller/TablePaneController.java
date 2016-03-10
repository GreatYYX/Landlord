package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.MainApplication;
import com.watch0ut.landlord.client.view.TablePane;

/**
 * Created by Jack on 16/3/3.
 */
public class TablePaneController {

    private MainApplication application;
    private TablePane tablePane;

    public TablePaneController(MainApplication application, TablePane tablePane) {
        this.application = application;
        this.tablePane = tablePane;
    }

    public void allReady() {
        tablePane.setState(TablePane.FULL);
        tablePane.updatePlayerPosition();
    }
}
