#version 130

uniform sampler2D brush;

uniform vec3 color;

in vec2 pass_texCoords;

out vec4 out_Color;

void main() {
	vec4 tex = texture(brush, pass_texCoords);
	out_Color = vec4(tex.rgb * color, tex.a);
}
