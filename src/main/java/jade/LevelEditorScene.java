package jade;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    private boolean changinScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("Inside Level editor scene");
    }

    @Override
    public void update(float dt) {
        // Check frame rate
        // System.out.println("" + (1.0f / dt + "FPS"));
        if (!changinScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changinScene = true;
        }

        if (changinScene && timeToChangeScene > 0){
            timeToChangeScene -= dt; // take away elapsed time in the last frame
            Window.get().r -= dt * 5.0f;
            Window.get().g = dt * 5.0f;
            Window.get().b = dt * 5.0f;
        } else if (changinScene) {
            Window.changeScene(1); // change us to the level editor scene
        }
    }
}
