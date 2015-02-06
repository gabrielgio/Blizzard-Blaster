package com.blizzardBlaster.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Define basics function for a Entity which will added int the scenario.
 */
public interface IEntity
{
    /**
     * Update properties of a Entity
     */
    public void Update();

    /**
     * If needed draw on batch
     * @param batch batch to draw
     */
    public void Draw(Batch batch);

    /**
     * Get MustDie value (why java are u not C#?)
     * @return MustDie value
     */
    public boolean GetMustDie();

    /**
     * Set MustDie value
     * @param mustDie value to be set
     */
    public void SetMustDie(boolean mustDie);

    /**
     * Get Entity's Body;
     * @return
     */
    public Body[] GetBodies();

}
