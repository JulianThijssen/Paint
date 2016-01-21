package graphics.nim.paint;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureLoader {
	public static Texture loadImage(String path) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			ByteBuffer data = BufferUtils.createByteBuffer(width * height * 4);
			
			data.put((byte[]) image.getRaster().getDataElements(0, 0, width, height, null));
			data.flip();
			
			int handle = uploadImage(width, height, data);
			
			Texture texture = new Texture(handle, width, height);
			
			return texture;
		} catch (IOException e) {
			System.err.println("Failed to load image: " + path);
		}
		
		return null;
	}
	
	private static int uploadImage(int width, int height, ByteBuffer data) {
		int handle = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, handle);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		glGenerateMipmap(GL_TEXTURE_2D);
		
		return handle;
	}
}
