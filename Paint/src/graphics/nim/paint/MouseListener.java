package graphics.nim.paint;

public interface MouseListener {
	public void onMouseScroll(float xoffset, float yoffset);
	public void onMouseMove(float xpos, float ypos);
	public void onMouseClick(int button, int mods, boolean pressed);
}
