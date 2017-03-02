package com.bigmouth.matatuking.data;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Random;


public class HumanHand {
    public boolean bakubanja=false;
    public boolean opikinze=false;
    public ArrayList<Card> cardlist=new ArrayList<Card>();
    Random random=new Random();
    public void Changa(ArrayList<Card> cards){
        //perform fisher-yates sort on unpickedcards. See wikipedia
        for (int i = cards.size()-1; i > 1; i--) {
            int j =random.nextInt(i);//this is not secure because its predictable. Use a truly random source on server or there will be problems. :)
            Card temp= cards.get(j);
            cards.set(j,cards.get(i));
            cards.set(i, temp);
        }
    }
    public int GetCutCount(){
        int tempcount=0;
        for(Card m: cardlist){
            if(m.isCutter)continue;
            tempcount+=m.GetCutCount();
        }
        return tempcount;
    }
    public Card GetCutterOrNull(){
        for(Card m:cardlist){
            if(m.isCutter)return m;
        }
        return null;
    }
    public boolean IsMoveValid(Card matatuCard){
        if(GameHands.osaabaki.length()>0){
            if(matatuCard.suit.equals(GameHands.osaabaki))return true;
            if(matatuCard.number.equals("a"))return true;//a always valid
            return false;
        }
        if(cardlist.contains(matatuCard)){
            if(bakubanja){
                if(matatuCard.number.equals("2"))return true;
                return false;
            }
            if(GameHands.lastplayedcard==null)return true;
            if(GameHands.lastplayedcard.suit.equals(matatuCard.suit))return true;
            if(GameHands.lastplayedcard.number.equals(matatuCard.number))return true;
            if(matatuCard.number.equals("a"))return true;
            return false;
        }
        return false;
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
    public Card Pick(){
        if(GameHands.deck.size()==0){
            Gdx.app.log("papermatatu","You are trying to pick from and empty deck. Nayanga");
            DaamuOkucangaDiscards();
        }
        Card picked=GameHands.deck.remove(GameHands.deck.size()-1);
        cardlist.add(picked);
        opikinze=true;
        return picked;
    }
    public Card PickForMbwa(){
        if(GameHands.deck.size()==0){
            Gdx.app.log("papermatatu","You are trying to pick from and empty deck. Nayanga");
            DaamuOkucangaDiscards();
        }
        Card picked=GameHands.deck.remove(GameHands.deck.size()-1);
        cardlist.add(picked);
        return picked;
    }
    public void Play(Card card){
        if(cardlist.size()==0){
            Gdx.app.log("papermatatu","You are trying to pick from and empty deck. Nayanga");
            return;
        }
        opikinze=false;
        bakubanja=false;
        cardlist.remove(card);
        GameHands.discards.add(card);
        GameHands.lastplayedcard=card;
        GameHands.discards.size();
    }
}
