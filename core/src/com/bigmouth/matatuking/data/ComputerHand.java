package com.bigmouth.matatuking.data;


import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by deo on 11/20/2016.
 */

public class ComputerHand {
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
    public String GetBestSuitToAsk(){//returns h,s,c,d
        int hc=0;//heart count
        int sc=0;//spade count
        int cc=0;//club count
        int dc=0;//dai count
        for(Card c:cardlist){
            if(c.suit.equals("h")){
                hc+=c.GetCutCount();
                continue;
            }
            if(c.suit.equals("s")){
                sc+=c.GetCutCount();
                continue;
            }
            if(c.suit.equals("c")){
                cc+=c.GetCutCount();
                continue;
            }
            if(c.suit.equals("d")){
                dc+=c.GetCutCount();
                continue;
            }
        }
        int maxcount=0;
        maxcount=Math.max(hc,sc);
        maxcount=Math.max(maxcount,cc);
        maxcount=Math.max(maxcount,dc);
        if(maxcount==hc)return "h";
        if(maxcount==cc)return "c";
        if(maxcount==sc)return "s";
        if(maxcount==dc)return "d";
        return "";//if we fall here there is some error
    }
    public Card GetBestCard(){
        if(bakubanja){
            for(Card c:cardlist){
                if(c.number.equals("2")){
                    return c;
                }
            }
        }//bakubanja. Only twos work
        Card localbestcard=null;
        for(Card c:cardlist){
            if(IsMoveValid(c)){
                if(localbestcard==null){
                    if(GameHands.cutterCard!=null&&c!=null){
                        if(c.suit.equals(GameHands.cutterCard.suit)&&c.number.equals("7")){
                            if((GetCutCount()-7)>20){
                                Gdx.app.log("papermatatu","computer cant cut above 20. Cutter count is "+(GetCutCount()-7));
                                continue;
                            }
                        }
                    }
                    localbestcard=c;
                    continue;
                }
                if(c.GetCutCount()>localbestcard.GetCutCount()){
                    if(GameHands.cutterCard!=null&&c!=null){
                        if(c.suit.equals(GameHands.cutterCard.suit)&&c.number.equals("7")){
                            if((GetCutCount()-7)>20){
                                Gdx.app.log("papermatatu","computer cant cut above 20. Cutter count is "+(GetCutCount()-7));
                                continue;
                            }
                        }
                    }
                    localbestcard=c;
                    continue;
                }
            }
        }
        return localbestcard;
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
        if(cardlist.contains(matatuCard)){
            if(bakubanja){
                if(matatuCard.number.equals("2"))return true;
                return false;
            }
            if(GameHands.lastplayedcard==null)return true;
            if(GameHands.osaabaki.length()>0){
                if(GameHands.osaabaki.equals(matatuCard.suit))return true;
                if(matatuCard.number.equals("a"))return true;//nsaaba always valid
                return false;
            }
            if(GameHands.lastplayedcard.suit.equals(matatuCard.suit))return true;
            if(GameHands.lastplayedcard.number.equals(matatuCard.number))return true;
            if(matatuCard.number.equals("a"))return true;
            return false;
        }
        return false;
    }
    public Card Pick(){
        if(GameHands.deck.size()==0){
            Gdx.app.log("papermatatu","Computer trying to pick from and empty deck. Nayanga");
            DaamuOkucangaDiscards();
        }
        Card picked=GameHands.deck.remove(GameHands.deck.size()-1);
        opikinze=true;
        cardlist.add(picked);
        return picked;
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
    public void Play(Card card){
        if(cardlist.size()==0){
            Gdx.app.log("papermatatu","Computer trying to pick from and empty deck. Nayanga");
            return;
        }
        opikinze=false;
        bakubanja=false;
        cardlist.remove(card);
        GameHands.discards.add(card);
        GameHands.discards.size();
        GameHands.lastplayedcard=card;
    }
    public void RevealCards()
    {
        for(Card c: cardlist){
            c.Show();
        }
    }
    void HideCards(){
        for(Card c: cardlist){
            c.Hide();
        }
    }

}
