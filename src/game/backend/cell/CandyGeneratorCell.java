package game.backend.cell;

import game.backend.CandyGame;
import game.backend.level.LevelBase;
import game.backend.element.CandyColor;
import game.backend.element.Element;

public class CandyGeneratorCell extends Cell {
	
	public CandyGeneratorCell(LevelBase levelBase) {
		super(levelBase);
	}
	
	@Override
	public boolean isMovable(){
		return true;
	}
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public Element getContent() {
		int i = (int)(Math.random() * (CandyColor.values().length));
		return CandyGame.instance.level().generateCandy(CandyColor.values()[i]);
	}
	
	@Override
	public Element getAndClearContent() {
		return getContent();
	}

	@Override
	public boolean fallUpperContent() {
		throw new IllegalStateException();
	}
	
	@Override
	public void setContent(Element content) {
		throw new IllegalStateException();
	}
	
	@Override
	public boolean equals(Object obj) {
		return false;
	}

}
