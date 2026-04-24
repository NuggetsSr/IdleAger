/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package nuggets;
import javax.swing.Timer;
/**
 *
 * @author yifancai
 */
public class IdleAger {
    private static boolean paused = true;
    private static boolean inGame = false;
    private static final int tickAmount = 16; // framerate
    private static final int maxFdur = 60016; // focus duration
    private static int moneyCounter = 1;
    private static int quotaCount = 0;

    
    private static Timer timer = new Timer(tickAmount, e-> update());
    private static Timer UCtimer = new Timer(IdleItem.UCpassive, e-> addUC()); // timer for passive gain
    //returns a string given time in minutes and seconds
    
    private static void update(){
        // updateTrackerUI(displayData);
        convertUC();
        if(!paused){
            tickTimer();
        }
        if(isLazy()){
            System.out.println("we're so cooked");
            warnUser();
        }
    }

    public static void updateKeystroke(){
        if(!isLazy() && !paused){ // fix: keystrokes made when warning is up or session is paused will not count towards progress 
            UIdata.charCount++;
            quotaCount++;
            resetTimer();
        }
    }

    private static void warnUser(){ // need rewrite
        int result = IdleUI.warnPop();
        
        // call popup method 
        
        if(result == 0){
            resetTimer();
        }
        // JOptionPane.showConfirmDialog(frame, "hello world");
    }

    private static void convertUC(){
        if(UIdata.charCount > moneyCounter * IdleItem.charReq){
            moneyCounter += 1;
            UIdata.charCurrency += IdleItem.UCearned;
        }
    }

    private static void addUC(){
        UIdata.charCurrency += 1; // might add as future upgrade
    }

    private static boolean isLazy(){
        return UIdata.focusTimer <= 0;
    }

    public static void setPause(){
        paused = !paused;
    }

    public static boolean getPause(){
        return paused;
    }

    private static void resetTimer(){
        UIdata.focusTimer = maxFdur;
    }

    private  static void tickTimer(){
        UIdata.focusTimer -= tickAmount;
        UIdata.focusTimer = Math.max(0, UIdata.focusTimer);
    }

    public static int getCurrQuota(){
        if(quotaCount > IdleItem.charReq){
            quotaCount = 0;
        }
        return quotaCount;
    }

    public static void main(String[] args) {
        UIdata.focusTimer = maxFdur;
        IdleShop.initShop();
        IdleUI.init();
        // IdleShop.printItems();
        timer.start();
        System.out.println();

    }
}