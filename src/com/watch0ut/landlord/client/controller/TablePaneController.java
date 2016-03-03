package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.view.TablePane;

/**
 * Created by Jack on 16/3/3.
 */
public class TablePaneController {

    private TablePane tablePane;

    public TablePaneController(TablePane tablePane) {
        this.tablePane = tablePane;
    }

    public void allReady() {
        tablePane.setState(TablePane.FULL);
        tablePane.updatePlayerPosition();
    }
}
