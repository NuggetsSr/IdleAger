package nuggets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class IdleUI{
    private static JFrame Mframe = new JFrame("Tracker");
    private static JFrame Gframe = new JFrame("Game");
    private static JFrame popFrame = new JFrame("Warning"); // frame for the option panel 
    private static JLabel UIcharCount = new JLabel("",SwingConstants.CENTER);
    private static JLabel UItimer = new JLabel("",SwingConstants.CENTER);
    private static JLabel UIUncCoin = new JLabel("",SwingConstants.CENTER); // curency earned from typing
    private static JLabel UIGUncCoin = new JLabel("",SwingConstants.CENTER);
    private static JPanel panel = new JPanel();
    private static ImageIcon icon = new ImageIcon("/Users/yifancai/Documents/IdleAger/image.png");
    private static JLabel animation = new JLabel(icon);
    private static JButton sButton = new JButton("Start Session");
    private static JButton eButton = new JButton("End Session");
    private static JButton gButton = new JButton("Switch Game State");
    private static JButton tButton = new JButton("Switch Track State");

    private static UIdata displayData = new UIdata();

    private static int aniFrame = -240; // amount of pixels to shift animation by 
    private static final int tickAmount = 16; // framerate
    private static int frameCounter = 0;
    

    private static String formatTime(int s){
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

    private static Timer aniTimer = new Timer (300, e -> updateGameUI(displayData));

    public static int warnPop(){
        return JOptionPane.showOptionDialog(
            popFrame, 
            "GET BACK TO WORK",
            "WARNING",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null, 
            new Object[]{"OK"}, 
            "OK"
        );
    }

    private static void sGame(){
        Mframe.setVisible(false);
        Gframe.setVisible(true);
    }
    private static void eGame(){
        Mframe.setVisible(true);
        Gframe.setVisible(false);
    }

    public static void main(String[] args) {
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
            }
        });
        tButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                eGame();
            }
        });
        
        aniTimer.start();

    }

}