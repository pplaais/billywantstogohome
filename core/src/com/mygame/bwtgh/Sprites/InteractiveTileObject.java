package com.mygame.bwtgh.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Screens.PlayScreen;

public abstract class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;

	protected Fixture fixture;

	public InteractiveTileObject(PlayScreen screen, Rectangle bounds) {
		this.world = screen.getWorld();
		this.map = screen.getMap();
		this.bounds = bounds;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.StaticBody;
		bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / BillyWantsToGoHome.PPM,
				(bounds.getY() + bounds.getHeight() / 2) / BillyWantsToGoHome.PPM);

		body = world.createBody(bdef);

		shape.setAsBox(bounds.getWidth() / 2 / BillyWantsToGoHome.PPM, bounds.getHeight() / 2 / BillyWantsToGoHome.PPM);
		fdef.shape = shape;
		fixture = body.createFixture(fdef);

	}

}
