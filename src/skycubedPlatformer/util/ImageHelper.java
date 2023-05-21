package skycubedPlatformer.util;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import skycubedPlatformer.util.appdata.DataManager;

public class ImageHelper {
	
	public static BufferedImage screenshotImage;
	
	public void init() {
		try {
			screenshotImage = ImageIO.read(this.getClass().getResource("/gui/screenshot.png"));
		} catch (Exception e) {}
	}
	
	public static String formatCurrency(Long amt) {
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(2);
		
		BigDecimal coins = BigDecimal.valueOf(amt);
		String coinText;
		if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000_000_000L))) + "♚";} //Quintillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000_000L))) + "Q";} //Quadrillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000_000L))) + "T";} //Trillion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000_000L))) + "B";} //Billion
		else if (coins.compareTo(BigDecimal.valueOf(1_000_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000_000L))) + "M";} //Million
		else if (coins.compareTo(BigDecimal.valueOf(1_000L)) == 1) {
			coinText = df.format(coins.divide(BigDecimal.valueOf(1_000L))) + "k";} //Thousand
		else coinText = df.format(coins);
		
		return coinText;
	}
}
