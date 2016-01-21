package graphics.nim.paint;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL;

public class Window {
	public static final String DEFAULT_TITLE = "Window";
	public static final int    DEFAULT_WIDTH = 640;
	public static final int    DEFAULT_HEIGHT = 480;
	
	private String title = DEFAULT_TITLE;
	public static int width = DEFAULT_WIDTH;
	public static int height = DEFAULT_HEIGHT;
	
	private long window;
	
	// Input callbacks
	private List<MouseListener> mouseListeners = new ArrayList<MouseListener>();
	private List<KeyListener> keyListeners = new ArrayList<KeyListener>();
	
	private MouseClickCallback mouseClickCallback = new MouseClickCallback();
	private MouseMoveCallback mouseMoveCallback = new MouseMoveCallback();
	private ScrollCallback scrollCallback = new ScrollCallback();
	
	private KeyCallback keyCallback = new KeyCallback();
	
	public Window() {
		this(DEFAULT_TITLE, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		glfwInit();
		window = glfwCreateWindow(width, height, title, 0, 0);
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		glfwSetMouseButtonCallback(window, mouseClickCallback);
		glfwSetCursorPosCallback(window, mouseMoveCallback);
		glfwSetScrollCallback(window, scrollCallback);
		
		glfwSetKeyCallback(window, keyCallback);
		
		glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		glfwSwapInterval(0);
	}
	
	public void addMouseListener(MouseListener listener) {
		mouseListeners.add(listener);
	}
	
	public void addKeyListener(KeyListener listener) {
		keyListeners.add(listener);
	}
	
	public void update() {
		glfwSwapBuffers(window);
	}
	
	public void poll() {
		glfwPollEvents();
	}
	
	public boolean isClosed() {
		return glfwWindowShouldClose(window) == GL_TRUE;
	}
	
	public void close() {
		glfwSetWindowShouldClose(window, GL_TRUE);
	}
	
	public void destroy() {
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private class KeyCallback extends GLFWKeyCallback {
		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			for (KeyListener listener: keyListeners) {
				if (action == GLFW_PRESS) {
					listener.onKeyPress(key, mods);
				} else {
					listener.onKeyRelease(key, mods);
				}
			}
		}
	}
	
	private class MouseClickCallback extends GLFWMouseButtonCallback {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			for (MouseListener listener: mouseListeners) {
				if (action == GLFW_PRESS) {
					listener.onMouseClick(button, action, true);
				} else {
					listener.onMouseClick(button, action, false);
				}
			}
		}
	}
	
	private class MouseMoveCallback extends GLFWCursorPosCallback {
		@Override
		public void invoke(long window, double xpos, double ypos) {
			for (MouseListener listener: mouseListeners) {
				listener.onMouseMove((float) xpos, (float) ypos);
			}
		}
	}
	
	private class ScrollCallback extends GLFWScrollCallback {
		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			for (MouseListener listener: mouseListeners) {
				listener.onMouseScroll((float) xoffset, (float) yoffset);
			}
		}
	}
}
