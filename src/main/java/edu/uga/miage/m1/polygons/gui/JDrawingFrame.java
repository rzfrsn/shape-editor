package edu.uga.miage.m1.polygons.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import edu.uga.miage.m1.polygons.gui.persistence.command.AddShapeCommand;
import edu.uga.miage.m1.polygons.gui.persistence.command.Command;
import edu.uga.miage.m1.polygons.gui.persistence.command.CommandHistory;
import edu.uga.miage.m1.polygons.gui.persistence.command.GroupShapeCommand;
import edu.uga.miage.m1.polygons.gui.persistence.command.MoveShapeCommand;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.JSonVisitor;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.Visitable;
import edu.uga.miage.m1.polygons.gui.persistence.visitor.XMLVisitor;
import edu.uga.miage.m1.polygons.gui.shapes.GroupedShapes;
import edu.uga.miage.m1.polygons.gui.shapes.SelectableShape;
import edu.uga.miage.m1.polygons.gui.shapes.ShapeFactory;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape;
import edu.uga.miage.m1.polygons.gui.shapes.SimpleShape.ShapeTypes;

/**
 * This class represents the main application class, which is a JFrame subclass
 * that manages a toolbar of shapes and a drawing canvas.
 *
 * @author <a href=
 *         "mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 */
public class JDrawingFrame extends JFrame implements MouseListener, MouseMotionListener, KeyListener {
	public enum Shapes {
		SQUARE, TRIANGLE, CIRCLE, CUBE_PANEL, GROUPED_SHAPES
	}

	private static final long serialVersionUID = 1L;
	protected static final String EXPORT_JSON = "EXPORT_JSON";
	protected static final String EXPORT_XML = "EXPORT_XML";
	protected static final String IMPORT_JSON = "IMPORT_JSON";
	protected static final String GROUP_SHAPES = "GROUP_SHAPES";
	public static final Logger LOGGER = Logger.getLogger(JDrawingFrame.class.getName());

	private JToolBar mToolbar;
	private JToolBar mSecondToolbar;
	private ShapeTypes mSelected;
	private JPanel mPanel;
	private JLabel mLabel;
	private transient ActionListener mReusableActionListener = new ShapeActionListener();
	private EnumMap<ShapeTypes, JButton> mButtons = new EnumMap<>(ShapeTypes.class); // Tracks buttons to manage the
																																										// background.

	private transient ArrayList<SimpleShape> mDrawnShapes = new ArrayList<>();
	private transient CommandHistory mCommandHistory = new CommandHistory();

	private SimpleShape mMovingShape = null;

	public void setMSelected(ShapeTypes s) {
		mSelected = s;
	}

	/**
	 * Default constructor that populates the main window.
	 * 
	 * @param frameName
	 */
	public JDrawingFrame(String frameName) {
		super(frameName);

		// Instantiates components
		mToolbar = new JToolBar("Toolbar");
		mSecondToolbar = new JToolBar("Second Toolbar");

		JPanel toolbarPanel = new JPanel(new BorderLayout());
		toolbarPanel.add(mSecondToolbar, BorderLayout.EAST);
		toolbarPanel.add(mToolbar, BorderLayout.CENTER);

		mPanel = new JPanel();
		mPanel.setBackground(Color.WHITE);
		mPanel.setLayout(null);
		mPanel.setMinimumSize(new Dimension(480, 480));
		mPanel.addComponentListener(new PanelComponentListener());
		mPanel.addMouseListener(this);
		mPanel.addMouseMotionListener(this);
		mPanel.addKeyListener(this);

		mLabel = new JLabel(" ", SwingConstants.LEFT);

		// Fills the panel
		setLayout(new BorderLayout());
		add(toolbarPanel, BorderLayout.NORTH);
		add(mPanel, BorderLayout.CENTER);
		add(mLabel, BorderLayout.SOUTH);

		// Add shapes btns in the menu
		addButtonOnToolbar(mToolbar, ShapeTypes.SQUARE, createImageIcon("square.png"));
		addButtonOnToolbar(mToolbar, ShapeTypes.TRIANGLE, createImageIcon("triangle.png"));
		addButtonOnToolbar(mToolbar, ShapeTypes.CIRCLE, createImageIcon("circle.png"));
		addButtonOnToolbar(mToolbar, ShapeTypes.CUBE, createImageIcon("underc.png"));

		// Add other action btns in the menu
		addButtonOnToolbar(mSecondToolbar, GROUP_SHAPES, createImageIcon("group.png"), new SecondToolBarBtnListener());
		addButtonOnToolbar(mSecondToolbar, EXPORT_JSON, createImageIcon("json.png"), new SecondToolBarBtnListener());
		addButtonOnToolbar(mSecondToolbar, EXPORT_XML, createImageIcon("xml.png"), new SecondToolBarBtnListener());

		// set window size
		setPreferredSize(new Dimension(500, 500));
	}

