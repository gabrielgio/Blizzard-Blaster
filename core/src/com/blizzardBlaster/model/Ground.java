package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;
import com.blizzardBlaster.game.GameSetting;

/**
 * Model to ground
 */
public class Ground implements Entity {

    //variables to create to set up a  ground in the Box2D's world
    private BodyDef bodyDef;
    private Body body;
    private EdgeShape shape;
    private FixtureDef fixtureDef;
    private boolean mustDie = false;


    /**
     * Constructor
     * @param Box2D's world for creates body and applies physics
     */
    public Ground(World world) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth() / GameSetting.PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight() / GameSetting.PIXELS_TO_METERS;
        bodyDef.position.set(0, 0);
        fixtureDef = new FixtureDef();

        shape = new EdgeShape();
        shape.set(-w / 2, -h / 2, w / 2, -h / 2);
        fixtureDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);

        shape.dispose();

    }

    @Override
    public boolean getMustDie() {
        return mustDie;
    }

    @Override
    public void setMustDie(boolean mustDie) {
        //denied, never will be deleted
    }

    @Override
    public Body[] getBodies() {
        return new Body[]{body};
    }

    @Override
    public void update() {
        //this entity don't have to update, because it is  a static body
    }

    @Override
    public void draw(Batch batch) {
        //this entity don't have to draw, because it is invisible limit
    }
}
