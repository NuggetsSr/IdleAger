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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
/**
 *
 * @author yifancai
 */
public class IdleAger {
    static JFrame Mframe = new JFrame("Tracker");
    static JFrame Gframe = new JFrame("Game");
    static JFrame popFrame = new JFrame("Warning"); // frame for the option panel 
    static JLabel UIcharCount = new JLabel("",SwingConstants.CENTER);
    static JLabel UItimer = new JLabel("",SwingConstants.CENTER);
    static JLabel UIUncCoin = new JLabel("",SwingConstants.CENTER); // curency earned from typing
    static JLabel UIGUncCoin = new JLabel("",SwingConstants.CENTER);
    static JPanel panel = new JPanel();
    static ImageIcon icon = new ImageIcon("/Users/yifancai/Documents/IdleAger/image.png");
    static JLabel animation = new JLabel(icon);

    static JButton sButton = new JButton("Start Session");
    static JButton eButton = new JButton("End Session");
    static JButton gButton = new JButton("Switch Game State");
    static JButton tButton = new JButton("Switch Track State");

    static UIdata displayData = new UIdata();
    static boolean paused = true;
    static boolean inGame = false;
    static final int tickAmount = 16; // framerate
    static final int maxFdur = 60016; // focus duration
    static int aniFrame = -240; // amount of pixels to shift animation by 
    static int frameCounter = 0;
    static int moneyCounter = 1;
    
    static Timer timer = new Timer(tickAmount, e-> update());
    static Timer aniTimer = new Timer (300, e -> updateGameUI(displayData));

    //returns a string given time in minutes and seconds
    public static String formatTime(int s){
        int seconds = s%60;
        int minutes = s/60;
        int hours = s/3600;
        return String.valueOf(hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private static void updateTrackerUI(UIdata data){
        // if(!isLazy() && !paused){
            UIcharCount.setText("Letters Typed: " + Integer.toString(data.charCount));
            UItimer.setText("Time Left: " + formatTime(data.focusTimer/1000));
            UIUncCoin.setText("Unc Coin Earned: " + Integer.toString(data.charCurrency));
        // }
    }

    private static void updateGameUI(UIdata data){
        animation.setBounds(aniFrame * (frameCounter % 4),-10,960,240);
        UIGUncCoin.setText("Unc Coin Earned: " + Integer.toString(data.charCurrency));
        frameCounter+=1;
    }

    private static void update(){
        updateTrackerUI(displayData);
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
        int result = JOptionPane.showOptionDialog(
            popFrame, 
            "GET BACK TO WORK",
            "WARNING",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null, 
            new Object[]{"OK"}, 
            "OK"
        );
        
        
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

    private static void sGame(){
        Mframe.setVisible(false);
        Gframe.setVisible(true);
    }
    private static void eGame(){
        Mframe.setVisible(true);
        Gframe.setVisible(false);
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
        sButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startS();
            }
        });

        eButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                endS();
            }
        });
        gButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                sGame();
                aniTimer.start();
            }
        });
        tButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eGame();
            }
        });
        
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