package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;
import com.blizzardBlaster.game.GameSetting;

/**
 * Model for right and left limit;
 */
public class Limit implements Entity {
    private boolean mustDie = false;

    //variables to create to set up a  left limit in the Box2D's world
    private BodyDef bodyDefLeft;
    private Body bodyLeft;
    private EdgeShape shapeLeft;
    private FixtureDef fixtureDefLeft;

    //variables to create to set up a  right limit in the Box2D's world
    private BodyDef bodyDefRight;
    private Body bodyRight;
    private EdgeShape shapeRight;
    private FixtureDef fixtureDefRight;

    public Limit(World world) {
        float w = Gdx.graphics.getWidth() / GameSetting.PIXELS_TO_METERS;
        float h = Gdx.graphics.getHeight() / GameSetting.PIXELS_TO_METERS;

        bodyDefLeft = new BodyDef();
        bodyDefLeft.type = BodyDef.BodyType.StaticBody;

        bodyDefLeft.position.set(0, 0);
        fixtureDefLeft = new FixtureDef();

        shapeLeft = new EdgeShape();
        shapeLeft.set(new Vector2(-w / 2, h / 2), new Vector2(-w / 2, -h / 2));
        fixtureDefLeft.shape = shapeLeft;

        bodyLeft = world.createBody(bodyDefLeft);
        bodyLeft.createFixture(fixtureDefLeft);

        shapeLeft.dispose();

        bodyDefRight = new BodyDef();
        bodyDefRight.type = BodyDef.BodyType.StaticBody;
        bodyDefRight.position.set(0, 0);
        fixtureDefRight = new FixtureDef();

        shapeRight = new EdgeShape();
        shapeLeft.set(new Vector2(w / 2, h / 2), new Vector2(w / 2, -h / 2));
        fixtureDefRight.shape = shapeRight;

        bodyRight = world.createBody(bodyDefRight);
        bodyRight.createFixture(fixtureDefRight);

        shapeRight.dispose();
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Batch batch) {

    }

    @Override
    public boolean getMustDie() {
        return mustDie;
    }

    @Override
    public void setMustDie(boolean mustDie) {
        this.mustDie = mustDie;
    }

    @Override
    public Body[] getBodies() {
        return new Body[]{bodyLeft, bodyRight};
    }
}
