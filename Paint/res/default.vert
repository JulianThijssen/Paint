#version 130

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;

in vec3 position;
in vec2 texCoords;

out vec2 pass_texCoords;

void main() {
	gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1);
	pass_texCoords = texCoords;
}
