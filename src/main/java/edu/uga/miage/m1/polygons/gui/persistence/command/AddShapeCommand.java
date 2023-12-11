package edu.uga.miage.m1.polygons.gui.persistence.command;

import java.awt.event.MouseEvent;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class AddShapeCommand extends Command {
  private MouseEvent mMouseEvt;

  public AddShapeCommand(JDrawingFrame drawingFrame, MouseEvent evt) {
    super(drawingFrame);
    mMouseEvt = evt;
  }

  @Override
  public void execute() {
    backup();
    mDrawingFrame.drawShape(mMouseEvt);
  }
}
