package graphics.nim.paint.util;

public class Math {
	public static float clamp(float f, float l, float h) {
		if (f < l)
			return l;
		if (f > h)
			return h;
		return f;
	}
}
