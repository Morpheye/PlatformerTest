package platformerTest.assets.decoration;

import java.awt.Color;

import platformerTest.assets.DecorationObject;

public class TextObject extends DecorationObject {

	public String text;
	
	public TextObject(double x, double y, Color color, String text) {
		super(x, y, 0, 0, null);
		this.text = text;
		this.color = color;
	}

}
