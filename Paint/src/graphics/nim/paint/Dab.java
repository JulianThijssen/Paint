package graphics.nim.paint;

public class Dab {
	public Vector3f position = new Vector3f(0, 0, 0);
	public Vector3f scale = new Vector3f(1, 1, 1);
	public Vector3f color = new Vector3f(0, 0, 0);
	
	public Dab(float x, float y) {
		position.set(x, y, 1);
		scale.set(100, 100, 1);
	}
}
