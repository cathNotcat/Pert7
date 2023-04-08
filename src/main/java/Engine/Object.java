package Engine;

import org.joml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.opengl.GL30.*;

// child dari ShaderProgram karena segala sesuatu akan msk di shaderprogram dulu
public class Object extends ShaderProgram{
    List<Vector3f> vertices;

    int vao; //send vertice supaya bisa tampil di layar
    int vbo; // buat nyimpen vertices
    Vector4f color;
    UniformsMap uniformsMap;
    List<Vector3f> verticesColor;
    int vboColor;

    Matrix4f model;

    List<Object> childObject;

    public Vector3f updateCenterPoint() {
        Vector3f centerTemp = new Vector3f();
        model.transformPosition(0.0f,0.0f,0.0f, centerTemp);
        return centerTemp;
    }

    public void setChildObject(List<Object> childObject) {
        this.childObject = childObject;
    }

    public List<Object> getChildObject() {
        return childObject;
    }

    public Object(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices; //supaya bisa ditampilin di void main
        this.color = color;
        uniformsMap = new UniformsMap(getProgramId());
        uniformsMap.createUniform("uni_color");
        uniformsMap.createUniform("model");
        model = new Matrix4f();
        childObject = new ArrayList<>();
        setupVAOVBO(); //tiap vertice berubah kasi update\
    }

    public Object(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
//        uniformsMap = new UniformsMap(getProgramId());
//        uniformsMap.createUniform("uni_color");
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();
    }

//    kalo ga dipanggil (vaovbo) pas diubah" nnti isa error
    public void setupVAOVBO(){
        // set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);
    }

    public void setupVAOVBOWithVerticesColor(){
        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);

        // set vbo
        this.vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // mengirim vertices ke shader
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(vertices), GL_STATIC_DRAW);

        vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER, Utils.listoFloat(verticesColor), GL_STATIC_DRAW);
    }

    // sini ke vaovbo vert, vert->frag, frag->layar
    public void drawSetup(){
        bind();
        uniformsMap.setUniform("uni_color", color);
        uniformsMap.setUniform("model", model);
        //Bind VBO
        glEnableVertexAttribArray(0); //cmn bisa index
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0,3,GL_FLOAT, false,0,0);
    }
    public void drawSetupWithVerticesColor(){
        bind();
        //Bind VBO
        glEnableVertexAttribArray(0); //cmn bisa index
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0,3,GL_FLOAT, false,0,0);

        glEnableVertexAttribArray(1); //cmn bisa index
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glVertexAttribPointer(1,3,GL_FLOAT, false,0,0);
    }

    public void draw(){
        drawSetup();
        //draw vertices
        //opsional
        glLineWidth(10);//mengukur ketebalan
        glPointSize(0); //besar kecil vertex(cmn bisa GL_POINTS)

        //wajib
        //yang sering GL_LINE, LINE_STRIP, LINE_LOOP, LINE_TRIANGLE, TRIANGLE_FAN, GL_POINT, GL_TRIANGLEFAN
//        glDrawArrays(GL_TRIANGLES,0, vertices.size());
        glDrawArrays(GL_POLYGON,0, vertices.size());
        for (Object child: childObject){
            child.draw();
        }
    }
    public void drawWithVerticesColor(){
        drawSetupWithVerticesColor();
        glLineWidth(10);
        glPointSize(10);
        glDrawArrays(GL_TRIANGLES,0, vertices.size());
    }
    public void drawLine(){
        drawSetup();
        glLineWidth(3);//mengukur ketebalan
        glPointSize(0); //besar kecil vertex(cmn bisa GL_POINTS)
        glDrawArrays(GL_LINE_STRIP,0, vertices.size());
    }
    //tiap ngeklik tambah titik
    public void addVertices(Vector3f newVector){
        vertices.add(newVector);
        setupVAOVBO();
    }
    public void translateObject(Float offsetX, Float offsetY, Float offsetZ){
        // jaga" kalo alamatnya sama jadinya pake new
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Object child: childObject){
            child.translateObject(offsetX, offsetY, offsetZ);
        }
    }
    public void rotateObject(Float degree, Float offsetX, Float offsetY, Float offsetZ){
        // jaga" kalo alamatnya sama jadinya pake new
        model = new Matrix4f().rotate(degree, offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Object child: childObject){
            child.rotateObject(degree, offsetX, offsetY, offsetZ);
        }
    }
    public void scaleObject(Float offsetX, Float offsetY, Float offsetZ){
        // jaga" kalo alamatnya sama jadinya pake new
        model = new Matrix4f().scale(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        for (Object child: childObject){
            child.scaleObject(offsetX, offsetY, offsetZ);
        }
    }
}