	/**
	 * Injects an available <tt>SimpleShape</tt> into the drawing frame.
	 * 
	 * @param name The name of the injected <tt>SimpleShape</tt>.
	 * @param icon The icon associated with the injected <tt>SimpleShape</tt>.
	 */
	private void addButtonOnToolbar(JToolBar toolbar, ShapeTypes actionName, ImageIcon icon) {
		JButton button = new JButton(icon != null ? icon : null);
		button.setBorderPainted(false);
		mButtons.put(actionName, button);
		button.setActionCommand(actionName.toString());
		button.addActionListener(mReusableActionListener);

		if (mSelected == null) {
			button.doClick();
		}

		toolbar.add(button);
		toolbar.validate();
		repaint();
	}

	private void addButtonOnToolbar(JToolBar toolbar, String actionName, ImageIcon icon, ActionListener actionListener) {
		JButton button = new JButton(icon != null ? icon : null);
		button.setBorderPainted(false);
		button.setToolTipText(actionName);
		button.setActionCommand(actionName);
		button.addActionListener(actionListener);

		if (mSelected == null) {
			button.doClick();
		}

		toolbar.add(button);
		toolbar.validate();
		repaint();
	}

	private ImageIcon createImageIcon(String imgName) throws NullPointerException {
		return new ImageIcon(
				getClass().getResource(String.format("images/%s", imgName)));
	}

	/**
	 * Implements method for the <tt>MouseListener</tt> interface to
	 * draw the selected shape into the drawing canvas.
	 * 
	 * @param evt The associated mouse event.
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {
		if (evt.isShiftDown()) {
			Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
			for (SimpleShape s : mDrawnShapes) {
				if (s.cursorIsOnShape(evt.getX(), evt.getY())) {
					SelectableShape shape = (SelectableShape) s;
					shape.selectOrUnSelect(g2, s);
					break;
				}
			}
		} else {
			AddShapeCommand addShapeCommand = new AddShapeCommand(this, evt);
			addShapeCommand.execute();
			mCommandHistory.push(addShapeCommand);
		}

		// mPanel should requestFocus to activate keyListener
		mPanel.requestFocus();
	}

	/**
	 * @return the size of drawn shapes
	 */
	public int getDrawnShapesSize() {
		return mDrawnShapes.size();
	}

	/**
	 * Draw selected shape at the mouseClick position
	 * 
	 * @param evt in order to get the position of the mouse
	 */
	public void drawShape(MouseEvent evt) {
		if (mPanel.contains(evt.getX(), evt.getY())) {
			Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
			ShapeFactory shapeFactory = ShapeFactory.getInstance();
			SimpleShape shape = shapeFactory.createShape(mSelected, evt.getX(), evt.getY());
			shape.draw(g2);
			mDrawnShapes.add(shape);
		}
	}

	/**
	 * mDrawnShapes getter
	 *
	 * @return mDrawnShapes
	 */
	public List<SimpleShape> getDrawnShapes() {
		return mDrawnShapes;
	}

	/**
	 * mDrawnShapes setter
	 * 
	 * @param shapeList
	 */
	public void setDrawnShapes(List<SimpleShape> shapeList) {
		mDrawnShapes = new ArrayList<>(shapeList);
	}

	/**
	 * Call lastCommand.undo()
	 */
	public void undoing() {
		if (!mCommandHistory.isEmpty()) {
			Command command = mCommandHistory.pop();
			if (command != null) {
				command.undo();
			}
		}
	}

	public void groupShapes() {
		GroupShapeCommand groupShapeCommand = new GroupShapeCommand(this);
		groupShapeCommand.execute();
		mCommandHistory.push(groupShapeCommand);
	}

