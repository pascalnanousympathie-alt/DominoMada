package com.madamgames.dominomada;
public final class DominoGameTest{
 static void ok(boolean x,String m){if(!x)throw new AssertionError(m);}
 public static void main(String[]a){
  DominoGame g=new DominoGame();int total=0;for(java.util.List<String>h:g.hands)total+=h.size();ok(total==28,"28 tiles dealt");ok(g.remaining()==0,"deck empty");
  String t=g.hands[0].get(0);ok(g.play(0,t),"first move");ok(g.currentPlayer()==1,"turn advances");ok(!g.play(0,t),"wrong player blocked");ok(!g.canPlay("bad"),"invalid tile blocked");
  String saved=g.saveState();DominoGame r=new DominoGame();ok(r.loadState(saved),"state restores");ok(r.currentPlayer()==g.currentPlayer(),"turn restored");ok(r.board().equals(g.board()),"board restored");
  ok(r.winner()==-1,"no winner early");System.out.println("DOMINO_TEST_PASS");
 }
}
