package com.blizzardBlaster.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.blizzardBlaster.model.Ground;
import com.blizzardBlaster.model.Snow;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BlizzardBlaster extends ApplicationAdapter implements InputProcessor  {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Ground ground;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;

    private final float PIXELS_TO_METERS = 100f;

    private ArrayList<Snow> snows = new ArrayList();


    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -10.f), true);
        ground = new Ground(world);

        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(this);
    }

    float time = 0;

    @Override
    public void render(){

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        if(time > 1)
        {
           time = 0;
            snows.add(new Snow(world));
        }else
        {
            time += Gdx.graphics.getDeltaTime();
        }


        for (Snow snow : snows) {
            snow.Update();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        debugRenderer = new Box2DDebugRenderer();
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,PIXELS_TO_METERS, 0);
        batch.begin();
        for (Snow snow : snows) {
            snow.Draw(batch);
        }
        batch.end();
        //debugRenderer.render(world, debugMatrix);
    }

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

        snows.add(new Snow(world));
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
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}


