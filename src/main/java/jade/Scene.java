package jade;

/**
 * Base class for all game scenes.
 * Each scene represents a distinct game state (e.g. level editor, gameplay).
 * Extend this class and implement {@link #update(float)} to define scene behavior.
 */
public abstract class Scene {

    protected Scene() {
    }

    /**
     * Called once every frame by the game loop.
     * Use dt to make movement and transitions frame-rate independent.
     *
     * @param dt delta time — the time in seconds the last frame took to complete
     */
    public abstract void update(float dt);
}
