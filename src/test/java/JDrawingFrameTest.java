import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import edu.uga.miage.m1.polygons.gui.JDrawingFrame;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;

class JDrawingFrameTest {

	@Test
	void testSetDrawnShapes() {
		JDrawingFrame frame = new JDrawingFrame("test frame");
		List<SimpleShape> shapes = new ArrayList<>();
		shapes.add(new Circle(10, 21));

		frame.setDrawnShapes(shapes);

		assertEquals(1, frame.getDrawnShapesSize());
	}

}
