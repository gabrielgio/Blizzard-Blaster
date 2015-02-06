package com.blizzardBlaster.model;

import com.badlogic.gdx.graphics.g2d.Batch;

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
    public  void Draw(Batch batch);

}
