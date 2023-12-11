package edu.uga.miage.m1.polygons.gui.persistence.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {
  private Deque<Command> mHistory;

  public CommandHistory() {
    mHistory = new ArrayDeque<>();
  }

  public void push(Command c) {
    mHistory.push(c);
  }

  public Command pop() {
    return mHistory.pop();
  }

  public boolean isEmpty() {
    return mHistory.isEmpty();
  }
}
