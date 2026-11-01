package com.madamgames.dominomada;
import java.util.*;
public final class DominoGame {
 public final List<String>[] hands;
 private final List<String> deck = new ArrayList<>();
 private final Deque<String> board = new ArrayDeque<>();
 private final Random random;
 private int currentPlayer;

 @SuppressWarnings("unchecked")
 public DominoGame() { this(new Random()); }

 @SuppressWarnings("unchecked")
 public DominoGame(Random random) {
  this.random = Objects.requireNonNull(random, "random");
  hands = new List[4];
  for (int i = 0; i < 4; i++) hands[i] = new ArrayList<>();
  reset();
 }

 public void reset() {
  deck.clear(); board.clear(); currentPlayer = 0;
  for (List<String> hand : hands) hand.clear();
  for (int a = 0; a <= 6; a++) for (int b = a; b <= 6; b++) deck.add(tile(a, b));
  Collections.shuffle(deck, random);
  for (int player = 0; player < 4; player++)
   for (int i = 0; i < 7; i++) hands[player].add(deck.remove(0));
 }
 private static String tile(int a, int b) { return a + "|" + b; }
 private static int a(String tile) { return Integer.parseInt(tile.substring(0, tile.indexOf('|'))); }
 private static int b(String tile) { return Integer.parseInt(tile.substring(tile.indexOf('|') + 1)); }
 public int remaining() { return deck.size(); }
 public int currentPlayer() { return currentPlayer; }
 public List<String> board() { return new ArrayList<>(board); }
 public boolean canPlay(String tile) {
  if (!isValidTile(tile)) return false;
  if (board.isEmpty()) return true;
  int x = a(tile), y = b(tile), left = a(board.peekFirst()), right = b(board.peekLast());
  return x == left || y == left || x == right || y == right;
 }
 public boolean play(int player, String tile) {
  if (isGameOver() || player < 0 || player > 3 || player != currentPlayer ||
      !hands[player].contains(tile) || !canPlay(tile)) return false;
  int x = a(tile), y = b(tile);
  if (board.isEmpty()) board.add(tile);
  else if (x == a(board.peekFirst())) board.addFirst(tile(y, x));
  else if (y == a(board.peekFirst())) board.addFirst(tile);
  else if (x == b(board.peekLast())) board.addLast(tile);
  else if (y == b(board.peekLast())) board.addLast(tile(y, x));
  else return false;
  hands[player].remove(tile);
  currentPlayer = (currentPlayer + 1) % 4;
  return true;
 }
 public boolean won(int player) { return player >= 0 && player < 4 && hands[player].isEmpty(); }
 public boolean isGameOver() { return winner() >= 0 || isBlocked(); }
 public int score(int player) {
  if (player < 0 || player > 3) throw new IllegalArgumentException("player");
  int score = 0;
  for (String tile : hands[player]) score += a(tile) + b(tile);
  return score;
 }
 private static String canonicalTile(String tile) {
  int x = a(tile), y = b(tile);
  return x <= y ? tile(x, y) : tile(y, x);
 }
 private static boolean isValidTile(String tile) {
  try { int x = a(tile), y = b(tile); return x >= 0 && x <= 6 && y >= 0 && y <= 6; }
  catch (Exception e) { return false; }
 }
 public boolean isBlocked() {
  if (board.isEmpty()) return false;
  for (List<String> hand : hands) for (String tile : hand) if (canPlay(tile)) return false;
  return true;
 }
 public int winner() { for (int i = 0; i < 4; i++) if (won(i)) return i; return -1; }
 public String saveState() {
  StringBuilder state = new StringBuilder();
  state.append(currentPlayer).append(";").append(join(board)).append(";").append(join(deck)).append(";");
  for (int i = 0; i < 4; i++) { if (i > 0) state.append("#"); state.append(join(hands[i])); }
  return state.toString();
 }
 public boolean loadState(String state) {
  try {
   String[] parts = state.split(";", -1);
   if (parts.length != 4) return false;
   int nextPlayer = Integer.parseInt(parts[0]);
   if (nextPlayer < 0 || nextPlayer > 3) return false;
   List<String> nextBoard = split(parts[1]), nextDeck = split(parts[2]);
   String[] nextHands = parts[3].split("#", -1);
   if (nextHands.length != 4) return false;
   List<String> all = new ArrayList<>();
   all.addAll(nextBoard); all.addAll(nextDeck);
   for (String hand : nextHands) all.addAll(split(hand));
   if (all.size() != 28) return false;
   Set<String> unique = new HashSet<>();
   for (String tile : all) if (!isValidTile(tile) || !unique.add(canonicalTile(tile))) return false;
   board.clear(); board.addAll(nextBoard);
   deck.clear(); deck.addAll(nextDeck);
   for (int i = 0; i < 4; i++) { hands[i].clear(); hands[i].addAll(split(nextHands[i])); }
   currentPlayer = nextPlayer;
   return true;
  } catch (Exception e) { return false; }
 }
 private static String join(Collection<String> values) { return String.join(",", values); }
 private static List<String> split(String value) {
  return value.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(value.split(",")));
 }
}
