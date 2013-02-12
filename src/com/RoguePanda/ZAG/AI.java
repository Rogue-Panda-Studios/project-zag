/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 *
 * @author Oa10712
 */
public abstract class AI {

    /**
     * An optional counter for use in AI
     */
    int counter = 0;
    /**
     * The Entity that this AI belongs to
     */
    Entity entity;

    /**
     *
     * @return if the AI will execute
     */
    public abstract boolean execute();

    AI(Entity e) {
        entity = e;
    }
}
