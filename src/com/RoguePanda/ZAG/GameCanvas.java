/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * The object that everything is painted to
 *
 * @author Oa10712
 */
public class GameCanvas extends JPanel {

    private static final long serialVersionUID = 1L;
    CardGUI cgui;
    BuildingObject bo;
    Building b;
    BasicZombie z;

    /**
     *
     * @param aThis The gui that this is attached to
     */
    GameCanvas(CardGUI aThis) {
        cgui = aThis;
        bo = new BuildingObject(0, new Point(400, 0), b);
        z = new BasicZombie(
                "BasicZombie" + cgui.currentGame.currentLevel.entities.size(),
                15,
                new Point(500, 0),
                cgui.currentGame.currentLevel);
        ArrayList<BuildingObject> bos = new ArrayList<>();
        bos.add(bo);
        int[][] chunks = new int[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                chunks[x][y] = 0;
            }
        }
        int[][] chunks2 = new int[3][3];
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                chunks2[x][y] = 16;
            }
        }
        chunks[1][1] = 1;
        chunks[0][2] = 2;
        chunks[2][2] = 2;
        b = new Building(chunks, chunks2, bos, new Point(500, 0), cgui.currentGame.currentLevel);
        z.inside = b;
        cgui.currentGame.currentLevel.buildings.add(b);
        this.setSize(1600, 600);
        this.setBounds(0, 0, 1600, 600);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Damaging.closeAttack(cgui.currentGame.player, 100, 2);
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (cgui.currentGame.player.dead) {
            //Death Screen
        }
        try {
            g.drawImage(cgui.test, 0, 100, null);
            try {
                for (Building b : cgui.currentGame.currentLevel.buildings) {
                    g.drawImage(b.insideSprite, b.location.x, b.location.y, null);
                    if (cgui.debug) {
                        for (BuildingObject bos : b.getObjects()) {
                            g.drawPolygon(bos.boundingBox);
                        }
                    }
                    if (!b.inside) {
                        g.drawImage(b.outsideSprite, b.location.x, b.location.y, null);
                    } else {
                        g.drawImage(b.outsideSpriteClear, b.location.x, b.location.y, null);
                    }
                    if (cgui.debug) {
                        for (Rectangle r : b.entrances) {
                            g.drawRect(r.x, r.y, r.width, r.height);
                        }
                    }
                }
            } catch (Exception be) {
            }
            if (cgui.currentGame.player.dead) {
                g.setFont(new java.awt.Font("Tahoma", 0, 36));
                g.drawString("GAME OVER", 325, 300);
            } else if (cgui.paused) {
                g.setFont(new java.awt.Font("Tahoma", 0, 36));
                g.drawString("PAUSED", 340, 300);
                g.fillPolygon(bo.boundingBox);
            } else {
                g.setColor(Color.black);
                g.fillRect(100, 400, 100, 100);
                Rectangle2D r = cgui.currentGame.player.boundingBox;
                g.drawImage(cgui.currentGame.player.sprite, (int) r.getMinX(), (int) r.getMinY(), null);
                if (cgui.debug) {
                    g.setColor(Color.green);
                    g.drawRect((int) r.getMinX(), (int) r.getMinY(), (int) r.getWidth(), (int) r.getHeight());
                }
                try {
                    for (Entity e : cgui.currentGame.currentLevel.entities) {
                        if (e.inside == cgui.currentGame.player.inside) {
                            r = e.boundingBox;
                            g.drawImage(e.sprite, (int) r.getMinX(), (int) r.getMinY(), null);
                            if (cgui.debug) {
                                g.setColor(Color.green);
                                g.drawRect((int) r.getMinX(), (int) r.getMinY(), (int) r.getWidth(), (int) r.getHeight());
                                g.setColor(Color.red);
                                g.drawRect((int) e.location.getX() - 1, (int) e.location.getY() - 1, 3, 3);
                            }
                        }
                    }
                } catch (Exception bu) {
                }
                g.setColor(Color.black);
                g.fillRect(0, 400, 100, 100);
            }
        } catch (Exception ex) {
            //System.out.println(ex);
        }
    }
}
