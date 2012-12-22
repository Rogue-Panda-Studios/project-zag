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
        postMotionUpdates();
        hzb.setValue((int) player.location.getX() - 375);
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

            }
        }
        for (Entity e : gui.currentGame.currentLevel.entities) {
            e.tasks.action();
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
            player.tasks.action();
            if (player.boundingBox.x < 0) {
                player.boundingBox.x = 0;
                player.velocity.setLocation(0, player.velocity.getY());
            } else if (player.boundingBox.x > (gui.currentGame.currentLevel.size.width - player.boundingBox.width)) {
                player.boundingBox.x = (gui.currentGame.currentLevel.size.width - player.boundingBox.width);

                player.velocity.setLocation(0, player.velocity.getY());
            }
            if (player.boundingBox.y > 490 - player.boundingBox.height) {
                player.boundingBox.y = 490 - player.boundingBox.height;
                player.velocity.setLocation(player.velocity.getX(), 0);
                player.falling = false;
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
        player.velocity.setLocation(player.velocity.getX(), player.velocity.getY() + 1);
        if (!player.falling) {
            if (player.velocity.getX() > 0) {
                player.velocity.setLocation(player.velocity.getX() - 1, player.velocity.getY());
            } else if (player.velocity.getX() < 0) {
                player.velocity.setLocation(player.velocity.getX() + 1, player.velocity.getY());
            }
        }
    }

    private void postMotionUpdates() {
        double npx = player.velocity.getX(), npy = player.velocity.getY();
        for (Entity en : gui.currentGame.currentLevel.entities) {
            if (en.clippable && player.boundingBox.intersects(en.boundingBox)) {
                if (en.boundingBox.getCenterX() > player.boundingBox.getCenterX()) {
                    if (player.velocity.getX() > 0) {
                        npx = 0;
                    }
                } else if (en.boundingBox.getCenterX() < player.boundingBox.getCenterX()) {
                    if (player.velocity.getX() < 0) {
                        npx = 0;
                    }
                }
            }
        }
        player.velocity = new Point2D.Double(npx, npy);
        player.boundingBox.x += player.velocity.getX();
        player.boundingBox.y += player.velocity.getY();
        for (Entity e : gui.currentGame.currentLevel.entities) {
            for (Entity en : gui.currentGame.currentLevel.entities) {
                if (en.clippable && e.boundingBox.intersects(en.boundingBox)) {
                    e.velocity = new Point(0, 0);
                }
            }
            e.boundingBox.x += e.velocity.getX();
            e.boundingBox.y += e.velocity.getY();
        }
    }
}
