package graphics.nim.paint;

public class Canvas {
	public static final Vector3f DEFAULT_CANVAS_COLOR = new Vector3f(0.5f, 0.5f, 0.5f);
	
	public static final int DEFAULT_WIDTH = 640;
	public static final int DEFAULT_HEIGHT = 480;
	
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;
	
	private Vector3f canvasColor = DEFAULT_CANVAS_COLOR;
	private float zoom = 1;
	
	public Canvas(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public Vector3f getCanvasColor() {
		return canvasColor;
	}
}
