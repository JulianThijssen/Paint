package graphics.nim.paint;

import graphics.nim.paint.util.Math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Engine implements MouseListener, KeyListener {
	private Renderer renderer = new Renderer(this);
	
	//private Queue<Dab> dabQueue = new ArrayBlockingQueue<Dab>(1000000);
	private HashMap<String, Brush> brushSet = new HashMap<String, Brush>();
	public List<Dab> dabs = new ArrayList<Dab>();
	private Vector2f pdab = new Vector2f(0, 0);
	
	public HashMap<String, Canvas> canvasses = new HashMap<String, Canvas>();
	
	private Canvas currentCanvas;
	private Brush currentBrush;
	
	private Vector3f color = new Vector3f(0, 0, 0);

	public Engine() {
		addCanvas("Untitled", 400, 400);
		addBrush("HardBrush", "res/HardBrush.png", 32);
		
		setCanvas("Untitled");
		setBrush("HardBrush");
	}
	
	public void update() {
		if (Mouse.isPressed(Mouse.MOUSE_LEFT)) {
			Vector2f mousePos = Mouse.getPosition();
			addDab(mousePos.x, mousePos.y);
		}
		if (Mouse.isPressed(Mouse.MOUSE_SCROLL)) {
			//view.position.add(Mouse.getDelta().scale(0.1f * engine.getCanvas().getZoom()));
		}
	}
	
	public void render() {
		System.out.println(dabs.size());
		renderer.update(currentCanvas);
	}
	
	public void addDab(float x, float y) {
		Vector2f goal = new Vector2f(x, y);
		Vector2f dir = Vector2f.normalise(goal);
		System.out.println("IN the BEGINNING");
		
		while (Vector2f.distance(goal, pdab) > currentBrush.getSize()) {
			Vector2f newPos = new Vector2f(pdab.x + dir.x, pdab.y + dir.y);
			Dab dab = new Dab(newPos.x * 1/currentCanvas.getZoom(), newPos.y * 1/currentCanvas.getZoom());
			dab.scale.set(currentBrush.getSize(), currentBrush.getSize(), 0);
			dab.color.set(color);
			dabs.add(dab);
			pdab.set(newPos.x, newPos.y);
			System.out.println(Vector2f.distance(goal, pdab));
		}
	}
	
	public void addCanvas(String name, int width, int height) {
		canvasses.put(name, new Canvas(width, height));
	}
	
	public Canvas getCanvas() {
		return currentCanvas;
	}
	
	public void setCanvas(String name) {
		currentCanvas = canvasses.get(name);
	}
	
	public void addBrush(String name, String path, float size) {
		brushSet.put(name, new Brush(TextureLoader.loadImage(path), size));
	}
	
	public Brush getBrush() {
		return currentBrush;
	}
	
	public void setBrush(String name) {
		currentBrush = brushSet.get(name);
	}
	
	public Vector3f getColor() {
		return color;
	}

	/* Input handlers */
	@Override
	public void onMouseScroll(float xoffset, float yoffset) {
		currentCanvas.setZoom(Math.clamp(currentCanvas.getZoom() + yoffset * 0.1f, 0.01f, 100.0f));
	}

	@Override
	public void onMouseMove(float xpos, float ypos) {
		Mouse.setPosition(xpos - Window.width/2, ypos - Window.height/2);
	}

	@Override
	public void onMouseClick(int button, int mods, boolean pressed) {
		Mouse.setState(button, pressed);
	}

	@Override
	public void onKeyPress(int key, int mods) {
		Keyboard.setState(key, true);
		
		if (Keyboard.isPressed(Keyboard.KEY_W)) { color.set(1, 1, 1); }
		if (Keyboard.isPressed(Keyboard.KEY_R)) { color.set(1, 0, 0); }
		if (Keyboard.isPressed(Keyboard.KEY_B)) { color.set(0, 0, 0); }
		if (Keyboard.isPressed(Keyboard.KEY_O)) { color.set(1, 0.5f, 0); }
		if (Keyboard.isPressed(Keyboard.KEY_G)) { color.set(0, 1, 0); }
		if (Keyboard.isPressed(Keyboard.KEY_BRACKET_LEFT)) { currentBrush.changeSize(-3); }
		if (Keyboard.isPressed(Keyboard.KEY_BRACKET_RIGHT)) { currentBrush.changeSize(3); }
	}

	@Override
	public void onKeyRelease(int key, int mods) {
		Keyboard.setState(key, false);
	}
}
