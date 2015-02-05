package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by gabrielgiovaninidesouza on 2/4/15.
 */
public class Ground {

    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private EdgeShape shape;
    private FixtureDef fixtureDef;

    float torque = 0.0f;
    boolean drawSprite = true;

    final float PIXELS_TO_METERS = 100f;

    public Ground(World world)
    {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        bodyDef.position.set(0,0);
        fixtureDef = new FixtureDef();

        shape = new EdgeShape();
        shape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

    }
}
