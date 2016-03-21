package com.watch0ut.landlord.client.controller;

import com.watch0ut.landlord.client.model.PlayerModel;
import com.watch0ut.landlord.client.service.WClient;
import com.watch0ut.landlord.client.view.MiniTablePane;
import com.watch0ut.landlord.command.concrete.SeatCommand;
import com.watch0ut.landlord.object.Player;
import com.watch0ut.landlord.object.Table;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Created by Jack on 16/3/9.
 */
public class MiniTableController {

    private MiniTablePane miniTablePane;
    private MouseClickedHandler mouseClickedHandler;

    public MiniTableController(MiniTablePane miniTablePane) {
        this.miniTablePane = miniTablePane;
        mouseClickedHandler = new MouseClickedHandler();
        setMouseClickedHandler();
    }

    public void seat(PlayerModel playerModel) {
        miniTablePane.seat(playerModel);
        // TODO: 暂时用黑框标记，后面统一调整
//        miniTablePane.setStyle("-fx-border-color: black");
    }

    public void unseat(PlayerModel playerModel) {
        miniTablePane.unseat(playerModel);
//        miniTablePane.setStyle("");
    }

    public void ready(Player player) {
        miniTablePane.ready(player);
    }

    public void setMouseClickedHandler() {
        miniTablePane.setOnMouseClicked(mouseClickedHandler);
    }

    public void removeMouseClickedHandler() {
        miniTablePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickedHandler);
    }

    class MouseClickedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            double x = event.getX();
            double y = event.getY();
            int position = -1;
            if (x >= 40 && x <= 72) {
                if (y >= 0 && y <= 32)
                    position = Table.TOP;
                else if (y >= 80 && y <=112)
                    position = Table.BOTTOM;
            } else if (y >= 40 && y <= 72) {
                if (x >= 0 && x <= 32)
                    position = Table.LEFT;
                else if (x >= 80 && x <= 112)
                    position = Table.RIGHT;
            }
            if (position == -1)
                return;
            SeatCommand cmd = new SeatCommand(miniTablePane.getTableId(), position);
            WClient.getInstance().sendCommand(cmd);
        }
    }
}
