package shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

class SquareTest {

	@Test
	void testGetX() {
		final int x = 10;
		final int y = 12;
		Square s = new Square(x, y);

		assertEquals(-15, s.getX());
	}

	@Test
	void testGetY() {
		final int x = 10;
		final int y = 12;
		Square s = new Square(x, y);

		assertEquals(-13, s.getY());
	}

	@Test
	void testAcceptXMLVisitor() {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shape><type>square</type><x>-15</x><y>-13</y></shape>";
		final int x = 10;
		final int y = 12;
		Square s = new Square(x, y);
		XMLVisitor xmlVisitor = new XMLVisitor();

		s.accept(xmlVisitor);

		assertEquals(expected, xmlVisitor.getRepresentation());
	}

}
