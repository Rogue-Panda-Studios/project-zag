/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

/**
 *
 * @author Oa10712
 */
class Loader extends JFrame implements Runnable {

    Thread maingame;
    JProgressBar pro;

    public Loader() {
        pro = new JProgressBar(0, 15);
        this.add(pro);
    }

    @Override
    public void run() {
        //maingame = new Thread(new GUI());
        //maingame.start();

    }
}
