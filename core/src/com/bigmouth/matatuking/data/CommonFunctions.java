package com.bigmouth.matatuking.data;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

public class CommonFunctions {
    public SpriteBatch spriteBatch;
    public TextureAtlas textureAtlasCards;
    public float CARD_WIDTH=1f;
    public float CARD_HEIGHT=108f/76f;
    public float MINIMUM_VIEWPORT_SIZE=10;
    public String[] backSpriteNameArray={"b1","b2","b3","b4","b5","b6","b7","b8","b9","bt","bq","bk"};
    public Random random=new Random(System.currentTimeMillis());
}