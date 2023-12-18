package edu.uga.miage.m1.polygons.gui.persistence.adapter;

import java.awt.Graphics2D;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public class AppShapeAdapter implements com.persistence.json.shape.shapes.SimpleShape {
  private SimpleShape appShape;

  public AppShapeAdapter(SimpleShape s) {
    appShape = s;
  }

  @Override
  public int getX() {
    return appShape.getX();
  }

  @Override
  public int getY() {
    return appShape.getY();
  }

  @Override
  public void move(int x, int y) {
    appShape.move(x, y);
  }

  @Override
  public boolean cursorIsOnShape(int cursorX, int cursorY) {
    return appShape.cursorIsOnShape(cursorX, cursorY);
  }

  @Override
  public void draw(Graphics2D g2) {
    appShape.draw(g2);
  }

  @Override
  public JsonObject getJson() {
    Visitable shape = (Visitable) appShape;
    JSonVisitor jsonVisitor = new JSonVisitor();
    shape.accept(jsonVisitor);
    JsonReader jsonReader = Json.createReader(new StringReader(jsonVisitor.getRepresentation()));
    JsonObject object = jsonReader.readObject();
    jsonReader.close();

    return object;
  }
}
