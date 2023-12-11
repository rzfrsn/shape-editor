package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class SelectableShape {
  private boolean mSelected = false;

  public void select(Graphics2D g2, SimpleShape shape) {
    mSelected = true;
    enableSelectionStyle(g2, shape);
  }

  public void unSelect(Graphics2D g2, SimpleShape shape) {
    mSelected = false;
    disableSelectionStyle(g2, shape);
  }

  public boolean isSelected() {
    return mSelected;
  }

  public void selectOrUnSelect(Graphics2D g2, SimpleShape shape) {
    if (!mSelected) {
      select(g2, shape);
    } else {
      unSelect(g2, shape);
    }
  }

  void enableSelectionStyle(Graphics2D g2, SimpleShape shape) {
    g2.setColor(Color.BLACK);

    float[] dash1 = { 2.0f };
    g2.setStroke( new BasicStroke(1.0f,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        2.0f, dash1, 0.0f));
    g2.draw(new Rectangle2D.Double((shape.getX() - 2), (shape.getY() - 2), 54, 54));
  }

  void disableSelectionStyle(Graphics2D g2, SimpleShape shape) {
    g2.setColor(Color.WHITE);
    g2.setStroke(new BasicStroke());
    g2.draw(new Rectangle2D.Double((shape.getX() - 2), (shape.getY() - 2), 54, 54));
  }
}
