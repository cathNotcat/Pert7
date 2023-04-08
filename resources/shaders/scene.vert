#version 330

layout(location=0) in vec3 position;
// karena dikemudian" hari ada transformnya, jadi taro di scene.vert
uniform mat4 model;
void main() {
// agar bisa disimpan dilayar, kasi variable gl_Position (gabisa di ganti" karena pny gsl nya lgsung)
    gl_Position = model * vec4(position, 1.0);
}
