package com.mygame.bwtgh.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Scenes.Hud;
import com.mygame.bwtgh.Sprites.Billy;
import com.mygame.bwtgh.Sprites.Enemy;
import com.mygame.bwtgh.Tools.B2WorldCreator;
import com.mygame.bwtgh.Tools.WorldContactListener;

public class PlayScreen implements Screen {

	private BillyWantsToGoHome game;
	private TextureAtlas atlas;

	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;

	private World world;
	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;

	private Billy player;

	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	public PlayScreen(BillyWantsToGoHome game) {
		atlas = new TextureAtlas("Billy_and_Enemies.pack");

		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(BillyWantsToGoHome.V_WIDTH / BillyWantsToGoHome.PPM,
				BillyWantsToGoHome.V_HEIGHT / BillyWantsToGoHome.PPM, gamecam);
		gamePort.apply();
		hud = new Hud(game.batch);

		maploader = new TmxMapLoader();
		map = maploader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / BillyWantsToGoHome.PPM);

		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();

		creator = new B2WorldCreator(this);

		player = new Billy(this);

		world.setContactListener(new WorldContactListener());

		b2dr.setDrawBodies(false);

	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public void handleInput(float dt) {
		if (player.currentState != Billy.State.DEAD) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
				player.jump();
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
				player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
				player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
		}
	}

	public void update(float dt) {
		handleInput(dt);

		world.step(1 / 60f, 6, 2);
		gamecam.position.x = player.b2body.getPosition().x;

		player.update(dt);
		for (Enemy enemy : creator.getBears()) {
			enemy.update(dt);
			if (enemy.getX() < player.getX() + 224 / BillyWantsToGoHome.PPM) {
				enemy.b2body.setActive(true);
			}
		}
		hud.update(dt);

		gamecam.update();
		renderer.setView(gamecam);

		if (player.currentState != Billy.State.DEAD) {
			gamecam.position.x = player.b2body.getPosition().x;
		}
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		b2dr.render(world, gamecam.combined);

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		player.draw(game.batch);
		for (Enemy enemy : creator.getBears())
			enemy.draw(game.batch);
		game.batch.end();

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();

		if (gameOver()) {
			game.setScreen(new GameOverScreen(game));
			dispose();
		}
	}

	public boolean gameOver() {
		if (player.currentState == Billy.State.DEAD && player.getStateTimer() > 3) {
			return true;
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	public TiledMap getMap() {
		return map;
	}

	public World getWorld() {
		return world;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();

	}

	public Hud getHud() {
		return hud;
	}

}
