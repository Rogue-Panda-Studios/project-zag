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
    int counter;
    int subupdaterate = 6;
    int TARGET_FPS = 40;
    int fps = 0;
    Point ppos;

    gameUpdates(CardGUI g, int tg) {
        gui = g;
        TARGET_FPS = tg;
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
                            gui.debugText.setText("FPS: " + fps + " Scroll Position: " + gui.gameDisplay.getHorizontalScrollBar().getValue() + " Player Location: " + gui.currentGame.player.location);
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
        if (counter < subupdaterate) {
            counter++;
        } else {
            slowMotionUpdates();
            //gui.healthBar.setMaximum(gui.player.maxHealth);
            if ((int) gui.currentGame.player.health != gui.healthBar.getValue()) {
                gui.healthBar.setForeground(Color.BLUE);
                gui.healthBar.setValue((int) gui.currentGame.player.health);
            } else {
                int g = (int) (255 * (gui.currentGame.player.health / gui.currentGame.player.maxHealth));
                int r = 255 - g;
                gui.healthBar.setForeground(new Color(r, g, 0));
            }
            if ((int) gui.currentGame.player.thirst != gui.thirstBar.getValue()) {
                gui.thirstBar.setValue((int) gui.currentGame.player.thirst);
            }
            if ((int) gui.currentGame.player.hunger != gui.hungerBar.getValue()) {
                gui.hungerBar.setValue((int) gui.currentGame.player.hunger);
            }
            // System.out.println("update");
            counter = 0;
        }
        hzb.setValue((int) gui.currentGame.player.location.getX() - 375);
    }

    private void render() {
        if (gui.currentGame.player.getItems().get(0) != null) {
            gui.gamebuttons[0].setIcon(new ImageIcon(gui.currentGame.player.getItems().get(0).sprite));
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
        if (!gui.paused) {
            if (!currentGame.player.falling && !currentGame.player.dead) {
                if (gui.pressed.contains(gui.rightKey)) {
                    if (currentGame.player.velocity.getX() > 2) {
                    } else {
                        currentGame.player.velocity.setLocation(currentGame.player.velocity.getX() + 1, currentGame.player.velocity.getY());
                    }
                }
                if (gui.pressed.contains(gui.leftKey)) {
                    if (currentGame.player.velocity.getX() < -2) {
                    } else {
                        currentGame.player.velocity.setLocation(currentGame.player.velocity.getX() - 1, currentGame.player.velocity.getY());
                    }
                }
                if (gui.pressed.contains(gui.jumpKey)) {
                    currentGame.player.velocity.setLocation(currentGame.player.velocity.getX(), -3);
                    currentGame.player.falling = true;
                }
                if (gui.pressed.contains(gui.dropKey)) {
                    currentGame.currentLevel.entities.add(new DroppedItem("Item", 100, new Point2D.Double(currentGame.player.location.getX(), currentGame.player.location.getY()), currentGame.currentLevel, new ItemStack(currentGame.player.items.get(0).item.getID())));
                    currentGame.player.items.updateItemStack(1, -1);
                }
            }
        }
        for (Entity e : gui.currentGame.currentLevel.entities) {
            e.tasks.action();
            e.boundingBox.x += e.velocity.getX();
            e.boundingBox.y += e.velocity.getY();
            if (e.boundingBox.getMinX() < 0) {
                e.boundingBox.x = 0;
                e.velocity.setLocation(0, e.velocity.getY());
            } else if (e.boundingBox.x > gui.currentGame.currentLevel.size.width - e.boundingBox.width) {
                e.boundingBox.x = (gui.currentGame.currentLevel.size.width - e.boundingBox.width);
                e.velocity.setLocation(0, e.velocity.getY());
            }
            if (e.boundingBox.y > 490 - e.boundingBox.height) {
                e.boundingBox.y = 490 - e.boundingBox.height;
                e.velocity.setLocation(e.velocity.getX(), 0);
                e.falling = false;
            }
        }
        {
            gui.currentGame.player.tasks.action();
            gui.currentGame.player.boundingBox.x += gui.currentGame.player.velocity.getX();
            gui.currentGame.player.boundingBox.y += gui.currentGame.player.velocity.getY();
            if (gui.currentGame.player.boundingBox.x < 0) {
                gui.currentGame.player.boundingBox.x = 0;
                gui.currentGame.player.velocity.setLocation(0, gui.currentGame.player.velocity.getY());
            } else if (gui.currentGame.player.boundingBox.x > (gui.currentGame.currentLevel.size.width - gui.currentGame.player.boundingBox.width)) {
                gui.currentGame.player.boundingBox.x = (gui.currentGame.currentLevel.size.width - gui.currentGame.player.boundingBox.width);

                gui.currentGame.player.velocity.setLocation(0, gui.currentGame.player.velocity.getY());
            }
            if (gui.currentGame.player.boundingBox.y > 490 - gui.currentGame.player.boundingBox.height) {
                gui.currentGame.player.boundingBox.y = 490 - gui.currentGame.player.boundingBox.height;
                gui.currentGame.player.velocity.setLocation(gui.currentGame.player.velocity.getX(), 0);
                gui.currentGame.player.falling = false;
            }
        }
    }

    private void slowMotionUpdates() {
        for (Entity e : gui.currentGame.currentLevel.entities) {
            try {
                Projectile p = (Projectile) e;
            } catch (Exception ex) {
                e.velocity.setLocation(e.velocity.getX(), e.velocity.getY() + 1);
                if (!e.falling) {
                    if (e.velocity.getX() > 0) {
                        e.velocity.setLocation(e.velocity.getX() - 1, e.velocity.getY());
                    } else if (e.velocity.getX() < 0) {
                        e.velocity.setLocation(e.velocity.getX() + 1, e.velocity.getY());
                    }
                }
            }
        }
        gui.currentGame.player.velocity.setLocation(gui.currentGame.player.velocity.getX(), gui.currentGame.player.velocity.getY() + 1);
        if (!gui.currentGame.player.falling) {
            if (gui.currentGame.player.velocity.getX() > 0) {
                gui.currentGame.player.velocity.setLocation(gui.currentGame.player.velocity.getX() - 1, gui.currentGame.player.velocity.getY());
            } else if (gui.currentGame.player.velocity.getX() < 0) {
                gui.currentGame.player.velocity.setLocation(gui.currentGame.player.velocity.getX() + 1, gui.currentGame.player.velocity.getY());
            }
        }
    }
}
