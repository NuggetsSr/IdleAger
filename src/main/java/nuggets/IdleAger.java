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
            moneyCounter += IdleItem.UCearned;
            UIdata.charCurrency++;
        }
    }

    private static void addUC(){
        UIdata.charCurrency += 1; // might add as future upgrade
    }

    private static boolean isLazy(){
        return UIdata.focusTimer <= 0;
    }

    public static void setPause(boolean p){
        paused = p;
    }

    private static void resetTimer(){
        UIdata.focusTimer = maxFdur;
    }

    private  static void tickTimer(){
        UIdata.focusTimer -= tickAmount;
        UIdata.focusTimer = Math.max(0, UIdata.focusTimer);
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        UIdata.focusTimer = maxFdur;
        IdleUI.init();
        timer.start();
    }
}