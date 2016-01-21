package graphics.nim.paint;

public class Brush {
	public static final int DEFAULT_SIZE = 32;
	
	private float size = DEFAULT_SIZE;
	private Texture texture;
	
	public Brush(Texture texture, float size) {
		this.texture = texture;
		this.size = size;
	}
	
	public int getHandle() {
		return texture.handle;
	}
	
	public float getSize() {
		return size;
	}
	
	public void changeSize(float change) {
		size += change;
	}
}
