/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package nuggets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
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
    private static IdleUI UI = new IdleUI();
    
    
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
        int result = UI.warnPop();
        
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

    private static void startS(){
        paused = false;
    }

    private static void endS(){
        paused = true;
    }


    private static void resetTimer(){
        displayData.focusTimer = maxFdur;
    }

    private  static void tickTimer(){
        displayData.focusTimer -= tickAmount;
        displayData.focusTimer = Math.max(0, displayData.focusTimer);
    }

    public static void main(String[] args) {
        
        //1. Create the frame.
        // frame.setUndecorated(true);
        GlobalKeyListenerExample.init();
        displayData.focusTimer = maxFdur;
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int length = (int) size.getHeight();
        //2. Optional: What happens when the frame closes?
        Mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mframe.setSize(240,240);
        Mframe.setLocation(width-240, length-240);
        Mframe.setAlwaysOnTop(true);
        Mframe.setResizable(false);

        animation.setBounds(0,-10,960,240);
        tButton.setBounds(100, 50, 200, 200);
        
        //layers for Game animation and buttons
        JLayeredPane Glayers = new JLayeredPane();
        JPanel GPanel = new JPanel();
        Glayers.setLayout(new FlowLayout());
        GPanel.setLayout(new BorderLayout());
        GPanel.add(tButton, BorderLayout.CENTER);
        UIGUncCoin.setForeground(new Color(255,255,255));
        GPanel.add(UIGUncCoin, BorderLayout.SOUTH);
        GPanel.setOpaque(false);
        // Glayers.add(tButton, 2);
        // Glayers.add(UIGcharCurrency, 3);
        Glayers.add(GPanel,2);
        Glayers.add(animation,1);
        // Glayers.moveToFront(tButton);
        Gframe.add(Glayers);
        // Gframe.add(UIGcharCurrency);
        // Gframe.add(GPanel);

        Gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gframe.setSize(240,240);
        Gframe.setLocation(width-240, length-240);
        Gframe.setAlwaysOnTop(true);
        Gframe.setResizable(false);

        popFrame.setSize(10000, 10000);

        sButton.setSize(new Dimension(100,50));
        eButton.setSize(new Dimension(100,50));
        //   
        
        
        UIcharCount.setText("0");
        // button panel for buttons (start session, end session, switch to game)
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BorderLayout());
        bPanel.add(sButton, BorderLayout.NORTH);
        bPanel.add(eButton, BorderLayout.CENTER);
        bPanel.add(gButton, BorderLayout.SOUTH);
        bPanel.setSize(50,240);
        panel.setLayout(new FlowLayout());
        panel.add(UIcharCount);
        panel.add(UItimer);
        panel.add(UIUncCoin);
        panel.add(bPanel);
        Mframe.add(panel);
        
        Mframe.setVisible(true);
        timer.start();
    }
}