package persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

class JSonVisitorTest {

	@Test
	void testVisitSquare() {
		String expected = "{\"type\":\"square\",\"x\":-15,\"y\":-13}";
		Square s = new Square(10, 12);
		JSonVisitor visitor = new JSonVisitor();

		visitor.visit(s);

		assertEquals(expected, visitor.getRepresentation());
	}

	@Test
	void testVisitTriangle() {
		String expected = "{\"type\":\"triangle\",\"x\":-15,\"y\":-13}";
		Triangle s = new Triangle(10, 12);
		JSonVisitor visitor = new JSonVisitor();

		visitor.visit(s);

		assertEquals(expected, visitor.getRepresentation());
	}

	@Test
	void testVisitCircle() {
		String expected = "{\"type\":\"circle\",\"x\":-15,\"y\":-13}";
		Circle s = new Circle(10, 12);
		JSonVisitor visitor = new JSonVisitor();

		visitor.visit(s);

		assertEquals(expected, visitor.getRepresentation());
	}

}