	public void groupingShapes() {
		Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
		List<SimpleShape> selectedShapes = new ArrayList<>();

		for (SimpleShape s : mDrawnShapes) {
			SelectableShape shape = (SelectableShape) s;
			if (shape.isSelected()) {
				shape.unSelect(g2, s);
				selectedShapes.add(s);
			}
		}

		// if there is more than 1 figure then group it
		if (selectedShapes.size() > 1) {
			mDrawnShapes.removeAll(selectedShapes);

			SimpleShape groupedShape = new GroupedShapes(selectedShapes);
			mDrawnShapes.add(groupedShape);
		}
	}

	private class SecondToolBarBtnListener implements ActionListener {
		private void writeJsonInFile(JsonObject jsonObject, String filePath) {
			try (FileWriter fileWriter = new FileWriter(filePath)) {
				fileWriter.write(jsonObject.toString());
				StringBuilder infoMsg = new StringBuilder("Exported successfully into a JSON file at : ");
				infoMsg.append(filePath);
				displayInfoMsg(infoMsg);
			} catch (IOException e) {
				LOGGER.warning(e.getMessage());
			}
		}

		private void writeXmlInFile(Document document, String filePath) {
			TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
			DOMSource source = new DOMSource(document);
			Transformer transformer;
			FileWriter fileWriter;

			try {
				transformer = transformerFactory.newTransformer();
				fileWriter = new FileWriter(filePath);

				StreamResult result = new StreamResult(fileWriter);
				transformer.transform(source, result);
				StringBuilder infoMsg = new StringBuilder("Exported successfully into a XML file at : ");
				infoMsg.append(filePath);
				displayInfoMsg(infoMsg);
			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
			}

		}

		private String createFilePathExport(String fileName) {
			String directoryPath = getDownloadsDirectory();
			String filePath = directoryPath + File.separator + fileName;

			// Check if the directory exists or create it
			File directory = new File(directoryPath);
			if (!directory.exists() && directory.mkdir()) {
				LOGGER.info("Directory created successfully.");
			}

			return filePath;
		}

