package graphics.nim.paint;

public class Mouse {
	public static final int MOUSE_LEFT = 0;
	public static final int MOUSE_RIGHT = 1;
	public static final int MOUSE_SCROLL = 2;
	
	private static Vector2f position = new Vector2f(0, 0);
	private static Vector2f delta = new Vector2f(0, 0);
	
	private static boolean[] pressed = new boolean[3];
	
	public static Vector2f getDelta() {
		return delta;
	}
	
	public static Vector2f getPosition() {
		return position;
	}
	
	public static void setPosition(float x, float y) {
		delta = Vector2f.sub(new Vector2f(x, y), position);
		position.set(x, y);
	}
	
	public static boolean isPressed(int button) {
		return pressed[button];
	}
	
	public static void setState(int button, boolean state) {
		pressed[button] = state;
	}
}
