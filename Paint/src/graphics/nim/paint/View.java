package graphics.nim.paint;

public class View {
	public static final float DEFAULT_LEFT = -1;
	public static final float DEFAULT_RIGHT = 1;
	public static final float DEFAULT_TOP = 1;
	public static final float DEFAULT_BOTTOM = -1;
	public static final float DEFAULT_ZNEAR = -10;
	public static final float DEFAULT_ZFAR = 10;
	
	private float left = DEFAULT_LEFT;
	private float right = DEFAULT_RIGHT;
	private float top = DEFAULT_TOP;
	private float bottom = DEFAULT_BOTTOM;
	private float zNear = DEFAULT_ZNEAR;
	private float zFar = DEFAULT_ZFAR;
	
	public Vector2f position = new Vector2f(0, 0);
	private Matrix4f proj = new Matrix4f();
	private Matrix4f view = new Matrix4f();
	
	public View(float width, float height, float depth) {
		this.left = -width / 2;
		this.right = width / 2;
		this.top = -height / 2;
		this.bottom = height / 2;
		this.zNear = -depth / 2;
		this.zFar = depth / 2;
	}
	
	public Matrix4f getProjMatrix() {
		proj.setIdentity();
		proj.array[0] = 2 / (right - left);
		proj.array[5] = 2 / (top - bottom);
		proj.array[10] = -2 / (zFar - zNear);
		proj.array[12] = (-right - left) / (right - left);
		proj.array[13] = (-top - bottom) / (top - bottom);
		proj.array[14] = (-zFar - zNear) / (zFar - zNear);
		
		return proj;
	}
	
	public Matrix4f getViewMatrix() {
		view.setIdentity();
		view.translate(new Vector3f(position.x, position.y, 0));
		
		return view;
	}
}
