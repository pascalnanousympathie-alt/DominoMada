package com.madamgames.dominomada;
import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.widget.*;
import android.content.SharedPreferences;
public class MainActivity extends Activity{
 private DominoGame game;private TextView status;private SharedPreferences prefs;private CalmMusic music;private Button musicButton;
 @Override public void onCreate(Bundle b){super.onCreate(b);music=new CalmMusic(this);prefs=getSharedPreferences("domino",0);build();if(!restore())newGame();}
 private void build(){
  ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(BrandUi.dp(this,18),BrandUi.dp(this,16),BrandUi.dp(this,18),BrandUi.dp(this,22));root.setBackgroundColor(Color.rgb(7,19,38));scroll.addView(root);
  BrandUi.header(this,root,"DominoMada","Stratégie • réflexion • fluide",Color.rgb(13,91,67));
  root.addView(BrandUi.hero(this,"Domino premium","Chaque coup compte.",Color.rgb(20,184,138),Color.rgb(11,61,50)),new LinearLayout.LayoutParams(-1,BrandUi.dp(this,104)));
  LinearLayout actions=new LinearLayout(this);actions.setPadding(0,12,0,6);actions.setGravity(17);
  Button play=BrandUi.button(this,"Jouer",Color.rgb(16,140,96),Color.rgb(49,196,141));Button reset=BrandUi.button(this,"Nouvelle partie",Color.rgb(28,55,82),Color.rgb(53,82,112));actions.addView(play,new LinearLayout.LayoutParams(0,BrandUi.dp(this,56),1f));actions.addView(reset,new LinearLayout.LayoutParams(0,BrandUi.dp(this,56),1f));root.addView(actions);
  LinearLayout state=BrandUi.card(this,Color.rgb(215,235,229));state.addView(BrandUi.text(this,"Table",14,Color.rgb(24,151,105),true));status=BrandUi.text(this,"",14.5f,Color.rgb(55,65,81),false);status.setPadding(0,8,0,0);state.addView(status);root.addView(state);
  musicButton=BrandUi.button(this,"♫  Son • ON",Color.rgb(28,55,82),Color.rgb(59,78,99));root.addView(musicButton,new LinearLayout.LayoutParams(-1,BrandUi.dp(this,50)));
  play.setOnClickListener(v->autoPlay());reset.setOnClickListener(v->{newGame();persist();});musicButton.setOnClickListener(v->{music.toggle();musicButton.setText(musicLabel());});setContentView(scroll);
 }
 private void newGame(){game=new DominoGame();show();}
 private boolean restore(){String s=prefs.getString("state","");if(s.isEmpty())return false;game=new DominoGame();if(game.loadState(s)){show();return true;}return false;}
 private void persist(){prefs.edit().putString("state",game.saveState()).apply();}
 private void autoPlay(){int p=game.currentPlayer();for(String t:new java.util.ArrayList<>(game.hands[p]))if(game.play(p,t)){persist();show();return;}Toast.makeText(this,"Aucun coup possible pour J"+(p+1),Toast.LENGTH_SHORT).show();show();}
 private void show(){StringBuilder s=new StringBuilder("Tour : J").append(game.currentPlayer()+1).append("\n");s.append("Plateau : ").append(game.board()).append("\nPioche : ").append(game.remaining()).append("\n");for(int i=0;i<4;i++)s.append("J").append(i+1).append(" : ").append(game.hands[i].size()).append(" tuiles\n");status.setText(s.toString());}
 @Override protected void onStart(){super.onStart();if(music!=null)music.start();}
 @Override protected void onStop(){if(music!=null)music.stop();super.onStop();if(game!=null)persist();}
 private String musicLabel(){return music.enabled()?"Son • ON":"Son • OFF";}
}