/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author Oa10712
 */
public class ItemStack implements Serializable {
    private static final long serialVersionUID = 1L;

    Item item;
    int amount;
    double weight;
    double size;
    BufferedImage sprite;

    ItemStack(int id, int am) {
        item = new Item(id);
        amount = am;
        weight = item.getWeight() * am;
        size = item.getSize() * am;
        sprite = ImageManipulator.numberizeImage(item.getSprite(), am);
    }

    ItemStack(Item it, int am) {
        item = it;
        amount = am;
        weight = item.getWeight() * am;
        size = item.getSize() * am;
        sprite = ImageManipulator.numberizeImage(item.getSprite(), am);
    }

    ItemStack(int id) {
        item = new Item(id);
        amount = 1;
        weight = item.getWeight();
        size = item.getSize();
        sprite = ImageManipulator.numberizeImage(item.getSprite(), 1);
    }

    ItemStack(Item it) {
        item = it;
        amount = 1;
        weight = item.getWeight();
        size = item.getSize();
        sprite = ImageManipulator.numberizeImage(item.getSprite(), 1);
    }

    @Override
    public String toString() {
        String out = "Item:"+item+";Amount:"+amount;
        return out;
    }
}
