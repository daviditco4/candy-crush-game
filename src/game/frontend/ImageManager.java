package game.frontend;

import game.backend.element.*;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private static final String IMAGE_PATH = "images/";
	private Map<String, Image> images;

	//Se cachean las imagenes guardadas en la carpeta imagenes
	public ImageManager() {
		WrappedCandy wc = new WrappedCandy();
		VerticalStripedCandy vc = new VerticalStripedCandy();
		HorizontalStripedCandy hc = new HorizontalStripedCandy();
		images = new HashMap<>();
		images.put(new Nothing().getKey(), new Image(IMAGE_PATH + "nothing.png"));
		images.put(new Bomb().getKey(),  new Image(IMAGE_PATH + "bomb.png"));
		images.put(new Wall().getKey(),  new Image(IMAGE_PATH + "wall.png"));
		for (CandyColor cc: CandyColor.values()) {
			images.put(new Candy(cc).getKey(),   new Image(IMAGE_PATH + cc.toString().toLowerCase() + "Candy.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			wc.setColor(cc);
			images.put(wc.getKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "Wrapped.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			vc.setColor(cc);
			images.put(vc.getKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "VStripped.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			hc.setColor(cc);
			images.put(hc.getKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "HStripped.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			images.put(new CandyTimeBonus(cc).getKey(),   new Image(IMAGE_PATH + cc.toString().toLowerCase() + "Candy.png"));
		}
		images.put("JAIL",   new Image(IMAGE_PATH + "jail.png"));
		images.put("JELLY",   new Image(IMAGE_PATH + "jelly.png"));
	}

	//Permite obtener una imagen asociada a un elemento
	public Image getImage(Element e) {
		return images.get(e.getKey());
	}
	//Permite acceder a una imagen directamente
	public Image getImage(String s) {
		return images.get(s);
	}

}
