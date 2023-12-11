package edu.uga.miage.m1.polygons.gui.shapes;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape.ShapeTypes;

public class ShapeFactory {
  /**
   * The singleton.
   */
  private static ShapeFactory mShapeFactory;

  private ShapeFactory() {
  }

  /**
   * Implemente le pattern "singleton" pour la factory
   * 
   * @param nomLogiciel
   * @return
   */

  public static ShapeFactory getInstance() {
    if (mShapeFactory == null) {
      mShapeFactory = new ShapeFactory();
    }

    return mShapeFactory;
  }

  public SimpleShape createShape(ShapeTypes mSelected, int x, int y) {
    switch (mSelected) {
      case CIRCLE:
        return new Circle(x, y);
      case TRIANGLE:
        return new Triangle(x, y);
      case SQUARE:
        return new Square(x, y);
      case CUBE:
        return new Cube(x, y);
      default:
        StringBuilder warning = new StringBuilder("No shape named ");
        warning.append(mSelected);
        JDrawingFrame.LOGGER.warning(warning::toString);
        return null;
    }
  }
}
