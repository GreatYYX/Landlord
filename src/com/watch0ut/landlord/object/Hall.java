package com.watch0ut.landlord.object;

import com.watch0ut.landlord.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GreatYYX on 12/23/15.
 *
 * 大厅，包含牌桌
 */
public class Hall {

    private Table[] tables_ = new Table[Configuration.TABLE_PER_HALL];
    private Dealer[] dealers_ = new Dealer[Configuration.TABLE_PER_HALL];

    public Hall() {
        //创建tables和dealer
        for(int i = 0; i < Configuration.TABLE_PER_HALL; i++) {
            dealers_[i] = new Dealer();
            tables_[i] = new Table(i);
            dealers_[i].setTable(tables_[i]);
            tables_[i].setDealer(dealers_[i]);
        }
    }

    public Table getTable(int tableId) {
        return tables_[tableId];
    }

}