		@Override
		public void actionPerformed(ActionEvent evt) {
			String actionCommand = evt.getActionCommand();

			switch (actionCommand) {
				case GROUP_SHAPES:
					groupShapes();
					mPanel.requestFocus();
					break;

				case EXPORT_JSON:
					JsonArrayBuilder builder = Json.createArrayBuilder();

					for (SimpleShape s : mDrawnShapes) {
						Visitable shape = (Visitable) s;
						JSonVisitor jsonVisitor = new JSonVisitor();
						shape.accept(jsonVisitor);
						JsonReader jsonReader = Json.createReader(new StringReader(jsonVisitor.getRepresentation()));
						JsonObject object = jsonReader.readObject();
						jsonReader.close();
						builder.add(object);
					}

					JsonArray shapeJsonArray = builder.build();
					JsonObject allShapesJsonObject = Json.createObjectBuilder().add("shapes", shapeJsonArray).build();

					// write the content into json file
					writeJsonInFile(allShapesJsonObject, createFilePathExport("allShapes.json"));
					break;

				case EXPORT_XML:
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Select the JSON file to import");
					fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("JSON file", "json"));

					int userSelection = fileChooser.showOpenDialog(null);

					if (userSelection == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						String filePath = selectedFile.getAbsolutePath();

						// Utilisez maintenant le chemin du fichier (filePath) pour votre logique de
						// lecture JSON
						System.out.println("Fichier sélectionné : " + filePath);

						try (JsonReader reader = Json.createReader(new FileReader(filePath))) {
							JsonObject jsonObject = reader.readObject();
							JsonArray jsonArray = jsonObject.getJsonArray("shapes");

							mDrawnShapes.clear();

							Iterator<JsonValue> it = jsonArray.iterator();
							while (it.hasNext()) {
								JsonObject shape = (JsonObject) it.next();

								String type = shape.get("type").toString().toUpperCase();
								Integer x = Integer.parseInt(shape.get("x").toString());
								Integer y = Integer.parseInt(shape.get("y").toString());

								ShapeTypes shapeType = ShapeTypes.valueOf(type.substring(1, type.length() -
										1));
								ShapeFactory shapeFactory = ShapeFactory.getInstance();
								SimpleShape newShape = shapeFactory.createShape(shapeType, x, y);
								mDrawnShapes.add(newShape);
							}

							resetPanel();
							drawAllShapes();

						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println("Aucun fichier sélectionné.");
					}
					break;

				case IMPORT_JSON:
					try {
						DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
						factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",
								true);
						DocumentBuilder docBuilder = factory.newDocumentBuilder();
						Document document = docBuilder.newDocument();

						Element shapesElement = document.createElement("shapes");
						document.appendChild(shapesElement);

						for (SimpleShape s : mDrawnShapes) {
							Visitable shape = (Visitable) s;
							XMLVisitor visitor = new XMLVisitor();
							shape.accept(visitor);
							InputSource is = new InputSource(new StringReader(visitor.getRepresentation()));
							Document doc = docBuilder.parse(is);
							Element element = doc.getDocumentElement();
							document.adoptNode(element);
							shapesElement.appendChild(element);
						}

						// write the content into xml file
						writeXmlInFile(document, createFilePathExport("allShapes.xml"));
					} catch (Exception e) {
						LOGGER.warning(e.getMessage());
					}
					break;

				default:
					break;
			}
		}
	}

	/**
	 * Implements an empty method for the <tt>MouseListener</tt> interface.
	 * 
	 * @param evt The associated mouse event.
	 */
	public void mouseExited(MouseEvent evt) {
		mLabel.setText(" ");
		mLabel.repaint();
	}

	/**
	 * Implements an empty method for the <tt>MouseMotionListener</tt>
	 * interface.
	 * 
	 * @param evt The associated mouse event.
	 */
	public void mouseMoved(MouseEvent evt) {
		mLabel.setText("(" + evt.getX() + " , " + evt.getY() + ")");
		mLabel.repaint();
	}

	/**
	 * Simple action listener for shape tool bar buttons that sets
	 * the drawing frame's currently selected shape when receiving
	 * an action event.
	 */
	private class ShapeActionListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			// Iterator sur tous les boutons
			Iterator<ShapeTypes> keys = mButtons.keySet().iterator();

			while (keys.hasNext()) {
				ShapeTypes shape = keys.next();
				JButton btn = mButtons.get(shape);

				if (evt.getActionCommand().equals(shape.toString())) {
					btn.setBorderPainted(true);
					mSelected = shape;
				} else {
					btn.setBorderPainted(false);
				}
				btn.repaint();
			}
		}
	}

	/**
	 * @IMPORTANT will be used later
	 */
	public void resetPanel() {
		Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, mPanel.getWidth(), mPanel.getHeight());
	}

	/**
	 * @IMPORTANT will be used in future
	 */
	public void drawAllShapes() {
		Graphics2D g2 = (Graphics2D) mPanel.getGraphics();
		mDrawnShapes.forEach(s -> s.draw(g2));
	}

	public void displayInfoMsg(StringBuilder infoMsg) {
		JOptionPane.showMessageDialog(mPanel, infoMsg, "Notification", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String getDownloadsDirectory() {
		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		File homeDirectory = fileSystemView.getHomeDirectory();
		File downLoadsDirectory = new File(homeDirectory, "Downloads");

		return downLoadsDirectory.getAbsolutePath();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.isControlDown() || e.isMetaDown()) && e.getKeyCode() == KeyEvent.VK_Z) {
			undoing();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		// Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if (evt.isControlDown()) {
			for (SimpleShape shape : mDrawnShapes) {
				if (shape.cursorIsOnShape(evt.getX(), evt.getY())) {
					mMovingShape = shape;
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		if (mMovingShape != null) {
			MoveShapeCommand moveShapeCommand = new MoveShapeCommand(this, evt);
			moveShapeCommand.execute();
			mCommandHistory.push(moveShapeCommand);

			mMovingShape = null;
		}
	}

	public void moveShape(MouseEvent evt) {
		for (SimpleShape shape : mDrawnShapes) {
			if (shape.getX() == mMovingShape.getX() && shape.getY() == mMovingShape.getY()) {
				shape.move(evt.getX(), evt.getY());
			}
		}
	}

	private class PanelComponentListener implements ComponentListener {

		@Override
		public void componentResized(ComponentEvent e) {
			resetPanel();
			drawAllShapes();
		}

		@Override
		public void componentMoved(ComponentEvent e) {
			// Auto-generated method stub
		}

		@Override
		public void componentShown(ComponentEvent e) {
			// Auto-generated method stub
		}

		@Override
		public void componentHidden(ComponentEvent e) {
			// Auto-generated method stub
		}
	}

}
