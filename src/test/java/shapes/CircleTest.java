package shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Circle;

class CircleTest {
  @Test
  void testGetX() {
    final int x = 10;
    final int y = 12;
    Circle s = new Circle(x, y);

    assertEquals(-15, s.getX());
  }

  @Test
  void testGetY() {
    final int x = 10;
    final int y = 12;
    Circle s = new Circle(x, y);

    assertEquals(-15, s.getX());
  }

  @Test
  void testAcceptXMLVisitor() {
    String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><shape><type>circle</type><x>-15</x><y>-13</y></shape>";
    final int x = 10;
    final int y = 12;
    Circle s = new Circle(x, y);
    XMLVisitor xmlVisitor = new XMLVisitor();

    s.accept(xmlVisitor);

    assertEquals(expected, xmlVisitor.getRepresentation());
  }
}
