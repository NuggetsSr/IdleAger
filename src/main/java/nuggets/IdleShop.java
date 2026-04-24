package nuggets;

// keep track of upgrades player has 

import java.util.ArrayList;

import javax.swing.JFrame;

// implements the button interface & dropdown menu
public class IdleShop{

    private static JFrame Sframe = new JFrame(); // main shop UI
    private static ArrayList<IdleItem> items = new ArrayList<IdleItem>();

    private static IdleItem upgrade1 = new IdleItem();
    private static IdleItem upgrade2 = new IdleItem();
    private static IdleItem upgrade3 = new IdleItem();


    public void IdleShop(){
        
    }

    private void createItem(int index, int price, String name, IdleItem item){
        item.itemInd = index;
        item.itemPrice = price;
        item.itemName = name;
        item.itemIsBought = false;
        items.add(item);
    }

    private void applyUpgrade(){
        
    }

    public static void main(String args[]){
        
    }
}