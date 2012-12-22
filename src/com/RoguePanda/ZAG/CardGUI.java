/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author Oa10712
 */
class CardGUI extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Create Variables">
    int leftKey, rightKey, jumpKey, dropKey;
    BufferedImage bim;
    Graphics blank;
    ImageIcon bgicon, bgimage, logo;
    Image currentWeapon, menuBG, optionsBG, gameBG, test, pi, spaceFiller, hudf,
            hudb, scaleimage;
    JPanel frame, mainDisplay, menuCard, optionsCard, gameCard, gameHUD,
            menuDisplay, optionsDisplay, gameArea, gameDisplayHolder, htStats,
            pauseMenu;
    JLabel backLabel, spaceFillerl, hudfp, hudbp, gameBack, menuBack,
            optionsBack, testl, debugText, playerSprite, pauseText;
    JScrollPane gameDisplay, gameBackDisplay;
    GameCanvas gameCanvas;
    Game currentGame;
    JProgressBar healthBar, hungerBar, thirstBar;
    JLabel[] gamebuttons, menubuttons, optionsbuttons;
    int changeKey = 0;
    Color transparent;
    int vnum = 2;
    int hnum = 2;
    boolean paused = true;
    boolean debug = false;
    public final Set<Integer> pressed = new HashSet<>();
    //</editor-fold>

    CardGUI() {
        setUp();
    }

    void setUp() {
        //<editor-fold defaultstate="collapsed" desc="JFrame setup">
        setFont(new Font("Bitstream Vera Sans Mono", 0, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        currentGame = new Game(this);
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Image Setup">
        try {
            try {
                if (currentGame.player.getItems().get(0).item.getSprite() == null) {
                    currentWeapon = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/noWeapon.png"));
                } else {
                    currentWeapon = currentGame.player.getItems().get(0).item.getSprite();
                }
            } catch (Exception ex) {
                currentWeapon = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/noWeapon.png"));
            }
            hudf = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/HUDFront.png"));
            hudb = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/HUDBack.png"));
            spaceFiller = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/spaceFiller.png"));
            pi = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/simpleplayer.png"));
            test = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/longtest.png")).getScaledInstance(1600, 390, Image.SCALE_FAST);
            logo = new ImageIcon(ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/logo.png")));
            menuBG = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/menubackground.png")).getScaledInstance(800, 600, Image.SCALE_FAST);
            gameBG = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/gamebackground.png")).getScaledInstance(800, 600, Image.SCALE_FAST);
            optionsBG = getToolkit().getImage(getClass().getResource("/com/RoguePanda/ZAG/Images/optionsbackground.png")).getScaledInstance(800, 600, Image.SCALE_FAST);
        } catch (IOException ex) {
            Logger.getLogger(CardGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException er) {
            Logger.getLogger(CardGUI.class.getName()).log(Level.SEVERE, null, er);
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Define Variables">
        leftKey = 'a';
        rightKey = 'd';
        jumpKey = ' ';
        dropKey = 'q';
        frame = new JPanel();
        frame.setLayout(new CardLayout());
        menuDisplay = new JPanel();
        gameDisplay = new JScrollPane();
        gameHUD = new JPanel();
        pauseMenu = new JPanel();
        optionsDisplay = new JPanel();
        mainDisplay = new JPanel();
        hudfp = new JLabel(new ImageIcon(hudf));
        hudbp = new JLabel(new ImageIcon(hudb));
        menuCard = new JPanel();
        gameCard = new JPanel();
        optionsCard = new JPanel();
        debugText = new JLabel();
        pauseText = new JLabel("<html><font size=+3>Paused</font><html>");
        gameCanvas = new GameCanvas(this);
        playerSprite = new JLabel(new ImageIcon(pi));
        testl = new JLabel(new ImageIcon(test));
        menuBack = new JLabel(new ImageIcon(menuBG));
        gameBack = new JLabel(new ImageIcon(gameBG));
        gameBackDisplay = new JScrollPane();
        optionsBack = new JLabel(new ImageIcon(optionsBG));
        spaceFillerl = new JLabel(new ImageIcon(spaceFiller));
        healthBar = new JProgressBar(0, currentGame.player.maxHealth);
        menubuttons = new JLabel[4];
        menubuttons[0] = new JLabel(logo);
        menubuttons[1] = new JLabel("Adventure");
        menubuttons[2] = new JLabel("Options");
        menubuttons[3] = new JLabel("Exit");
        optionsbuttons = new JLabel[6];
        optionsbuttons[0] = new JLabel(logo);
        optionsbuttons[1] = new JLabel("Left Key: a");
        optionsbuttons[2] = new JLabel("Right Key: d");
        optionsbuttons[3] = new JLabel("Drop Key: q");
        optionsbuttons[4] = new JLabel("Jump Key: space");
        optionsbuttons[5] = new JLabel("Back");
        gamebuttons = new JLabel[2];
        gamebuttons[0] = new JLabel(new ImageIcon(currentWeapon));
        gamebuttons[1] = new JLabel();
        transparent = new Color(0, 0, 0, 0);
        hungerBar = new JProgressBar(0, 100);
        thirstBar = new JProgressBar(0, 100);
        gameDisplayHolder = new JPanel();
        htStats = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        GridBagConstraints d;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Layout Setup">
        menuCard.setLayout(new GridBagLayout());
        gameCard.setLayout(new AbsoluteLayout());
        optionsCard.setLayout(new GridBagLayout());
        mainDisplay.setLayout(new GridBagLayout());
        gameHUD.setLayout(new AbsoluteLayout());
        optionsDisplay.setLayout(new GridBagLayout());
        gameDisplayHolder.setLayout(null);
        htStats.setLayout(new BorderLayout());
        pauseMenu.setBackground(transparent);
        pauseText.setBackground(transparent);
        gameCanvas.setBackground(transparent);
        menuDisplay.setBackground(transparent);
        gameDisplay.setBackground(transparent);
        gameBackDisplay.setBackground(transparent);
        gameHUD.setBackground(transparent);
        mainDisplay.setBackground(transparent);
        optionsDisplay.setBackground(transparent);
        testl.setBackground(transparent);
        gameDisplayHolder.setBackground(transparent);
        htStats.setBackground(transparent);
        healthBar.setBackground(Color.black);
        healthBar.setForeground(Color.red);
        debugText.setForeground(Color.white);
        hungerBar.setForeground(new Color(200, 0, 0, 200));
        thirstBar.setForeground(new Color(0, 0, 200, 200));

        gameDisplay.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gameDisplay.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        gameBackDisplay.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        gameBackDisplay.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        gameDisplay.setSize(800, 600);
        gameBackDisplay.setSize(800, 600);
        gameDisplayHolder.setSize(currentGame.currentLevel.size);
        gameDisplayHolder.setPreferredSize(currentGame.currentLevel.size);
        gameCanvas.setSize(currentGame.currentLevel.size);
        playerSprite.setBounds(0, 0, -1, -1);
        spaceFillerl.setBounds(0, 0, currentGame.currentLevel.size.width, currentGame.currentLevel.size.height);
        gameDisplay.setBorder(null);
        gameBackDisplay.setBorder(null);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        d = c;
        healthBar.setValue((int) currentGame.player.health);
        hungerBar.setValue(100);
        thirstBar.setValue(100);
        healthBar.setString("Health");
        hungerBar.setString("Hunger");
        thirstBar.setString("Thirst");
        healthBar.setStringPainted(true);
        hungerBar.setStringPainted(true);
        thirstBar.setStringPainted(true);
        for (int i = 1; i < 5; i++) {
            final JLabel la = optionsbuttons[i];
            final int ne = i;
            la.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    la.setForeground(Color.GREEN);
                    changeKey = ne;
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }
            });
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Button Setup">
        for (int i = 0; i < menubuttons.length; i++) {
            menubuttons[i].setFont(getFont());
            menubuttons[i].setForeground(Color.black);
            final int j = i;
            menubuttons[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    mainMenuAction(j);
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    colorChange(me, 1);
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    colorChange(me, 2);
                }
            });
            c.gridy = i + 1;
            menuDisplay.add(menubuttons[i], c);
        }
        for (int i = 0; i < optionsbuttons.length; i++) {
            optionsbuttons[i].setFont(getFont());
            optionsbuttons[i].setForeground(Color.black);
            final int j = i;
            optionsbuttons[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    optionsMenuAction(j);
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    colorChange(me, 1);
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    colorChange(me, 2);
                }
            });
            c.gridy = i + 1;
            optionsDisplay.add(optionsbuttons[i], c);
        }
        for (int i = 0; i < gamebuttons.length; i++) {
            gamebuttons[i].setFont(getFont());
            final int j = i;
            gamebuttons[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    optionsMenuAction(j);
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                    colorChange(me, 1);
                }

                @Override
                public void mouseExited(MouseEvent me) {
                    colorChange(me, 2);
                }
            });
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Add Components">
        htStats.add(hungerBar, BorderLayout.PAGE_START);
        htStats.add(thirstBar, BorderLayout.PAGE_END);
        gameHUD.add(hudfp, new AbsoluteConstraints(2, 494, -1, -1));
        gameHUD.add(debugText, new AbsoluteConstraints(0, 0, -1, -1));
        gameHUD.add(gamebuttons[0], new AbsoluteConstraints(2, 494, -1, -1));
        //gameHUD.add(ammoBox, new AbsoluteConstraints(68, 504, -1, -1));
        gameHUD.add(healthBar, new AbsoluteConstraints(150, 504, 482, 42));
        gameHUD.add(htStats, new AbsoluteConstraints(634, 504, -1, -1));
        gameHUD.add(hudbp, new AbsoluteConstraints(2, 494, -1, -1));
        gameDisplayHolder.add(gameCanvas);
        gameDisplayHolder.add(spaceFillerl);
        gameDisplay.setViewportView(gameDisplayHolder);
        gameBackDisplay.setViewportView(gameBack);
        pauseMenu.add(pauseText);
        menuCard.add(menuDisplay, d);
        menuCard.add(menuBack, d);
        optionsCard.add(optionsDisplay, d);
        optionsCard.add(optionsBack, d);
        gameCard.add(gameHUD, new AbsoluteConstraints(0, 0, 800, 600));
        gameCard.add(gameDisplay, new AbsoluteConstraints(0, 0, 800, 600));
        gameCard.add(gameBackDisplay, new AbsoluteConstraints(0, 0, 800, 600));
        frame.add(menuCard, "Menu");
        frame.add(gameCard, "Game");
        frame.add(optionsCard, "Options");
        add(frame);
        //</editor-fold>
        setUpListeners(this);
    }

    @Override
    public void run() {
        setVisible(true);
    }

    private void colorChange(MouseEvent me, int i) {
        switch (i) {
            case 1:
                ((JLabel) me.getSource()).setForeground(Color.yellow);
                break;
            case 2:
                ((JLabel) me.getSource()).setForeground(Color.black);
                break;
        }
        mainDisplay.repaint();
        frame.repaint();
    }

    private void optionsMenuAction(int i) {
        CardLayout cl = (CardLayout) (frame.getLayout());
        switch (i) {
            case 5:
                cl.show(frame, "Menu");
                break;
        }
    }

    private void mainMenuAction(int i) {
        CardLayout cl = (CardLayout) frame.getLayout();
        switch (i) {
            case 1:
                cl.show(frame, "Game");
                paused = false;
                break;
            case 2:
                cl.show(frame, "Options");
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    private void startAction() {
    }

    private void setUpListeners(final Component co) {
        try {
            JComponent jco = (JComponent) co;
            for (Component com : jco.getComponents()) {
                setUpListeners(com);
            }
        } catch (Exception ex) {
            try {
                JFrame jco = (JFrame) co;
                for (Component com : jco.getComponents()) {
                    setUpListeners(com);
                }
            } catch (Exception ex1) {
            }
        }
        co.addKeyListener(new KeyListener() {
            //<editor-fold defaultstate="collapsed" desc="keylistener">
            @Override
            public void keyTyped(KeyEvent ke) {
                if (changeKey != 0) {
                    String ch = String.valueOf(ke.getKeyChar());
                    if (" ".equals(ch)) {
                        ch = "space";
                    }
                    switch (changeKey) {
                        case 1:
                            leftKey = ke.getKeyChar();
                            optionsbuttons[1].setText("Left Key: " + ch);
                            break;
                        case 2:
                            rightKey = ke.getKeyChar();
                            optionsbuttons[2].setText("Right Key: " + ch);
                            break;
                        case 3:
                            dropKey = ke.getKeyChar();
                            optionsbuttons[3].setText("Drop Key: " + ch);
                            break;
                        case 4:
                            jumpKey = ke.getKeyChar();
                            optionsbuttons[4].setText("Jump Key: " + ch);
                            break;
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                pressed.add((int) ke.getKeyChar());
                if (ke.getKeyCode() == 113) {
                    if (debug) {
                        debug = false;
                        debugText.setBackground(new Color(0, 0, 0, 0));
                    } else {
                        debug = true;
                        debugText.setBackground(new Color(0, 0, 0, 50));
                    }
                }
                if (ke.getKeyChar() == dropKey) {
                    currentGame.currentLevel.entities.add(new DroppedItem(
                            "Item",
                            100,
                            new Point2D.Double(currentGame.player.location.getX(),
                            currentGame.player.location.getY()),
                            currentGame.currentLevel,
                            new ItemStack(currentGame.player.items.get(0).item.getID())));
                    //  currentGame.player.items.updateItemStack(1, -1);
                }
                if (ke.getKeyCode() == 112) {
                    currentGame.currentLevel.entities.add(new BasicZombie(
                            "BasicZombie" + currentGame.currentLevel.entities.size(),
                            15,
                            new Point(100, 100),
                            currentGame.currentLevel));
                }
                if (ke.getKeyCode() == 115) {
                    currentGame.currentLevel.entities.add(new FireZombie(
                            "FireZombie" + currentGame.currentLevel.entities.size(),
                            15,
                            new Point(100, 100),
                            currentGame.currentLevel));
                }
                if (ke.getKeyCode() == 114) {
                    currentGame.currentLevel.entities.add(new GameObject(
                            "Television" + currentGame.currentLevel.entities.size(),
                            15,
                            new Point(200, 100),
                            currentGame.currentLevel,
                            0));
                }
                if (ke.getKeyChar() == 'p') {
                    if (paused) {
                        paused = false;
                        gameCard.remove(pauseMenu);
                    } else {
                        paused = true;
                        gameCard.add(pauseMenu, new AbsoluteConstraints(260, 390, -1, -1), 0);
                    }
                    gameCanvas.repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                pressed.remove((int) ke.getKeyChar());
            }
            //</editor-fold>
        });

    }
}
