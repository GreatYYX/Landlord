package com.watch0ut.landlord.client.view;

import com.watch0ut.landlord.object.Card;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 自己的手牌
 *
 * Created by Jack on 16/2/28.
 */
public class SelfHandPane extends VBox implements HandPane {
    private final static int DELTA_WIDTH = 23;
    private final static int DELTA_HEIGHT = 23;

    private List<Card> hand;

    private AnchorPane handPane;
    private List<CardView> handView;
    private List<CardView> selectedCardViews;
    private MarkLabel surplusLabel;

    private int startIndex;
    private int endIndex;

    public SelfHandPane(List<Card> cards) {
        hand = cards;
        selectedCardViews = new ArrayList<>();
        handView = new ArrayList<>(hand.size());

        int width = DELTA_WIDTH * (hand.size() - 1) + CardView.WIDTH;
        int height = DELTA_HEIGHT * 2 + CardView.HEIGHT;

        setMinSize(width, height);
        setMaxSize(width, height);
        setAlignment(Pos.CENTER);

        handPane = new AnchorPane();
        handPane.setPrefSize(CardView.WIDTH, height - DELTA_HEIGHT);
        MouseClickedHandler mouseClickedHandler = new MouseClickedHandler();
        handPane.setOnMousePressed(mouseClickedHandler);
        handPane.setOnMouseReleased(mouseClickedHandler);
        HBox hBox = new HBox(handPane);
        hBox.setAlignment(Pos.CENTER);

        surplusLabel = new MarkLabel(MarkLabel.SURPLUS);
        surplusLabel.setVisible(false);

        getChildren().addAll(hBox, surplusLabel);
    }

    public void draw() {
        int index = handView.size() + 1;
        if (index <= hand.size()) {
            CardView cardView = new CardView(hand.get(index - 1));
            int delta = DELTA_WIDTH * (index - 1);
            cardView.setLayoutX(delta);
            cardView.setLayoutY(DELTA_HEIGHT);
            handPane.setPrefWidth(delta + CardView.WIDTH);
            handPane.getChildren().add(cardView);
            handView.add(cardView);
        }
    }

    public List<Card> getSelectedCards() {
        List<Card> selectedCards = new ArrayList<>(selectedCardViews.size());
        for (CardView cardView : selectedCardViews) {
            selectedCards.add(cardView.getCard());
        }
        return selectedCards;
    }

    public void clearSelectedCards() {
        selectedCardViews.clear();
    }

    public void play() {
        for (CardView cardView : selectedCardViews) {
            hand.remove(cardView.getCard());
            handView.remove(cardView);
            handPane.getChildren().remove(cardView);
        }
        int index = 0;
        for (CardView cardView : handView) {
            int x = DELTA_WIDTH * index;
            cardView.setLayoutX(x);
            index++;
        }
        handPane.setPrefWidth(DELTA_WIDTH * (hand.size() - 1) + CardView.WIDTH);
    }

    public void pass() {
        for (CardView cardView : selectedCardViews) {
            cardView.setLayoutY(DELTA_HEIGHT);
        }
    }

    public void updateState() {
        if (!surplusLabel.isVisible()) {
            surplusLabel.setVisible(true);
        }
        int number = handView.size();
        if (number == 0)
            surplusLabel.setVisible(false);
        else
            surplusLabel.updateSurplus(number);
    }

    public int getCardNumber() {
        return hand.size();
    }

    public void onMouseClicked() {
        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            CardView cardView = handView.get(i);
            if (cardView.getLayoutY() < DELTA_HEIGHT) {
                cardView.setLayoutY(DELTA_HEIGHT);
                selectedCardViews.remove(cardView);
            } else {
                cardView.setLayoutY(0);
                selectedCardViews.add(cardView);
            }
        }
        startIndex = -1;
        endIndex = -1;
    }

    class MouseClickedHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (event.getY() < DELTA_HEIGHT || event.getY() > DELTA_HEIGHT + CardView.HEIGHT)
                return;
            EventType<MouseEvent> eventType = (EventType<MouseEvent>) event.getEventType();
            if (eventType == MouseEvent.MOUSE_PRESSED) {
                startIndex = (int) (event.getX() / DELTA_WIDTH);
                if (startIndex >= handView.size()) {
                    startIndex = handView.size() - 1;
                }
            } else if (eventType == MouseEvent.MOUSE_RELEASED) {
                if (startIndex == -1)
                    return;
                endIndex = (int) (event.getX() / DELTA_WIDTH);
                if (endIndex >= handView.size()) {
                    endIndex = handView.size() - 1;
                }
                onMouseClicked();
            }
        }
    }
}
