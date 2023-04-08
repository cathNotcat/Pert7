package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Sphere extends Circle{
    Float rz;
    List<Integer> index;
    int ibo;
    int stackCount = 200, sectorCount = 300;

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, Vector3f cp, Float rx, Float ry, Float rz) {
        super(shaderModuleDataList, vertices, color, cp, rx, ry);
        this.rz = rz;
        createBox();
//        createSphere();
//        createElipsoid();
//        createHyperboloid1();
//        createHyperboloid2();
        setupVAOVBO();
    }

    public void createBox(){
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //titik 1 kiri atas belakang
        temp.x = cp.get(0) - rx / 2;
        temp.y = cp.get(1) + ry / 2;
        temp.z = cp.get(2) - rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 2 kiri bawah belakang
        temp.x = cp.get(0) - rx / 2;
        temp.y = cp.get(1) - ry / 2;
        temp.z = cp.get(2) - rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 3 kanan bawah belakang
        temp.x = cp.get(0) + rx / 2;
        temp.y = cp.get(1) - ry / 2;
        temp.z = cp.get(2) - rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 4 kanan atas belakang
        temp.x = cp.get(0) + rx / 2;
        temp.y = cp.get(1) + ry / 2;
        temp.z = cp.get(2) - rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 5 kiri bawah depam
        temp.x = cp.get(0) - rx / 2;
        temp.y = cp.get(1) + ry / 2;
        temp.z = cp.get(2) + rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 6 kiri bawah depan
        temp.x = cp.get(0) + rx / 2;
        temp.y = cp.get(1) - ry / 2;
        temp.z = cp.get(2) - rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 7 kanan bawah depan
        temp.x = cp.get(0) + rx / 2;
        temp.y = cp.get(1) - ry / 2;
        temp.z = cp.get(2) + rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //titik 8 kanan atas depan
        temp.x = cp.get(0) + rx / 2;
        temp.y = cp.get(1) + ry / 2;
        temp.z = cp.get(2) + rz / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        // kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));

        // kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));

        // kotak kanan
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));

        //kotak kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));

        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(0));

        // kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));

        // kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(1));
    }

    public void createSphere() {
        vertices.clear();

        ArrayList<Vector3f> temp = new ArrayList<>();
        int stackCount = 18, sectorCount = 26;
        float x,y,z,xy,nx,ny,nz;
        float sectorStep = (float)(2* Math.PI )/ sectorCount; //sector count
        float stackStep = (float)Math.PI / stackCount; // stack count
        float sectorAngle, stackAngle;

        for(int i = 0; i <= stackCount; i++){
            stackAngle = (float)Math.PI/2 - i * stackStep;
            xy = (float) (0.5f * Math.cos(stackAngle));
            z = (float) (0.5f * Math.sin(stackAngle));
            for(int j = 0; j < sectorCount; j++){
                sectorAngle = j * sectorStep;
                x = (float) (xy * Math.cos(sectorAngle));
                y = (float) (xy * Math.sin(sectorAngle));
                temp.add(new Vector3f(x,y,z));
            }
        }

        // !!!
        vertices = temp;

        int k1, k2;
        ArrayList<Integer> temp_indices = new ArrayList<>();
        for(int i = 0; i < stackCount; i++){
            k1 = i * (sectorCount + 1);
            k2 = k1 + sectorCount + 1;

            for(int j = 0; j < sectorCount; ++j, ++k1, ++k2){
                if(i != 0){
                    temp_indices.add(k1);
                    temp_indices.add(k2);
                    temp_indices.add(k1+1);
                }
                if(i!=(18-1)){
                    temp_indices.add(k1+1);
                    temp_indices.add(k2);
                    temp_indices.add(k2+1);
                }
            }


            this.index = temp_indices;
            ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(index), GL_STATIC_DRAW);
        }

    }

    public void createElipsoid(){
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        // u stackup angle
        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = rx * (float)Math.cos(StackAngle);
            y = rx * (float)Math.cos(StackAngle);
            z = rx * (float)Math.sin(StackAngle);

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = cp.get(0) + x * (float)Math.cos(sectorAngle);
                temp_vector.y = cp.get(1) + y * (float)Math.sin(sectorAngle);
                temp_vector.z = cp.get(2) + z;
                vertices.add(temp_vector);
            }
        }
    }

    public void createHyperboloid1(){
        vertices.clear();
        float pi = (float)Math.PI;
        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        // u stackup angle
        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            z = rx * (float) (1.0 /Math.cos(StackAngle));
            y = ry * (float) (1.0/Math.cos(StackAngle));
            x = rz * (float) Math.tan(StackAngle);

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = cp.get(0) + x * (float)Math.cos(sectorAngle);
                temp_vector.y = cp.get(1) + y * (float)Math.sin(sectorAngle);
                temp_vector.x = cp.get(2) + z;
                vertices.add(temp_vector);
            }
        }
    }
    public void createHyperboloid2(){
        vertices.clear();
        float pi = (float)Math.PI;
        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        // u stackup angle
        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            z = rx * (float) (Math.tan(StackAngle));
            y = ry * (float) (Math.tan(StackAngle));
            x = rz * (float) (1.0/Math.cos(StackAngle));

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.z = cp.get(0) + x * (float)Math.cos(sectorAngle);
                temp_vector.y = cp.get(1) + y * (float)Math.sin(sectorAngle);
                temp_vector.x = cp.get(2) + z;
                vertices.add(temp_vector);
            }
        }
    }



//    public void createEllipticCone () {
//        vertices.clear();
//
//        ArrayList<Vector3f> temp = new ArrayList<>();
//
//        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
//            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
//                float x = 0.5f * (float)(v * Math.cos(u));
//                float y = 0.5f * (float)(v * Math.sin(u));
//                float z = 0.5f * (float) v;
//                temp.add(new Vector3f(x,y,z));
//            }
//        }
//        vertices = temp;
//
//        int k1, k2;
//        ArrayList<Integer> temp_indices = new ArrayList<>();
//        for(int i = 0; i < stackCount; i++){
//            k1 = i * (sectorCount + 1);
//            k2 = k1 + sectorCount + 1;
//
//            for(int j = 0; j < sectorCount; ++j, ++k1, ++k2){
//                if(i != 0){
//                    temp_indices.add(k1);
//                    temp_indices.add(k2);
//                    temp_indices.add(k1+1);
//                }
//                if(i!=(18-1)){
//                    temp_indices.add(k1+1);
//                    temp_indices.add(k2);
//                    temp_indices.add(k2+1);
//                }
//            }
//
//            this.index = temp_indices;
//            ibo = glGenBuffers();
//            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
//            glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(index), GL_STATIC_DRAW);
//        }
//
//    }

//    public void draw(){
//        drawSetup();
//        glLineWidth(10);
//        glPointSize(10);
//        glDrawArrays(GL_LINE_STRIP, 0, vertices.size());
//    }
}
