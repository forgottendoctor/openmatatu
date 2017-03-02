package com.bigmouth.matatuking.data;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by deo on 2/20/2016.
 */
public class Hud {
    public Stage stage;
    Viewport viewport;
    public Label label1;
    public Label label2;
    public Label label3;
    public int V_WIDTH=400;
    public int V_HEIGHT=208;
    FreeTypeFontGenerator ttfgen;
    BitmapFont fpstextfont;
    public Hud(SpriteBatch spriteBatch){
        ttfgen=new FreeTypeFontGenerator(Gdx.files.internal("roboto.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param=new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size=18;
        fpstextfont=ttfgen.generateFont(param);
        fpstextfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        ttfgen.dispose();
        viewport=new FitViewport(V_WIDTH, V_HEIGHT,new OrthographicCamera());
        stage= new Stage(viewport,spriteBatch);
        Table table=new Table();
        table.top();
        table.setFillParent(true);
        label1= new Label("",new Label.LabelStyle(fpstextfont, Color.CORAL));;
        label2= new Label("",new Label.LabelStyle(fpstextfont, Color.CORAL));;
        label3= new Label("",new Label.LabelStyle(fpstextfont, Color.CORAL));
        table.add(label1).expandX().padTop(10);
        table.add(label2).expandX().padTop(10);
        table.add(label3).expandX().padTop(10);
        stage.addActor(table);
    }
}