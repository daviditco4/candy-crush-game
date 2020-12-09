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
	public boolean firstMoveDone = false;

	public abstract String getDisplayMessage();
	protected abstract GameState newState();
	public void updateFixedTime(){}

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
		state = newState();
	}

	public Element get(int x, int y) {
		return g[x][y].getContent();
	}
	
	public Cell getCell(int x, int y) {
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
	
	public void clearContent(int x, int y) {
		g[x][y].clearContent();
	}

	public void clearContentSpecial(int x, int y) {
		clearContent(x, y);
		g[x][y].onSpecialDestroyed();
	}
	
	public void setContent(int x, int y, Element e) {
		g[x][y].setContent(e);
	}
	
	public boolean tryMove(int x1, int y1, int x2, int y2) {
		Move move = moveMaker.getMove(x1, y1, x2, y2);
		swapContent(x1, y1, x2, y2);
		if (move.isValid()) {
			state().addMove();
			if (!firstMoveDone) firstMoveDone = true;
			move.removeElements();
			fallElements();
			wasUpdated();
			return true;
		} else {
			swapContent(x1, y1, x2, y2);
			return false;
		}
	}	
	
	public Figure tryRemove(Cell cell) {
		if (gMap.containsKey(cell)) {
			Point p = gMap.get(cell);
			Figure f = figureDetector.checkFigure(p.x, p.y);
			if (f != null) {
				removeFigure(p.x, p.y, f);
			}
			return f;
		}
		return null;
	}
	
	private void removeFigure(int x, int y, Figure f) {
		CandyColor color = ((Candy)get(x, y)).getColor();
		if (f.hasReplacement()) {
			setContent(x, y, f.generateReplacement(color));
		} else {
			clearContent(x, y);
		}
		for (Point p: f.getPoints()) {
			clearContent(x + p.x, y + p.y);
		}
	}

	public void swapContent(int x1, int y1, int x2, int y2) {
		Element e = g[x1][y1].getContent();
		g[x1][y1].setContent(g[x2][y2].getContent());
		g[x2][y2].setContent(e);
		wasUpdated();
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

	public abstract String getVictoryMessage();
	public abstract String getLosingMessage();

}
