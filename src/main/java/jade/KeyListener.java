package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {

    private static KeyListener instace;
    private boolean keypressed[] = new boolean[350];

    private KeyListener(){}

    public static KeyListener get(){
        if (KeyListener.instace == null){
            KeyListener.instace = new KeyListener();
        }
        return KeyListener.instace;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if (action == GLFW_PRESS){
            get().keypressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keypressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode){
        if (keyCode < get().keypressed.length){
            return  get().keypressed[keyCode];
        }else {
            return false;
        }
    }
}
