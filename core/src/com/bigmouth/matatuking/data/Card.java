package com.bigmouth.matatuking.data;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {
    public CommonFunctions gameData;
    public String suit;
    public String number;
    public Sprite frontsprite;
    public Sprite backsprite;
    public boolean shouldBeRevealed=false;
    public boolean isCutter=false;
    public Card(String psuit, String pnumber, CommonFunctions pgameData) {
        gameData=pgameData;
        suit=psuit;
        number=pnumber;
        frontsprite=gameData.textureAtlasCards.createSprite(suit+number);
        backsprite=gameData.textureAtlasCards.createSprite(gameData.backSpriteNameArray[gameData.random.nextInt(gameData.backSpriteNameArray.length)]);
        frontsprite.setSize(gameData.CARD_WIDTH,gameData.CARD_HEIGHT);
        backsprite.setSize(gameData.CARD_WIDTH,gameData.CARD_HEIGHT);
        frontsprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backsprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
    public void SetPosition(float x, float y){//we may suffer during lerp
        if(frontsprite!=null){
            if(backsprite!=null){
                frontsprite.setPosition(x,y);
                backsprite.setPosition(x,y);
            }
        }
    }
    public void SetRotation(float angle){//we may suffer during lerp
        if(frontsprite!=null){
            if(backsprite!=null){
                frontsprite.setOriginCenter();
                backsprite.setOriginCenter();
                frontsprite.setRotation(angle);
                backsprite.setRotation(angle);
            }
        }
    }
    public void ResetRotation(){//we may suffer during lerp
        if(frontsprite!=null){
            if(backsprite!=null){
                frontsprite.setRotation(0);
                backsprite.setRotation(0);
            }
        }
    }
    public void Show(){
        shouldBeRevealed=true;
    }
    public void Hide(){
        shouldBeRevealed=false;
    }
    public Sprite GetFrontSprite(){
        return frontsprite;
    }
    public Sprite GetBackSprite(){
        return backsprite;
    }
    public int GetCutCount(){//0 indicates an error
        if(suit==null)return 0;
        if(number==null)return 0;
        if(number.equals("a"))return 15;
        if(number.equals("2"))return 20;
        if(number.equals("3"))return 3;
        if(number.equals("4"))return 4;
        if(number.equals("5"))return 5;
        if(number.equals("6"))return 6;
        if(number.equals("7"))return 7;
        if(number.equals("8"))return 8;
        if(number.equals("9"))return 9;
        if(number.equals("t"))return 10;
        if(number.equals("j"))return 11;
        if(number.equals("q"))return 12;
        if(number.equals("k"))return 13;
        return 0;
    }
    public void Draw(SpriteBatch batch){
        if(frontsprite!=null){
            if(shouldBeRevealed)frontsprite.draw(batch);
            else backsprite.draw(batch);
        }
    }
}
