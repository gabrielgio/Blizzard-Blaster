package com.blizzardBlaster.game;

/**
 * Game settings
 */
public class GameSetting {
    /**
     * Relationship between pixel and meters
     */
    public static final float PIXELS_TO_METERS = 85f;

    /**
     * How often the snow is dropped
     */
    public static final float SNOW_PERIOD = 1f;

    /**
     * The distance to a projectile be destroyed.
     */
    public static final float MAX_TO_DIE = 10f;

    /**
     * How often you can shoot
     */
    public static final float TIME_TO_SHOOT = .5f;

    /**
     * Show debug mode of the Box2D
     */
    public static final boolean IS_DEBUGGING = false;
}
