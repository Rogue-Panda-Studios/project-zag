/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;

/**
 * This manages the game update loop using the specified target FPS
 *
 * @author Oa10712
 */
public class gameUpdates implements Runnable {

    CardGUI gui;
    Player player;
    int counter;
    int subupdaterate = 6;
    int TARGET_FPS = 40;
    int fps = 0;
    Point ppos;

    gameUpdates(CardGUI g, int tg) {
        gui = g;
        TARGET_FPS = tg;
        player = gui.currentGame.player;
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        fps = 0;
        counter = 0;
        while (true) {
            try {
                //<editor-fold defaultstate="collapsed" desc="all update">
                if (!gui.paused) {// work out how long its been since the last update, this
                    // will be used to calculate how far the entities should
                    // move this loop
                    long now = System.nanoTime();
                    long updateLength = now - lastLoopTime;
                    lastLoopTime = now;
                    double delta = updateLength / ((double) OPTIMAL_TIME);

                    // update the frame counter
                    lastFpsTime += updateLength;
                    fps++;

                    // update our FPS counter if a second has passed since
                    // we last recorded
                    if (lastFpsTime >= 1000000000) {
                        if (gui.debug) {
                            gui.debugText.setText("<html>FPS: " + fps + "<br>Scroll Position: " + gui.gameDisplay.getHorizontalScrollBar().getValue() + "<br>Player Location: " + player.location + "</html>");
                        } else {
                            gui.debugText.setText(null);
                        }
                        System.out.println("(FPS: " + fps + ")");
                        lastFpsTime = 0;
                        fps = 0;
                    }

                    // update the game logic
                    doGameUpdates(delta);
                    render();
                    try {
                        Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(gameUpdates.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //</editor-fold>
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void doGameUpdates(double delta) {
        motionUpdates();
        JScrollBar hzb = gui.gameDisplay.getHorizontalScrollBar();
        JScrollBar vsb = gui.gameDisplay.getVerticalScrollBar();
        if (counter < subupdaterate) {
            counter++;
        } else {
            slowMotionUpdates();
            //gui.healthBar.setMaximum(gui.player.maxHealth);
            if ((int) player.health != gui.healthBar.getValue()) {
                gui.healthBar.setForeground(Color.BLUE);
                gui.healthBar.setValue((int) player.health);
            } else {
                int g = (int) (255 * (player.health / player.maxHealth));
                int r = 255 - g;
                gui.healthBar.setForeground(new Color(r, g, 0));
            }
            if ((int) player.thirst != gui.thirstBar.getValue()) {
                gui.thirstBar.setValue((int) player.thirst);
            }
            if ((int) player.hunger != gui.hungerBar.getValue()) {
                gui.hungerBar.setValue((int) player.hunger);
            }
            // System.out.println("update");
            counter = 0;
        }
        hzb.setValue((int) player.location.getX() - 375);
        vsb.setValue((int) player.location.getY() - 400);
    }

    private void render() {
        if (player.getItems().get(0) != null) {
            gui.gamebuttons[0].setIcon(new ImageIcon(player.getItems().get(0).sprite));
        } else {
            try {
                gui.gamebuttons[0].setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/noWeapon.png"))));
            } catch (IOException ex) {
                Logger.getLogger(gameUpdates.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gui.gameCard.repaint();
    }

    private void motionUpdates() {
        Game currentGame = gui.currentGame;
        for (Entity e : gui.currentGame.currentLevel.entities) {
            double nex = e.velocity.getX(), ney = e.velocity.getY();
            //<editor-fold defaultstate="collapsed" desc="Entity Friction and Gravity">
            if (e.canFall) {
                ney += .2;
                if (!e.falling) {
                    if (nex > 0) {
                        if (nex >= .2) {
                            nex -= .2;
                        } else {
                            nex = 0;
                        }
                    } else if (nex < 0) {
                        if (nex <= -.2) {
                            nex += .2;
                        } else {
                            nex = 0;
                        }
                    }
                } else {
                    if (nex >= .02) {
                        nex -= .02;
                    } else if (nex <= -.02) {
                        nex += .02;
                    }
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Entity/Level-Boundry collision and Entity Action">
            if (e.boundingBox.getMinX() < 0) {
                e.boundingBox.x = 0;
                nex = 0;
            } else if (e.boundingBox.x > gui.currentGame.currentLevel.size.width - e.boundingBox.width) {
                e.boundingBox.x = (gui.currentGame.currentLevel.size.width - e.boundingBox.width);
                nex = 0;
            }
            if (e.boundingBox.y > (gui.currentGame.currentLevel.size.height - 110) - e.boundingBox.height) {
                e.boundingBox.y = (gui.currentGame.currentLevel.size.height - 110) - e.boundingBox.height;
                ney = 0;
                e.falling = false;
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Entity/Clippable-Entity Collision">
            for (Entity en : gui.currentGame.currentLevel.entities) {
                if (en.clippable && en.inside == e.inside && e.boundingBox.intersects(en.boundingBox)) {
                    if (en.boundingBox.getCenterX() >= e.boundingBox.getCenterX()) {
                        if (e.velocity.getX() > 0) {
                            nex = 0;
                        }
                    } else if (en.boundingBox.getCenterX() <= e.boundingBox.getCenterX()) {
                        if (e.velocity.getX() < 0) {
                            nex = 0;
                        }
                    }
                    if (en.boundingBox.getCenterY() >= e.boundingBox.getCenterY()) {
                        if (e.velocity.getY() > 0) {
                            ney = 0;
                            e.falling = false;
                        }
                    }
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Entity/Building Collision">
            if (e.inside != null) {
                if (e.boundingBox.getMinX() <= e.inside.location.x) {
                    if (nex < 0) {
                        nex = 0;
                    }
                }
                if (e.inside.location.x + e.inside.insideSprite.getWidth() <= e.boundingBox.getMaxX()) {
                    if (nex > 0) {
                        nex = 0;
                    }
                }
                for (BuildingObject bo : e.inside.getObjects()) {
                    if (bo.boundingBox.intersects(e.boundingBox)) {
                        if (e.boundingBox.getCenterX() > bo.boundingBox.getBounds().getCenterX()) {
                            if (nex < 0) {
                                nex = 0;
                            }
                        } else if (e.boundingBox.getCenterX() < bo.boundingBox.getBounds().getCenterX()) {
                            if (nex > 0) {
                                // nex = 0;
                            }
                        }
                        if (e.boundingBox.getCenterY() <= bo.boundingBox.getBounds().getMaxY()) {
                            if (ney > 0) {
                                ney = 0;
                                e.falling = false;
                            }
                        } else {
                            if (ney < 0) {
                                ney = 0;
                                e.falling = false;
                            }
                        }
                    }
                }
            }
            //</editor-fold>
            e.velocity = new Point2D.Double(nex, ney);
            e.boundingBox.x += e.velocity.getX();
            e.boundingBox.y += e.velocity.getY();
            e.tasks.action();
        }
        //<editor-fold defaultstate="collapsed" desc="Player Physics">
        double npx = player.velocity.getX(), npy = player.velocity.getY();
        //<editor-fold defaultstate="collapsed" desc="Player Friction and Gravity">
        npy += .15;
        if (!player.falling) {
            if (npx > 0) {
                if (npx >= .2) {
                    npx -= .2;
                } else {
                    npx = 0;
                }
            } else if (npx < 0) {
                if (npx <= -.2) {
                    npx += .2;
                } else {
                    npx = 0;
                }
            }
        } else {
            if (npx >= .02) {
                npx -= .02;
            } else if (npx <= -.02) {
                npx += .02;
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Player/Level-Boundry Collision and Player Action">
        player.tasks.action();
        if (player.boundingBox.x < 0) {
            player.boundingBox.x = 0;
            npx = 0;
        } else if (player.boundingBox.x > (gui.currentGame.currentLevel.size.width - player.boundingBox.width)) {
            player.boundingBox.x = (gui.currentGame.currentLevel.size.width - player.boundingBox.width);
            npx = 0;
        }
        if (player.boundingBox.y > (gui.currentGame.currentLevel.size.height - 110) - player.boundingBox.height) {
            player.boundingBox.y = (gui.currentGame.currentLevel.size.height - 110) - player.boundingBox.height;
            npy = 0;
            player.falling = false;
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Key actions">
        if (!gui.paused) {
            if (!currentGame.player.dead) {
                if (gui.pressed.contains(gui.rightKey)) {
                    if (npx <= 3) {
                        npx += 1;
                    }
                }
                if (gui.pressed.contains(gui.leftKey)) {
                    if (npx >= -3) {
                        npx -= 1;
                    }
                }
                if (gui.pressed.contains(gui.phazeKey)) {
                    player.phazing = true;
                } else {
                    player.phazing = false;
                }
                if (!player.falling) {
                    if (gui.pressed.contains(gui.jumpKey)) {
                        npy -= 3;
                        currentGame.player.falling = true;
                    }
                }
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Player/Clippable-Entity Collision">
        for (Entity en : gui.currentGame.currentLevel.entities) {
            if (en.clippable && player.inside == en.inside && player.boundingBox.intersects(en.boundingBox)) {
                /*if (en.boundingBox.getCenterX() >= player.boundingBox.getCenterX()) {
                 if (player.velocity.getX() > 0) {
                 npx = 0;
                 }
                 } else if (en.boundingBox.getCenterX() <= player.boundingBox.getCenterX()) {
                 if (player.velocity.getX() < 0) {
                 npx = 0;
                 }
                 }
                 if (en.boundingBox.getMinY() >= player.boundingBox.getMaxY()) {
                 if (player.velocity.getY() > 0) {
                 npy = 0;
                 player.falling = false;
                 }
                 }*/
                npx = npx / 2;
                npy = npy / 2;
            }
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Player/Building Collision">
        if (player.inside != null) {
            if (player.inside.location.x >= player.boundingBox.getMinX()) {
                if (npx < 0) {
                    npx = 0;
                }
            }
            if (player.inside.location.x + player.inside.insideSprite.getWidth() <= player.boundingBox.getMaxX()) {
                if (npx > 0) {
                    npx = 0;
                }
            }
            for (BuildingObject bo : player.inside.getObjects()) {
                if (bo.phazeBottom && bo.boundingBox.contains(new Point2D.Double(player.boundingBox.getCenterX(), player.boundingBox.getMaxY())) && player.phazing) {
                    if (npy < 0) {
                        npy = 0;
                        player.falling = false;
                    }
                } else {
                    if (bo.phaze && player.phazing) {
                    } else {
                        if (bo.boundingBox.intersects(player.boundingBox)) {
                            if (player.boundingBox.getCenterX() > bo.boundingBox.getBounds().getCenterX() && !bo.phazeBottom) {
                                if (npx < 0) {
                                    npx = 0;
                                }
                            }
                            if (player.boundingBox.getMaxY() <= bo.boundingBox.getBounds().getMaxY()) {
                                if (npy > 0) {
                                    npy = 0;
                                    player.falling = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        //</editor-fold>
        player.velocity = new Point2D.Double(npx, npy);
        player.boundingBox.x += player.velocity.getX();
        player.boundingBox.y += player.velocity.getY();
        //</editor-fold>
    }

    private void slowMotionUpdates() {
    }
}
