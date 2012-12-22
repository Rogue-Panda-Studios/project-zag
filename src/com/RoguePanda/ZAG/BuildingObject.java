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

    BuildingObject(int type, Point p, Building b) {
        building = b;
        int[] xpoints;
        int[] ypoints;
        switch (type) {
            case 0:
                xpoints = new int[]{0, 4, 4, 8, 8, 12, 12, 16, 16, 20, 20, 24, 24, 28, 28, 32, 32, 0};
                ypoints = new int[]{0, 0, 4, 4, 8, 8, 12, 12, 16, 16, 20, 20, 24, 24, 28, 28, 32, 32};
                phaze = true;
                break;
            default:
                xpoints = new int[]{0, 1, 1, 0};
                ypoints = new int[]{0, 0, 1, 1};
                break;
        }
        boundingBox = new Polygon(xpoints, ypoints, xpoints.length);
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
