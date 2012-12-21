/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.util.ArrayList;

/**
 * The object that holds the ItemStacks in possession of a specified Entity
 *
 * @author Oa10712
 */
public class Inventory extends ArrayList<ItemStack> {

    private static final long serialVersionUID = 1L;
    private double weight;
    private double maxWeight;//kilograms
    private double size;
    private double maxSize;//cubic cm

    Inventory() {
        weight = 0;
        maxWeight = 24;
        size = 0;
        maxSize = 29500;
        /*for (int i = 0; i < 40; i++) {
            this.addItemStack(new ItemStack(new Item(63)));
        }*/
    }

    /**
     * Attempts to add an ItemStack to the Inventory
     *
     * @param is The ItemStack to be added
     * @return True if the ItemStack is successfully added
     */
    boolean addItemStack(ItemStack is) {
        boolean success = true;
        updateInventory();
        if (size + is.size < maxSize && weight + is.weight < maxWeight) {
            add(is);
        } else {
            success = false;
        }
        updateInventory();
       // System.out.println(this);
        return success;
    }

    /**
     * Removes an ItemStack from the inventory
     *
     * @param is
     * @return The ItemStack removed
     */
    ItemStack removeItemStack(ItemStack is) {// TODO Add capability to remove part of a stack
        boolean success = false;
        updateInventory();
        if (remove(is)) {
            success = true;
        }
        updateInventory();
        return is;
    }

    ItemStack updateItemStack(int is, int change) {
        ItemStack is1;
        updateInventory();
        is1 = get(is);
        is1.amount += change;
        if (is1.amount == 0) {
            removeItemStack(is1);
        }
        updateInventory();
        return is1;
    }

    /**
     * Updates the weight and used size of the inventory
     */
    private void updateInventory() {
        weight = 0;
        size = 0;
        for (ItemStack is : this) {
            weight += is.weight;
            size += is.size;
        }
    }

    /**
     * Pushes ItemStacks with the same item to one stack, if possible
     */
    private void compressItemStacks() {

    }
}
