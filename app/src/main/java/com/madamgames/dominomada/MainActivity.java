package com.madamgames.dominomada;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.*;
import android.content.SharedPreferences;
import java.util.*;

public class MainActivity extends Activity{
 private DominoGame game; private TextView status; private SharedPreferences prefs; private CalmMusic music; private Button musicButton;
 @Override public void onCreate(Bundle b){super.onCreate(b);music=new CalmMusic(this);prefs=getSharedPreferences("domino",0);build();if(!restore())newGame();}
 private void build(){
  ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);
  LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(20,18,20,24);root.setBackgroundColor(Color.rgb(246,249,252));scroll.addView(root);
  BrandUi.header(this,root,"DominoMada","Stratégie • Réflexion • Victoire",Color.rgb(13,91,67));
  root.addView(BrandUi.badge(this,"HORS-LIGNE • 4 JOUEURS",Color.rgb(24,151,105)),new LinearLayout.LayoutParams(-1,44));
  LinearLayout hero=BrandUi.card(this,Color.rgb(226,232,240));
  hero.addView(BrandUi.text(this,"Maîtrisez chaque coup",20,Color.rgb(20,35,55),true));
  hero.addView(BrandUi.text(this,"Une partie locale fluide avec sauvegarde automatique de votre progression.",14,Color.rgb(92,105,122),false),new LinearLayout.LayoutParams(-1,70));root.addView(hero);
  LinearLayout row=new LinearLayout(this);row.setGravity(17);row.setPadding(0,12,0,4);
  Button play=BrandUi.button(this,"Jouer maintenant",Color.rgb(16,140,96),Color.rgb(49,196,141));Button reset=BrandUi.button(this,"Nouvelle partie",Color.rgb(28,55,82),Color.rgb(53,82,112));
  row.addView(play,new LinearLayout.LayoutParams(0,58,1f));row.addView(reset,new LinearLayout.LayoutParams(0,58,1f));root.addView(row);
  LinearLayout state=BrandUi.card(this,Color.rgb(226,232,240));state.addView(BrandUi.text(this,"État de la partie",15,Color.rgb(24,151,105),true));
  status=BrandUi.text(this,"",15,Color.rgb(70,84,102),false);status.setPadding(0,10,0,0);state.addView(status);root.addView(state);
  musicButton=BrandUi.button(this,"",Color.rgb(28,55,82),Color.rgb(59,78,99));musicButton.setOnClickListener(v->{music.toggle();musicButton.setText(musicLabel());});root.addView(musicButton,new LinearLayout.LayoutParams(-1,54));
  play.setOnClickListener(v->autoPlay());reset.setOnClickListener(v->{newGame();persist();});musicButton.setText(musicLabel());setContentView(scroll);
 }
 private void newGame(){game=new DominoGame();show();}
 private boolean restore(){String s=prefs.getString("state","");if(s.isEmpty())return false;game=new DominoGame();if(game.loadState(s)){show();return true;}return false;}
 private void persist(){prefs.edit().putString("state",game.saveState()).apply();}
 private void autoPlay(){int p=game.currentPlayer();for(String t:new ArrayList<>(game.hands[p]))if(game.play(p,t)){persist();show();return;}Toast.makeText(this,"Aucun coup possible pour J"+(p+1),Toast.LENGTH_SHORT).show();show();}
 private void show(){StringBuilder s=new StringBuilder("Tour : Joueur ").append(game.currentPlayer()+1).append("\n");s.append("Plateau : ").append(game.board()).append("\nPioche : ").append(game.remaining()).append(" tuiles\n");for(int i=0;i<4;i++)s.append("J").append(i+1).append(" : ").append(game.hands[i].size()).append(" tuiles\n");status.setText(s.toString());}
 @Override protected void onStart(){super.onStart();if(music!=null)music.start();}
 @Override protected void onStop(){if(music!=null)music.stop();super.onStop();if(game!=null)persist();}
 private String musicLabel(){return music.enabled()?"Musique · ACTIVÉE":"Musique · DÉSACTIVÉE";}
}
