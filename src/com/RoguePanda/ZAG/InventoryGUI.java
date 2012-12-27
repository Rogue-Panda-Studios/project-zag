package com.RoguePanda.ZAG;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class InventoryGUI extends JPanel {

    JLabel[] slots;
    JLabel[] stackCount;
    JLabel[] highLights;
    Image backGround = null;
    Image highLight = null;
    CardGUI cgui;
    boolean heldItem = false;
    int x, y;
    Image mouseImage;
    public int inventorySize;
    Image nullItem = null;
    Inventory inv;

    InventoryGUI(CardGUI c) {
        cgui = c;
        inv = c.currentGame.player.getItems();
        inventorySize = c.currentGame.player.inventorySpace;
        this.setLayout(new AbsoluteLayout());
        slots = new JLabel[inventorySize];
        stackCount = new JLabel[inventorySize];
        highLights = new JLabel[inventorySize];
        setBackground(new Color(0, 0, 0, 0));
        this.setSize(c.getWidth(), c.getHeight());
        try {
            backGround = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/template.png"));
            nullItem = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/noWeapon.png"));
            highLight = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/SelectedIcon.png"));
            //spriteSheet = ImageIO.read(getClass().getResource("/com/RoguePanda/ZAG/Images/itemSheet0.png"));
        } catch (IOException ex) {
            Logger.getLogger(InventoryGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        //sprite = ImageManipulator.selectFromSheet(spriteSheet, id, 64, 64);
        int rowTemp = 0;
        int colTemp = 0;
        for (int i = 0; i < inventorySize; i++) {
            Item item;
            Image img = nullItem;
            int itemID;
            int stackSize = 0;
            try {
                item = inv.get(i).item;
                itemID = item.getID();
                img = inv.get(i).sprite;
                ItemStack stack = inv.get(i);
                stackSize = stack.amount;
            } catch (Exception ex) {
                itemID = -1;
            }
            System.out.println(stackSize);
            if (itemID == -1) {
                slots[i] = new JLabel(new ImageIcon(nullItem));
            } else {
                slots[i] = new JLabel(new ImageIcon(img));
                stackCount[i] = new JLabel();
                stackCount[i].setText("<html><p><font color=#FF0000" + "size=\"7\" face=\"Verdana\">" + stackSize + "</font></p></html>");
                //highLights[i] = new JLabel(new ImageIcon(highLight));
                slots[i].setSize(new Dimension(64, 64));
                stackCount[i].setSize(new Dimension(16, 16));
                //highLights[i].setSize(new Dimension(64, 64));
                addMouseListeners(slots[i], i, 0);
                //addMouseListeners(highLights[i], i, 1);
                this.add(stackCount[i], new AbsoluteConstraints(56 + 72 * rowTemp, 56 + 72 * colTemp));
                this.add(slots[i], new AbsoluteConstraints(8 + 72 * rowTemp, 8 + 72 * colTemp, 64, 64));
                //this.add(highLights[i], new AbsoluteConstraints(8 + 72 * rowTemp, 8 + 72 * colTemp, 64, 64));
            }
            rowTemp++;
            if (rowTemp == 8) {
                rowTemp = 0;
                colTemp++;
            }
        }
        JLabel back = new JLabel(new ImageIcon(backGround));
        this.add(back, new AbsoluteConstraints(0, 0, c.getWidth(), c.getHeight()));
    }

    private void addMouseListeners(final JLabel jLabel, final int i, final int a) {

        jLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                Inventory inv = cgui.currentGame.player.getItems();
                if (inv.get(i) != null) {
                    if (inv.get(i).item.name.length() == 0) {
                        return;
                    }
                    String html;
                    try {
                        html =
                                "<html><p><font "
                                + "size=\"5\" face=\"Verdana\">" + inv.get(i).item.name
                                + "</font></p></html>";
                    } catch (Exception ex) {
                        html =
                                "<html><p><font "
                                + "size=\"5\" face=\"Verdana\">"
                                + "</font></p></html>";
                    }
                    JLabel jl = (JLabel) e.getSource();
                    jl.setToolTipText(html);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }
//    @Override
//    public void paintComponent(Graphics g) {
//
//        ImageIcon imageicon = new ImageIcon(getClass().getResource("/com/RoguePanda/ZAG/Images/template.png"));
//        Image image = imageicon.getImage();
//        /*Draw image on the panel*/
//        super.paintComponent(g);
//
//        if (image != null) {
//            g.drawImage(image, 0, 0, cgui.getWidth(), cgui.getHeight(), this);
//        }
//    }
}
