package com.madamgames.dominomada;
public final class DominoGameTest {
 static void ok(boolean condition, String message) {
  if (!condition) throw new AssertionError(message);
 }
 public static void main(String[] args) {
  DominoGame game = new DominoGame(new java.util.Random(0));
  int total = 0;
  for (java.util.List<String> hand : game.hands) total += hand.size();
  ok(total == 28, "28 tiles dealt");
  ok(game.remaining() == 0, "deck empty");
  String first = game.hands[0].get(0);
  ok(game.play(0, first), "first move");
  ok(game.currentPlayer() == 1, "turn advances");
  ok(!game.play(0, first), "wrong player blocked");
  ok(!game.canPlay("bad"), "invalid tile blocked");
  String saved = game.saveState();
  DominoGame restored = new DominoGame(new java.util.Random(1));
  ok(restored.loadState(saved), "state restores");
  ok(restored.currentPlayer() == game.currentPlayer(), "turn restored");
  ok(restored.board().equals(game.board()), "board restored");
  String next = null;
  for (String tile : new java.util.ArrayList<>(game.hands[1])) {
   if (game.play(1, tile)) { next = tile; break; }
  }
  ok(next != null, "second move");
  String saved2 = game.saveState();
  ok(restored.loadState(saved2), "oriented board state restores");
  ok(restored.winner() == -1, "no winner early");
  ok(!game.isGameOver(), "game active");
  ok(game.score(0) >= 0, "score available");
  String bad = saved.replace("0|0", "0|7");
  ok(!restored.loadState(bad), "corrupt state rejected");
  System.out.println("DOMINO_TEST_PASS");
 }
}
