package persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

class XMLVisitorTest {

	@Test
	void testConvertDocumentToString() {
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shape><type>square</type><x>-15</x><y>-13</y></shape>";
		Square s = new Square(10, 12);
		XMLVisitor visitor = new XMLVisitor();

		visitor.visit(s);

		assertEquals(expected, visitor.getRepresentation());
	}

}
