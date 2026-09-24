package com.madamgames.dominomada;
import android.app.Activity;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
public final class BrandUi {
 private BrandUi() {}
 public static int dp(Activity a,int v){return (int)(v*a.getResources().getDisplayMetrics().density+0.5f);}
 public static GradientDrawable bg(int start,int end,float radius){GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{start,end});g.setCornerRadius(radius*3f);return g;}
 public static TextView text(Activity a,String s,float size,int color,boolean bold){TextView v=new TextView(a);v.setText(s);v.setTextSize(size);v.setTextColor(color);v.setGravity(Gravity.CENTER_VERTICAL);v.setTypeface(null,bold?Typeface.BOLD:Typeface.NORMAL);return v;}
 public static Button button(Activity a,String s,int start,int end){Button b=new Button(a);b.setText(s);b.setTextSize(15f);b.setAllCaps(false);b.setTextColor(Color.WHITE);b.setTypeface(null,Typeface.BOLD);b.setMinHeight(dp(a,54));b.setPadding(dp(a,16),0,dp(a,16),0);b.setBackground(bg(start,end,18));b.setElevation(dp(a,3));return b;}
 public static LinearLayout card(Activity a,int stroke){LinearLayout c=new LinearLayout(a);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(dp(a,18),dp(a,16),dp(a,18),dp(a,16));GradientDrawable g=new GradientDrawable();g.setColor(Color.WHITE);g.setCornerRadius(dp(a,22));g.setStroke(dp(a,1),stroke);c.setBackground(g);c.setElevation(dp(a,2));return c;}
 public static TextView badge(Activity a,String s,int accent){TextView v=text(a,s,12f,accent,true);v.setGravity(Gravity.CENTER);v.setPadding(dp(a,14),dp(a,7),dp(a,14),dp(a,7));GradientDrawable g=new GradientDrawable();g.setColor(Color.argb(24,Color.red(accent),Color.green(accent),Color.blue(accent)));g.setCornerRadius(dp(a,50));v.setBackground(g);return v;}
 public static void header(Activity a,LinearLayout root,String name,String subtitle,int bg){LinearLayout bar=new LinearLayout(a);bar.setGravity(Gravity.CENTER_VERTICAL);bar.setPadding(0,0,0,dp(a,12));bar.addView(new Logo(a,bg),new LinearLayout.LayoutParams(dp(a,58),dp(a,58)));LinearLayout titles=new LinearLayout(a);titles.setOrientation(LinearLayout.VERTICAL);titles.addView(text(a,name,22f,Color.rgb(20,35,55),true));titles.addView(text(a,subtitle,12.5f,Color.rgb(104,116,132),false),new LinearLayout.LayoutParams(-1,dp(a,28)));bar.addView(titles,new LinearLayout.LayoutParams(0,-2,1f));root.addView(bar);}
 public static final class Logo extends View{
  private final Paint p=new Paint(Paint.ANTI_ALIAS_FLAG); private final int base;
  public Logo(Activity a,int base){super(a);this.base=base;setLayerType(View.LAYER_TYPE_SOFTWARE,null);}
  @Override protected void onDraw(Canvas c){float w=getWidth(),h=getHeight(),s=Math.min(w,h),x=(w-s)/2f,y=(h-s)/2f;p.setColor(base);p.setShadowLayer(8,0,3,0x33000000);c.drawRoundRect(x,y,x+s,y+s,s*.27f,s*.27f,p);p.clearShadowLayer();p.setColor(Color.rgb(24,163,255));c.drawCircle(x+s*.50f,y+s*.30f,s*.15f,p);c.drawRoundRect(x+s*.36f,y+s*.40f,x+s*.64f,y+s*.84f,s*.08f,s*.08f,p);p.setColor(Color.WHITE);c.drawCircle(x+s*.50f,y+s*.30f,s*.075f,p);p.setColor(Color.rgb(255,200,61));c.drawCircle(x+s*.22f,y+s*.70f,s*.06f,p);c.drawCircle(x+s*.78f,y+s*.70f,s*.06f,p);c.drawCircle(x+s*.22f,y+s*.84f,s*.06f,p);c.drawCircle(x+s*.78f,y+s*.84f,s*.06f,p);}
 }
}
