package com.blizzardBlaster.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Define basics function for a Entity which will added int the scenario.
 */
public interface Entity {
    /**
     * update properties of a Entity
     */
    public void update();

    /**
     * If needed draw on batch
     *
     * @param batch batch to draw
     */
    public void draw(Batch batch);

    /**
     * Get MustDie value (why java are u not C#?)
     *
     * @return MustDie value
     */
    public boolean getMustDie();

    /**
     * Set MustDie value
     *
     * @param mustDie value to be set
     */
    public void setMustDie(boolean mustDie);

    /**
     * Get Entity's Body;
     *
     * @return
     */
    public Body[] getBodies();

}
