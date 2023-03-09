package com.mygame.bwtgh.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Screens.PlayScreen;

public class Bear extends Enemy {
	private float stateTime;
	private Animation<TextureRegion> walkAnimation;
	private Array<TextureRegion> frames;

	public Bear(PlayScreen screen, float x, float y) {
		super(screen, x, y);
		frames = new Array<TextureRegion>();
		frames.add(new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 0, 305, 16, 16));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 16, 305, 16, 16));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 32, 305, 16, 16));
		frames.add(new TextureRegion(screen.getAtlas().findRegion("spritesheet"), 48, 305, 16, 16));
		walkAnimation = new Animation<TextureRegion>(0.4f, frames);
		stateTime = 0;
		setBounds(getX(), getY(), 16 / BillyWantsToGoHome.PPM, 16 / BillyWantsToGoHome.PPM);
	}

	public void update(float dt) {
		stateTime += dt;
		b2body.setLinearVelocity(velocity);
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(walkAnimation.getKeyFrame(stateTime, true));

	}

	@Override
	protected void defineEnemy() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(6 / BillyWantsToGoHome.PPM);
		fdef.filter.categoryBits = BillyWantsToGoHome.ENEMY_BIT;
		fdef.filter.maskBits = BillyWantsToGoHome.GROUND_BIT | BillyWantsToGoHome.BILLY_BIT
				| BillyWantsToGoHome.OBJECT_BIT | BillyWantsToGoHome.END_BIT;
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);

	}
}
