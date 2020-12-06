package game.backend.level;

import game.backend.Figure;
import game.backend.FigureDetector;
import game.backend.GameListener;
import game.backend.GameState;
import game.backend.cell.CandyGeneratorCell;
import game.backend.cell.Cell;
import game.backend.element.Candy;
import game.backend.element.CandyColor;
import game.backend.element.Element;
import game.backend.element.Wall;
import game.backend.move.Move;
import game.backend.move.MoveMaker;

import java.awt.Point;
import java.util.*;

public abstract class LevelBase {
	
	public static final int SIZE = 9;

	private Cell[][] g = new Cell[SIZE][SIZE];
	private Map<Cell, Point> gMap = new HashMap<>();
	private GameState state;
	private List<GameListener> listeners = new ArrayList<>();
	private MoveMaker moveMaker;
	private FigureDetector figureDetector;

	public abstract String getDisplayString();
	protected abstract GameState newState();
	protected void fillCells() {
		Cell wallCell = new Cell(this);
		wallCell.setContent(new Wall());
		Cell candyGenCell = new CandyGeneratorCell(this);

		//corners
		g[0][0].setAround(candyGenCell, g[0][1], wallCell, g[1][0]);
		g[SIZE-1][0].setAround(candyGenCell, g[SIZE-1][1], g[SIZE-2][0], wallCell);
		g[0][SIZE-1].setAround(g[0][SIZE-2], wallCell, wallCell, g[1][SIZE-1]);
		g[SIZE-1][SIZE-1].setAround(g[SIZE-1][SIZE-2], wallCell, g[SIZE-2][SIZE-1], wallCell);

		//upper line cells
		for (int x = 1; x < SIZE-1; x++) {
			g[x][0].setAround(candyGenCell,g[x][1],g[x-1][0],g[x+1][0]);
		}
		//bottom line cells
		for (int x = 1; x < SIZE-1; x++) {
			g[x][SIZE-1].setAround(g[x][SIZE-2], wallCell, g[x-1][SIZE-1],g[x+1][SIZE-1]);
		}
		//left line cells
		for (int y = 1; y < SIZE-1; y++) {
			g[0][y].setAround(g[0][y-1],g[0][y+1], wallCell,g[1][y]);
		}
		//right line cells
		for (int y = 1; y < SIZE-1; y++) {
			g[SIZE-1][y].setAround(g[SIZE-1][y-1],g[SIZE-1][y+1], g[SIZE-2][y], wallCell);
		}
		//central cells
		for (int x = 1; x < SIZE-1; x++) {
			for (int y = 1; y < SIZE-1; y++) {
				g[x][y].setAround(g[x][y-1],g[x][y+1],g[x-1][y],g[x+1][y]);
			}
		}
	}
	
	public GameState state(){
		return state;
	}
	
	public void initialize() {
		moveMaker = new MoveMaker(this);
		figureDetector = new FigureDetector(this);
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				g[x][y] = new Cell(this);
				gMap.put(g[x][y], new Point(x,y));
			}
		}
		fillCells();
		fallElements();
	}	

	public Element get(int y, int x) {
		return g[x][y].getContent();
	}
	
	public Cell getCell(int y, int x) {
		return g[x][y];
	}

	public void fallElements() {
		for (int y=SIZE-1;y>=0;y--){
			for (int x=0;x<SIZE;x++){
				if (g[x][y].isEmpty()) {
					if (g[x][y].fallUpperContent()) {
						y = SIZE;
						break;
					}
				}
			}
		}
	}
	
	public void clearContent(int y, int x) {
		g[x][y].clearContent();
	}
	
	public void setContent(int y, int x, Element e) {
		g[x][y].setContent(e);
	}
	
	public boolean tryMove(int y1, int x1, int y2, int x2) {
		Move move = moveMaker.getMove(y1, x1, y2, x2);
		swapContent(y1, x1, y2, x2);
		if (move.isValid()) {
			move.removeElements();
			fallElements();
			return true;
		} else {
			swapContent(y1, x1, y2, x2);
			return false;
		}
	}	
	
	public Figure tryRemove(Cell cell) {
		if (gMap.containsKey(cell)) {
			Point p = gMap.get(cell);
			Figure f = figureDetector.checkFigure(p.y, p.x);
			if (f != null) {
				removeFigure(p.y, p.x, f);
			}
			return f;
		}
		return null;
	}
	
	private void removeFigure(int y, int x, Figure f) {
		CandyColor color = ((Candy)get(y, x)).getColor();
		if (f.hasReplacement()) {
			setContent(y, x, f.generateReplacement(color));
		} else {
			clearContent(y, x);
		}
		for (Point p: f.getPoints()) {
			clearContent(y + p.y, x + p.x);
		}
	}

	public void swapContent(int y1, int x1, int y2, int x2) {
		Element e = g[x1][y1].getContent();
		g[x1][y1].setContent(g[x2][y2].getContent());
		g[x2][y2].setContent(e);
		wasUpdated();
	}
	
	public GameState createState() {
		this.state = newState();
		return this.state;
	}
	
	public void addListener(GameListener listener) {
		listeners.add(listener);
	}
	
	public void wasUpdated(){
		if (listeners.size() > 0) {
			for (GameListener gl: listeners) {
				gl.gridUpdated();
			}
		}
	}
	
	public void cellExplosion(Element e) {
		for (GameListener gl: listeners) {
			gl.cellExplosion(e);
		}
	}

	public Element generateCandy(CandyColor color){
		return new Candy(color);
	}

}
