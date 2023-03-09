package com.mygame.bwtgh.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Screens.PlayScreen;

public class Billy extends Sprite {
	public enum State {
		FALLING, JUMPING, STANDING, RUNNING, DEAD
	};

	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion billyStand;
	private Animation<TextureRegion> billyRun;
	private Animation<TextureRegion> billyStanding;
	private TextureRegion billyJump;
	private TextureRegion billyDead;
	private float stateTimer;

	private boolean runningRight;
	private boolean billyIsDead;

	private PlayScreen screen;

	public Billy(PlayScreen screen) {
		super(screen.getAtlas().findRegion("doggy1"));
		this.screen = screen;
		this.world = screen.getWorld();
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;

		Array<TextureRegion> frames = new Array<TextureRegion>();

		frames.add(new TextureRegion(getTexture(), 359, 423, 25, 19));
		frames.add(new TextureRegion(getTexture(), 259, 442, 25, 19));
		frames.add(new TextureRegion(getTexture(), 284, 442, 25, 19));
		frames.add(new TextureRegion(getTexture(), 309, 442, 25, 19));
		frames.add(new TextureRegion(getTexture(), 334, 442, 25, 19));
		frames.add(new TextureRegion(getTexture(), 359, 442, 25, 19));
		frames.add(new TextureRegion(getTexture(), 259, 461, 25, 19));
		frames.add(new TextureRegion(getTexture(), 284, 461, 25, 19));
		billyRun = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		frames.add(new TextureRegion(getTexture(), 284, 404, 25, 19));
		frames.add(new TextureRegion(getTexture(), 309, 404, 25, 19));
		frames.add(new TextureRegion(getTexture(), 334, 404, 25, 19));
		frames.add(new TextureRegion(getTexture(), 359, 404, 25, 19));
		frames.add(new TextureRegion(getTexture(), 259, 423, 25, 19));
		frames.add(new TextureRegion(getTexture(), 284, 423, 25, 19));
		frames.add(new TextureRegion(getTexture(), 309, 423, 25, 19));
		frames.add(new TextureRegion(getTexture(), 334, 423, 25, 19));
		billyStanding = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();

		billyJump = new TextureRegion(getTexture(), 284, 442, 25, 19);

		billyStand = new TextureRegion(getTexture(), 283, 404, 25, 19);

		billyDead = new TextureRegion(getTexture(), 334, 461, 25, 19);

		defineBilly();
		setBounds(283, 404, 24 / BillyWantsToGoHome.PPM, 24 / BillyWantsToGoHome.PPM);
		setRegion(billyStand);
	}

	public void update(float dt) {

		if (screen.getHud().isTimeUp() && !isDead()) {
			hit();
		}
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}

	private TextureRegion getFrame(float dt) {
		currentState = getState();

		TextureRegion region;
		switch (currentState) {
		case DEAD:
			region = billyDead;
			break;
		case RUNNING:
			region = billyRun.getKeyFrame(stateTimer, true);
			break;
		case STANDING:
			region = billyStanding.getKeyFrame(stateTimer, true);
			break;
		case JUMPING:
			region = billyJump;
			break;
		case FALLING:
		default:
			region = billyStand;
			break;
		}

		if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		} else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}

	private State getState() {
		if (billyIsDead)
			return State.DEAD;
		else if (b2body.getLinearVelocity().y > 0)
			return State.JUMPING;
		else if (b2body.getLinearVelocity().y < 0)
			return State.FALLING;
		else if (b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
	}

	public boolean isDead() {
		return billyIsDead;
	}

	public float getStateTimer() {
		return stateTimer;
	}

	private void defineBilly() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / BillyWantsToGoHome.PPM, 32 / BillyWantsToGoHome.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(11 / BillyWantsToGoHome.PPM);
		fdef.filter.categoryBits = BillyWantsToGoHome.BILLY_BIT;
		fdef.filter.maskBits = BillyWantsToGoHome.GROUND_BIT | BillyWantsToGoHome.ENEMY_BIT

				| BillyWantsToGoHome.OBJECT_BIT | BillyWantsToGoHome.END_BIT;
		fdef.shape = shape;
		b2body.createFixture(fdef).setUserData(this);

	}

	public void hit() {
		billyIsDead = true;
	}

	public void jump() {
		if (currentState == State.STANDING || currentState == State.RUNNING) {
			b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
			currentState = State.JUMPING;
		}
	}
}
