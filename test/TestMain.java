import com.watch0ut.landlord.command.AbstractCommand;
import com.watch0ut.landlord.command.concrete.TextCommand;
import com.watch0ut.landlord.object.Dealer;
import com.watch0ut.landlord.object.Player;
import com.watch0ut.landlord.object.Table;

public class TestMain {

    public static void main(String[] args) {
	// write your code here
        Table tab = new Table();
        tab.setPosition(new Player(0), Table.POSITION.Top);
        tab.setPosition(new Player(1), Table.POSITION.Right);
        tab.setPosition(new Player(2), Table.POSITION.Bottom);
        tab.setPosition(new Player(3), Table.POSITION.Left);

        Dealer dealer = new Dealer();
        dealer.setTable(tab);

        dealer.init();
        dealer.shuffle();
        dealer.deal();
        //dealer.tribute();

//        Card card = new Card(1, 1);
//        List<Card> list = new ArrayList<Card>();
//        list.add(card);
//        cardtype type = new One(list);
//        op.play(type);

//        AbstractCommand cmd = new TextCommand();
//
//        String name = cmd.getClass().getName();
//        int start = name.lastIndexOf('.') + 1;
//        int end = name.length() - 7; //"Command"
//        name = name.substring(start, end);
//
//
//        System.out.println(name);
    }
}
