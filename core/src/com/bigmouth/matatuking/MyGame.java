package com.bigmouth.matatuking;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bigmouth.matatuking.data.CommonFunctions;
import com.bigmouth.matatuking.data.SpriteTweenAccessor;
import com.bigmouth.matatuking.screens.PlayScreen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class MyGame extends Game {
	SpriteBatch batch;
	CommonFunctions gameData;
	public static TweenManager tweenManager=new TweenManager();

	@Override
	public void create () {
		Tween.registerAccessor(Sprite.class,new SpriteTweenAccessor());
		batch = new SpriteBatch();
		gameData=new CommonFunctions();
		gameData.spriteBatch=batch;
		gameData.textureAtlasCards=new TextureAtlas(Gdx.files.internal("cardspack.atlas"));
		setScreen(new PlayScreen(gameData));
	}
	@Override
	public void render () {
		super.render();
	}
	@Override
	public void dispose () {
		batch.dispose();
	}
}
