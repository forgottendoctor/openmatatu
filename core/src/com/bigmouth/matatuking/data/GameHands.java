package com.bigmouth.matatuking.data;

import java.util.ArrayList;
import java.util.Random;

public class GameHands {
    public static Card lastplayedcard=null;
    public static int playertomove= new Random(System.currentTimeMillis()).nextInt(2);//first to move is random
    public static ArrayList<Card> discards=new ArrayList<Card>();
    public static ArrayList<Card> deck=new ArrayList<Card>();
    public static int twakagaabaCardMeeka=0;
    public static boolean isanimating=false;
    public static boolean waitingforanimationtocomplete=false;
    public static Card cutterCard=null;
    public static String osaabaki="";
}
