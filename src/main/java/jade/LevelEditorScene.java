package jade;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = """
            #version 330 core

            // Input attribute at location 0: vertext position (x,y,z)
            layout (location=0) in vec3 aPos;

            // Input attribute at location 1: vertext color(r, g, b, a)
            layout (location=1) in vec4 aColor;

            // Output variable passed to fragment shader
            out vec4 fColor;

            void main(){
                // pass the color directly to fragment shader
                fColor = aColor;

                // convert vec3 position into vec4 for OpenGL
                // w = 1.0 means this is a posotion not direction
                gl_Position = vec4(aPos, 1.0);
            }""";

    private String fragmentShaderSrc = """
                            #version 330 core

                            in vec4 fColor;

                            out vec4 color;

                            void main() {
                                color = fColor;
                            }
            """;

    private int vertexId;
    private int fragmentID;
    private int shaderProgram;

    private float [] vertexArray = {
        // position          //  color
        0.5f, -0.5f, 0.0f,  1.0f, 0.0f, 0.0f, 1.0f, //Bottom right => red
        -0.5f, 0.5f, 0.0f,  0.0f, 1.0f, 0.0f, 1.0f, // Top left => blue
        0.5f, 0.5f,  0.0f,  0.0f, 0.0f, 1.0f, 1.0f, // Top right => green
        -0.5f,-0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f, // Bottom left => yellow
    };

    // Importany: Must be in counter-clockwise order
    private int[] elementArray = {
        2, 1, 0, // Top right Triangle
        0, 1, 3 // Bottom left triangle
    };

    private int vaoId, vboId, eboId;

    @Override
    public void init() {
        // compile and link the shaders

        // 1. Load and compile the vertex shader
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        //2. Pass the shader source to the GPU
        glShaderSource(vertexId, vertexShaderSrc);
        // 3. Compile shder
        glCompileShader(vertexId);

        // 4. check for any errors in compilation process
        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tVertext shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexId, length));
            assert false : "";
        }


        // 1. Load and compile the Fragment Shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //2. Pass the shader source to the GPU
        glShaderSource(fragmentID, fragmentShaderSrc);
        // 3. Compile shader
        glCompileShader(fragmentID);

        // 4. check for any errors in compilation process
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            int length = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentID, length));
            assert false : "";
        }

        // Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexId);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        // Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int length = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsl'\n\tLinking Shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, length));
            assert false : "";
        }

        /*
        ===========================================================
        Generate VAO, VBO, and EBO buffer objects, and send to GPU
        ===========================================================
         */
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // create a float buffer of vertices
        FloatBuffer vertextBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertextBuffer.put(vertexArray).flip();

        // Create VBO and upload the vertex buffer
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertextBuffer, GL_STATIC_DRAW);

        // create indeces buffer and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // add the vertex attriute pointers
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSizeBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * floatSizeBytes);
        glEnableVertexAttribArray(1);
    }

    public LevelEditorScene() {
        // to be used
    }

    @Override
    public void update(float dt) {
        // Bind shader program
        glUseProgram(shaderProgram);
        // Bind the VAO that we're using
        glBindVertexArray(vaoId);

        //enable the vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0); // bind nothing
        glUseProgram(0);
    }
}
