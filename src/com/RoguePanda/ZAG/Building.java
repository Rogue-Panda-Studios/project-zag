package com.RoguePanda.ZAG;

import com.RoguePanda.Library.ImageManipulator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
    BufferedImage outsideSpriteClear;
    private ArrayList<BuildingObject> objects;
    Block level;
    public ArrayList<Rectangle> entrances;
    boolean inside = false;

    Building(int[][] outsidechunks, int[][] insidechunks, ArrayList<BuildingObject> bo, Point loc, Block lev) {
        entrances = new ArrayList<>();
        objects = bo;
        level = lev;
        location = loc;
        location.y = (level.size.height - 10) - size * outsidechunks[0].length - (size - 28);
        outsideSprite = new BufferedImage(size * outsidechunks.length, size * outsidechunks[0].length, BufferedImage.TYPE_INT_ARGB);
        outsideSpriteClear = new BufferedImage(size * outsidechunks.length, size * outsidechunks[0].length, BufferedImage.TYPE_INT_ARGB);
        insideSprite = new BufferedImage(size * insidechunks.length, size * insidechunks[0].length, BufferedImage.TYPE_INT_ARGB);
        Graphics osg = outsideSprite.getGraphics();
        for (int x = 0; x < outsidechunks.length; x++) {
            for (int y = 0; y < outsidechunks[0].length; y++) {
                if (outsidechunks[x][y] == 2 || outsidechunks[x][y] == 3) {
                    entrances.add(new Rectangle(size * x + location.x + 46, size * y + location.y + size / 2 - 8, size - 46 * 2, size - 56));
                }
                if (outsidechunks[x][y] == 4) {
                    entrances.add(new Rectangle(size * x + location.x + 23, size * y + location.y + size / 2 - 35, size - 46, size - 44));
                }
                osg.drawImage(ImageManipulator.scaleImage(
                        ImageManipulator.selectFromSheet(
                        level.buildingsheet,
                        outsidechunks[x][y],
                        128,
                        128),
                        size,
                        size),
                        size * x,
                        size * y,
                        null);
            }
        }
        Graphics osgc = outsideSpriteClear.getGraphics();
        for (int x = 0; x < outsidechunks.length; x++) {
            for (int y = 0; y < outsidechunks[0].length; y++) {
                osgc.drawImage(ImageManipulator.scaleImage(
                        ImageManipulator.selectFromSheet(
                        level.buildingsheet,
                        outsidechunks[x][y] + 8,
                        128,
                        128),
                        size,
                        size),
                        size * x,
                        size * y,
                        null);
            }
        }
        Graphics isg = insideSprite.getGraphics();
        for (int x = 0; x < insidechunks.length; x++) {
            for (int y = 0; y < insidechunks[0].length; y++) {
                isg.drawImage(ImageManipulator.scaleImage(
                        ImageManipulator.selectFromSheet(
                        level.buildingsheet,
                        insidechunks[x][y],
                        128,
                        128),
                        size,
                        size),
                        size * x,
                        size * y,
                        null);
            }
        }
        for (BuildingObject buo : objects) {
            isg.drawImage(buo.sprite, buo.location.x, buo.location.y, null);
        }
        for (BuildingObject bos : bo) {
            isg.drawImage(bos.sprite, bos.location.x, bos.location.y, null);
            bos.boundingBox.translate(location.x + bos.location.x, location.y + bos.location.y);
        }
    }

    public ArrayList<BuildingObject> getObjects() {
        return objects;
    }
}
