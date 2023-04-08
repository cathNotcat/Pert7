#version 330

//out, minta variabel di situ dilempar keluar

out vec4 frag_color;
uniform vec4 uni_color;
void main() {
//    frag_color = vec4(0,1,0.8f,1);
    frag_color = uni_color;
}

