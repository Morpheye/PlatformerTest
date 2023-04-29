package platformerTest.assets.powerups;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import platformerTest.Main;
import platformerTest.assets.triggers.Code;
import platformerTest.assets.triggers.Powerup;
import platformerTest.game.Player;
import platformerTest.menu.GamePanel;

public class OverhealPowerup extends Powerup {

	public int cameraSize;
	
	public OverhealPowerup(double x, double y, double size, int amount) {
		this(x,y,size,null);
		Code code = new Code() {
			@Override
			public void run() {
				GamePanel.player.overheal += amount;
			}
		};
		
		this.code = code;
		
	}
	
	public OverhealPowerup(double x, double y, double size, Code code) {
		super(x, y, size, code);
		this.color = Powerup.COLOR_POWERUP_OVERHEAL;
		try {this.image = ImageIO.read(this.getClass().getResource("/powerups/overheal.png"));
		} catch (IOException e) {}
		
	}
	
	@Override
	public void run() {
		this.code.run();
		GamePanel.createFlash(this.color, 75);
	}
	
	@Override
	public void draw(Graphics g, Player player, double cam_x, double cam_y, double size) {
		super.draw(g, player, cam_x, cam_y, size);
		int drawX = (int) ( (this.x - (this.size_x)/2 - (cam_x - size/2)) * (Main.SIZE/size));
		int drawY = (int) ( (size - (this.y + (this.size_y)/2) + (cam_y - size/2)) * (Main.SIZE/size));

		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(this.image, drawX, drawY, (int) (this.size_x * Main.SIZE/size), (int) (this.size_y * Main.SIZE/size), null);
		
	}

}
