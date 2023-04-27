package platformerTest.assets.decorations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import platformerTest.Main;
import platformerTest.assets.DecorationObject;
import platformerTest.game.Player;

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
	
	@Override
	public void draw(Graphics g, Player player, double x, double y, double size) {
		int drawX = (int) (this.x - this.size_x/2 - (x - Main.SIZE_X/2));
		int drawY = (int) (Main.SIZE_Y - (this.y + this.size_y/2) + (y - Main.SIZE_Y/2));
		
		g.setColor(this.color);
		g.setFont(((TextObject) this).font);
		g.drawString(((TextObject) this).text, drawX, drawY);
	}

}
