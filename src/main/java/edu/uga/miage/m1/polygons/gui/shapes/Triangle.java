/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitor;

/**
 * This inner class implements the triangle <tt>SimpleShape</tt> service.
 * It simply provides a <tt>draw()</tt> that paints a triangle.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Triangle extends SelectableShape implements SimpleShape, Visitable {

    private static final long serialVersionUID = 123456L;
    protected int mX;
    protected int mY;

    public Triangle(int x, int y) {
        mX = minus25(x);
        mY = minus25(y);
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * 
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(mX, mY, Color.GREEN, (mX + 50), mY, Color.WHITE);

        g2.setPaint(gradient);
        int[] xcoords = { mX + 25, mX, mX + 50 };
        int[] ycoords = { mY, mY + 50, mY + 50 };
        GeneralPath polygon = new GeneralPath(Path2D.WIND_EVEN_ODD, xcoords.length);
        polygon.moveTo((mX + 25), mY);
        for (int i = 0; i < xcoords.length; i++) {
            polygon.lineTo(xcoords[i], ycoords[i]);
        }
        polygon.closePath();
        g2.fill(polygon);
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(polygon);
    }

    @Override
    public void move(int x, int y) {
        mX = minus25(x);
        mY = minus25(y);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean cursorIsOnShape(int cursorX, int cursorY) {
        return cursorX >= mX && cursorX <= mX + 50 &&
                cursorY >= mY && cursorY <= mY + 50;
    }

    private int minus25(int a) {
        return a - 25;
    }
}
