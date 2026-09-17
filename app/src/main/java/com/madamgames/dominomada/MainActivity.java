package com.madamgames.dominomada;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {
  private final List<String> hand = new ArrayList<>();
  private TextView status;
  private List<String> deck;

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    buildDeck();
    LinearLayout root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setPadding(32, 48, 32, 32);
    root.setGravity(Gravity.CENTER_HORIZONTAL);
    TextView title = new TextView(this);
    title.setText("DominoMada"); title.setTextSize(30); title.setTextColor(Color.DKGRAY);
    status = new TextView(this); status.setTextSize(18); status.setPadding(0, 24, 0, 24);
    Button deal = new Button(this); deal.setText("Distribuer 7 tuiles");
    Button reset = new Button(this); reset.setText("Nouvelle partie");
    root.addView(title); root.addView(status); root.addView(deal); root.addView(reset);
    setContentView(root);
    updateStatus();
    deal.setOnClickListener(v -> dealHand());
    reset.setOnClickListener(v -> { buildDeck(); hand.clear(); updateStatus(); });
  }

  private void buildDeck() {
    deck = new ArrayList<>();
    for (int a = 0; a <= 6; a++) for (int b = a; b <= 6; b++) deck.add(a + "|" + b);
    Collections.shuffle(deck);
  }

  private void dealHand() {
    hand.clear();
    for (int i = 0; i < 7 && !deck.isEmpty(); i++) hand.add(deck.remove(0));
    updateStatus();
  }

  private void updateStatus() {
    status.setText("Pioche: " + (deck == null ? 28 : deck.size()) + "\nMain: " +
        (hand.isEmpty() ? "—" : hand.toString()));
  }
}
