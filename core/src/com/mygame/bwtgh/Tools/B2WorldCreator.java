package com.mygame.bwtgh.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Screens.PlayScreen;
import com.mygame.bwtgh.Sprites.Bear;
import com.mygame.bwtgh.Sprites.Fence;
import com.mygame.bwtgh.Sprites.House;

public class B2WorldCreator {
	private Array<Bear> bears;

	public B2WorldCreator(PlayScreen screen) {

		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;

		for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / BillyWantsToGoHome.PPM,
					(rect.getY() + rect.getHeight() / 2) / BillyWantsToGoHome.PPM);
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / BillyWantsToGoHome.PPM, rect.getHeight() / 2 / BillyWantsToGoHome.PPM);
			fdef.shape = shape;
			body.createFixture(fdef);
		}

		for (MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / BillyWantsToGoHome.PPM,
					(rect.getY() + rect.getHeight() / 2) / BillyWantsToGoHome.PPM);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2 / BillyWantsToGoHome.PPM, rect.getHeight() / 2 / BillyWantsToGoHome.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = BillyWantsToGoHome.OBJECT_BIT;
			body.createFixture(fdef);

			new Fence(screen, rect);
		}

		for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / BillyWantsToGoHome.PPM,
					(rect.getY() + rect.getHeight() / 2) / BillyWantsToGoHome.PPM);

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2 / BillyWantsToGoHome.PPM, rect.getHeight() / 2 / BillyWantsToGoHome.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = BillyWantsToGoHome.END_BIT;
			body.createFixture(fdef);

			new House(screen, rect);
		}

		bears = new Array<Bear>();
		for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bears.add(new Bear(screen, rect.getX() / BillyWantsToGoHome.PPM, rect.getY() / BillyWantsToGoHome.PPM));

			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2 / BillyWantsToGoHome.PPM, rect.getHeight() / 2 / BillyWantsToGoHome.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = BillyWantsToGoHome.ENEMY_BIT;
			body.createFixture(fdef);

		}

	}

	public Array<Bear> getBears() {
		return bears;

	}
}
