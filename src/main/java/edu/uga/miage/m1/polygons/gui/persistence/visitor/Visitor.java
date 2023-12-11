package edu.uga.miage.m1.polygons.gui.persistence.visitor;

import edu.uga.miage.m1.polygons.gui.shapes.Circle;
import edu.uga.miage.m1.polygons.gui.shapes.Cube;
import edu.uga.miage.m1.polygons.gui.shapes.GroupedShapes;
import edu.uga.miage.m1.polygons.gui.shapes.Square;
import edu.uga.miage.m1.polygons.gui.shapes.Triangle;

/**
 * You must define a method for each type of Visitable.
 * 
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public interface Visitor {

    public void visit(Circle circle);

    public void visit(Square square);

    public void visit(Triangle triangle);

    public void visit(Cube cubePanel);

    public void visit(GroupedShapes groupedShapes);
}
