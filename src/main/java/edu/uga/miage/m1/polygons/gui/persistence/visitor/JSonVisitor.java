package edu.uga.miage.m1.polygons.gui.persistence.visitor;

import java.io.StringReader;

import javax.json.*;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.GroupedShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape.ShapeTypes;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JSonVisitor implements Visitor {

    private String mRepresentation;

    public JSonVisitor() {
        mRepresentation = "";
    }

    @Override
    public void visit(Circle circle) {
        mRepresentation = this.createShapeJsonObject(circle, ShapeTypes.CIRCLE).toString();
    }

    @Override
    public void visit(Square square) {
        mRepresentation = this.createShapeJsonObject(square, ShapeTypes.SQUARE).toString();
    }

    @Override
    public void visit(Triangle triangle) {
        mRepresentation = this.createShapeJsonObject(triangle, ShapeTypes.TRIANGLE).toString();
    }

    @Override
    public void visit(Cube cube) {
        mRepresentation = this.createShapeJsonObject(cube, ShapeTypes.CUBE).toString();
    }

    @Override
    public void visit(GroupedShapes groupedShapes) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        groupedShapes.getShapes().forEach(s -> {
            Visitable vShape = (Visitable) s;
            vShape.accept(this);
            JsonReader jsonReader = Json.createReader(new StringReader(this.getRepresentation()));
            JsonObject object = jsonReader.readObject();
            jsonReader.close();
            builder.add(object);
        });

        JsonArray shapeJsonArray = builder.build();
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("type", ShapeTypes.GROUPED_SHAPES.toString().toLowerCase())
                .add("x", groupedShapes.getX())
                .add("y", groupedShapes.getY())
                .add("composite", shapeJsonArray)
                .build();

        mRepresentation = jsonObject.toString();
    }

    /**
     * @return the representation in JSon example for a Circle
     *
     *         <pre>
     * {@code
     *  {
     *     "shape": {
     *     	  "type": "circle",
     *        "x": -25,
     *        "y": -25
     *     }
     *  }
     * }
     *         </pre>
     */
    public String getRepresentation() {
        return mRepresentation;
    }

    public JsonObject createShapeJsonObject(SimpleShape shape, ShapeTypes type) {
        return Json.createObjectBuilder()
                .add("type", type.toString().toLowerCase())
                .add("x", shape.getX())
                .add("y", shape.getY())
                .build();
    }

}
