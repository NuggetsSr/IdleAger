package nuggets;

public class IdleItem{
    public int itemInd; // index of item
    public int itemPrice; // price of item
    public int itemProgress; // how many times has this item been 
    public int itemProgRate; // how fast the item scales (Linear)
    public int itemPriceRate; // rate of price scale (exoponential)
    public int itemInit; // starting value of the item, example: item gives itemInit amount of money generation when bought
    public String itemName; // name of item
    public boolean itemIsBought; // is the item brought or not
}