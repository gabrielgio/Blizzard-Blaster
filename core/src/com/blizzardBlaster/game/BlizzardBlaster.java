package com.blizzardBlaster.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.model.*;


import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * This is the game for itself, here contains whole logic game.
 * TODO: add more explanations about this class.
 */
public class BlizzardBlaster extends ApplicationAdapter implements InputProcessor, ContactListener, DestructionListener
{
    //I get here: http://stackoverflow.com/questions/3776204/how-to-find-out-if-debug-mode-is-enabled
    private boolean isDebug = true;

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;
    private Cannon cannon;
    private float time = 0;
    private float timeShoot = 0;

    //list of IEntity that will be updated and draw
    private ArrayList<IEntity> entities = new ArrayList();

    @Override
    public void create() {

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -8.f), true);
        world.setContactListener(this);
        world.setDestructionListener(this);
        //the game has been built based in this resolution
        Gdx.graphics.setDisplayMode(1280,720,false);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //making level

        //adding ground on scene
        Ground ground = new Ground(world);
        //adding limit
        Limit limit = new Limit(world);
        //adding base
        BaseGun base = new BaseGun(world);
        //adding base cannon
        cannon = new Cannon(world);

        entities.add(cannon);
        Gdx.input.setCursorImage(null,0,0);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render()
    {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        CheckScheduledToDie();

        ApplySnow();

        timeShoot -= Gdx.graphics.getDeltaTime();

        for (IEntity entity : entities)
                entity.Update();


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(GameSetting.PIXELS_TO_METERS,GameSetting.PIXELS_TO_METERS, 0);

        batch.begin();
        //Drawing
        for (IEntity entity : entities)
                entity.Draw(batch);

        batch.end();

        //if (isDebug) debugRenderer.render(world, debugMatrix);
    }

    private void ApplySnow() {
        if(time > GameSetting.SNOW_PERIOD)
        {
            time = 0;
            entities.add(new Snow(world));
        }
        else
            time += Gdx.graphics.getDeltaTime();
    }

    /**
     * Relationship(I am not sure that is the correct word) between pixel and meters
     */
    public static float GetPixelMeter()
    {
        return GameSetting.PIXELS_TO_METERS;
    }

    /**
     * Delete body scheduled to die.
     */
    private void CheckScheduledToDie()
    {
        //I had to do it because Java don't lambda expression, shame of you Java
        ArrayList<IEntity> entitiesToDie = new ArrayList<IEntity>();

        for(IEntity entity : entities)
            if(entity.GetMustDie())
            {
                for (Body body : entity.GetBodies()) {
                    world.destroyBody(body);
                }
                entitiesToDie.add(entity);
            }

        entities.removeAll(entitiesToDie);

    }

    /**
     * Search and find a IEntity from Body.
     * @param body body to be compared.
     * @return IEntity with that body.
     */
    public IEntity GetEntityFromBody(Body body)
    {
        for(IEntity entity: entities)
        for (Body bodyA : entity.GetBodies())
            if( bodyA ==  body)
                return entity;

        return null;
    }

    //region key bindings
    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button)
    {
        if(timeShoot <= 0) {

            timeShoot = GameSetting.TIME_TO_SHOOT;

            float angle = (float) (cannon.getAngle() + 1.5 * Math.PI);
            float vecX = (float) Math.cos(angle) * 1.25f;
            float vecY = (float) Math.sin(angle) * 1.25f;

            Projectile projectile = new Projectile(world, vecX, vecY + (-((Gdx.graphics.getHeight() / BlizzardBlaster.GetPixelMeter()) / 2) + 1.25f));
            entities.add(projectile);
            projectile.GetBodies()[0].applyForceToCenter(vecX * 1000, vecY * 1000, true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y)
    {
        float metX = Gdx.graphics.getWidth()/2 - x;
        float metY = Gdx.graphics.getHeight() - 127.5f - y;
        cannon.setAngle((float)((Math.atan(metX/metY)) + Math.PI));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //endregion

    //region Contact

    @Override
    public void beginContact(Contact contact) {

        IEntity entityA = GetEntityFromBody(contact.getFixtureA().getBody());
        IEntity entityB = GetEntityFromBody(contact.getFixtureB().getBody());

        if((entityA instanceof Projectile && !(entityB instanceof Projectile)) || (entityB instanceof Projectile && !(entityA instanceof Projectile)) && entityB != null && entityA != null)
        {
            entityA.SetMustDie(true);
            entityB.SetMustDie(true);
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    //endregion

}


