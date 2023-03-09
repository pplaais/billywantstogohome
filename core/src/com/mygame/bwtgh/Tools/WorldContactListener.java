package com.mygame.bwtgh.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygame.bwtgh.BillyWantsToGoHome;
import com.mygame.bwtgh.Sprites.Billy;
import com.mygame.bwtgh.Sprites.Enemy;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

		switch (cDef) {
		case BillyWantsToGoHome.ENEMY_BIT | BillyWantsToGoHome.OBJECT_BIT:
			if (fixA.getFilterData().categoryBits == BillyWantsToGoHome.ENEMY_BIT)
				((Enemy) fixA.getUserData()).reverseVelocity(true, false);
			else
				((Enemy) fixB.getUserData()).reverseVelocity(true, false);
			break;
		case BillyWantsToGoHome.BILLY_BIT | BillyWantsToGoHome.ENEMY_BIT:
			if (fixA.getFilterData().categoryBits == BillyWantsToGoHome.ENEMY_BIT)
				((Billy) fixA.getUserData()).hit();
			else
				((Billy) fixA.getUserData()).hit();
			break;
		case BillyWantsToGoHome.BILLY_BIT | BillyWantsToGoHome.END_BIT:
			Gdx.app.log("Billy", "has reached his home");
			Gdx.app.exit();
		}

	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
