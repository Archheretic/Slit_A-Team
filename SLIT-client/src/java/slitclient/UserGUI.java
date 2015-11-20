/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slitclient;

import notification.Notification;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;



/**
 *
 * @author Arild
 */
public class UserGUI {

    private JFrame frame;
    private TabForside tabForside;
    private TabModuloversikt tabModuloversikt;
    private TabFagstoff tabFagstoff;
    private HashMap<String, String> userInfo;
    private static final String LOGO_PATH = "src/img/slit_logo.png";
    private JButton notificationButton;
    // Its very important that the below field do not go out of scope.
    private Notification notification;
    
    public ImageIcon loadLogo() {
        ImageIcon icon = null;
      try {
         BufferedImage img = ImageIO.read(new File(LOGO_PATH));
         icon = new ImageIcon(img);
      } catch (IOException e) {
         e.printStackTrace();
      }
      return icon;
    }

    /**
     * Constructor for MakeGUI. Oppretter objekter av alle tab-klassene, og
     * kaller makeFrame()
     */
    public UserGUI(HashMap<String, String> userInfo) {
        
        this.userInfo = userInfo;
        tabForside = new TabForside();
        //create the moduloversikt-tab for the given userType
        tabModuloversikt = new TabModuloversikt(userInfo, frame);
        tabFagstoff = new TabFagstoff(userInfo, frame);
        makeFrame();
    }
    public String getUserInfo(String key)   {
        return userInfo.get(key);
    }
    
    public class TabPane extends JTabbedPane {
        
        public TabPane() {
            
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1024, 670);
        }
    }
    
    /**
     * Lager vinduet. Vinduet har GridBagLayout (enn så lenge i hvert fall).
     * Kaller makeCommon(). 
     * Kaller også makeTabs().
     */
    public void makeFrame() {
        String fName = getUserInfo("fName");
        String lName = getUserInfo("lName");
        frame = new JFrame("SLIT - " + fName + " " + lName);
        // Its very important that line under does not get uncommented or implemented anew.
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) frame.getContentPane();
        GridBagLayout gblContent = new GridBagLayout();
        contentPane.setLayout(gblContent);
       
        notificationButton = new JButton("Varsler");
        JPanel commonContent =  makeCommon();
        GridBagConstraints gbcCommon = new GridBagConstraints();
        gbcCommon.gridx = 0;
        gbcCommon.gridy = 0;
        gblContent.setConstraints(commonContent, gbcCommon);
        
        JTabbedPane tabbedPane = makeTabs();
        GridBagConstraints gbcTab = new GridBagConstraints();
        gbcTab.gridx = 0;
        gbcTab.gridy = 1;
        gblContent.setConstraints(tabbedPane, gbcTab);

        contentPane.add(commonContent);
        contentPane.add(tabbedPane);
        
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        
        notification = new Notification(frame, userInfo, notificationButton);
    }
    
    /**
     * Lager elementene som er felles for alle faner 
     * (kun topplinja med logo og navn på bruker foreløpig).
     * Har BorderLayout, skal endres. Layout er foreløpig, funker ikke som den
     * bør gjøre nå.
     * @return JPanel content panelet med innholdet som er felles for alle faner
    */
    public JPanel makeCommon() {

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(400, 25));
        
        JButton menuButton = new JButton("Meny"){
            @Override
            public int getHeight() {
                return 25;
            }
        };
        content.add(menuButton, BorderLayout.LINE_START);

        JLabel logoLabel = new JLabel(loadLogo());
        content.add(logoLabel, BorderLayout.CENTER);

        String fName = getUserInfo("fName");
        String lName = getUserInfo("lName");

        JButton nameButton = new JButton(fName + " " + lName){
            @Override
            public int getHeight() {
                return 25;
            }
        };

        //content.add(nameButton, BorderLayout.EAST);
        JPanel eastContent = new JPanel();
        eastContent.add(nameButton, BorderLayout.WEST);
        eastContent.add(notificationButton, BorderLayout.LINE_END);
        content.add(eastContent, BorderLayout.EAST);
        
        notificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notification.createNotification();
            }
        });

        return content;
        
    }

 
    /**
     * Lager faner. Kaller de respektive klassene for å lage innholdet, lager 
     * fanene med navn (og innhold) og legger de til tabbedPane.
     * @return JTabbedPane tabbedPane returnerer linja med fanene
     */  
    public JTabbedPane makeTabs()  {
        JTabbedPane tabbedPane = new TabPane();
       
        JComponent tab1 = tabForside.makeForsideTab();
        tabbedPane.addTab("Forside", null, tab1, null);
        
        JComponent tab2 = tabModuloversikt.makeModuloversiktTab();
        tabbedPane.addTab("Moduloversikt", null, tab2, null);
        
        JComponent tab3 = tabFagstoff.makeFagstoff();
        tabbedPane.addTab("Fagstoff", null, tab3, null);
        return tabbedPane;
    }
        
}