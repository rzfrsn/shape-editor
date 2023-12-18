package shapes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.uga.miage.m1.polygons.gui.persistence.visitor.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.GroupedShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.Square;

public class GroupedShapesTest {
  @Test
  void testGetX() {
    List<SimpleShape> list = new ArrayList<>();
    list.add((SimpleShape) new Cube(10, 12));
    list.add((SimpleShape) new Square(15, 10));

    GroupedShapes s = new GroupedShapes(list);

    assertEquals(10, s.getX());
  }

  @Test
  void testGetY() {
    List<SimpleShape> list = new ArrayList<>();
    list.add((SimpleShape) new Cube(10, 12));
    list.add((SimpleShape) new Square(15, 10));

    GroupedShapes s = new GroupedShapes(list);

    assertEquals(12, s.getY());
  }

  @Test
  void testAcceptJsonVisitor() {
    String expected = "{\"type\":\"grouped_shapes\",\"x\":10,\"y\":12,\"composite\":[{\"type\":\"cube\",\"x\":10,\"y\":12},{\"type\":\"square\",\"x\":-10,\"y\":-15}]}";
    List<SimpleShape> list = new ArrayList<>();
    list.add((SimpleShape) new Cube(10, 12));
    list.add((SimpleShape) new Square(15, 10));

    GroupedShapes s = new GroupedShapes(list);
    JSonVisitor jsonVisitor = new JSonVisitor();

    s.accept(jsonVisitor);

    assertEquals(expected, jsonVisitor.getRepresentation());
  }
}
