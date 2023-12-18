package shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;

class CubeTest {
  @Test
  void testGetX() {
    final int x = 10;
    final int y = 12;
    Cube s = new Cube(x, y);

    assertEquals(10, s.getX());
  }

  @Test
  void testGetY() {
    final int x = 10;
    final int y = 12;
    Cube s = new Cube(x, y);

    assertEquals(12, s.getY());
  }

  @Test
  void testAcceptJsonVisitor() {
    String expected = "{\"type\":\"cube\",\"x\":10,\"y\":12}";
    final int x = 10;
    final int y = 12;
    Cube s = new Cube(x, y);
    JSonVisitor jsonVisitor = new JSonVisitor();

    s.accept(jsonVisitor);

    assertEquals(expected, jsonVisitor.getRepresentation());
  }
}
