package graphics.nim.paint;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class ModelLoader {
	public static int getQuad() {
		float[] vertices = {
			-0.5f, -0.5f, 0,
			0.5f, -0.5f, 0,
			-0.5f, 0.5f, 0,
			-0.5f, 0.5f, 0,
			0.5f, -0.5f, 0,
			0.5f, 0.5f, 0
		};
		
		float[] texCoords = {
			0, 0,
			1, 0,
			0, 1,
			0, 1,
			1, 0,
			1, 1
		};
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(18);
		FloatBuffer texCoordBuffer = BufferUtils.createFloatBuffer(12);
		
		// Store vertices in vertex buffer
		vertexBuffer.put(vertices);
		vertexBuffer.flip();
		
		// Store tex coords in a tex buffer
		texCoordBuffer.put(texCoords);
		texCoordBuffer.flip();
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		int vertexVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexVBO);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);
		
		int texCoordVBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texCoordVBO);
		glBufferData(GL_ARRAY_BUFFER, texCoordBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);
		
		return vao;
	}
}
