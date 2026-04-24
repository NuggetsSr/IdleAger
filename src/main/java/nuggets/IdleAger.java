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

    //returns a string given time in minutes and seconds
    private static UIdata displayData = new UIdata();    
    
    private static void update(){
        // updateTrackerUI(displayData);
        convertCurrency(displayData);
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
            displayData.charCount++;
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

    private static void convertCurrency(UIdata data){
        if(data.charCount > moneyCounter*100){
            moneyCounter+=1;
            data.charCurrency++;
        }
    }

    private static boolean isLazy(){
        return displayData.focusTimer <= 0;
    }

    public static void setPause(boolean p){
        paused = p;
    }

    private static void resetTimer(){
        displayData.focusTimer = maxFdur;
    }

    private  static void tickTimer(){
        displayData.focusTimer -= tickAmount;
        displayData.focusTimer = Math.max(0, displayData.focusTimer);
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        displayData.focusTimer = maxFdur;
        IdleUI.init();
        timer.start();
    }
}