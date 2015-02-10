package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;
import com.blizzardBlaster.game.GameSetting;

/**
 * Created by gabrielgiovaninidesouza on 2/7/15.
 */
public class Cannon implements Entity
{
    private boolean mustDie = false;

    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private float angle = (float)Math.PI;

    public Cannon(World world) {

        texture = new Texture("square.png");
        sprite = new Sprite(texture);
        sprite.setSize(25,85);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight());

        //setting up the definition fot the body.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(bodyDef.position.x,(-((Gdx.graphics.getHeight()/ GameSetting.PIXELS_TO_METERS)/2)+1.25f));

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
    public void update()
    {
        if(angle < 1.5*Math.PI && angle > Math.PI/2)
        body.setTransform(body.getPosition(),angle);

        sprite.setPosition((body.getPosition().x * GameSetting.PIXELS_TO_METERS) - sprite.getWidth() / 2, (body.getPosition().y * GameSetting.PIXELS_TO_METERS) - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    @Override
    public void draw(Batch batch)
    {
        batch.draw(sprite, sprite.getX(), sprite.getY()-40,sprite.getOriginX(),sprite.getOriginY(), sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
    }

    @Override
    public boolean getMustDie()
    {
        return mustDie;
    }

    @Override
    public void setMustDie(boolean mustDie)
    {
        //NEVER MUST DIE
    }

    @Override
    public Body[] getBodies() {
        return new Body[0];
    }
}
