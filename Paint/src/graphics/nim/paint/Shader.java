package graphics.nim.paint;

import static org.lwjgl.opengl.GL20.*;
import java.util.HashMap;

public class Shader {
	private int handle;
	private HashMap<String, Integer> locations = new HashMap<String, Integer>();
	
	public Shader(int handle) {
		this.handle = handle;
	}
	
	public int getHandle() {
		return handle;
	}
	
	public int uniform(String name) {
		if (locations.containsKey(name)) {
			return locations.get(name);
		}
		int location = glGetUniformLocation(handle, name);
		locations.put(name, location);
		return location;
	}
}
