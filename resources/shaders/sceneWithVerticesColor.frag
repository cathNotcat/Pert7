#version 330
out vec4 frag_color;
in vec4 out_color;
void main() {
//    frag_color = vec4(0,1,0.8f,1);
    frag_color = out_color;
}

