package com.mygame.bwtgh;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygame.bwtgh.Screens.PlayScreen;

public class BillyWantsToGoHome extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short BILLY_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short OBJECT_BIT = 8;
	public static final short END_BIT = 16;

	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
