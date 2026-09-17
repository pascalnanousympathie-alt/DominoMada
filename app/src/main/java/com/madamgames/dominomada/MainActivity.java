package com.madamgames.dominomada;

import android.app.Activity; import android.os.Bundle; import android.graphics.Color; import android.view.Gravity; import android.widget.*; import java.util.*;

public class MainActivity extends Activity {
  private DominoGame game; private TextView status;
  @Override public void onCreate(Bundle b){super.onCreate(b); build(); newGame();}
  private void build(){
    LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setGravity(Gravity.CENTER_HORIZONTAL); root.setPadding(20,30,20,20);
    TextView title=new TextView(this); title.setText("DominoMada · 4 joueurs"); title.setTextSize(26); title.setTextColor(Color.DKGRAY);
    status=new TextView(this); status.setTextSize(16); status.setPadding(0,15,0,15);
    Button play=new Button(this); play.setText("Jouer un coup automatique"); Button reset=new Button(this); reset.setText("Nouvelle partie");
    root.addView(title); root.addView(status); root.addView(play); root.addView(reset); setContentView(root);
    play.setOnClickListener(v->autoPlay()); reset.setOnClickListener(v->newGame());
  }
  private void newGame(){game=new DominoGame(); show();}
  private void autoPlay(){
    int p=game.currentPlayer(); List<String> hand=new ArrayList<>(game.hands[p]);
    for(String t:hand) if(game.play(p,t)){show(); return;} Toast.makeText(this,"Aucun coup possible",Toast.LENGTH_SHORT).show(); show();
  }
  private void show(){StringBuilder s=new StringBuilder("Tour: Joueur ").append(game.currentPlayer()+1).append("\n"); s.append("Plateau: ").append(game.board()).append("\n"); for(int i=0;i<4;i++)s.append("J").append(i+1).append(": ").append(game.hands[i].size()).append(" tuiles\n"); status.setText(s.toString());}
}
