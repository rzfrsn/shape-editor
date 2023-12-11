package edu.uga.miage.m1.polygons.gui.persistence.command;

import java.util.ArrayList;

import org.apache.commons.lang3.SerializationUtils;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

public abstract class Command {
  private ArrayList<SimpleShape> mBackup;
  protected JDrawingFrame mDrawingFrame;

  Command(JDrawingFrame jdf) {
    mDrawingFrame = jdf;
    mBackup = new ArrayList<>();
  }

  /**
   * Deep copying drawnShapes in backup
   */
  public void backup() {
    mBackup = SerializationUtils.clone((ArrayList<SimpleShape>) mDrawingFrame.getDrawnShapes());
  }

  public void undo() {
    mDrawingFrame.resetPanel();
    mDrawingFrame.setDrawnShapes(mBackup);
    mDrawingFrame.drawAllShapes();

  }

  public int getBackupSize() {
    return mBackup.size();
  }

  public abstract void execute();
}
