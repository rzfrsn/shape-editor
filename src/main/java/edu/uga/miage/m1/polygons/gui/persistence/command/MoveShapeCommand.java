package edu.uga.miage.m1.polygons.gui.persistence.command;

import java.awt.event.MouseEvent;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class MoveShapeCommand extends Command {
  MouseEvent mMouseEvent;

  public MoveShapeCommand(JDrawingFrame jdf, MouseEvent evt) {
    super(jdf);
    mMouseEvent = evt;
  }

  @Override
  public void execute() {
    backup();
    mDrawingFrame.moveShape(mMouseEvent);
    mDrawingFrame.resetPanel();
    mDrawingFrame.drawAllShapes();
  }

}
