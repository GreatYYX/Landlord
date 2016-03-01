package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.model.PlayerModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * 玩家列表，显示头像、昵称和积分。
 *
 * Created by Jack on 16/2/27.
 */
public class PlayerListTable extends StackPane {

    private TableView<PlayerModel> tableView;

    public PlayerListTable() {
        setMinWidth(240);

        TableColumn<PlayerModel, String> avatarColumn =
                new TableColumn<PlayerModel, String>();
        avatarColumn.setMinWidth(32);
        avatarColumn.setPrefWidth(40);
        avatarColumn.setSortable(false);
        avatarColumn.setCellValueFactory(
                new PropertyValueFactory<PlayerModel, String>("avatar")
        );
        avatarColumn.setCellFactory(
                new Callback<TableColumn<PlayerModel, String>, TableCell<PlayerModel, String>>() {
                    @Override
                    public TableCell<PlayerModel, String> call(TableColumn<PlayerModel, String> param) {
                        return new AvatarTableCell();
                    }
                }
        );

        TableColumn<PlayerModel, String> nickNameColumn =
                new TableColumn<PlayerModel, String>("昵称");
        nickNameColumn.setMinWidth(75);
        nickNameColumn.setPrefWidth(100);
        nickNameColumn.setSortable(true);
        nickNameColumn.setCellValueFactory(
                new PropertyValueFactory<PlayerModel, String>("nickName")
        );

        TableColumn<PlayerModel, Integer> scoreColumn =
                new TableColumn<PlayerModel, Integer>("积分");
        scoreColumn.setMinWidth(75);
        scoreColumn.setPrefWidth(100);
        scoreColumn.setSortable(true);
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<PlayerModel, Integer>("score")
        );

        tableView = new TableView<PlayerModel>();
        tableView.setMinWidth(240);
        tableView.setPrefSize(240, 250);
        tableView.setEditable(false);
        tableView.getColumns().addAll(
                avatarColumn,
                nickNameColumn,
                scoreColumn
        );
        getChildren().add(tableView);

    }

    public void setItems(ObservableList<PlayerModel> value) {
        tableView.setItems(value);
    }

}
