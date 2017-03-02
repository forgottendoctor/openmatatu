package com.bigmouth.matatuking.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.bigmouth.matatuking.MyGame;
import com.bigmouth.matatuking.data.Card;
import com.bigmouth.matatuking.data.CommonFunctions;
import com.bigmouth.matatuking.data.ComputerHand;
import com.bigmouth.matatuking.data.GameHands;
import com.bigmouth.matatuking.data.Hud;
import com.bigmouth.matatuking.data.HumanHand;
import com.bigmouth.matatuking.data.SpriteTweenAccessor;

import java.util.ArrayList;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;

public class PlayScreen extends InputAdapter implements Screen{

    CommonFunctions m_gamedata;
    private boolean m_tugaabyecards=false;
    private ComputerHand computerHand=new ComputerHand();
    private HumanHand humanHand=new HumanHand();
    OrthographicCamera cam=new OrthographicCamera();
    private boolean gameisover=false;
    private Stage stage;
    Stage stagecomputer;
    Skin skin;
    Label winorloselabel;
    Label humancutcountlabel;
    Label computercutcountlabel;
    TextButton restartButton;
    TextButton quitButton;
    Dialog dialoggameover;
    Dialog dialoghumanask;
    Dialog dialogcomputeraskdai;
    Dialog dialogcomputeraskkamuli;
    Dialog dialogcomputeraskmutima;
    Dialog dialogcomputeraskkitiyo;
    Dialog dialogmsgbelow20;
    ImageButton humanpickdaibutton;
    ImageButton humanpickkamulibutton;
    ImageButton humanpickmutimabutton;
    ImageButton humanpickkitiyobutton;
    //private Hud hud;
    Sprite kalezanyasprite;
    public boolean kalezanyaanimationplaying=false;
    boolean gameisovernonce=false;
    private boolean m_basaaze=false;
    int m_humancutcount=0;
    int m_computercutcount=0;

