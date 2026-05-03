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

    /**
     * GLFW callback — called automatically whenever a key is pressed, released, or repeated.
     * Do not call this method manually; GLFW invokes it and fills all parameters.
     * Only GLFW_PRESS and GLFW_RELEASE are handled; GLFW_REPEAT is intentionally ignored
     * since the game checks key state directly every frame.
     *
     * @param window   the GLFW window handle where the event occurred
     * @param key      the GLFW key code of the key that triggered the event
     *                 (e.g. GLFW_KEY_SPACE = 32). Used as the index into keypressed[]
     * @param scancode the raw hardware code from the physical keyboard.
     *                 Varies per keyboard model — not used here but always sent by GLFW
     * @param action   what happened to the key:
     *                 GLFW_PRESS = key went down,
     *                 GLFW_RELEASE = key came up,
     *                 GLFW_REPEAT = OS fired a hold-repeat event (ignored here)
     * @param mods     modifier keys held at the same time, as bit flags
     *                 (e.g. GLFW_MOD_SHIFT if Shift was held) — not used here but always sent by GLFW
     */
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
