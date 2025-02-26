package game.backend.element;

public class Candy extends Element {

	private CandyColor color;
	
	public Candy() {
	}
	//Seteo de colores
	public Candy(CandyColor color) {
		this.color = color;
	}
	
	public CandyColor getColor() {
		return color;
	}
	
	public void setColor(CandyColor color) {
		this.color = color;
	}
	
	@Override
	public boolean isMovable() {
		return true;
	}
	
	@Override
	public int hashCode() {
		return ((color == null) ? 0 : color.hashCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Candy))
			return false;
		Candy other = (Candy) obj;
		return color == other.color;
	}
	
	@Override
	public String getKey() {
		return color.toString() + "-CANDY";
	}
	
	@Override
	public long getScore() {
		return 50;
	}
}
