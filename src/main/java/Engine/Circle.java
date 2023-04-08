package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Circle extends Object {
    public Vector3f cp;
    public Float rx, ry;
    public float x,y;
    public int degree, count;
    int ibo;
    public Circle(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color,Vector3f cp,Float rx,Float ry) {
        super(shaderModuleDataList, vertices, color);
        this.cp = cp;
        this.rx = rx;
        this.ry = ry;
        ibo = glGenBuffers();
        createCircle();
        setupVAOVBO();
    }

    public void setCp(Vector3f cp) {
        this.cp = cp;
    }

    public void setCp(float x, float y, float z) {
        this.cp.x = x;
        this.cp.y = y;
        this.cp.z = z;
    }

    public Vector3f getCp() {
        return cp;
    }

    public void createCircle() {
        //vertices clear
        vertices.clear();
        // Rumus lingkaran
        degree = 0;
        for (double i = degree; i < 360; i += 0.01f) {
            x = (float) (cp.x + rx * Math.cos(Math.toRadians(i)));
            y = (float) (cp.y + ry * Math.sin(Math.toRadians(i)));

            // Add koordinat ke dalam vertices
            vertices.add(new Vector3f(x, y, 0.0f));
        }
    }
//    public void draw() {
//        drawSetup();
//
//        glLineWidth(10);//mengukur ketebalan
//        glPointSize(10); //besar kecil vertex(cmn bisa GL_POINTS)
//
//        glDrawArrays(GL_TRIANGLE_FAN,0, vertices.size());
//    }
}
