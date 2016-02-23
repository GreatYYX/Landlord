package com.watch0ut.landlord.client.view;

import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;

/**
 * 测试ActionView，查看各种角色的效果
 *
 * Created by Jack on 16/2/21.
 */
public class TestActionView {

    public static Parent initialize() {
        ActionView farmer = new ActionView(ActionView.FARMER);
        ActionView landlord3 = new ActionView(ActionView.LANDLORD_3);
        ActionView landlordA = new ActionView(ActionView.LANDLORD_A);
        ActionView landlord3A = new ActionView(ActionView.LANDLORD_3A);

        FlowPane parent = new FlowPane();
        parent.getChildren().addAll(farmer, landlord3, landlordA, landlord3A);

        return parent;
    }
}
