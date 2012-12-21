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

    BuildingObject(int type) {
    }

    BuildingObject(int[] xpoints, int[] ypoints) {
        int nump = xpoints.length;
        if (ypoints.length < nump) {
            nump = ypoints.length;
        }
        boundingBox = new Polygon(xpoints, ypoints, nump);
    }
}
