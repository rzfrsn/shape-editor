package edu.uga.miage.m1.polygons.gui.shapes;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitor;

public class GroupedShapes extends SelectableShape implements SimpleShape, Visitable {
  private static final long serialVersionUID = 1234L;
  private List<SimpleShape> mShapes = new ArrayList<>();
  private int mX;
  private int mY;

  public GroupedShapes(List<SimpleShape> shapes) {
    add(shapes);
    initX();
    initY();
  }

  public List<SimpleShape> getShapes() {
    return mShapes;
  }

  public void add(SimpleShape shape) {
    mShapes.add(shape);
  }

  public void add(List<SimpleShape> shapes) {
    mShapes.addAll(shapes);
  }

  @Override
  public void select(Graphics2D g2, SimpleShape shape) {
    for (SimpleShape s : mShapes) {
      SelectableShape selectable = (SelectableShape) s;
      selectable.select(g2, s);
    }
  }

  @Override
  public void unSelect(Graphics2D g2, SimpleShape shape) {
    for (SimpleShape s : mShapes) {
      SelectableShape selectable = (SelectableShape) s;
      selectable.unSelect(g2, s);
    }
  }

  @Override
  public void selectOrUnSelect(Graphics2D g2, SimpleShape shape) {
    if (!super.isSelected()) {
      select(g2, shape);
    } else {
      unSelect(g2, shape);
    }
  }

  @Override
  public boolean isSelected() {
    boolean selected = true;
    for (SimpleShape s : mShapes) {
      SelectableShape selectable = (SelectableShape) s;
      if (!selectable.isSelected()) {
        selected = false;
        break;
      }
    }

    return selected;
  }

  public void remove(List<SimpleShape> shapes) {
    mShapes.removeAll(shapes);
  }

  public void clear() {
    mShapes.clear();
  }

  public void initX() {
    if (mShapes.isEmpty()) {
      mX = 0;
    } else {
      mX = mShapes.get(0).getX();
    }
  }

  public void initY() {
    if (mShapes.isEmpty()) {
      mY = 0;
    } else {
      mY = mShapes.get(0).getY();
    }
  }

  @Override
  public void move(int x, int y) {
    for (SimpleShape shape : mShapes) {
      int deltaX = shape.getX() - getX();
      int deltaY = shape.getY() - getY();
      int finalX = deltaX + x;
      int finalY = deltaY + y;

      shape.move(finalX, finalY);
    }

    initX();
    initY();
  }

  @Override
  public boolean cursorIsOnShape(int x, int y) {
    boolean isOnShape = false;
    for (SimpleShape s : mShapes) {
      if (s.cursorIsOnShape(x, y)) {
        isOnShape = true;
      }
    }
    return isOnShape;
  }

  @Override
  public void draw(Graphics2D g2) {
    mShapes.forEach(s -> s.draw(g2));
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
  public void accept(Visitor visitor) {
    visitor.visit(this);
  }

}
