package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.client.model.PlayerModel;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;

/**
 * 头像单元格，玩家列表中头像列根据头像路径显示对应的头像。
 *
 * Created by Jack on 16/2/27.
 */
public class AvatarTableCell extends TableCell<PlayerModel, String> {

    public AvatarTableCell() {
        setAlignment(Pos.CENTER);
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            AvatarView avatarView = new AvatarView(item, AvatarView.TINY);
            setGraphic(avatarView);
        }
    }
}
