package com.madamgames.dominomada;

import android.app.Activity;import android.os.Bundle;import android.graphics.Color;import android.view.Gravity;import android.widget.*;import android.content.SharedPreferences;import java.util.*;

public class MainActivity extends Activity{
 private DominoGame game;private TextView status;private SharedPreferences prefs; private CalmMusic music; private Button musicButton;
 @Override public void onCreate(Bundle b){super.onCreate(b);music=new CalmMusic(this);prefs=getSharedPreferences("domino",0);build();if(!restore())newGame();}
 private TextView label(String text,int size){TextView v=new TextView(this);v.setText(text);v.setTextSize(size);v.setGravity(Gravity.CENTER);v.setPadding(0,10,0,10);return v;}
 private void build(){
  LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setGravity(Gravity.CENTER_HORIZONTAL);root.setPadding(20,22,20,16);
  TextView title=label("DominoMada · 4 joueurs",26);title.setTextColor(Color.rgb(35,45,55));status=label("",16);
  Button play=new Button(this);play.setText("Jouer un coup automatique");Button reset=new Button(this);reset.setText("Nouvelle partie");
  root.addView(title);root.addView(status);root.addView(play);root.addView(reset);musicButton=new Button(this);musicButton.setText(musicLabel());musicButton.setOnClickListener(v->{music.toggle();musicButton.setText(musicLabel());});root.addView(musicButton);setContentView(root);
  play.setOnClickListener(v->autoPlay());reset.setOnClickListener(v->{newGame();persist();});
 }
 private void newGame(){game=new DominoGame();show();}
 private boolean restore(){String s=prefs.getString("state","");if(s.isEmpty())return false;game=new DominoGame();if(game.loadState(s)){show();return true;}return false;}
 private void persist(){prefs.edit().putString("state",game.saveState()).apply();}
 private void autoPlay(){int p=game.currentPlayer();for(String t:new ArrayList<>(game.hands[p]))if(game.play(p,t)){persist();show();return;}Toast.makeText(this,"Aucun coup possible pour J"+(p+1),Toast.LENGTH_SHORT).show();show();}
 private void show(){StringBuilder s=new StringBuilder("Tour: Joueur ").append(game.currentPlayer()+1).append("\n");s.append("Plateau: ").append(game.board()).append("\nPioche: ").append(game.remaining()).append(" tuiles\n");for(int i=0;i<4;i++)s.append("J").append(i+1).append(": ").append(game.hands[i].size()).append(" tuiles\n");status.setText(s.toString());}
 @Override protected void onStart(){super.onStart();if(music!=null)music.start();}
 @Override protected void onStop(){if(music!=null)music.stop();super.onStop();if(game!=null)persist();}
 private String musicLabel(){return music.enabled()?"🎵 Mozika: MISOKATRA":"🎵 Mozika: NOKATONA";}

}
