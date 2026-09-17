package com.madamgames.dominomada;

import java.util.*;

public final class DominoGame {
  public final List<String>[] hands;
  private final List<String> deck = new ArrayList<>();
  private final Deque<String> board = new ArrayDeque<>();
  private int currentPlayer;

  @SuppressWarnings("unchecked")
  public DominoGame() {
    hands = new List[4];
    for (int i=0;i<4;i++) hands[i]=new ArrayList<>();
    reset();
  }

  public void reset() {
    deck.clear(); board.clear(); currentPlayer=0;
    for (List<String> h:hands) h.clear();
    for(int a=0;a<=6;a++) for(int b=a;b<=6;b++) deck.add(tile(a,b));
    Collections.shuffle(deck);
    for(int r=0;r<4;r++) for(int i=0;i<7;i++) hands[r].add(deck.remove(0));
  }

  private static String tile(int a,int b){return a+"|"+b;}
  private static int a(String t){return Integer.parseInt(t.substring(0,t.indexOf('|')));}
  private static int b(String t){return Integer.parseInt(t.substring(t.indexOf('|')+1));}
  public int remaining(){return deck.size();}
  public int currentPlayer(){return currentPlayer;}
  public List<String> board(){return new ArrayList<>(board);}

  public boolean canPlay(String t){
    if(board.isEmpty()) return true;
    int x=a(t), y=b(t), left=a(board.peekFirst()), right=b(board.peekLast());
    return x==left||y==left||x==right||y==right;
  }

  public boolean play(int player,String t){
    if(player!=currentPlayer||!hands[player].contains(t)||!canPlay(t)) return false;
    int x=a(t), y=b(t);
    if(board.isEmpty()) board.add(t);
    else if(x==a(board.peekFirst())) board.addFirst(tile(y,x));
    else if(y==a(board.peekFirst())) board.addFirst(t);
    else if(x==b(board.peekLast())) board.addLast(t);
    else if(y==b(board.peekLast())) board.addLast(tile(y,x));
    else return false;
    hands[player].remove(t); currentPlayer=(currentPlayer+1)%4; return true;
  }

  public boolean won(int player){return hands[player].isEmpty();}
}
