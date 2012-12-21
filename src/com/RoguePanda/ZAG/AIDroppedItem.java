/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the actions for DroppedItems, such as detecting if the player
 * should pick it up
 *
 * @author Oa10712
 */
public class AIDroppedItem extends AI {

    int waiter = 0;
    DroppedItem di;

    /**
     *
     * @param e The DroppedItem that owns this AI
     */
    public AIDroppedItem(DroppedItem e) {
        super(e);
        di = e;
    }

    @Override
    public boolean execute() {
        if (entity.boundingBox.intersects(entity.level.game.player.boundingBox)) {
            entity.level.game.player.items.addItemStack(di.itemst);
            Cleanup.remove(entity.level, entity);
        }
        if (waiter > 5) {
            waiter = 0;
            entity.health--;
        } else {
            waiter++;
        }
        if (entity.health < 0) {
            Cleanup.remove(entity.level, entity);
        }
        return true;
    }
}
