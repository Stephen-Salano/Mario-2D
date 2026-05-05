package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private boolean mouseButtonPressed[] = new boolean[3]; // stores the last pressed button
    private boolean isDragging;

    private MouseListener() {
        // Initialized to zero to avoid seeing awkward bugs like mouse position in a random memory position
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }

    /**
     * GLFW callback — called automatically whenever the mouse cursor moves within the window.
     * Do not call this method manually; GLFW invokes it and fills all parameters.
     * Saves the previous position before updating, enabling delta (movement) calculation.
     * Also updates the dragging state based on whether any mouse button is currently held.
     *
     * @param window the GLFW window handle where the event occurred
     * @param xpos   the new x position of the cursor in pixels, from the left edge of the window
     * @param ypos   the new y position of the cursor in pixels, from the top edge of the window
     */
    public static void mousePosCallback(long window, double xpos, double ypos) {
        // Setting the last X and Y positions of the mouse before setting it to the new y
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }


    /**
     * GLFW callback — called automatically whenever a mouse button is pressed or released.
     * Do not call this method manually; GLFW invokes it and fills all parameters.
     *
     * @param window the GLFW window handle where the event occurred
     * @param button the mouse button that triggered the event
     *               (0 = left, 1 = right, 2 = middle)
     * @param action what happened to the button
     *               (GLFW_PRESS = 1 means pressed, GLFW_RELEASE = 0 means released)
     * @param mods   modifier keys held during the click, as bit flags
     *               (e.g. GLFW_MOD_CONTROL if Ctrl was held)
     */
    public static void mousButtonCallback(long window, int button, int action, int mods) {

        // Checking if a button was pressed or released
        if (action == GLFW_PRESS) { // button was pressed
            // If mouse has more buttons than what we programmed into our booleans, if you press more than one button we don't want an error
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) { // was released
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false; // mouse is no longer dragging
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

}
