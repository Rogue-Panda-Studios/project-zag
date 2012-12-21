/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RoguePanda.ZAG;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author Oa10712
 */
public class BoundBox extends Rectangle2D.Double {

    private static final long serialVersionUID = 1L;

    BoundBox(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public Rectangle2D setX(double nx) {
        this.x = nx;
        return this;
    }

    public Rectangle2D setY(double nY) {
        this.y = nY;
        return this;
    }

    public Rectangle2D setWidth(double nw) {
        this.width = nw;
        return this;
    }

    public Rectangle2D setHeight(double nh) {
        this.height = nh;
        return this;
    }
}
