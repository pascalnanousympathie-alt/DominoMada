package com.madamgames.dominomada;
import android.app.Activity;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public final class BrandUi {
 private BrandUi(){}
 public static int dp(Activity a,int v){return (int)(v*a.getResources().getDisplayMetrics().density+0.5f);}
 private static float r(float v){return v*3f;}
 public static GradientDrawable bg(int start,int end,float radius){GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{start,end});g.setCornerRadius(r(radius));return g;}
 public static TextView text(Activity a,String s,float size,int color,boolean bold){TextView v=new TextView(a);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setGravity(Gravity.CENTER_VERTICAL);v.setTypeface(null,bold?Typeface.BOLD:Typeface.NORMAL);return v;}
 public static Button button(Activity a,String s,int start,int end){Button b=new Button(a);b.setText(s);b.setTextSize(14.5f);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(null,Typeface.BOLD);b.setMinHeight(dp(a,54));b.setPadding(dp(a,16),0,dp(a,16),0);b.setLetterSpacing(.01f);b.setBackground(bg(start,end,19));b.setElevation(dp(a,6));b.setStateListAnimator(null);return b;}
 public static LinearLayout card(Activity a,int stroke){LinearLayout c=new LinearLayout(a);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(dp(a,17),dp(a,15),dp(a,17),dp(a,15));GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{Color.WHITE,Color.rgb(235,241,249)});g.setCornerRadius(dp(a,23));g.setStroke(dp(a,1),stroke);c.setBackground(g);c.setElevation(dp(a,5));return c;}
 public static TextView badge(Activity a,String s,int accent){TextView v=text(a,s,11.5f,accent,true);v.setGravity(Gravity.CENTER);v.setPadding(dp(a,12),dp(a,6),dp(a,12),dp(a,6));GradientDrawable g=new GradientDrawable();g.setColor(Color.argb(24,Color.red(accent),Color.green(accent),Color.blue(accent)));g.setCornerRadius(dp(a,40));v.setBackground(g);return v;}
 public static void header(Activity a,LinearLayout root,String name,String subtitle,int bg){LinearLayout bar=new LinearLayout(a);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setPadding(dp(a,8),dp(a,6),dp(a,8),dp(a,8));bar.setBackground(bg(Color.rgb(10,28,53),Color.rgb(21,62,108),20));bar.setElevation(dp(a,5));bar.addView(new Logo(a,bg,name),new LinearLayout.LayoutParams(dp(a,58),dp(a,58)));LinearLayout titles=new LinearLayout(a);titles.setOrientation(LinearLayout.VERTICAL);titles.setPadding(dp(a,10),0,0,0);titles.addView(text(a,name,22f,Color.WHITE,true));titles.addView(text(a,subtitle,11.5f,Color.rgb(164,179,201),false),new LinearLayout.LayoutParams(-1,dp(a,24)));bar.addView(titles,new LinearLayout.LayoutParams(0,-2,1f));root.addView(bar);}
 public static LinearLayout hero(Activity a,String title,String subtitle,int accent,int dark){LinearLayout h=new LinearLayout(a);h.setGravity(Gravity.CENTER_VERTICAL);h.setPadding(dp(a,16),dp(a,13),dp(a,16),dp(a,13));h.setBackground(bg(dark,accent,22));h.setElevation(dp(a,5));h.addView(new Logo(a,accent,title),new LinearLayout.LayoutParams(dp(a,68),dp(a,68)));LinearLayout copy=new LinearLayout(a);copy.setOrientation(LinearLayout.VERTICAL);copy.setPadding(dp(a,14),0,0,0);copy.addView(text(a,title,19f,Color.WHITE,true));copy.addView(text(a,subtitle,12.5f,0xD9FFFFFF,false),new LinearLayout.LayoutParams(-1,dp(a,40)));h.addView(copy,new LinearLayout.LayoutParams(0,-2,1f));return h;}
 public static final class Logo extends View{
  private final Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);private final int base;private final String mode;
  public Logo(Activity a,int base,String mode){super(a);this.base=base;this.mode=mode;setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  @Override protected void onDraw(Canvas c){float w=getWidth(),h=getHeight(),s=Math.min(w,h),x=(w-s)/2f,y=(h-s)/2f;p.setShader(new LinearGradient(x,y,x+s,y+s,base,Color.rgb(7,19,38),Shader.TileMode.CLAMP));p.setShadowLayer(11,0,4,0x55000000);c.drawRoundRect(x,y,x+s,y+s,s*.26f,s*.26f,p);p.clearShadowLayer();p.setShader(null);drawDomino(c,x,y,s);}
  private void drawDomino(Canvas c,float x,float y,float s){p.setColor(Color.WHITE);c.drawRoundRect(x+s*.30f,y+s*.13f,x+s*.70f,y+s*.87f,s*.10f,s*.10f,p);p.setColor(base);c.drawRect(x+s*.34f,y+s*.48f,x+s*.66f,y+s*.52f,p);p.setColor(Color.rgb(14,45,53));for(float q:new float[]{.28f,.72f})c.drawCircle(x+s*q,y+s*.30f,s*.045f,p);for(float q:new float[]{.28f,.50f,.72f})c.drawCircle(x+s*q,y+s*.70f,s*.042f,p);}
 }
}
