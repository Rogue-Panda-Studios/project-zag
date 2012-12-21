/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 * This handles the removal of Entities form a level and sets the Entity to be
 * collected by Garbage Cleanup
 *
 * @author Oa10712
 */
class Cleanup {

    static void remove(Block level, Entity aThis) {
        level.entities.remove(aThis);
        aThis = null;
    }
}
