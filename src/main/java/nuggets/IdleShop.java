package nuggets;

// keep track of upgrades player has 

import java.util.ArrayList;

// implements the button interface & dropdown menu
public class IdleShop{

    private static ArrayList<IdleItem> items = new ArrayList<IdleItem>();

    private static IdleItem upgrade1 = new IdleItem(0, 1.0, "", 0, 1, 1.15, 1); 
    private static IdleItem upgrade2 = new IdleItem(1, 2.0,"", 0, 1, 1.5, 1);
    private static IdleItem upgrade3 = new IdleItem(2, 3.0, "", 0, 1, 1.4, 5000);

    public static void initShop(){
        items.add(upgrade1);
        items.add(upgrade2);
        items.add(upgrade3);
    }

    public static double getItemCost(int i){
        return items.get(i).itemPrice;
    }
    public static int getItemProg(int i){
        return items.get(i).itemProgress;
    }

    public static void setCharReq(){ // lowers the requirement 
        if(UIdata.charCurrency >= upgrade1.itemPrice){
            UIdata.charCurrency -= upgrade1.itemPrice;
            upgrade1.itemPrice *= upgrade1.itemPriceRate;
            IdleItem.charReq -= upgrade1.itemInit;
            upgrade1.itemProgress++;
        }
    }

    public static void setUCearned(){ // increase how much UC earned per quota
        if(UIdata.charCurrency >= upgrade2.itemPrice){
            UIdata.charCurrency -= upgrade2.itemPrice;
            upgrade2.itemPrice *= upgrade2.itemPriceRate;
            IdleItem.UCearned += upgrade2.itemInit;
            upgrade2.itemProgress++;
        }
    }

    public static void setUCpassive(){ // decreases time interval between earning new UC
        if(UIdata.charCurrency >= upgrade3.itemPrice){
            UIdata.charCurrency -= upgrade3.itemPrice;
            upgrade3.itemPrice *= upgrade3.itemPriceRate;
            IdleItem.UCpassive -= upgrade3.itemInit;
            upgrade3.itemProgress++;
        }
    }


}