import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;



public class Main {
    private Window window =
            new Window(800, 800, "Hello World");

    ArrayList<Circle> objects = new ArrayList<>();
    ArrayList<Object> objectsPointsControl = new ArrayList<>();


    public void init() {
        window.init();
        GL.createCapabilities();

        objects.add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.5f, 0),
                                new Vector3f(-0.5f, -0.5f, 0),
                                new Vector3f(0.5f, -0.5f, 0),
                                new Vector3f(0.5f, 0.5f, 0.f)
                        )
                ),
                new Vector4f(1f, 0.8f, 0.0f, 1),
                new Vector3f(0.0f, 0.0f, 0.0f),
                0.1f, 0.1f, 0.1f
        ));
        objects.get(0).scaleObject(2.0f, 2.0f, 2.0f);

        objects.get(0).getChildObject().add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.5f, 0),
                                new Vector3f(-0.5f, -0.5f, 0),
                                new Vector3f(0.5f, -0.5f, 0),
                                new Vector3f(0.5f, 0.5f, 0.f)
                        )
                ),
                new Vector4f(1f, 0.8f, 0.0f, 1),
                new Vector3f(0.0f, 0.0f, 0.0f),
                0.1f, 0.1f, 0.1f
        ));

        objects.get(0).getChildObject().get(0).translateObject(0.25f, 0.0f, 0.0f);

        objects.get(0).getChildObject().add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.5f, 0),
                                new Vector3f(-0.5f, -0.5f, 0),
                                new Vector3f(0.5f, -0.5f, 0),
                                new Vector3f(0.5f, 0.5f, 0.f)
                        )
                ),
                new Vector4f(1f, 0.8f, 0.0f, 1),
                new Vector3f(0.0f, 0.0f, 0.0f),
                0.1f, 0.1f, 0.1f
        ));

        objects.get(0).getChildObject().get(1).translateObject(0.5f, 0.0f, 0.0f);

        objects.get(0).getChildObject().get(1).getChildObject().add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.5f, 0.5f, 0),
                                new Vector3f(-0.5f, -0.5f, 0),
                                new Vector3f(0.5f, -0.5f, 0),
                                new Vector3f(0.5f, 0.5f, 0.f)
                        )
                ),
                new Vector4f(1f, 0.8f, 0.0f, 1),
                new Vector3f(0.0f, 0.0f, 0.0f),
                0.1f, 0.1f, 0.1f
        ));

        objects.get(0).getChildObject().get(1).getChildObject().get(0).translateObject(0.5f, -0.1f, 0.0f);
    }
    public void input() {
        if (window.isKeyPressed(GLFW_KEY_W)){
            // muter poros tengah
            objects.get(0).rotateObject((float) Math.toRadians(0.1f), 0.0f, 0.0f, 1.0f);

            // muter pada poros masing"
            for (Object child: objects.get(0).getChildObject()){
                Vector3f tempCenterPoint = child.updateCenterPoint();
                child.translateObject(tempCenterPoint.x * -1 , tempCenterPoint.y * -1 , tempCenterPoint.z * -1 );
                //dikali -1 supaya dia balik 0,0

                child.rotateObject((float) Math.toRadians(1f), 0.0f, 0.0f, 1.0f);

                child.translateObject(tempCenterPoint.x * 1, tempCenterPoint.y * 1, tempCenterPoint.z * 1);
            }

            // child muter sendiri dan muter pada parent
            for (Object child: objects.get(0).getChildObject().get(1).getChildObject()){
                Vector3f tempCenterPoint = child.updateCenterPoint();
                child.translateObject(tempCenterPoint.x * -1 , tempCenterPoint.y * -1 , tempCenterPoint.z * -1 );

                child.rotateObject((float) 0.1, 0.0f, 0.0f, 1.0f); //klo jdiin math.toradiants lebih lama muternya

                child.translateObject(tempCenterPoint.x * 1,
                        tempCenterPoint.y * 1,
                        tempCenterPoint.z * 1);
            }
        }
}


    public void loop() {
//        kalo ga pake loop nnti habis buka window baru langsung ketutup (ga bisa tambahin frame)
        while(window.isOpen()){
            window.update();
            glClearColor(0,0,0,0);
            GL.createCapabilities();
            input();

            //code
            //..
            for(Object object:objects){
                object.draw();
            }
            glDisableVertexAttribArray(0);
            glfwPollEvents();
        }
    }

    public void run() {
        init();
        loop();

        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }
    public static void main(String[] args) {
        new Main().run();

    }
}