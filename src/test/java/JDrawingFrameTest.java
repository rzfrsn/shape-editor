import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape.ShapeTypes;

class JDrawingFrameTest {

	@Test
	void testSetDrawnShapes() {
		JDrawingFrame frame = new JDrawingFrame("test frame");
		List<SimpleShape> shapes = new ArrayList<>();
		shapes.add(new Circle(10, 21));

		frame.setDrawnShapes(shapes);

		assertEquals(1, frame.getDrawnShapesSize());
	}

	@Test
	void testDrawSquare() {
		JDrawingFrame frame = new JDrawingFrame("test frame");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);
		MouseEvent evt = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 118, 54, 1, false,
				MouseEvent.BUTTON1);

		frame.setMSelected(ShapeTypes.SQUARE);

		frame.mouseClicked(evt);

		assertEquals(1, frame.getDrawnShapesSize());
	}

	@Test
	void testDrawCircle() {
		JDrawingFrame frame = new JDrawingFrame("test frame");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);
		MouseEvent evt = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 118, 54, 1, false,
				MouseEvent.BUTTON1);

		frame.setMSelected(ShapeTypes.CIRCLE);

		frame.mouseClicked(evt);

		assertEquals(1, frame.getDrawnShapesSize());
	}

	@Test
	void testDrawTriangle() {
		JDrawingFrame frame = new JDrawingFrame("test frame");
		WindowAdapter wa = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(wa);
		frame.pack();
		frame.setVisible(true);
		MouseEvent evt = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 118, 54, 1, false,
				MouseEvent.BUTTON1);

		frame.setMSelected(ShapeTypes.TRIANGLE);

		frame.mouseClicked(evt);

		assertEquals(1, frame.getDrawnShapesSize());
	}
}
