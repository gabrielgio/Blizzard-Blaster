package com.blizzardBlaster.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Random;

public class Snow {

    private Sprite sprite;
    private Texture texture;
    private BodyDef bodyDef;
    private Body body;
    private CircleShape shape;
    private FixtureDef fixtureDef;

    public Snow(World world)
    {
        texture = new Texture("snowball.png");
        sprite = new Sprite(texture);
        sprite.setSize(20,20);
        sprite.setOrigin(10f,10f);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(RadonWidth(), (Gdx.graphics.getHeight()/100f)/2);

        body = world.createBody(bodyDef);

        shape = new CircleShape();
        shape.setRadius(.07f);
        shape.setPosition(new Vector2(0,0));

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void Update()
    {
        sprite.setPosition((body.getPosition().x*100f)-sprite.getWidth()/2, (body.getPosition().y*100f)-sprite.getHeight()/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        //body.applyForce(0,-100,0,0,true);
    }

    public  void Draw(Batch batch)
    {
        batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                sprite.getOriginY(),
                sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                        getScaleY(),sprite.getRotation());
    }

    public Sprite GetSprit()
    {
        return  sprite;
    }

    private float RadonWidth()
    {
        float minX = -(Gdx.graphics.getWidth()/100f)/2;
        float maxX = (Gdx.graphics.getWidth()/100f)/2;
        Random rand = new Random();
        return rand.nextFloat() * (maxX - minX) + minX;
    }
}
