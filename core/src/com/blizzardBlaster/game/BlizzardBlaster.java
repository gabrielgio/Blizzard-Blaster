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

/**
 * This is the game for itself, here contains whole logic game.
 */
public class BlizzardBlaster extends ApplicationAdapter implements InputProcessor, ContactListener, DestructionListener {
    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;
    private Cannon cannon;
    private BitmapFont font;

    private float time = 0;
    private float timeShoot = 0;
    private int point = 0;

    //list of Entity that will be updated and drew
    private ArrayList<Entity> entities = new ArrayList();

    @Override
    public void create() {

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -8.f), true);
        world.setContactListener(this);
        world.setDestructionListener(this);
        //the game has been built based in this resolution
        Gdx.graphics.setDisplayMode(1280, 720, false);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        //making level
        //adding ground on scene
        Ground ground = new Ground(world);
        //adding limit
        Limit limit = new Limit(world);
        //adding base
        BaseGun base = new BaseGun(world);
        //adding cannon
        cannon = new Cannon(world);

        entities.add(base);
        entities.add(cannon);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        //stepping the world
        world.step(Gdx.graphics.getDeltaTime(), 20, 2);

        checkScheduledToDie();

        applySnow();

        timeShoot -= Gdx.graphics.getDeltaTime();

        for (Entity entity : entities)
            entity.update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(GameSetting.PIXELS_TO_METERS, GameSetting.PIXELS_TO_METERS, 0);

        //drawing sprites
        batch.begin();

        //Drawing
        for (Entity entity : entities)
            entity.draw(batch);

        //counting snow balls
        int count = 0;
        for (Entity entity : entities) {
            if (entity instanceof Snow)
                count++;

            if (count == 100)
                reset();
        }

        font.draw(batch, "Snow Balls: " + count, -Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        font.draw(batch, "Points: " + point, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);

        batch.end();

        if (GameSetting.IS_DEBUGGING) debugRenderer.render(world, debugMatrix);
    }

    private void applySnow() {
        if (time > GameSetting.SNOW_PERIOD) {
            time = 0;
            entities.add(new Snow(world));
        } else
            time += Gdx.graphics.getDeltaTime();
    }

    /**
     * Reset the level
     */
    public void reset() {
        point = 0;
        for (Entity entity : entities) {
            if (entity instanceof Snow)
                entity.setMustDie(true);
        }
    }

    /**
     * Delete body scheduled to die.
     */
    private void checkScheduledToDie() {
        //I had to do it because Java don't lambda expression, shame of you Java
        ArrayList<Entity> entitiesToDie = new ArrayList<Entity>();

        for (Entity entity : entities)
            if (entity.getMustDie()) {
                for (Body body : entity.getBodies()) {
                    world.destroyBody(body);
                }
                entitiesToDie.add(entity);
            }

        entities.removeAll(entitiesToDie);

    }

    /**
     * Search and find a Entity from a Body.
     *
     * @param body body to be compared.
     * @return Entity with that body.
     */
    public Entity getEntityFromBody(Body body) {
        for (Entity entity : entities)
            for (Body bodyA : entity.getBodies())
                if (bodyA == body)
                    return entity;

        return null;
    }

    //region key bindings
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if (timeShoot <= 0) {

            timeShoot = GameSetting.TIME_TO_SHOOT;

            float angle = (float) (cannon.getAngle() + 1.5 * Math.PI);
            float vecX = (float) Math.cos(angle) * 1.15f;
            float vecY = (float) Math.sin(angle) * 1.15f;

            Projectile projectile = new Projectile(world, vecX, vecY + (-((Gdx.graphics.getHeight() / GameSetting.PIXELS_TO_METERS) / 2) + 1.25f));
            entities.add(projectile);
            projectile.getBodies()[0].applyForceToCenter(vecX * 1000, vecY * 1000, true);
        }
        return false;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        float metX = Gdx.graphics.getWidth() / 2 - x;
        float metY = Gdx.graphics.getHeight() - 127.5f - y;
        cannon.setAngle((float) ((Math.atan(metX / metY)) + Math.PI));

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

        Entity entityA = getEntityFromBody(contact.getFixtureA().getBody());
        Entity entityB = getEntityFromBody(contact.getFixtureB().getBody());

        if ((entityA instanceof Projectile && !(entityB instanceof Projectile)) || (entityB instanceof Projectile && !(entityA instanceof Projectile)) && entityB != null && entityA != null) {
            entityA.setMustDie(true);
            entityB.setMustDie(true);
            point++;
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


