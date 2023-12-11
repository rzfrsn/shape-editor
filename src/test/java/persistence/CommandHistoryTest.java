package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.persistence.command.AddShapeCommand;
import edu.uga.miage.m1.polygons.gui.persistence.command.CommandHistory;

class CommandHistoryTest {

	@Test
	void testPush() {
		JDrawingFrame frame = new JDrawingFrame("frame for test");
		AddShapeCommand addShapeCommand = new AddShapeCommand(frame, new MouseEvent(frame, 0, 0, 0, 0, 0, 0, false));
		CommandHistory cmdHist = new CommandHistory();

		cmdHist.push(addShapeCommand);

		assertFalse(cmdHist.isEmpty());
	}

	@Test
	void testPop() {
		JDrawingFrame frame = new JDrawingFrame("frame for test");
		AddShapeCommand addShapeCommand = new AddShapeCommand(frame, new MouseEvent(frame, 0, 0, 0, 0, 0, 0, false));
		CommandHistory cmdHist = new CommandHistory();
		cmdHist.push(addShapeCommand);

		AddShapeCommand result = (AddShapeCommand) cmdHist.pop();

		assertEquals(addShapeCommand, result);
	}

}
