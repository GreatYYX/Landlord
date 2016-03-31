package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Player;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试ActionView，查看各种角色的效果
 *
 * Created by Jack on 16/2/21.
 */
public class TestActionView {

    public static Parent initialize() {
        RoleView farmer = new RoleView(Player.ROLE.Farmer);
        RoleView landlord3 = new RoleView(Player.ROLE.Landlord3);
        RoleView landlordA = new RoleView(Player.ROLE.LandlordA);
        RoleView landlord3A = new RoleView(Player.ROLE.Landlord3A);

        FlowPane parent = new FlowPane();
        parent.getChildren().addAll(farmer, landlord3, landlordA, landlord3A);

        return parent;
    }
}
