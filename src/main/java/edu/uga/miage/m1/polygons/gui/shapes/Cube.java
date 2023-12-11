package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitor;
import edu.uga.singleshape.CubePanel;

public class Cube extends SelectableShape implements SimpleShape, Visitable {

  private static final long serialVersionUID = 123L;
  protected int mX;
  protected int mY;

  public Cube(int x, int y) {
    mX = x;
    mY = y;
  }

  @Override
  public void draw(Graphics2D g2) {
    CubePanel c = new CubePanel(50, mX, mY);
    c.paintComponent(g2);
  }

  @Override
  public boolean cursorIsOnShape(int cursorX, int cursorY) {
    return cursorX >= mX - 25 && cursorX <= mX + 25 &&
        cursorY >= mY - 25 && cursorY <= mY + 25;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

  @Override
  public void move(int x, int y) {
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

  @Override
  void enableSelectionStyle(Graphics2D g2, SimpleShape shape) {
    g2.setColor(Color.BLACK);

    float[] dash1 = { 2.0f };
    g2.setStroke(new BasicStroke(1.0f,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        2.0f, dash1, 0.0f));
    g2.draw(new Rectangle2D.Double((shape.getX() - 27), (shape.getY() - 27), 54, 54));
  }

  @Override
  void disableSelectionStyle(Graphics2D g2, SimpleShape shape) {
    g2.setColor(Color.WHITE);
    g2.setStroke(new BasicStroke());
    g2.draw(new Rectangle2D.Double((shape.getX() - 27), (shape.getY() - 27), 54, 54));
  }

  private int minus25(int a) {
    return a - 25;
  }

}
