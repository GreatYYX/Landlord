package com.watch0ut.landlord.client.util;

import com.watch0ut.landlord.Configuration;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;

/**
 * Created by Jack on 16/2/28.
 */
public class DealTask extends Task<IntegerProperty> {
    private Integer number;
    private IntegerProperty cardIndex;

    public DealTask(int number) {
        this.number = number;
        cardIndex = new SimpleIntegerProperty(0);
    }

    public ReadOnlyIntegerProperty cardIndexProperty() {
        return IntegerProperty.readOnlyIntegerProperty(cardIndex);
    }

    @Override
    protected IntegerProperty call() throws Exception {
        for (int i = 0; i < number; i++) {
            if (isCancelled())
                break;
            updateProgress(i + 1, number);
            cardIndex.set(i + 1);
            try {
                Thread.sleep(Configuration.CLIENT_START_DELAY / number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new SimpleIntegerProperty(number);
    }
}
