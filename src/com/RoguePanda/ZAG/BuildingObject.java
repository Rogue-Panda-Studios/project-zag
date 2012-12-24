/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

/**
 *
 * @author Oa10712
 */
class BuildingObject {

    public boolean phaze;
    Polygon boundingBox;
    Building building;
    BufferedImage sprite;
    Point location;

    BuildingObject(int type, Point p) {
        int[] xpoints;
        int[] ypoints;
        switch (type) {
            case 0:
                xpoints = new int[]{0, 8, 8, 16, 16, 24, 24, 32, 32, 40, 40, 48, 48, 56, 56, 64, 64, 0};
                ypoints = new int[]{0, 0, 8, 8, 16, 16, 24, 24, 32, 32, 40, 40, 48, 48, 56, 56, 64, 64};
                phaze = true;
                sprite = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
                break;
            default:
                xpoints = new int[]{0, 1, 1, 0};
                ypoints = new int[]{0, 0, 1, 1};
                break;
        }
        boundingBox = new Polygon(xpoints, ypoints, xpoints.length);
        sprite.getGraphics().fillPolygon(boundingBox);
        location = p;
    }

    BuildingObject(int[] xpoints, int[] ypoints, Building b) {
        building = b;
        int nump = xpoints.length;
        if (ypoints.length < nump) {
            nump = ypoints.length;
        }
        boundingBox = new Polygon(xpoints, ypoints, nump);
    }
}
