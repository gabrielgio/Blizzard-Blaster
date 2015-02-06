package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.game.BlizzardBlaster;

import java.util.Random;

/**
 * Model to a Snow Ball
 */
public class Snow implements IEntity {

    private boolean mustDie = false;
    //necessary variable to create a snow ball in the Box2D's world
    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private CircleShape shape;
    private FixtureDef fixtureDef;

    /*
     * Constructor
     * @param Box2D's world for creates body and applies physics
     */
    public Snow(World world)
    {
        //creating texture,
        // texture are in pixel be careful to convert to meter before update on screen
        texture = new Texture("snowball.png");
        sprite = new Sprite(texture);
        sprite.setSize(30,30);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);

        //setting up the definition fot the bady.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(RadonWidth(), (Gdx.graphics.getHeight()/BlizzardBlaster.GetPixelMeter())/2);

        //creating a body from body definition
        body = world.createBody(bodyDef);

        //setting up the a shape of snow ball
        shape = new CircleShape();
        shape.setRadius(.1f);
        shape.setPosition(new Vector2(0,0));

        //sinceramente não sei oque é isso.
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        fixtureDef.restitution = .1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void Update()
    {
        sprite.setPosition((body.getPosition().x* BlizzardBlaster.GetPixelMeter())-sprite.getWidth()/2, (body.getPosition().y*BlizzardBlaster.GetPixelMeter())-sprite.getHeight()/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
    }

    /**
     *
     * @param batch
     */
    public  void Draw(Batch batch)
    {
        //batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),sprite.getOriginY(), sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.getScaleY(),sprite.getRotation());
    }

    @Override
    public boolean GetMustDie() {
        return mustDie;
    }

    @Override
    public void SetMustDie(boolean mustDie) {
        this.mustDie = mustDie;
    }

    @Override
    public Body[] GetBodies()
    {
        return new Body[]{body};
    }

    /**
     * Create a random number based on with screem
     * @return Randomized number
     */
    private float RadonWidth()
    {
        float minX = -(Gdx.graphics.getWidth()/BlizzardBlaster.GetPixelMeter())/2;
        float maxX = (Gdx.graphics.getWidth()/BlizzardBlaster.GetPixelMeter())/2;
        Random rand = new Random();
        return rand.nextFloat() * (maxX - minX) + minX;
    }
}
