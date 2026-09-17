package com.madamgames.dominomada;

import android.app.Activity; import android.os.Bundle; import android.graphics.Color;
import android.view.Gravity; import android.widget.*;

public class MainActivity extends Activity {
  private DominoGame game; private TextView status;
  @Override public void onCreate(Bundle b){super.onCreate(b); build(); newGame();}
  private void build(){
    LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
    root.setGravity(Gravity.CENTER_HORIZONTAL); root.setPadding(28,40,28,28);
    TextView title=new TextView(this); title.setText("DominoMada · 4 joueurs"); title.setTextSize(28); title.setTextColor(Color.DKGRAY);
    status=new TextView(this); status.setTextSize(17); status.setPadding(0,20,0,20);
    Button play=new Button(this); play.setText("Jouer une tuile possible");
    Button reset=new Button(this); reset.setText("Nouvelle partie"); root.addView(title); root.addView(status); root.addView(play); root.addView(reset); setContentView(root);
    play.setOnClickListener(v->autoPlay()); reset.setOnClickListener(v->newGame());
  }
  private void newGame(){game=new DominoGame(); show();}
  private void autoPlay(){
    int p=game.currentPlayer(); for(String t:game.hands[p]) if(game.play(p,t)){show(); return;}
    show();
  }
  private void show(){
    int p=game.currentPlayer(); StringBuilder s=new StringBuilder("Tour joueur ").append(p+1).append("\n");
    s.append("Pioche restante: ").append(game.remaining()).append("\nPlateau: ").append(game.board()).append("\n");
    for(int i=0;i<4;i++) s.append("J").append(i+1).append(": ").append(game.hands[i].size()).append(" tuiles\n");
    status.setText(s.toString());
  }
}
