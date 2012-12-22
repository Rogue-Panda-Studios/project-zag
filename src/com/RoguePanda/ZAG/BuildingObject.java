/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Oa10712
 */
class BuildingObject {

    public boolean phaze;
    Polygon boundingBox;

    BuildingObject(int type, Point p) {
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
        boundingBox.translate(p.x, p.y);
    }

    BuildingObject(int[] xpoints, int[] ypoints) {
        int nump = xpoints.length;
        if (ypoints.length < nump) {
            nump = ypoints.length;
        }
        boundingBox = new Polygon(xpoints, ypoints, nump);
    }
}
