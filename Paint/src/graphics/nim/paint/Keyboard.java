package graphics.nim.paint;

public class Keyboard {
	public static final int KEY_W = 87;
	public static final int KEY_R = 82;
	public static final int KEY_B = 66;
	public static final int KEY_O = 79;
	public static final int KEY_G = 71;
	public static final int KEY_BRACKET_LEFT = 91;
	public static final int KEY_BRACKET_RIGHT = 93;
	
	public static boolean[] keys = new boolean[400];
	
	public static void setState(int key, boolean state) {
		keys[key] = state;
	}
	
	public static boolean isPressed(int key) {
		return keys[key];
	}
}
