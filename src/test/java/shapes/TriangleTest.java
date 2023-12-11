package shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class TriangleTest {

	@Test
	void testXPosition() {
		int x = 25;
		int y = 10;
		Triangle triangle = new Triangle(x, y);

		int triangleX = triangle.getX();

		assertEquals(0, triangleX, "X should have value 0");
	}

	@Test
	void testYPosition() {
		int x = 25;
		int y = 10;
		Triangle triangle = new Triangle(x, y);

		int triangleY = triangle.getY();

		assertEquals(-15, triangleY, "Y should have value -15");
	}

	@Test
	void testAcceptXMLVisitor() {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shape><type>triangle</type><x>-15</x><y>-13</y></shape>";
		final int x = 10;
		final int y = 12;
		Triangle s = new Triangle(x, y);
		XMLVisitor xmlVisitor = new XMLVisitor();

		s.accept(xmlVisitor);

		assertEquals(expected, xmlVisitor.getRepresentation());
	}
}
