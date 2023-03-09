package com.mygame.bwtgh.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.mygame.bwtgh.Screens.PlayScreen;

public class Fence extends InteractiveTileObject {

	public Fence(PlayScreen screen, Rectangle bounds) {
		super(screen, bounds);
		fixture.setUserData(this);

	}
}