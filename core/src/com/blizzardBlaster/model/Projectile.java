package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;
import com.blizzardBlaster.game.GameSetting;

/**
 * Created by gabrielgiovaninidesouza on 2/8/15.
 */
public class Projectile implements IEntity
{

    private boolean mustDie = false;
    //necessary variable to create a snow ball in the Box2D's world
    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private CircleShape shape;
    private FixtureDef fixtureDef;


    public Projectile(World world,float x, float y)
    {
        //creating texture,
        // texture are in pixel be careful to convert to meter before update on screen
        texture = new Texture("fireball.png");
        sprite = new Sprite(texture);
        sprite.setSize(40,40);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);

        //setting up the definition fot the bady.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(x,y);

        //creating a body from body definition
        body = world.createBody(bodyDef);
        body.setGravityScale(0);

        //setting up the a shape of snow ball
        shape = new CircleShape();
        shape.setRadius(.2f);

        //sinceramente não sei oque é isso.
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        fixtureDef.restitution = .9f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void Update()
    {
        if(body.getPosition().x > GameSetting.MAX_TO_DIE || body.getPosition().y > GameSetting.MAX_TO_DIE)
            mustDie = true;

        sprite.setPosition((body.getPosition().x* BlizzardBlaster.GetPixelMeter())-sprite.getWidth()/2, (body.getPosition().y*BlizzardBlaster.GetPixelMeter())-sprite.getHeight()/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    @Override
    public void Draw(Batch batch)
    {
        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),sprite.getOriginY(), sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
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
