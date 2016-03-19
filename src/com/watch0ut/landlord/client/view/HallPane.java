package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.Configuration;
import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.object.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 大厅面板，包括牌桌和玩家列表
 *
 * Created by Jack on 16/2/27.
 */
public class HallPane extends VBox {

    private final static int RIGHT_WIDTH = 240;

    private BorderPane mainPane;
    private FlowPane tablesPane;
    private List<MiniTablePane> tableList;
    private PlayerInfoPane playerInfoPane;
    private PlayerListTable playerListTable;

    private MenuItem aboutMenuItem;
    private MenuItem logoutMenuItem;
    private MenuItem exitMenuItem;

    public HallPane() {
        this(null);
    }

    public HallPane(PlayerModel player) {
        setPrefSize(800, 620);

        initMenuBar();

        mainPane = new BorderPane();
        mainPane.setPadding(new Insets(10, 10, 10, 10));
        mainPane.setPrefSize(800, 600);
        getChildren().add(mainPane);

        tableList = new ArrayList<>(Configuration.TABLE_PER_HALL);
        tablesPane = new FlowPane();
        tablesPane.setPadding(new Insets(10, 10, 10, 10));
        tablesPane.setVgap(10);
        tablesPane.setHgap(10);
        for (int i = 0; i < Configuration.TABLE_PER_HALL; i++) {
            MiniTablePane miniTablePane = new MiniTablePane(i + 1);
            tableList.add(miniTablePane);
            tablesPane.getChildren().add(miniTablePane);
        }
        mainPane.setCenter(tablesPane);

        if (player == null)
            playerInfoPane = new PlayerInfoPane();
        else {
            playerInfoPane = new PlayerInfoPane(player);
        }
        playerListTable = new PlayerListTable();
        VBox rightPart = new VBox();
        rightPart.setMinWidth(RIGHT_WIDTH);
        rightPart.setMaxWidth(RIGHT_WIDTH);
        rightPart.setAlignment(Pos.TOP_CENTER);
        rightPart.getChildren().add(playerInfoPane);
        rightPart.getChildren().add(playerListTable);
        mainPane.setRight(rightPart);
    }

    private void initMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("斗地主");
        aboutMenuItem = new MenuItem("关于");
        logoutMenuItem = new MenuItem("注销");
        exitMenuItem = new MenuItem("退出");
        menu.getItems().addAll(
                aboutMenuItem,
                new SeparatorMenuItem(),
                logoutMenuItem,
                new SeparatorMenuItem(),
                exitMenuItem
        );
        menuBar.getMenus().addAll(menu);
        getChildren().add(menuBar);
    }

    public void updatePlayer(PlayerModel playerModel) {
        if (playerModel == null)
            playerInfoPane.unbind();
        else
            playerInfoPane.bind(playerModel);
    }

    public List<MiniTablePane> getTableList() {
        return tableList;
    }

    public PlayerListTable getPlayerListTable() {
        return playerListTable;
    }

    public void setOnAbout(EventHandler<ActionEvent> value) {
        aboutMenuItem.setOnAction(value);
    }

    public void setOnLogout(EventHandler<ActionEvent> value) {
        logoutMenuItem.setOnAction(value);
    }

    public void setOnExit(EventHandler<ActionEvent> value) {
        exitMenuItem.setOnAction(value);
    }

}
