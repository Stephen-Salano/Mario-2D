package util;

/**
 * Utility class for tracking elapsed game time.
 * Time is measured from the moment this class is first loaded.
 */
public class Time {

    /** The nanosecond timestamp recorded when the game started. */
    public static float timeStarted = System.nanoTime();

    // Find out time elapsed in seconds
    public static float getTime(){
        // System.nanoTime() -> returns the current time in nanoseconds. Very precise
        // - timeStarted -> subtracts when the game started, giving us elapsed tie in nanoseconds
        //  1E-9 -> converts nanoseconds to seconds
        //      1E-9 - 0.000000001 = 1 nanosecond in seconds.
        //      Multiplying nanoseconds x 1E-9 cancels out the nano, leaving plan seconds
        // (float) cast the result to a float since OpenGL works in floats
        return (float) ((System.nanoTime() - timeStarted) *1E-9);
    }
}
