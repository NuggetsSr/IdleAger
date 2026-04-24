package nuggets;

public class IdleItem{
    public int itemInd; // index of item
    public int itemPrice; // price of item
    public int itemProgress; // how many times has this item been 
    public int itemProgRate; // how fast the item scales (Linear)
    public int itemPriceRate; // rate of price scale (exoponential)
    public int itemInit; // starting value of the item, example: item gives itemInit amount of money generation when bought
    public String itemName; // name of item

    public static int charReq = 100;
    public static int UCearned = 1;
    public static int UCpassive = 300000;

    public IdleItem(int index, int price, String name, int prog, int progRate, int priceRate, int init){
        itemInd = index;
        itemPrice = price;
        itemName = name;
        itemProgress = prog;
        itemProgRate = progRate;
        itemPriceRate = priceRate;
        itemInit = init;
    }
}