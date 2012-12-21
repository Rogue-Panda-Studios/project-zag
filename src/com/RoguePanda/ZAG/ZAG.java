/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

/**
 *
 * @author Oa10712
 */
public class ZAG {

    Thread loader;
    static Thread mainGame;
    static Thread gameUpdate;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CardGUI gui = new CardGUI();
        mainGame = new Thread(gui);
        mainGame.start();
        gameUpdate = new Thread(new gameUpdates(gui, 60));
        gameUpdate.start();
    }
}
