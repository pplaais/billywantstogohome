package com.mygame.bwtgh.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygame.bwtgh.BillyWantsToGoHome;

public class Hud implements Disposable {
	public Stage stage;
	private Viewport viewport;

	private Integer worldTimer;
	private boolean timeUp;
	private float timeCount;

	Label timeLabel;
	Label countdownLabel;
	Label billyLabel;

	public Hud(SpriteBatch sb) {
		timeCount = 0;
		worldTimer = 30;

		viewport = new FitViewport(BillyWantsToGoHome.V_WIDTH, BillyWantsToGoHome.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);

		Table table = new Table();
		table.top();
		table.setFillParent(true);

		countdownLabel = new Label(String.format("%3d", worldTimer),
				new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		timeLabel = new Label("TIME LEFT :", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
		billyLabel = new Label("HELP BILLY GET HOME!!", new Label.LabelStyle(new BitmapFont(), Color.LIME));

		table.add(billyLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.add(countdownLabel).expandX().padTop(10);

		stage.addActor(table);
	}

	public void update(float dt) {
		timeCount += dt;
		if (timeCount >= 1) {
			if (worldTimer > 0) {
				worldTimer--;
			} else {
				timeUp = true;
			}
			countdownLabel.setText(worldTimer);
			timeCount = 0;
		}
	}

	@Override
	public void dispose() {
		stage.dispose();

	}

	public boolean isTimeUp() {
		return timeUp;
	}

}
