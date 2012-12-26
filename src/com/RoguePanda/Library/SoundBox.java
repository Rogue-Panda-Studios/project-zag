/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.Library;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Oa10712
 */
public class SoundBox {

    public static void playClip(File clipFile) {
        try {
            InputStream in = new FileInputStream(clipFile);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
