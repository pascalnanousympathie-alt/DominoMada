package com.madamgames.dominomada;
import java.util.*;
public final class DominoGame{
 public final List<String>[] hands;private final List<String> deck=new ArrayList<>();private final Deque<String> board=new ArrayDeque<>();private int currentPlayer;
 @SuppressWarnings("unchecked") public DominoGame(){hands=new List[4];for(int i=0;i<4;i++)hands[i]=new ArrayList<>();reset();}
 public void reset(){deck.clear();board.clear();currentPlayer=0;for(List<String>h:hands)h.clear();for(int a=0;a<=6;a++)for(int b=a;b<=6;b++)deck.add(tile(a,b));Collections.shuffle(deck);for(int r=0;r<4;r++)for(int i=0;i<7;i++)hands[r].add(deck.remove(0));}
 private static String tile(int a,int b){return a+"|"+b;}private static int a(String t){return Integer.parseInt(t.substring(0,t.indexOf('|')));}private static int b(String t){return Integer.parseInt(t.substring(t.indexOf('|')+1));}
 public int remaining(){return deck.size();}public int currentPlayer(){return currentPlayer;}public List<String> board(){return new ArrayList<>(board);}
 public boolean canPlay(String t){if(!isValidTile(t))return false;if(board.isEmpty())return true;int x=a(t),y=b(t),l=a(board.peekFirst()),r=b(board.peekLast());return x==l||y==l||x==r||y==r;}
 public boolean play(int player,String t){if(player<0||player>3||player!=currentPlayer||!hands[player].contains(t)||!canPlay(t))return false;int x=a(t),y=b(t);if(board.isEmpty())board.add(t);else if(x==a(board.peekFirst()))board.addFirst(tile(y,x));else if(y==a(board.peekFirst()))board.addFirst(t);else if(x==b(board.peekLast()))board.addLast(t);else if(y==b(board.peekLast()))board.addLast(tile(y,x));else return false;hands[player].remove(t);currentPlayer=(currentPlayer+1)%4;return true;}
 public boolean won(int player){return player>=0&&player<4&&hands[player].isEmpty();}private static boolean isValidTile(String t){try{int x=a(t),y=b(t);return x>=0&&x<=6&&y>=0&&y<=6;}catch(Exception e){return false;}}
 public boolean isBlocked(){if(board.isEmpty())return false;for(List<String>h:hands)for(String t:h)if(canPlay(t))return false;return true;}public int winner(){for(int i=0;i<4;i++)if(won(i))return i;return -1;}
 public String saveState(){StringBuilder s=new StringBuilder();s.append(currentPlayer).append(";").append(join(board)).append(";").append(join(deck)).append(";");for(int i=0;i<4;i++){if(i>0)s.append("#");s.append(join(hands[i]));}return s.toString();}
 public boolean loadState(String state){try{String[]p=state.split(";",-1);if(p.length!=4)return false;int cp=Integer.parseInt(p[0]);if(cp<0||cp>3)return false;board.clear();board.addAll(split(p[1]));deck.clear();deck.addAll(split(p[2]));String[]hs=p[3].split("#",-1);if(hs.length!=4)return false;for(int i=0;i<4;i++){hands[i].clear();hands[i].addAll(split(hs[i]));}currentPlayer=cp;return true;}catch(Exception e){return false;}}
 private static String join(Collection<String>c){return String.join(",",c);}private static List<String>split(String s){return s.isEmpty()?new ArrayList<>():new ArrayList<>(Arrays.asList(s.split(",")));}
}
