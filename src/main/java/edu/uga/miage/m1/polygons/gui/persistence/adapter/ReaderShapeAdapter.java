package edu.uga.miage.m1.polygons.gui.persistence.adapter;

import java.awt.Graphics2D;

import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class ReaderShapeAdapter implements SimpleShape {
  com.persistence.json.shape.shapes.SimpleShape readerShape;

  public ReaderShapeAdapter(com.persistence.json.shape.shapes.SimpleShape s) {
    readerShape = s;
  }

  @Override
  public int getX() {
    return readerShape.getX();
  }

  @Override
  public int getY() {
    return readerShape.getY();
  }

  @Override
  public void move(int x, int y) {
    readerShape.move(x, y);
  }

  @Override
  public boolean cursorIsOnShape(int cursorX, int cursorY) {
    return readerShape.cursorIsOnShape(cursorX, cursorY);
  }

  @Override
  public void draw(Graphics2D g2) {
    readerShape.draw(g2);
  }

}
