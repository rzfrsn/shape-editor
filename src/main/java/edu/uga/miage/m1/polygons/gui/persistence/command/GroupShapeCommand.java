package edu.uga.miage.m1.polygons.gui.persistence.command;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;

public class GroupShapeCommand extends Command {

  public GroupShapeCommand(JDrawingFrame jdf) {
    super(jdf);
  }

  @Override
  public void execute() {
    backup();
    mDrawingFrame.groupingShapes();
    mDrawingFrame.resetPanel();
    mDrawingFrame.drawAllShapes();
  }

}
