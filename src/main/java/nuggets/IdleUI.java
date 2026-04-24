package nuggets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class IdleUI{
    private static JFrame Mframe = new JFrame("Tracker");
    private static JFrame Gframe = new JFrame("Game");
    private static JFrame Sframe = new JFrame("Shop");
    private static JFrame popFrame = new JFrame("Warning"); // frame for the option panel 
    private static JLabel UIcharCount = new JLabel("",SwingConstants.CENTER);
    private static JLabel UItimer = new JLabel("",SwingConstants.CENTER);
    private static JLabel UIUncCoin = new JLabel("",SwingConstants.CENTER); // curency earned from typing
    private static JLabel UIGUncCoin = new JLabel("",SwingConstants.CENTER);
    private static JPanel panel = new JPanel();
    private static ImageIcon icon = new ImageIcon("/Users/yifancai/Documents/IdleAger/image.png");
    private static JLabel animation = new JLabel(icon);
    private static JButton sButton = new JButton("Unpause");
    private static JButton gButton = new JButton("Switch Game State");
    private static JButton tButton = new JButton("Switch Track State");
    private static JButton EnterSButton = new JButton("Open Shop");
    private static JButton ExitSButton = new JButton("Exit Shop");
    private static JButton up1Button = new JButton("Decrease Quota");
    private static JButton up2Button = new JButton("More Unc Coin per quota");
    private static JButton up3Button = new JButton("Passive Unc Coin Generation");

    private static int aniFrame = -240; // amount of pixels to shift animation by 
    private static final int tickAmount = 16; // framerate
    private static int frameCounter = 0;
    

    private static String formatTime(int s){
        int seconds = s%60;
        int minutes = s/60;
        int hours = s/3600;
        return String.valueOf(hours) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
    }

    private static void updateTrackerUI(){
        // if(!isLazy() && !paused){
            UIcharCount.setText("Letters Typed: " + Integer.toString(UIdata.charCount));
            UItimer.setText("Time Left: " + formatTime(UIdata.focusTimer/1000));
            UIUncCoin.setText("Unc Coin Earned: " + Double.toString(UIdata.charCurrency));
            sButton.setText((IdleAger.getPause() ? "Pause":"Unpause"));
        // }
    }

    private static void updateGameUI(){
        animation.setBounds(aniFrame * (frameCounter % 4),-10,960,240);
        UIGUncCoin.setText("Unc Coin Earned: " + Double.toString(UIdata.charCurrency));
        frameCounter+=1;
    }

    private static Timer aniTimer = new Timer (300, e -> updateGameUI());
    private static Timer timer = new Timer(tickAmount, e-> updateTrackerUI());


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

    private static void stateMachine(boolean inGame, boolean inShop){ // refactor
        Mframe.setVisible(!inGame);
        Gframe.setVisible(inGame);
        Sframe.setVisible(inShop);
    }

    public static void init(){ // initializes all the UI
        GlobalKeyListenerExample.init();
        // set an actionlistener in main, add it here

        JLabel up1cost = new JLabel("Up 1 Cost: " + Double.toString(IdleShop.getItemCost(0)));
        JLabel up2cost = new JLabel("Up 2 Cost: " + Double.toString(IdleShop.getItemCost(1)));
        JLabel up3cost = new JLabel("Up 3 Cost: " + Double.toString(IdleShop.getItemCost(2)));
        sButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                IdleAger.setPause();
            }
        });
        gButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                stateMachine(true,false);
            }
        });
        tButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                stateMachine(false, false);
            }
        });

        EnterSButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                stateMachine(false, true);
                up1cost.setText("Up 1 Cost: " + Double.toString(IdleShop.getItemCost(0))); 
                up2cost.setText("Up 2 Cost: " + Double.toString(IdleShop.getItemCost(1))); 
                up3cost.setText("Up 3 Cost: " + Double.toString(IdleShop.getItemCost(2))); 
                

            }
        });
        ExitSButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                stateMachine(false, false);
            }
        });

        up1Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                // trigger upgrade to be applied
                IdleShop.setCharReq();
                // up1Button.setVisible(false);
            }
        }); 
        up2Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                IdleShop.setUCearned();
                // up2Button.setVisible(false);
            }
        }); 
        up3Button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                IdleShop.setUCpassive();
                // up3Button.setVisible(false);
            }
        }); 

        aniTimer.start();

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int length = (int) size.getHeight();
        
        Mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mframe.setSize(240,240);
        Mframe.setLocation(width-240, length-240);
        Mframe.setAlwaysOnTop(true);
        Mframe.setResizable(false);

        Sframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Sframe.setSize(240,240);
        Sframe.setLocation(width-240, length-240);
        Sframe.setAlwaysOnTop(true);
        Sframe.setResizable(false); 

        JPanel SPanel = new JPanel();
        SPanel.setLayout(new BoxLayout(SPanel,BoxLayout.Y_AXIS));
        SPanel.add(ExitSButton);
        SPanel.add(UIUncCoin);
        SPanel.add(up1cost);
        SPanel.add(up1Button);
        SPanel.add(up2cost);
        SPanel.add(up2Button);
        SPanel.add(up3cost);
        SPanel.add(up3Button);
        Sframe.add(SPanel);

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
        
        // button panel for buttons (start session, end session, switch to game)
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.Y_AXIS));
        bPanel.add(sButton);
        bPanel.add(EnterSButton);
        bPanel.add(gButton);
        bPanel.setSize(240,240);
        panel.setLayout(new FlowLayout());
        panel.add(UIcharCount);
        panel.add(UItimer);
        panel.add(UIUncCoin);
        panel.add(bPanel);
        // Mframe.setLayout();
        Mframe.add(panel);
        // Mframe.pack();
        Mframe.setVisible(true);
        // Gframe.setVisible(true);
        System.out.println("done");
        timer.start();
    }


}