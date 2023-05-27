package skycubedPlatformer.util;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

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
	
	public static void playSound(Clip clip, float gain) {
		Thread thread = new Thread(() -> {
			try {
				if (clip != null) {
					if (!clip.isOpen()) Thread.sleep(10);
					
					FloatControl gainControl = (FloatControl) clip
					        .getControl(FloatControl.Type.MASTER_GAIN);
					    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
					    gainControl.setValue(dB);
					    
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
	
	public static void playFinalSound(Clip clip, float gain) {
		Thread thread = new Thread(() -> {
			try {
				if (clip != null) {
					if (!clip.isOpen()) Thread.sleep(10);
					
					FloatControl gainControl = (FloatControl) clip
					        .getControl(FloatControl.Type.MASTER_GAIN);
					    float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
					    gainControl.setValue(dB);
					
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
