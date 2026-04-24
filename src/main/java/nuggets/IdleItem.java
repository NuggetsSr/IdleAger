package nuggets;

public class IdleItem{
    public static int itemInd; // index of item
    public static double itemPrice; // price of item
    public static int itemProgress; // how many times has this item been 
    public static double itemProgRate; // how fast the item scales (Linear)
    public static double itemPriceRate; // rate of price scale (exoponential)
    public static int itemInit; // starting value of the item, example: item gives itemInit amount of money generation when bought
    public static String itemName; // name of item

    public static int charReq = 100;
    public static int UCearned = 1;
    public static int UCpassive = 300000;

    public IdleItem(int index, double price, String name, int prog, double progRate, double priceRate, int init){
        itemInd = index;
        itemPrice = price;
        itemName = name;
        itemProgress = prog;
        itemProgRate = progRate;
        itemPriceRate = priceRate;
        itemInit = init;
    }
}