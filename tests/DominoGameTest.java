package com.madamgames.dominomada;
public final class DominoGameTest{
 static void ok(boolean x,String m){if(!x)throw new AssertionError(m);}
 public static void main(String[]a){
  DominoGame g=new DominoGame();int total=0;for(java.util.List<String>h:g.hands)total+=h.size();ok(total==28,"28 tiles dealt");ok(g.remaining()==0,"deck empty");
  String t=g.hands[0].get(0);ok(g.play(0,t),"first move");ok(g.currentPlayer()==1,"turn advances");ok(!g.play(0,t),"wrong player blocked");ok(!g.canPlay("bad"),"invalid tile blocked");
  String saved=g.saveState();DominoGame r=new DominoGame();ok(r.loadState(saved),"state restores");ok(r.currentPlayer()==g.currentPlayer(),"turn restored");ok(r.board().equals(g.board()),"board restored");
  String next=null;for(String x:new java.util.ArrayList<>(g.hands[1]))if(g.play(1,x)){next=x;break;}ok(next!=null,"second move");String saved2=g.saveState();ok(r.loadState(saved2),"oriented board state restores");
  ok(r.winner()==-1,"no winner early");ok(!g.isGameOver(),"game active");ok(g.score(0)>=0,"score available");
  String bad=saved.replace("0|0","0|7");ok(!r.loadState(bad),"corrupt state rejected");
  System.out.println("DOMINO_TEST_PASS");
 }
}
