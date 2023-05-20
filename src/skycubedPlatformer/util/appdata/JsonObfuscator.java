package skycubedPlatformer.util.appdata;

public class JsonObfuscator {
	static String obfuscate(String input, int key) {
		char[] output = new char[input.length()];
		
		for (int i=0; i<input.length(); i++) {
			short c = (short) input.charAt(i); //get unicode value
			output[i] = (char) shortValue(32767 - c + key);
			
		}
		
		return String.valueOf(output);
	}
	
	static String deobfuscate(String input, int key) {
		char[] output = new char[input.length()];
		
		for (int i=0; i<input.length(); i++) {
			short c = (short) input.charAt(i); //get unicode value
			output[i] = (char) shortValue(32767 - c + key);
			
		}
		
		return String.valueOf(output);
	}
	
	static short shortValue(int input) {
		if (input >= Short.MIN_VALUE && input <= Short.MAX_VALUE) return (short) input;
		else {
			int value = input;
			
			while (value > 32767 || input < -32768) {
				if (value <= Short.MIN_VALUE) value += 65536;
				else if (value >= Short.MAX_VALUE) value -= 65536;
				
			}
			
			return (short) value;
		}
		
	}
}
