package graphics.nim.paint;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {
	private Engine engine;
	private View view = new View(Window.width, Window.height, 20);
	private Matrix4f modelMatrix = new Matrix4f();
	
	private Shader defaultShader;
	private Shader brushShader;
	private int quad;
	
	public Renderer(Engine engine) {
		this.engine = engine;
		
		defaultShader = ShaderLoader.loadShader("res/default");
		brushShader = ShaderLoader.loadShader("res/brush");
		quad = ModelLoader.getQuad();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void update(Canvas canvas) {
		Vector3f canvasColor = canvas.getCanvasColor();
		glClearColor(canvasColor.x, canvasColor.y, canvasColor.z, 1);
		glClear(GL_COLOR_BUFFER_BIT);
		
		glUseProgram(defaultShader.getHandle());
		glUniformMatrix4fv(defaultShader.uniform("projectionMatrix"), false, view.getProjMatrix().getBuffer());
		
		// Draw canvas
		modelMatrix.setIdentity();
		modelMatrix.scale(new Vector3f(engine.getCanvas().getWidth(), engine.getCanvas().getHeight(), 1));
		modelMatrix.scale(new Vector3f(engine.getCanvas().getZoom(), engine.getCanvas().getZoom(), 1));
		
		glUniformMatrix4fv(defaultShader.uniform("viewMatrix"), false, view.getViewMatrix().getBuffer());
		glUniformMatrix4fv(defaultShader.uniform("modelMatrix"), false, modelMatrix.getBuffer());
		
		glBindVertexArray(quad);
		glDrawArrays(GL_TRIANGLES, 0, 6);
		
		glUseProgram(brushShader.getHandle());
		glUniformMatrix4fv(brushShader.uniform("projectionMatrix"), false, view.getProjMatrix().getBuffer());
		
		// Draw brush strokes
		glActiveTexture(0);
		glBindTexture(GL_TEXTURE_2D, engine.getBrush().getHandle());
		glUniform1i(brushShader.uniform("brush"), 0);
		
		for (Dab dab: engine.dabs) {
			modelMatrix.setIdentity();
			modelMatrix.scale(new Vector3f(engine.getCanvas().getZoom(), engine.getCanvas().getZoom(), 1));
			modelMatrix.translate(dab.position);
			modelMatrix.scale(dab.scale);
			
			glUniform3f(brushShader.uniform("color"), dab.color.x, dab.color.y, dab.color.z);
			glUniformMatrix4fv(brushShader.uniform("viewMatrix"), false, view.getViewMatrix().getBuffer());
			glUniformMatrix4fv(brushShader.uniform("modelMatrix"), false, modelMatrix.getBuffer());
			
			glDrawArrays(GL_TRIANGLES, 0, 6);
		}
		
		// Draw cursor
		modelMatrix.setIdentity();
		
		modelMatrix.translate(new Vector3f(Mouse.getPosition(), 0));
		modelMatrix.scale(new Vector3f(engine.getCanvas().getZoom(), engine.getCanvas().getZoom(), 1));
		modelMatrix.scale(new Vector3f(engine.getBrush().getSize(), engine.getBrush().getSize(), 0));
		
		Vector3f brushColor = engine.getColor();
		glUniform3f(brushShader.uniform("color"), brushColor.x, brushColor.y, brushColor.z);
		glUniformMatrix4fv(brushShader.uniform("viewMatrix"), false, view.getViewMatrix().getBuffer());
		glUniformMatrix4fv(brushShader.uniform("modelMatrix"), false, modelMatrix.getBuffer());
		
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}
}
