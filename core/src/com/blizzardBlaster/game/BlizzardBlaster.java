package com.blizzardBlaster.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.model.Ground;
import com.blizzardBlaster.model.IEntity;
import com.blizzardBlaster.model.Limit;
import com.blizzardBlaster.model.Snow;

import java.util.ArrayList;

/**
 * This is the game for itself, here contains whole logic game.
 * TODO: add more explanations about this class.
 */
public class BlizzardBlaster extends ApplicationAdapter implements InputProcessor
{
    //I get here: http://stackoverflow.com/questions/3776204/how-to-find-out-if-debug-mode-is-enabled
    private boolean isDebug = false;

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;

    //Relationship(I am not sure that is the correct word) between pixel and meters
    static float PIXELS_TO_METERS = 150f;

    //list of IEntity that will be updated and draw
    private ArrayList<IEntity> entities = new ArrayList();

    public BlizzardBlaster()
    {
        resize(1920, 1080);
    }

    @Override
    public void create() {

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -10.f), true);
        Gdx.graphics.setDisplayMode(1280,720,false);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //adding ground on scene
        Ground ground = new Ground(world);
        //adding limit
        Limit limit = new Limit(world);


        Gdx.input.setInputProcessor(this);
    }


    float time = 0;

    @Override
    public void render()
    {

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if(time > .3)
        {
            time = 0;
            entities.add(new Snow(world));
        }
        else
            time += Gdx.graphics.getDeltaTime();

        for (IEntity entity : entities) entity.Update();


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,PIXELS_TO_METERS, 0);
        batch.begin();
        for (IEntity entity : entities)
        {
            entity.Draw(batch);
        }
        batch.end();

        if ( isDebug) debugRenderer.render(world, debugMatrix);

    }

    /**
     * Relationship(I am not sure that is the correct word) between pixel and meters
     */
    public static float GetPixelMeter()
    {
        return PIXELS_TO_METERS;
    }

    //key biding
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

        entities.add(new Snow(world));
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
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}


