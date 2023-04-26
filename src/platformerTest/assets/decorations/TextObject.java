package platformerTest.assets.decorations;

import java.awt.Color;
import java.awt.Font;

import platformerTest.assets.DecorationObject;

public class TextObject extends DecorationObject {

	public String text;
	public Font font;
	
	public TextObject(double x, double y, Color color, String text) {
		super(x, y, 0, 0, null);
		this.text = text;
		this.color = color;
		
		Font customFont = new Font(Font.MONOSPACED, Font.BOLD, 25);
		
		this.font = customFont;
	
		
	}

}
