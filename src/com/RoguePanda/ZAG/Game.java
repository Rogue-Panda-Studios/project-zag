/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.util.ArrayList;

/**
 * The game that holds all data for a save file
 *
 * @author Oa10712
 */
final class Game {

    ArrayList<Block> levels;
    Block currentLevel;
    CardGUI cgui;
    Player player;

    @SuppressWarnings("unchecked")
    Game(CardGUI c) {
        cgui = c;
        levels = new ArrayList<>();
        player = new Player();
        Block b = new Block(this);
        currentLevel = b;
        addLevel(b);
        player.setLevel(this);
    }

    void addLevel(Block b) {
        levels.add(b);
    }

    void changeLevel(int i) {
        currentLevel = levels.get(i);
        player.setLevel(this);
    }
}
