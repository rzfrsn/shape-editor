package edu.uga.miage.m1.polygons.gui.persistence.visitor;

import java.io.StringReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

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
public class XMLVisitor implements Visitor {

  private String mRepresentation;
  private static final Logger LOGGER = Logger.getLogger(XMLVisitor.class.getName());

  public XMLVisitor() {
    mRepresentation = "";
  }

  @Override
  public void visit(Circle circle) {
    try {
      mRepresentation = convertDocumentToString(createShapeXmlDocument(circle, ShapeTypes.CIRCLE));
    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
    }
  }

  @Override
  public void visit(Square square) {
    try {
      mRepresentation = this.convertDocumentToString(createShapeXmlDocument(square, ShapeTypes.SQUARE));
    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
    }
  }

  @Override
  public void visit(Triangle triangle) {
    try {
      mRepresentation = convertDocumentToString(createShapeXmlDocument(triangle, ShapeTypes.TRIANGLE));
    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
    }
  }

  @Override
  public void visit(Cube cubePanel) {
    try {
      mRepresentation = convertDocumentToString(createShapeXmlDocument(cubePanel, ShapeTypes.CUBE));
    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
    }
  }

  @Override
  public void visit(GroupedShapes groupedShapes) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
      factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",
          true);
      DocumentBuilder docBuilder = factory.newDocumentBuilder();
      Document document = docBuilder.newDocument();

      Element shapeElement = document.createElement("shape");
      Element typeElement = document.createElement("type");
      Element xElement = document.createElement("x");
      Element yElement = document.createElement("y");
      Element compositeElement = document.createElement("composite");

      typeElement.appendChild(document.createTextNode(ShapeTypes.GROUPED_SHAPES.toString().toLowerCase()));
      xElement.appendChild(document.createTextNode(Integer.toString(groupedShapes.getX())));
      yElement.appendChild(document.createTextNode(Integer.toString(groupedShapes.getY())));

      for (SimpleShape s : groupedShapes.getShapes()) {
        Visitable vShape = (Visitable) s;
        vShape.accept(this);
        InputSource is = new InputSource(new StringReader(this.getRepresentation()));
        Document doc = docBuilder.parse(is);
        Element element = doc.getDocumentElement();
        document.adoptNode(element);
        compositeElement.appendChild(element);
      }

      shapeElement.appendChild(typeElement);
      shapeElement.appendChild(xElement);
      shapeElement.appendChild(yElement);
      shapeElement.appendChild(compositeElement);

      document.appendChild(shapeElement);

      mRepresentation = convertDocumentToString(document);
    } catch (Exception e) {
      LOGGER.warning(e.getMessage());
    }
  }

  /**
   * @return the representation in JSon example for a Triangle:
   *
   *         <pre>
   * {@code
   *  <shape>
   *    <type>triangle</type>
   *    <x>-25</x>
   *    <y>-25</y>
   *  </shape>
   * }
   * </pre>
   */
  public String getRepresentation() {
    return mRepresentation;
  }

  private Document createShapeXmlDocument(SimpleShape shape, ShapeTypes type) throws ParserConfigurationException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
    factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.newDocument();
    Element shapeElement = document.createElement("shape");

    Element typeElement = document.createElement("type");
    Element xElement = document.createElement("x");
    Element yElement = document.createElement("y");

    typeElement.appendChild(document.createTextNode(type.toString().toLowerCase()));
    xElement.appendChild(document.createTextNode(Integer.toString(shape.getX())));
    yElement.appendChild(document.createTextNode(Integer.toString(shape.getY())));

    shapeElement.appendChild(typeElement);
    shapeElement.appendChild(xElement);
    shapeElement.appendChild(yElement);

    document.appendChild(shapeElement);

    return document;
  }

  private String convertDocumentToString(Document document) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();

    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(document);
    StreamResult result = new StreamResult(new java.io.StringWriter());
    transformer.transform(source, result);

    return result.getWriter().toString();
  }

}
