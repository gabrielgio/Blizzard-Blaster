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

    private Sprite spriteCir;
    private Texture textureCir;
    private BodyDef bodyDefCir;
    private Body bodyCir;
    private CircleShape shapeCir;
    private FixtureDef fixtureDefCir;

    public BaseGun(World world)
    {
        //creating texture,
        // texture are in pixel be careful to convert to meter before update on screen
        texture = new Texture("square.png");
        sprite = new Sprite(texture);
        sprite.setSize(175,175);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);

        //setting up the definition fot the bady.
        bodyDef = new BodyDef();
        //the snow ball moves
        bodyDef.type = BodyDef.BodyType.StaticBody;
        //set in a random X but in a fixed Y(roof)
        bodyDef.position.set(bodyDef.position.x,(-((Gdx.graphics.getHeight()/BlizzardBlaster.GetPixelMeter())/2)+.25f));

        //creating a body from body definition
        body = world.createBody(bodyDef);

        shape = new PolygonShape();

        shape.setAsBox(1,1);

        //sinceramente não sei oque é isso.
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;

        body.createFixture(fixtureDef);

        shape.dispose();


        //creating texture,
        // texture are in pixel be careful to convert to meter before update on screen
        textureCir = new Texture("circle.png");
        spriteCir = new Sprite(textureCir);
        spriteCir.setSize(150,150);
        spriteCir.setOrigin(spriteCir.getWidth()/2,spriteCir.getHeight()/2);

        //setting up the definition fot the bady.
        bodyDefCir = new BodyDef();
        //the snow ball moves
        bodyDefCir.type = BodyDef.BodyType.StaticBody;
        //set in a random X but in a fixed Y(roof)
        bodyDefCir.position.set(bodyDefCir.position.x,(-((Gdx.graphics.getHeight()/BlizzardBlaster.GetPixelMeter())/2)+1.25f));

        //creating a bodyCir from bodyCir definition
        bodyCir = world.createBody(bodyDefCir);

        shapeCir = new CircleShape();

        shapeCir.setRadius(.75f);

        //sinceramente não sei oque é isso.
        fixtureDefCir = new FixtureDef();
        fixtureDefCir.shape = shapeCir;
        fixtureDefCir.density = 10f;

        bodyCir.createFixture(fixtureDefCir);

        shapeCir.dispose();
    }

    @Override
    public void Update()
    {
        sprite.setPosition((body.getPosition().x* BlizzardBlaster.GetPixelMeter())-sprite.getWidth()/2, (body.getPosition().y*BlizzardBlaster.GetPixelMeter())-sprite.getHeight()/2);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        spriteCir.setPosition((bodyCir.getPosition().x* BlizzardBlaster.GetPixelMeter())-spriteCir.getWidth()/2, (bodyCir.getPosition().y*BlizzardBlaster.GetPixelMeter())-spriteCir.getHeight()/2);
        spriteCir.setRotation((float)Math.toDegrees(bodyCir.getAngle()));
    }

    @Override
    public void Draw(Batch batch)
    {
        batch.draw(spriteCir, spriteCir.getX(), spriteCir.getY(),spriteCir.getOriginX(),spriteCir.getOriginY(), spriteCir.getWidth(),spriteCir.getHeight(),spriteCir.getScaleX(),spriteCir.getScaleY(),spriteCir.getRotation());
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
        //NEVER MUST DIE
    }

    @Override
    public Body[] GetBodies()
    {
        return new Body[]{body,bodyCir};
    }
}
