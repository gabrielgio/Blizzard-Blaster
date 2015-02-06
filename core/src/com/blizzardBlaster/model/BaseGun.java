package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;

/**
 * Created by gabrielgiovaninidesouza on 2/6/15.
 */
public class BaseGun implements IEntity
{
    private boolean mustDie = false;

    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private PolygonShape shape;
    private FixtureDef fixtureDef;

    public BaseGun(World world)
    {
//creating texture,
        // texture are in pixel be careful to convert to meter before update on screen

        //setting up the definition fot the bady.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(bodyDef.position.x,(-((Gdx.graphics.getHeight()/BlizzardBlaster.GetPixelMeter())/2)+.25f));

        //creating a body from body definition
        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        shape.setAsBox(1,.25f);

        //sinceramente não sei oque é isso.
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(Batch batch)
    {

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
    public Body[] GetBodies()
    {
        return new Body[]{body};
    }
}
