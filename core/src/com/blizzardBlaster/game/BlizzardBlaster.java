package com.blizzardBlaster.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BlizzardBlaster extends ApplicationAdapter {

    private SpriteBatch batch;
    World world;

	@Override
	public void create () {
        batch = new SpriteBatch();
        world = new World(new Vector2(0, -98f), true);
	}

	@Override
	public void render () {

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();
	}
}