    void InitGui(){
        skin=new Skin(Gdx.files.internal("uiskin.json"));
        winorloselabel=new Label("Computer Has Won",skin);
        winorloselabel.setWrap(true);
        winorloselabel.setWidth(Gdx.graphics.getWidth() * 1.5f);
        winorloselabel.setAlignment(Align.center);
        winorloselabel.setPosition(Gdx.graphics.getWidth() * 0.5f - winorloselabel.getWidth() * 0.5f, Gdx.graphics.getHeight()/2f+30f);
        winorloselabel.setColor(Color.CYAN);

        humancutcountlabel=new Label("",skin);
        humancutcountlabel.setWrap(true);
        humancutcountlabel.setWidth(Gdx.graphics.getWidth() * 1.5f);
        humancutcountlabel.setAlignment(Align.center);
        humancutcountlabel.setPosition(Gdx.graphics.getWidth() * 0.5f - winorloselabel.getWidth() * 0.5f, Gdx.graphics.getHeight()/2f+90f);
        humancutcountlabel.setColor(Color.CYAN);

        computercutcountlabel=new Label("",skin);
        computercutcountlabel.setWrap(true);
        computercutcountlabel.setWidth(Gdx.graphics.getWidth() * 1.5f);
        computercutcountlabel.setAlignment(Align.center);
        computercutcountlabel.setPosition(Gdx.graphics.getWidth() * 0.5f - winorloselabel.getWidth() * 0.5f, Gdx.graphics.getHeight()/2f+60f);
        computercutcountlabel.setColor(Color.CYAN);

        restartButton=new TextButton("Restart",skin);
        restartButton.setPosition(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight()/2-30f);
        restartButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        });
        dialogmsgbelow20=new Dialog("",skin){
            @Override
            protected void result(Object object) {
                super.result(object);
                Gdx.input.setInputProcessor(PlayScreen.this);
            }
        };
        dialogmsgbelow20.getContentTable().add(new Label("You can't cut above 20",skin)).row();
        dialogmsgbelow20.getContentTable().add(new Label("Reduce your age hehehe",skin));
        dialogmsgbelow20.button(new TextButton("OK",skin));
        dialoggameover=new Dialog("Game Over",skin){
            @Override
            protected void result(Object object) {
                super.result(object);
                Gdx.app.log("papermatatu","object "+object+" clicked");
                ResetGame();
            }
        };
        dialoggameover.padTop(20f).padBottom(20f);
        dialoggameover.getContentTable().add(winorloselabel).width(Gdx.graphics.getWidth()/2-60f).row();
        dialoggameover.getContentTable().add(computercutcountlabel).width(Gdx.graphics.getWidth()/2-60f).row();
        dialoggameover.getContentTable().add(humancutcountlabel).width(Gdx.graphics.getWidth()/2-60f).row();
        //dialoggameover.getButtonTable().padTop(50f);
        dialoggameover.button(restartButton,true);
        dialoggameover.layout();
        dialoggameover.hide();
        //dialoghumanask
        dialoghumanask=new Dialog("Pick What You Want",skin);
        humanpickdaibutton=new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("dai")));
        humanpickdaibutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameHands.osaabaki="d";
                GameHands.playertomove=0;
                Gdx.input.setInputProcessor(PlayScreen.this);
                super.clicked(event, x, y);
            }
        });
        humanpickkamulibutton=new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("kamuli")));
        humanpickkamulibutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameHands.osaabaki="c";
                GameHands.playertomove=0;
                Gdx.input.setInputProcessor(PlayScreen.this);
                super.clicked(event, x, y);
            }
        });
        humanpickmutimabutton=new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("hearts")));
        humanpickmutimabutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameHands.osaabaki="h";
                GameHands.playertomove=0;
                Gdx.input.setInputProcessor(PlayScreen.this);
                super.clicked(event, x, y);
            }
        });
        humanpickkitiyobutton=new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("spade")));
        humanpickkitiyobutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameHands.osaabaki="s";
                GameHands.playertomove=0;
                Gdx.input.setInputProcessor(PlayScreen.this);
                super.clicked(event, x, y);
            }
        });
        dialoghumanask.button(humanpickdaibutton);
        dialoghumanask.button(humanpickkamulibutton);
        dialoghumanask.button(humanpickmutimabutton);
        dialoghumanask.button(humanpickkitiyobutton);
        dialoghumanask.layout();
        //dialoghumanask.show(stage);
        dialogcomputeraskdai=new Dialog("Computer Wants",skin);
        dialogcomputeraskdai.button(new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("dai"))));
        dialogcomputeraskkamuli=new Dialog("Computer Wants",skin);
        dialogcomputeraskkamuli.button(new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("kamuli"))));
        dialogcomputeraskmutima=new Dialog("Computer Wants",skin);
        dialogcomputeraskmutima.button(new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("hearts"))));
        dialogcomputeraskkitiyo=new Dialog("Computer Wants",skin);
        dialogcomputeraskkitiyo.button(new ImageButton(new SpriteDrawable(m_gamedata.textureAtlasCards.createSprite("spade"))));
        //dialogcomputeraskdai.show(stagecomputer);
    }

    private void ResetGame() {
            GameHands.osaabaki="";
            computercutcountlabel.setText("");
            humancutcountlabel.setText("");
            m_tugaabyecards=false;
            m_basaaze=false;
            m_computercutcount=0;
            m_humancutcount=0;
            kalezanyaanimationplaying=false;
            computerHand.cardlist.clear();
            humanHand.cardlist.clear();
            gameisover=false;
            gameisovernonce=false;
            GameHands.lastplayedcard=null;
            GameHands.playertomove= new Random(System.currentTimeMillis()).nextInt(2);
            GameHands.discards.clear();
            GameHands.deck.clear();
            GameHands.twakagaabaCardMeeka=0;
            GameHands.isanimating=false;
            GameHands.waitingforanimationtocomplete=false;
            GameHands.cutterCard=null;
            Gdx.input.setInputProcessor(this);
            String[] suitstrings={"s","c","d","h"};
            String[] numberstrings={"2","3","4","5","6","7","8","9","t","j","q","k","a"};
            for(String s:suitstrings){
                for(String n:numberstrings){
                    Card c=new Card(s,n,m_gamedata);
                    c.SetPosition(3,-1);
                    GameHands.deck.add(c);
                }
            }
            Changa(GameHands.deck);
            GameHands.cutterCard=GameHands.deck.remove(0);
            GameHands.cutterCard.Show();
            GameHands.cutterCard.SetPosition(2.9f,-1);
            GameHands.cutterCard.SetRotation(90);
            for(Card c:computerHand.cardlist){
                c.Show();
            }
            gameisover=false;
    }

    public PlayScreen(CommonFunctions gameData) {
        m_gamedata=gameData;
        String[] suitstrings={"s","c","d","h"};
        String[] numberstrings={"2","3","4","5","6","7","8","9","t","j","q","k","a"};
        for(String s:suitstrings){
            for(String n:numberstrings){
                Card c=new Card(s,n,gameData);
                c.SetPosition(3,-1);
                GameHands.deck.add(c);
            }
        }
        Changa(GameHands.deck);
        //Gaaba();
        stage=new Stage();
        stagecomputer=new Stage();
    }
    void renderwinorloseifneccessary(){
        if(GameHands.waitingforanimationtocomplete)return;
        if((m_tugaabyecards&&humanHand.cardlist.size()==0)||(m_basaaze)||gameisover){//not dry
            if(!gameisovernonce){
                gameisovernonce=true;
                Gdx.input.setInputProcessor(stage);
                if(humanHand.cardlist.size()==0)winorloselabel.setText("You have won");
                if(computerHand.cardlist.size()==0)winorloselabel.setText("You Lost");
                if(m_basaaze&&(m_humancutcount<m_computercutcount)){
                    winorloselabel.setText("You have won");
                    if(m_basaaze)humancutcountlabel.setText("You have "+m_humancutcount);
                    if(m_basaaze)computercutcountlabel.setText("Computer has "+m_computercutcount);
                }
                if(m_basaaze&&(m_humancutcount==m_computercutcount)){
                    winorloselabel.setText("Draw");
                    if(m_basaaze)humancutcountlabel.setText("You have "+m_humancutcount);
                    if(m_basaaze)computercutcountlabel.setText("Computer has "+m_computercutcount);
                }
                if(m_basaaze&&(m_humancutcount>m_computercutcount)){
                    winorloselabel.setText("You Lost");
                    if(m_basaaze)humancutcountlabel.setText("You have "+m_humancutcount);
                    if(m_basaaze)computercutcountlabel.setText("Computer has "+m_computercutcount);
                }
                dialoggameover.show(stage);
            }
            Gdx.gl.glClearColor(1,1,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            m_gamedata.spriteBatch.setProjectionMatrix(cam.combined);
            m_gamedata.spriteBatch.begin();
            if(m_tugaabyecards)GameHands.cutterCard.Draw(m_gamedata.spriteBatch);
            for(Card c:humanHand.cardlist){
                c.Draw(m_gamedata.spriteBatch);
            }
            for(Card c:computerHand.cardlist){
                c.Draw(m_gamedata.spriteBatch);
            }
            for(Card c:GameHands.deck){
                c.Draw(m_gamedata.spriteBatch);
            }
            for(Card c:GameHands.discards){
                c.Draw(m_gamedata.spriteBatch);
            }
            m_gamedata.spriteBatch.end();
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
            //hud.stage.draw();
            return;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!m_tugaabyecards)return false;
        if(GameHands.waitingforanimationtocomplete)return false;
        if (GameHands.playertomove==0)return false;
        Vector3 worldcoords=cam.unproject(new Vector3(screenX,screenY,0));
        Card chosen=null;
        for(int i=humanHand.cardlist.size()-1;i>=0;i--){
            Card c=humanHand.cardlist.get(i);
            if(c.frontsprite.getBoundingRectangle().contains(worldcoords.x,worldcoords.y)){
                if(c.number.equals("7")&&c.suit.equals(GameHands.cutterCard.suit)){
                    //is a cutter
                    if((humanHand.GetCutCount()-7)>20){
                        Gdx.app.log("papermatatu","Sorry Human, Wont cut below 20 . CutCount "+(humanHand.GetCutCount()-7));
                        dialogmsgbelow20.show(stage);
                        Gdx.input.setInputProcessor(stage);
                        return false;
                    }
                }
                chosen=c;
                Gdx.app.log("papermatatu","Card "+c.suit+c.number+" touched");
                break;
            }
        }
        if(chosen==null){
            if(kalezanyasprite.getBoundingRectangle().contains(worldcoords.x,worldcoords.y)){
                if(GameHands.playertomove==1){
                        humanHand.opikinze=false;
                        computerHand.opikinze=false;//bug easy to miss. computer refuses to play
                        animateOutKaleZanya();
                        GameHands.playertomove=0;
                        return true;
                }
            }
            //picking
            for(Card c:GameHands.deck){
                if(humanHand.opikinze){
                    Gdx.app.log("papermatatu","human, you have alraedy picked");
                    return false;
                }
                if(c.frontsprite.getBoundingRectangle().contains(worldcoords.x,worldcoords.y)) {
                    if(humanHand.bakubanja){
                        Card picked=humanHand.PickForMbwa();
                        GameHands.waitingforanimationtocomplete = true;//block
                        picked.Show();
                        Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(3)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(-4+.8f)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(3)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(-4+.8f)
                                .start(MyGame.tweenManager);
                        picked=humanHand.PickForMbwa();
                        picked.Show();
                        Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(4)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(-4+.8f)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(4)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(-4+.8f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        ReorganiseCardsHuman(humanHand.cardlist);
                                        humanHand.opikinze=false;
                                        humanHand.bakubanja=false;
                                        computerHand.opikinze=false;
                                        computerHand.bakubanja=false;
                                        GameHands.playertomove=0;//turn to computer
                                        GameHands.waitingforanimationtocomplete = false;
                                    }
                                })
                                .setCallbackTriggers(TweenCallback.COMPLETE)
                                .start(MyGame.tweenManager);
                        return true;
                    }
                    //trying to pick play
                    Card picked=humanHand.Pick();
                    GameHands.waitingforanimationtocomplete = true;//block
                    picked.Show();
                    Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(3)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(-4+.8f)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(3)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(-4+.8f)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    ReorganiseCardsHuman(humanHand.cardlist);
                                    computerHand.opikinze=false;
                                    computerHand.bakubanja=false;
                                    GameHands.waitingforanimationtocomplete = false;
                                }
                            })
                            .setCallbackTriggers(TweenCallback.COMPLETE)
                            .start(MyGame.tweenManager);
                    return true;
                }
            }
        }
        if(chosen!=null){
            if (GameHands.playertomove==1) {
                if(!humanHand.IsMoveValid(chosen)){
                    Gdx.app.log("papermatatu","invalid move");
                    return false;
                }
                humanHand.Play(chosen);
                GameHands.osaabaki="";
                hideComputerNsaaba();
                GameHands.lastplayedcard=chosen;
                animateOutKaleZanya();
                GameHands.waitingforanimationtocomplete = true;//block
                //GameHands.discards.add(chosen);
                humanHand.cardlist.remove(chosen);
                chosen.Show();
                Tween.to(chosen.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                        .target(0)
                        .start(MyGame.tweenManager);
                Tween.to(chosen.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                        .target(0)
                        .start(MyGame.tweenManager);
                Tween.to(chosen.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                        .target(0)
                        .start(MyGame.tweenManager);
                Tween.to(chosen.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                        .target(0)
                        .setCallback(new TweenCallback() {
                            @Override
                            public void onEvent(int type, BaseTween<?> source) {
                                ReorganiseCardsHuman(humanHand.cardlist);
                                GameHands.waitingforanimationtocomplete = false;
                                if(GameHands.lastplayedcard.number.equals("2"))computerHand.bakubanja=true;
                                if(GameHands.lastplayedcard.number.equals("j"))return;
                                if(GameHands.lastplayedcard.number.equals("8"))return;
                                if(GameHands.lastplayedcard.number.equals("a")){
                                    if(humanHand.cardlist.size()==0){
                                        gameisover=true;
                                        return;//we have won
                                    }
                                    dialoghumanask.show(stage);
                                    Gdx.input.setInputProcessor(stage);
                                    return;
                                }
                                if(GameHands.lastplayedcard.number.equals("7")&&GameHands.cutterCard.suit.equals(GameHands.lastplayedcard.suit)){
                                    m_basaaze=true;
                                    gameisover=true;
                                    m_computercutcount=computerHand.GetCutCount();
                                    m_humancutcount=humanHand.GetCutCount();
                                    return;
                                }
                                GameHands.playertomove = 0;
                            }
                        })
                        .setCallbackTriggers(TweenCallback.COMPLETE)
                        .start(MyGame.tweenManager);
            }else {
                Gdx.app.log("papermatatu","wait for your turn . Its computers turn");
            }
        }
        return true;

    }

    private void hideComputerNsaaba() {
        dialogcomputeraskdai.hide();
        dialogcomputeraskkitiyo.hide();
        dialogcomputeraskkamuli.hide();
        dialogcomputeraskmutima.hide();
    }

    @Override
    public void show() {
        //hud=new Hud(m_gamedata.spriteBatch);
        Gdx.input.setInputProcessor(this);
        InitGui();
        kalezanyasprite=m_gamedata.textureAtlasCards.createSprite("arrow");
        kalezanyasprite.setSize(m_gamedata.CARD_WIDTH,m_gamedata.CARD_HEIGHT);
        kalezanyasprite.setPosition(-40,0.5f);
    }

    @Override
    public void render(float delta) {
        if(m_tugaabyecards&&!GameHands.waitingforanimationtocomplete&&humanHand.cardlist.size()==0){
            gameisover=true;
        }
        if(m_tugaabyecards&&!GameHands.waitingforanimationtocomplete&&computerHand.cardlist.size()==0){
            gameisover=true;
        }
        if(m_tugaabyecards&&GameHands.deck.size()==0){
            DaamuOkucangaDiscards();
        }
        animateInKaleZanyaIfNeccessary();
        if(m_basaaze)computerHand.RevealCards();
        renderwinorloseifneccessary();
        if(!m_tugaabyecards)Gaaba();
        if(GameHands.playertomove==0)ComputerPlayNow();
        m_gamedata.spriteBatch.setProjectionMatrix(cam.combined);
        MyGame.tweenManager.update(delta);
        Gdx.gl.glClearColor(1,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        m_gamedata.spriteBatch.begin();
        drawhands();
        kalezanyasprite.draw(m_gamedata.spriteBatch);
        m_gamedata.spriteBatch.end();
        //hud.label1.setText("Player To Move "+GameHands.playertomove);
        //hud.label2.setText("deck size  "+GameHands.deck.size());
        //hud.label3.setText(Gdx.graphics.getFramesPerSecond()+" fps");
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        stagecomputer.act();
        stagecomputer.draw();
        //hud.stage.draw();
    }

    void drawhands(){
        if(m_tugaabyecards)GameHands.cutterCard.Draw(m_gamedata.spriteBatch);
        for(Card c:humanHand.cardlist){
            c.Draw(m_gamedata.spriteBatch);
        }
        for(Card c:computerHand.cardlist){
            c.Draw(m_gamedata.spriteBatch);
        }
        for(Card c:GameHands.deck){
            c.Draw(m_gamedata.spriteBatch);
        }
        for(Card c:GameHands.discards){
            c.Draw(m_gamedata.spriteBatch);
        }
    }

    @Override
    public void resize(int width, int height) {
        if(width>height){
            cam.viewportHeight=m_gamedata.MINIMUM_VIEWPORT_SIZE*.7f;
            cam.viewportWidth=cam.viewportHeight*(float)width/(float)height;
        }
        else {
            cam.viewportWidth=m_gamedata.MINIMUM_VIEWPORT_SIZE;
            cam.viewportHeight=cam.viewportWidth*(float)height/(float)width*.7f;
        }
        stagecomputer.getViewport().update(width,height,true);
        stage.getViewport().update(width,height,true);
        cam.update();
    }
    public void Changa(ArrayList<Card> cards){
        //perform fisher-yates sort on unpickedcards. See wikipedia
        for (int i = cards.size()-1; i > 1; i--) {
            int j =m_gamedata.random.nextInt(i);//this is not secure because its predictable. Use a truly random source on server or there will be problems. :)
            Card temp= cards.get(j);
            cards.set(j,cards.get(i));
            cards.set(i, temp);
        }
    }
    public void Gaaba(){
        if(GameHands.isanimating){
            //Gdx.app.log("papermatatu","animating Gaaba");
            return;
        }
        GameHands.isanimating=true;//block again
        if(GameHands.twakagaabaCardMeeka>13){
            //gaaba cutter and exit
            GameHands.cutterCard=GameHands.deck.remove(0);
            GameHands.cutterCard.SetRotation(90);
            GameHands.cutterCard.SetPosition(2f,-1);
            GameHands.cutterCard.Show();
            m_tugaabyecards=true;//terminate
            //debug
            GameHands.deck.size();
            return;
        }
        Card newcard;
        if(GameHands.playertomove==0){//computer
            newcard=GameHands.deck.remove(0);
            computerHand.cardlist.add(newcard);
            Tween.to(newcard.backsprite, SpriteTweenAccessor.XPOSITION,1f)
                    .target(3)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.backsprite ,SpriteTweenAccessor.YPOSITION,1f)
                    .target(2)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                    .target(3)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                    .target(2)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            ReorganiseCardsComputer(computerHand.cardlist);
                            GameHands.isanimating=false;
                        }
                    })
                    .setCallbackTriggers(TweenCallback.COMPLETE)
                    .start(MyGame.tweenManager);
            GameHands.playertomove=1;

        }else {//human
            newcard=GameHands.deck.remove(0);
            humanHand.cardlist.add(newcard);
            newcard.Show();
            Tween.to(newcard.backsprite,SpriteTweenAccessor.XPOSITION,1f)
                    .target(3)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.backsprite ,SpriteTweenAccessor.YPOSITION,1f)
                    .target(-4+.8f)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                    .target(3)
                    .start(MyGame.tweenManager);
            Tween.to(newcard.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                    .target(-4+.8f)
                    .setCallback(new TweenCallback() {
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
                            ReorganiseCardsHuman(humanHand.cardlist);
                            GameHands.isanimating=false;
                        }
                    })
                    .setCallbackTriggers(TweenCallback.COMPLETE)
                    .start(MyGame.tweenManager);
            GameHands.playertomove=0;
        }
        GameHands.twakagaabaCardMeeka+=1;
    }
    public void ReorganiseCardsComputer(ArrayList<Card> cardlist){
        int size=cardlist.size();
        if(size==0)return;//avoid divide by zero
        for(int i=0;i<size;i++){
            Card c=cardlist.get(i);
            float xval=(i/(float)size)*8f-4f;
            if(size>8){
                Tween.to(c.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(xval)
                        .start(MyGame.tweenManager);
                Tween.to(c.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(2)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(xval)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(2)
                        .start(MyGame.tweenManager);
                //c.TweenTo(xval,1f);
            }
            else {
                Tween.to(c.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(i-(size/2))
                        .start(MyGame.tweenManager);
                Tween.to(c.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(2)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(i-(size/2))
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(2)
                        .start(MyGame.tweenManager);
                //c.TweenTo(i-(size/2),1f);
            }
        }
    }
    public void ReorganiseCardsHuman(ArrayList<Card> cardlist){
        int size=cardlist.size();
        if(size==0)return;//avoid divide by zero
        for(int i=0;i<size;i++){
            Card c=cardlist.get(i);
            float xval=(i/(float)size)*8f-4f;
            if(size>8){
                Tween.to(c.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(xval)
                        .start(MyGame.tweenManager);
                Tween.to(c.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(-4+.8f)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(xval)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(-4+.8f)
                        .start(MyGame.tweenManager);
                //c.TweenTo(xval,1f);
            }
            else {
                Tween.to(c.frontsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(i-(size/2))
                        .start(MyGame.tweenManager);
                Tween.to(c.frontsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(-4+.8f)
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.XPOSITION,1f)
                        .target(i-(size/2))
                        .start(MyGame.tweenManager);
                Tween.to(c.backsprite,SpriteTweenAccessor.YPOSITION,1f)
                        .target(-4+.8f)
                        .start(MyGame.tweenManager);
                //c.TweenTo(i-(size/2),1f);
            }
        }
    }
    public void animateInKaleZanyaIfNeccessary(){
        if(GameHands.playertomove==1){
            if(m_tugaabyecards){
                if(humanHand.opikinze){
                    if(!kalezanyaanimationplaying){
                        kalezanyaanimationplaying=true;
                        Tween.to(kalezanyasprite, SpriteTweenAccessor.XPOSITION, 0.5f)
                                .target(-3f)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        kalezanyaanimationplaying=false;
                                        //humanHand.opikinze=false;
                                    }
                                })
                                .setCallbackTriggers(TweenCallback.COMPLETE)
                                .start(MyGame.tweenManager);
                    }
                }
            }
        }
    }
    public void animateOutKaleZanya(){
                        Tween.to(kalezanyasprite, SpriteTweenAccessor.XPOSITION, 2f)
                                .target(-50)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        kalezanyaanimationplaying=false;
                                        //humanHand.opikinze=false;
                                    }
                                })
                                .setCallbackTriggers(TweenCallback.COMPLETE)
                                .start(MyGame.tweenManager);
    }
    public void ComputerPlayNow() {
        if(GameHands.playertomove==0){
            if(gameisover){
                //Gdx.app.log("papermatatu","game is over. Refusing to play (computers turn)");
                return;
            }
            Card chosen=computerHand.GetBestCard();
            if(!m_tugaabyecards)return ;
            if(GameHands.waitingforanimationtocomplete)return ;
            if(chosen!=null){
                if (GameHands.playertomove==0) {
                    if(!computerHand.IsMoveValid(chosen)){
                        Gdx.app.log("papermatatu","computer trying invalid move");
                        return ;
                    }
                    computerHand.Play(chosen);
                    computerHand.bakubanja=false;
                    computerHand.opikinze=false;
                    GameHands.lastplayedcard=chosen;
                    GameHands.waitingforanimationtocomplete = true;//block
                    //GameHands.discards.add(chosen);
                    computerHand.cardlist.remove(chosen);
                    chosen.Show();
                    Tween.to(chosen.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(0)
                            .start(MyGame.tweenManager);
                    Tween.to(chosen.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(0)
                            .start(MyGame.tweenManager);
                    Tween.to(chosen.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(0)
                            .start(MyGame.tweenManager);
                    Tween.to(chosen.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(0)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    ReorganiseCardsComputer(computerHand.cardlist);
                                    if(GameHands.lastplayedcard.number.equals("2")){
                                        GameHands.waitingforanimationtocomplete = false;
                                        humanHand.bakubanja=true;//allow playertomove to change by not returning
                                        GameHands.osaabaki="";
                                        hideComputerNsaaba();
                                        GameHands.playertomove = 1;
                                        return;
                                    }
                                    if(GameHands.lastplayedcard.number.equals("j")){
                                        GameHands.waitingforanimationtocomplete = false;
                                        GameHands.osaabaki="";
                                        hideComputerNsaaba();
                                        return;
                                    }
                                    if(GameHands.lastplayedcard.number.equals("8")){
                                        GameHands.waitingforanimationtocomplete = false;
                                        GameHands.osaabaki="";
                                        hideComputerNsaaba();
                                        return;
                                    }
                                    if(GameHands.lastplayedcard.number.equals("7")&&GameHands.cutterCard.suit.equals(GameHands.lastplayedcard.suit)){
                                        GameHands.waitingforanimationtocomplete = false;
                                        GameHands.osaabaki="";
                                        m_computercutcount=computerHand.GetCutCount();
                                        m_humancutcount=humanHand.GetCutCount();
                                        m_basaaze=true;
                                        gameisover=true;
                                        return;
                                    }
                                    if(GameHands.lastplayedcard.number.equals("a")){
                                        if(computerHand.cardlist.size()==0){
                                            gameisover=true;
                                            return;//computer has won
                                        }
                                        GameHands.osaabaki=computerHand.GetBestSuitToAsk();
                                        if(GameHands.osaabaki.equals("h"))dialogcomputeraskmutima.show(stagecomputer);
                                        if(GameHands.osaabaki.equals("d"))dialogcomputeraskdai.show(stagecomputer);
                                        if(GameHands.osaabaki.equals("c"))dialogcomputeraskkamuli.show(stagecomputer);
                                        if(GameHands.osaabaki.equals("s"))dialogcomputeraskkitiyo.show(stagecomputer);
                                        GameHands.waitingforanimationtocomplete = false;
                                        GameHands.playertomove = 1;
                                        return;
                                    }
                                    GameHands.waitingforanimationtocomplete=false;
                                    GameHands.osaabaki="";
                                    hideComputerNsaaba();
                                    GameHands.playertomove = 1;
                                }
                            })
                            .setCallbackTriggers(TweenCallback.COMPLETE)
                            .start(MyGame.tweenManager);
                }else {
                    Gdx.app.log("papermatatu","wait for your turn . Its humans turn");
                }

            }else{//Best card==null. Try pick and play or pick2
                //picking
                for(Card c:GameHands.deck){
                    if(computerHand.opikinze){
                        Gdx.app.log("papermatatu","Computer, you have alraedy picked. Kale zanya");
                        computerHand.opikinze=false;
                        GameHands.playertomove=1;
                        break;
                    }
                    if(computerHand.bakubanja){
                        Card picked=computerHand.Pick();
                        GameHands.waitingforanimationtocomplete = true;//block
                        //picked.Show();
                        Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(3)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(2)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(3)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(2)
                                .start(MyGame.tweenManager);

                        //pick second card
                        picked=computerHand.Pick();
                        //picked.Show();
                        Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(4)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(2)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                                .target(4)
                                .start(MyGame.tweenManager);
                        Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                                .target(2)
                                .setCallback(new TweenCallback() {
                                    @Override
                                    public void onEvent(int type, BaseTween<?> source) {
                                        ReorganiseCardsComputer(computerHand.cardlist);
                                        GameHands.waitingforanimationtocomplete = false;
                                        computerHand.bakubanja=false;
                                        computerHand.opikinze=false;
                                        GameHands.playertomove=1;//allow human to move
                                    }
                                })
                                .setCallbackTriggers(TweenCallback.COMPLETE)
                                .start(MyGame.tweenManager);
                        return ;
                    }
                    //trying to pick play
                    Card picked=computerHand.Pick();
                    GameHands.waitingforanimationtocomplete = true;//block
                    //picked.Show();
                    Tween.to(picked.backsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(3)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.backsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(2)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.frontsprite, SpriteTweenAccessor.XPOSITION, 1f)
                            .target(3)
                            .start(MyGame.tweenManager);
                    Tween.to(picked.frontsprite, SpriteTweenAccessor.YPOSITION, 1f)
                            .target(2)
                            .setCallback(new TweenCallback() {
                                @Override
                                public void onEvent(int type, BaseTween<?> source) {
                                    ReorganiseCardsComputer(computerHand.cardlist);
                                    GameHands.waitingforanimationtocomplete = false;
                                }
                            })
                            .setCallbackTriggers(TweenCallback.COMPLETE)
                            .start(MyGame.tweenManager);
                    return ;
                }
            }
        }else {
            Gdx.app.log("papermatatu","computer should wait its turn");
        }
    }
    void DaamuOkucangaDiscards(){
        //remove last discard
        int discardsize=GameHands.discards.size();
        Card lastdiscard=GameHands.discards.remove(discardsize-1);
        discardsize=GameHands.discards.size();
        for(int i=0;i<discardsize;i++){
            GameHands.deck.add(GameHands.discards.get(i));
        }
        GameHands.discards.clear();
        GameHands.discards.add(lastdiscard);
        for (Card c:GameHands.deck) {
            c.SetPosition(4,-1);
            c.Hide();
        }
        Changa(GameHands.deck);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
