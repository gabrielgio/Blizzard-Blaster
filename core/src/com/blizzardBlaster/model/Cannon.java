package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;

/**
 * Created by gabrielgiovaninidesouza on 2/7/15.
 */
public class Cannon implements IEntity
{
    private boolean mustDie = false;

    private BodyDef bodyDef;
    private Body body;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private float angle = (float)Math.PI;

    public Cannon(World world) {

        //setting up the definition fot the body.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(bodyDef.position.x,(-((Gdx.graphics.getHeight()/ BlizzardBlaster.GetPixelMeter())/2)+1.25f));

        bodyDef.position.angle(new Vector2(0,1.5f));

        //creating a body from body definition
        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        shape.setAsBox(.125f,.50f,new Vector2(0,-.5f),0);

        //sinceramente não sei oque é isso.
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;

        body.createFixture(fixtureDef);

        shape.dispose();

    }

    public float getAngle()
    {
        return angle;
    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

    @Override
    public void Update()
    {
        if(angle < 1.5*Math.PI && angle > Math.PI/2)
        body.setTransform(body.getPosition(),angle);
    }

    @Override
    public void Draw(Batch batch) {

    }

    @Override
    public boolean GetMustDie()
    {
        return mustDie;
    }

    @Override
    public void SetMustDie(boolean mustDie)
    {
        this.mustDie = mustDie;
    }

    @Override
    public Body[] GetBodies() {
        return new Body[0];
    }
}