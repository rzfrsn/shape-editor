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
import java.awt.geom.Rectangle2D;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitor;

/**
 * This class implements the square <tt>SimpleShape</tt> extension.
 * It simply provides a <tt>draw()</tt> that paints a square.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class Square extends SelectableShape implements SimpleShape, Visitable {

    private static final long serialVersionUID = 12345L;
    protected int mX;
    protected int mY;

    public Square(int x, int y) {
        mX = minus25(x);
        mY = minus25(y);
    }

    /**
     * Implements the <tt>SimpleShape.draw()</tt> method for painting
     * the shape.
     * 
     * @param g2 The graphics object used for painting.
     */
    public void draw(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(mX, mY, Color.BLUE, (float) mX + 50, mY, Color.WHITE);
        g2.setPaint(gradient);
        g2.fill(new Rectangle2D.Double(mX, mY, 50, 50));
        BasicStroke wideStroke = new BasicStroke(2.0f);
        g2.setColor(Color.black);
        g2.setStroke(wideStroke);
        g2.draw(new Rectangle2D.Double(mX, mY, 50, 50));
    }

    @Override
    public int getX() {
        return mX;
    }

    @Override
    public int getY() {
        return mY;
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

    private int minus25(int a) {
        return a - 25;
    }

    @Override
    public boolean cursorIsOnShape(int cursorX, int cursorY) {
        return cursorX >= mX && cursorX <= mX + 50 &&
                cursorY >= mY && cursorY <= mY + 50;
    }
}
