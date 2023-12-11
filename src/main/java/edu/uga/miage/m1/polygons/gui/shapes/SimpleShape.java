package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * This interface defines the <tt>SimpleShape</tt> extension. This extension
 * is used to draw shapes.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface SimpleShape extends Serializable {

    public enum ShapeTypes {
        SQUARE,
        TRIANGLE,
        CIRCLE,
        CUBE,
        GROUPED_SHAPES
    }

    public int getX();

    public int getY();

    public void move(int x, int y);

    public boolean cursorIsOnShape(int cursorX, int cursorY);

    /**
     * Method to draw the shape of the extension.
     * 
     * @param g2 The graphics object used for painting.
     */
    public abstract void draw(Graphics2D g2);
}
