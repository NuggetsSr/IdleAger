/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package nuggets;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author yifancai
 */
public class IdleAger {
    static JFrame frame = new JFrame("Idle Ager");;
    static JLabel UIcharCount = new JLabel("");
    static JLabel UItimer = new JLabel("");
    static JPanel panel = new JPanel();
    static UIdata displayData = new UIdata();
    static final int tickAmount = 16; // framerate
    static final int maxFdur = 1000; // focus duration
    
    static Timer timer = new Timer(tickAmount, e-> update());

    public static void updateUI(UIdata data){
        UIcharCount.setText(Integer.toString(data.charCount));
        UItimer.setText(Integer.toString(data.focusTimer));
    }

    public static void update(){
        updateUI((displayData));
        tickTimer();
        if(isLazy()){
            System.out.println("we're so cooked");
            warnUser();
        }
    }

    public static void updateKeystroke(){
        displayData.charCount++;
        resetTimer();
    }

    public static void warnUser(){
        int result = JOptionPane.showOptionDialog(
            frame, 
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

    private static boolean isLazy(){
        return displayData.focusTimer <= 0;
    }

    public static void resetTimer(){
        displayData.focusTimer = maxFdur;
    }

    public static void tickTimer(){
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(240,240);
        frame.setLocation(width-240, length-240);
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);

        //3. Create components and put them in the frame.
        //...create emptyLabel...
        
        UIcharCount.setText("0");
        UIcharCount.setSize(50, 50);
        UItimer.setLocation(100, 100);
        panel.add(UIcharCount);
        panel.add(UItimer);
        frame.add(panel);

        //4. Size the frame.

        //5. Show it.
        frame.setVisible(true);
        // while (true) { 
        //     GlobalKeyListenerExample.update();
        //     System.out.println("Hello World");
        // }
        timer.start();
    }
}