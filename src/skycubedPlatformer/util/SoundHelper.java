package skycubedPlatformer.util;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundHelper {
	public static void loadSound(Object obj, Clip clip, String resource) {
		Thread thread = new Thread(() -> {
			try {
				if (resource == null) return;
				InputStream stream = new BufferedInputStream(obj.getClass().getResourceAsStream(resource));
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(stream);
				clip.open(audioStream);
				
			} catch (Exception e) {e.printStackTrace();}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}
	
	public static void playSound(Clip clip) {
		Thread thread = new Thread(() -> {
			try {
				if (clip != null) {
					if (!clip.isOpen()) Thread.sleep(10);
					if (clip.isActive() || clip.isRunning()) {
						clip.setMicrosecondPosition(0);
					} else {
						clip.setMicrosecondPosition(0);
						clip.start();
					}
				}
			} catch (Exception e) {e.printStackTrace();}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}
	
	public static void playFinalSound(Clip clip) {
		Thread thread = new Thread(() -> {
			try {
				if (clip != null) {
					if (!clip.isOpen()) Thread.sleep(10);
					clip.setMicrosecondPosition(0);
					clip.start();
					
					Thread.sleep(10);
					
					while (clip.isActive()) {}
					clip.close();
				}
			} catch (Exception e) {e.printStackTrace();}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}
}
