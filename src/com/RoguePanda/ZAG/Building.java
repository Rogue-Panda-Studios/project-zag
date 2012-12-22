package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * This is a building to be placed on screen.
 *
 * @author Oa10712
 */
class Building {

    final int size = 128;
    Point location;
    BufferedImage insideSprite;
    BufferedImage outsideSprite;
    BuildingObject[] objects;
    Block level;

    Building(int[][] chunks, BuildingObject[] bo, Point loc, Block lev) {
        objects = bo;
        level = lev;
        location = loc;
        outsideSprite = new BufferedImage(size * chunks[0].length, size * chunks.length, BufferedImage.TYPE_INT_ARGB);
        Graphics osg = outsideSprite.getGraphics();
        for (int x = 0; x < chunks[0].length; x++) {
            for (int y = 0; y < chunks.length; y++) {
                osg.drawImage(ImageManipulator.scaleImage(
                        ImageManipulator.selectFromSheet(
                        level.buildingsheet,
                        chunks[x][y],
                        128,
                        128),
                        size,
                        size),
                        size * x,
                        size * y,
                        null);
            }
        }
        location.y = 590 - size*chunks.length-(size-28);
    }
}
