package graphics.nim.paint;

public class Application {
	public static final int RENDER_RATE = 100;
	
	private Window window;
	private Engine engine;
	
	public Application() {
		window = new Window("Paint", 640, 480);
		engine = new Engine();

		window.addMouseListener(engine);
		window.addKeyListener(engine);
	}
	
	public void start() {
		update();
	}
	
	public void update() {
		long nextRender = System.nanoTime();
		int frames = 0;
		
		long time = System.currentTimeMillis();
		
		while (!window.isClosed()) {
			window.poll();
			engine.update();
			
			if (System.nanoTime() > nextRender) {
				engine.render();
				window.update();
				
				nextRender += 10000000;
				
			}
			frames++;
			if (System.currentTimeMillis() - time > 1000) {
				System.out.println("Frames: " + frames);
				frames = 0;
				time = System.currentTimeMillis();
			}
		}
	}
	
	public static void main(String[] args) {
		Application app = new Application();
		app.start();
	}
}
