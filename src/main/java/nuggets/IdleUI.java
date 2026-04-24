package nuggets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class IdleUI{
    private static final JFrame Mframe = new JFrame("Tracker");
    private static JFrame Gframe = new JFrame("Game");
    private static JFrame Sframe = new JFrame("Shop");
    private static JFrame popFrame = new JFrame("Warning"); // frame for the option panel 
    private static JLabel UIcharCount = new JLabel("",SwingConstants.CENTER);
    private static JLabel UItimer = new JLabel("",SwingConstants.CENTER);
    // private static JLabel UIGtimer = new JLabel("", SwingConstants.CENTER)

    private static JLabel UIUncCoin = new JLabel("",SwingConstants.CENTER); // curency earned from typing
    private static JLabel quota = new JLabel("", SwingConstants.CENTER);
    private static JLabel UCperQ = new JLabel("", SwingConstants.CENTER);
    private static JLabel timeTillPassive = new JLabel("",SwingConstants.CENTER);

    private static JTextField UIGtimer = new JTextField();
    private static JTextField UIGUncCoin = new JTextField("");
    private static JTextField Gquota = new JTextField("");

    private static JLabel up1cost = new JLabel("Up 1 Cost: " + Double.toString(IdleShop.getItemCost(0)));
    private static JLabel up2cost = new JLabel("Up 2 Cost: " + Double.toString(IdleShop.getItemCost(1)));
    private static JLabel up3cost = new JLabel("Up 3 Cost: " + Double.toString(IdleShop.getItemCost(2)));
    private static JPanel MPanel = new JPanel();
    private static ImageIcon icon = new ImageIcon("/Users/yifancai/Documents/IdleAger/image.png");
    private static JLabel animation = new JLabel(icon);
    private static JButton sButton = new JButton("Unpause");
    private static JButton EnterSButton = new JButton("Open Shop");
    private static JButton ExitSButton = new JButton("Exit Shop");
    private static JButton up1Button = new JButton("1. Decrease Quota");
    private static JButton up2Button = new JButton("2. More Unc Coin per quota");
    private static JButton up3Button = new JButton("3. Passive Unc Coin Generation");

    private static int aniFrame = -240; // amount of pixels to shift animation by 
    private static final int tickAmount = 16; // framerate
    private static int frameCounter = 0;
    private static boolean inFocus = true;
    

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
            UIUncCoin.setText("Unc Coin Total: " + Double.toString(UIdata.charCurrency));
            UCperQ.setText("Unc Coin Earned Per Quota: " + Integer.toString(IdleItem.UCearned));
            timeTillPassive.setText("Passive Income Interval: " +formatTime(IdleItem.UCpassive/1000));
            sButton.setText((IdleAger.getPause() ? "Unpause":"Pause"));
            quota.setText("Quota: " + Integer.toString(IdleAger.getCurrQuota()) + " / " + Integer.toString(IdleItem.charReq));
            Gquota.setText("Quota: " + Integer.toString(IdleAger.getCurrQuota()) + " / " + Integer.toString(IdleItem.charReq));
            up1cost.setText("Up 1 Cost: " + String.format("%.2f", IdleShop.getItemCost(0)) + " Owned: " + Integer.toString(IdleShop.getItemProg(0))); 
            up2cost.setText("Up 2 Cost: " + String.format("%.2f", IdleShop.getItemCost(1)) + " Owned: " + Integer.toString(IdleShop.getItemProg(1))); 
            up3cost.setText("Up 3 Cost: " + String.format("%.2f", IdleShop.getItemCost(2)) + " Owned: " + Integer.toString(IdleShop.getItemProg(2))); 
        // }
    }

    private static void updateGameUI(){
        animation.setBounds(aniFrame * (frameCounter % 4),-10,960,240);
        UIGtimer.setText("Time Left: " + formatTime(UIdata.focusTimer/1000));
        UIGUncCoin.setText("Unc Coin Earned: " + Double.toString(UIdata.charCurrency));
        frameCounter+=1;
    }

    private static final Timer aniTimer = new Timer (300, e -> updateGameUI());
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
        // Init GlobalKeyListener

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) size.getWidth();
        int length = (int) size.getHeight();
        // get dimension of screen

        //main frame
        Mframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Mframe.setSize(240,240);
        Mframe.setLocation(width-240, length-240);
        Mframe.setAlwaysOnTop(true);
        Mframe.setResizable(false);

        //shop frame
        Sframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Sframe.setSize(240,240);
        Sframe.setLocation(width-240, length-240);
        Sframe.setAlwaysOnTop(true);
        Sframe.setResizable(false); 

        //game frame
        Gframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gframe.setSize(240,240);
        Gframe.setLocation(width-240, length-240);
        Gframe.setAlwaysOnTop(true);
        Gframe.setResizable(false);

        Mframe.addWindowFocusListener(new WindowFocusListener(){
            @Override
            public void windowLostFocus(WindowEvent e){
                if(inFocus){
                    stateMachine(true, false);
                }
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                stateMachine(false, false);
            }
        });

         Gframe.addWindowFocusListener(new WindowFocusListener(){
            @Override
            public void windowLostFocus(WindowEvent e){
                
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                stateMachine(false, false);
            }
        });

        sButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                IdleAger.setPause();
            }
        });

        EnterSButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inFocus = false;
                stateMachine(false, true);
                

            }
        });
        ExitSButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                inFocus = true;
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
        
        //layers for Game animation and buttons
        JLayeredPane Glayers = new JLayeredPane();
        JPanel GPanel = new JPanel();
        UIGtimer.setEditable(false);
        UIGtimer.setBorder(null);
        UIGtimer.setOpaque(false);
        Gquota.setEditable(false);
        Gquota.setBorder(null);
        Gquota.setOpaque(false);
        UIGUncCoin.setEditable(false);
        UIGUncCoin.setBorder(null);
        UIGUncCoin.setOpaque(false);
        Glayers.setLayout(new FlowLayout());
        GPanel.setLayout(new BorderLayout());
        animation.setPreferredSize(new Dimension(240,240));
        UIGtimer.setForeground(Color.WHITE);
        Gquota.setForeground(Color.WHITE);
        UIGUncCoin.setForeground(Color.WHITE);
        GPanel.add(UIGtimer, BorderLayout.NORTH);
        GPanel.add(Gquota, BorderLayout.CENTER);
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

        // button panel for buttons (start session, end session, switch to game)
        JPanel BPanel = new JPanel();
        BPanel.setLayout(new BoxLayout(BPanel, BoxLayout.Y_AXIS));
        BPanel.add(sButton);
        BPanel.add(EnterSButton);
        BPanel.setSize(240,240);
        MPanel.setLayout(new FlowLayout());
        MPanel.add(UIcharCount);
        MPanel.add(quota);
        MPanel.add(UItimer);
        MPanel.add(UIUncCoin);
        MPanel.add(UCperQ);
        MPanel.add(timeTillPassive);
        MPanel.add(BPanel);
        // Mframe.setLayout();
        Mframe.add(MPanel);
        // Mframe.pack();
        Mframe.setVisible(true);
        // Gframe.setVisible(true);
        System.out.println("done");
        timer.start();
    }

    public static Timer getTimer() {
        return timer;
    }


}