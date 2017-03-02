package com.bigmouth.matatuking.data;

import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteTweenAccessor implements TweenAccessor<Sprite> {
    public static final int XPOSITION=1;
    public static final int YPOSITION=2;
    @Override
    public int getValues(Sprite target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case XPOSITION:
                returnValues[0]=target.getX();
                return 1;
            case YPOSITION:
                returnValues[0]=target.getY();
                return 1;
        }
        return 0;
    }

    @Override
    public void setValues(Sprite target, int tweenType, float[] newValues) {
        switch (tweenType){
            case XPOSITION:
                target.setX(newValues[0]);
                break;
            case YPOSITION:
                target.setY(newValues[0]);
                break;
        }
    }
}
