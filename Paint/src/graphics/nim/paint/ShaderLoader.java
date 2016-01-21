package graphics.nim.paint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ShaderLoader {
	private static final int LOG_SIZE = 1024;
	
	public static Shader loadShader(String path) {
		int vert = loadSubShader(path + ".vert", GL_VERTEX_SHADER);
		int frag = loadSubShader(path + ".frag", GL_FRAGMENT_SHADER);
		
		int program = glCreateProgram();
		
		if (glGetShaderi(vert, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error in vertex shader: " + glGetShaderInfoLog(vert, LOG_SIZE));
		}
		if (glGetShaderi(frag, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error in fragment shader: " + glGetShaderInfoLog(frag, LOG_SIZE));
		}
		
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		
		glLinkProgram(program);
		glValidateProgram(program);
		
		Shader shader = new Shader(program);
		
		return shader;
	}
	
	private static int loadSubShader(String path, int type) {
		StringBuilder source = new StringBuilder();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(path));
			
			String line = null;
			while ((line = in.readLine()) != null) {
				source.append(line).append("\n");
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("Failed to find sub shader: " + path);
		} catch (IOException e) {
			System.err.println("An error occurred while reading sub shader");
		}
		
		int handle = glCreateShader(type);
		glShaderSource(handle, source);
		glCompileShader(handle);
		
		return handle;
	}
}
