package com.example.unzkeyboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.R.string;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
@TargetApi(11)
public class MainActivity extends Activity {

	//Keyboard buttons
	private Button bt_q, bt_w, bt_e, bt_r, bt_t, bt_y, bt_u, bt_i, bt_o, bt_p,
				   bt_a, bt_s, bt_d, bt_f, bt_g, bt_h, bt_j, bt_k, bt_l,
				   bt_shf, bt_z, bt_x, bt_c, bt_v, bt_b, bt_n, bt_m, bt_bks,
				   bt_com, bt_spc, bt_dot, bt_invisible;
	
	//Other View elements
	private TextView preview, touchView, label ;
	private EditText form;
	private boolean capitalize, horizontal;
	private Vibrator vibe;
	private String fileName;
	private FrameLayout frame;	
	private PhraseGenerator phraseGen;
	
    private Button pressed1, pressed2;
    private int offset;
    String log;
	
	/*Initializing Activity elements*/
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
	    
	    log = new String();
	    
	    
	    WindowManager mWindowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display mDisplay = mWindowManager.getDefaultDisplay();
	    if(mDisplay.getRotation() == 0) {
	    	setContentView(R.layout.activity_main);
	    	horizontal = false;
	    	phraseGen = new PhraseGeneratorVer();
	    	fileName = String.valueOf(System.currentTimeMillis()) + "_VER_320dp";
	    }
	    else {
	    	setContentView(R.layout.activity_main_320dp_hor);
	    	phraseGen = new PhraseGeneratorHor();
	    	horizontal = true;
	    	fileName = String.valueOf(System.currentTimeMillis()) + "_HOR_320dp";
	    }
	    
	    offset = horizontal ? 0 : 90;
	    
	    bt_q = (Button) findViewById(R.id.bt_q);
	    bt_w = (Button) findViewById(R.id.bt_w);
	    bt_e = (Button) findViewById(R.id.bt_e);
	    bt_r = (Button) findViewById(R.id.bt_r);
	    bt_t = (Button) findViewById(R.id.bt_t);
	    bt_y = (Button) findViewById(R.id.bt_y);
	    bt_u = (Button) findViewById(R.id.bt_u);
	    bt_i = (Button) findViewById(R.id.bt_i);
	    bt_o = (Button) findViewById(R.id.bt_o);
	    bt_p = (Button) findViewById(R.id.bt_p);
	    bt_a = (Button) findViewById(R.id.bt_a);
	    bt_s = (Button) findViewById(R.id.bt_s);
	    bt_d = (Button) findViewById(R.id.bt_d);
	    bt_f = (Button) findViewById(R.id.bt_f);
	    bt_g = (Button) findViewById(R.id.bt_g);
	    bt_h = (Button) findViewById(R.id.bt_h);
	    bt_j = (Button) findViewById(R.id.bt_j);
	    bt_k = (Button) findViewById(R.id.bt_k);
	    bt_l = (Button) findViewById(R.id.bt_l);
	    bt_shf = (Button) findViewById(R.id.bt_shf);
	    bt_z = (Button) findViewById(R.id.bt_z);
	    bt_x = (Button) findViewById(R.id.bt_x);
	    bt_c = (Button) findViewById(R.id.bt_c);
	    bt_v = (Button) findViewById(R.id.bt_v);
	    bt_b = (Button) findViewById(R.id.bt_b);
	    bt_n = (Button) findViewById(R.id.bt_n);
	    bt_m = (Button) findViewById(R.id.bt_m);
	    bt_bks = (Button) findViewById(R.id.bt_bks);
	    bt_com = (Button) findViewById(R.id.bt_com);
	    bt_spc = (Button) findViewById(R.id.bt_spc);
	    bt_dot = (Button) findViewById(R.id.bt_dot);
	    
	    bt_invisible = (Button) findViewById(R.id.bt_invisible);
	    
	    preview = (TextView) findViewById(R.id.preview);
	    touchView = (TextView) findViewById(R.id.touchView);
//	    coordinatesDebug = (TextView) findViewById(R.id.coordinatesDebug);
	    form = (EditText) findViewById(R.id.form);
	    label = (TextView) findViewById(R.id.label);
	    
	    frame = (FrameLayout) findViewById(R.id.frame);
	    

	    

	    touchView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				String timeStamp = String.valueOf(System.currentTimeMillis());
				
				int action = event.getAction();
				switch (action)
				{
				case MotionEvent.ACTION_DOWN:
				{
					pressed1 = getButtonAt(event.getX(0), event.getY(0)-offset);
					pressed1.setPressed(true);
					drawLabel(pressed1.getX() + (pressed1 == bt_spc ? 50 : -15), pressed1.getY()-78, pressed1.getText());
					log = log.concat("<EVENT>");
					log = log.concat("<DOWN id=\"0\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
							+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed1.getText() +
							"\" />");
					vibe.vibrate(30);
					break;
				}
				case MotionEvent.ACTION_UP:
				{
					if (pressed2 != null)
					{
						pressed2 = getButtonAt(event.getX(0), event.getY(0)-offset);
						pressed2.performClick();
						pressed2.setPressed(false);
						log = log.concat("<UP id=\"1\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
								+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed2.getText() +
								"\" />");
						log = log.concat("</EVENT>");
						appendLog(log);
						log = "";
						pressed2 = null;
						label.setVisibility(View.INVISIBLE);
					}
					else
					{
						pressed1 = getButtonAt(event.getX(0), event.getY(0)-offset);
						pressed1.performClick();
						pressed1.setPressed(false);
						log = log.concat("<UP id=\"0\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
								+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed1.getText() +
								"\" />");
						log = log.concat("</EVENT>");
						appendLog(log);
						log = "";
						pressed1 = null;
						label.setVisibility(View.INVISIBLE);
					}
					break;
				}
				case MotionEvent.ACTION_POINTER_2_DOWN:
				{
					pressed2 = getButtonAt(event.getX(1), event.getY(1)-offset);
					pressed2.setPressed(true);
					log = log.concat("<DOWN id=\"1\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
							+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed2.getText() +
							"\" />");
					break;
				}
				case MotionEvent.ACTION_POINTER_1_UP:
				{
					pressed1.performClick();
					pressed1.setPressed(false);
					log = log.concat("<UP id=\"0\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
							+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed1.getText() +
							"\" />");
					break;
				}
				case MotionEvent.ACTION_POINTER_2_UP:
				{
					pressed2.performClick();
					pressed2.setPressed(false);
					log = log.concat("<UP id=\"1\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
							+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed2.getText() +
							"\" />");
					break;
				}
				case MotionEvent.ACTION_MOVE:
				{
					Button moved = getButtonAt(event.getX(0), event.getY(0)-offset);
					log = log.concat("<MOVE id=\"0\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
							+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed1.getText() +
							"\" />");

					if (moved != pressed1)
					{
						pressed1.setPressed(false);
						pressed1 = moved;
						drawLabel(pressed1.getX() + (pressed1 == bt_spc ? 120 : -15), pressed1.getY()-78, pressed1.getText());
						pressed1.setPressed(true);

					}
					if (event.getPointerCount() > 1)
					{
						moved = getButtonAt(event.getX(1), event.getY(1)-offset);
						if (moved != pressed2)
						{
							pressed2.setPressed(false);
							pressed2 = moved;
							drawLabel(pressed2.getX() + (pressed2 == bt_spc ? 120 : -15), pressed2.getY()-78, pressed2.getText());
							pressed2.setPressed(true);

						}
						log = log.concat("<MOVE id=\"1\" x=\"" + String.valueOf(event.getX()) + "\" y=\"" + String.valueOf(event.getY())
								+ "\" time=\"" + timeStamp + "\" size=\"" + event.getSize() + "\" pressure=\""+ event.getPressure() +"\" key=\"" + pressed2.getText() +
								"\" />");
					}
				}
				}
				
		        return true;
			}
			
	    });

	}

	protected Button getButtonAt(float x, float y) {
			double scaleFactor = 1.5;
			x = (int) (x/scaleFactor);
			y = (int) (y/scaleFactor);
			
			if(horizontal==false) {
				if(y>=0 && y<=55) {
					if (x>=0 && x<=32) {
						return bt_q;
					}
					else if (x>=0 && x<=32*2)
					{
						return bt_w;
					}
					else if (x>=0 && x<=32*3)
					{
						return bt_e;
					}
					else if (x>=0 && x<=32*4)
					{
						return bt_r;
					}
					else if (x>=0 && x<=32*5)
					{
						return bt_t;
					}
					else if (x>=0 && x<=32*6)
					{
						return bt_y;
					}
					else if (x>=0 && x<=32*7)
					{
						return bt_u;
					}
					else if (x>=0 && x<=32*8)
					{
						return bt_i;
					}
					else if (x>=0 && x<=32*9)
					{
						return bt_o;
					}
					else if (x>=0 && x<=32*10)
					{
						return bt_p;
					}
				}
				else if (y>=0 && y<=110) {
					if (x>=00 && x<=16+32*1)
					{
						return bt_a;
					}
					else if (x>16 && x<=16+32*2)
					{
						return bt_s;
					}
					else if (x>16 && x<=16+32*3)
					{
						return bt_d;
					}
					else if (x>16 && x<=16+32*4)
					{
						return bt_f;
					}
					else if (x>16 && x<=16+32*5)
					{
						return bt_g;
					}
					else if (x>16 && x<=16+32*6)
					{
						return bt_h;
					}
					else if (x>16 && x<=16+32*7)
					{
						return bt_j;
					}
					else if (x>16 && x<=16+32*8)
					{
						return bt_k;
					}
					else if (x>16 && x<=16+32*9+16)
					{
						return bt_l;
					}
				}
				else if (y>=0 && y<=165) {
					if (x>=0 && x<=48)
					{
						return bt_shf;
					}
					else if (x>=0 && x<=48+32*1)
					{
						return bt_z;
					}
					else if (x>=0 && x<=48+32*2)
					{
						return bt_x;
					}
					else if (x>=0 && x<=48+32*3)
					{
						return bt_c;
					}
					else if (x>=0 && x<=48+32*4)
					{
						return bt_v;
					}
					else if (x>=0 && x<=48+32*5)
					{
						return bt_b;
					}
					else if (x>=0 && x<=48+32*6)
					{
						return bt_n;
					}
					else if (x>=0 && x<=48+32*7)
					{
						return bt_m;
					}
					else if (x>=0 && x<=48+32*8+16)
					{
						return bt_bks;
					}				
				}
				else if (y>=0) {
					if (x>48 && x<=48+32)
					{
						return bt_com;
					}
					else if (x>48+32 && x<=48+32*6)
					{
						return bt_spc;
					}
					else if (x>48+32*6 && x<=48+32*7)
					{
						return bt_dot;
					}
				}
			}
			else
			{
				if(y>=0 && y<=40) {
					if (x>=0 && x<=57) {
						return bt_q;
					}
					else if (x>=0 && x<=57*2)
					{
						return bt_w;
					}
					else if (x>=0 && x<=57*3)
					{
						return bt_e;
					}
					else if (x>=0 && x<=57*4)
					{
						return bt_r;
					}
					else if (x>=0 && x<=57*5)
					{
						return bt_t;
					}
					else if (x>=0 && x<=57*6)
					{
						return bt_y;
					}
					else if (x>=0 && x<=57*7)
					{
						return bt_u;
					}
					else if (x>=0 && x<=57*8)
					{
						return bt_i;
					}
					else if (x>=0 && x<=57*9)
					{
						return bt_o;
					}
					else if (x>=0 && x<=57*10)
					{
						return bt_p;
					}
				}
				else if (y>=0 && y<=80) {
					if (x>=0 && x<=26+57*1)
					{
						return bt_a;
					}
					else if (x>26 && x<=26+57*2)
					{
						return bt_s;
					}
					else if (x>26 && x<=26+57*3)
					{
						return bt_d;
					}
					else if (x>26 && x<=26+57*4)
					{
						return bt_f;
					}
					else if (x>26 && x<=26+57*5)
					{
						return bt_g;
					}
					else if (x>26 && x<=26+57*6)
					{
						return bt_h;
					}
					else if (x>26 && x<=26+57*7)
					{
						return bt_j;
					}
					else if (x>26 && x<=26+57*8)
					{
						return bt_k;
					}
					else if (x>26 && x<=26+57*9+26)
					{
						return bt_l;
					}
				}
				else if (y>=0 && y<=120) {
					if (x>=0 && x<=82)
					{
						return bt_shf;
					}
					else if (x>=0 && x<=82+57*1)
					{
						return bt_z;
					}
					else if (x>=0 && x<=82+57*2)
					{
						return bt_x;
					}
					else if (x>=0 && x<=82+57*3)
					{
						return bt_c;
					}
					else if (x>=0 && x<=82+57*4)
					{
						return bt_v;
					}
					else if (x>=0 && x<=82+57*5)
					{
						return bt_b;
					}
					else if (x>=0 && x<=82+57*6)
					{
						return bt_n;
					}
					else if (x>=0 && x<=82+57*7)
					{
						return bt_m;
					}
					else if (x>=0 && x<=88+57*8+26)
					{
						return bt_bks;
					}				
				}
				else if (y>=0) {
					if (x>82 && x<=82+57)
					{
						return bt_com;
					}
					else if (x>82+57 && x<=82+57*6)
					{
						return bt_spc;
					}
					else if (x>82+57*6 && x<=82+57*7)
					{
						return bt_dot;
					}
				}
			}
			return bt_invisible;
	}
	
	/*Called when the application is loaded or rotated */
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);
	    
	    ViewGroup.LayoutParams dimensionsParams;
	    double scaleFactor;
	    
	    //Display rotation
	    WindowManager mWindowManager =  (WindowManager) getSystemService(WINDOW_SERVICE);
	    Display mDisplay = mWindowManager.getDefaultDisplay();
	    if(mDisplay.getRotation() == 0) {
	    	scaleFactor = 0.69;
	    }
	    else {
	    	scaleFactor = 0.28;
	    }
	    
	    int newTotalHeight = (int) (touchView.getWidth()*scaleFactor);
	    int newButtonHeight = (int) (newTotalHeight/4);
	    
	}
	
	//Keys listener
	
	private void addText (String s) {
		if(s.equals("bks")) {
			if (form.getText().length() > 0) {
				//deleting the last character
				form.getText().delete(form.getText().length() - 1, form.getText().length());
			}
		}
		else if (s.equals("shf")) {
			capitalize = !capitalize;
			if (capitalize) {
				bt_q.setText("Q"); bt_w.setText("W"); bt_e.setText("E"); bt_r.setText("R"); bt_t.setText("T"); bt_y.setText("Y"); bt_u.setText("U"); bt_i.setText("I"); bt_o.setText("O"); bt_p.setText("P");  
				bt_a.setText("A"); bt_s.setText("S"); bt_d.setText("D"); bt_f.setText("F"); bt_g.setText("G"); bt_h.setText("H"); bt_j.setText("J"); bt_k.setText("K"); bt_l.setText("L"); 
				bt_z.setText("Z"); bt_x.setText("X"); bt_c.setText("C"); bt_v.setText("V"); bt_b.setText("B"); bt_n.setText("N"); bt_m.setText("M"); 
			}
			else {
				bt_q.setText("q"); bt_w.setText("w"); bt_e.setText("e"); bt_r.setText("r"); bt_t.setText("t"); bt_y.setText("y"); bt_u.setText("u"); bt_i.setText("i"); bt_o.setText("o"); bt_p.setText("p");  
				bt_a.setText("a"); bt_s.setText("s"); bt_d.setText("d"); bt_f.setText("f"); bt_g.setText("g"); bt_h.setText("h"); bt_j.setText("j"); bt_k.setText("k"); bt_l.setText("l"); 
				bt_z.setText("z"); bt_x.setText("x"); bt_c.setText("c"); bt_v.setText("v"); bt_b.setText("b"); bt_n.setText("n"); bt_m.setText("m"); 
			}
		}
		else {
			if (capitalize) {
				s = s.toUpperCase();
				addText("shf");
			}
			form.append(s);
		}
	}
	
	public void onClick_q (View target) {
		addText("q");
	}
	
	public void onClick_w (View target) {
		addText("w");
	}
	
	public void onClick_e (View target) {
		addText("e");
	}
	
	public void onClick_r (View target) {
		addText("r");
	}
	
	public void onClick_t (View target) {
		addText("t");
	}
	
	public void onClick_y (View target) {
		addText("y");
	}
	
	public void onClick_u (View target) {
		addText("u");
	}
	
	public void onClick_i (View target) {
		addText("i");
	}
	
	public void onClick_o (View target) {
		addText("o");
	}
	
	public void onClick_p (View target) {
		addText("p");
	}
	
	public void onClick_a (View target) {
		addText("a");
	}
	
	public void onClick_s (View target) {
		addText("s");
	}
	
	public void onClick_d (View target) {
		addText("d");
	}
	
	public void onClick_f (View target) {
		addText("f");
	}
	
	public void onClick_g (View target) {
		addText("g");
	}
	
	public void onClick_h (View target) {
		addText("h");
	}
	
	public void onClick_j (View target) {
		addText("j");
	}
	
	public void onClick_k (View target) {
		addText("k");
	}
	
	public void onClick_l (View target) {
		addText("l");
	}
	
	public void onClick_shf (View target) {
		addText("shf");
	}
	
	public void onClick_z (View target) {
		addText("z");
	}
	
	public void onClick_x (View target) {
		addText("x");
	}
	
	public void onClick_c (View target) {
		addText("c");
	}
	
	public void onClick_v (View target) {
		addText("v");
	}
	
	public void onClick_b (View target) {
		addText("b");
	}
	
	public void onClick_n (View target) {
		addText("n");
	}
	
	public void onClick_m (View target) {
		addText("m");
	}
	
	public void onClick_bks (View target) {
		addText("bks");
	}
	
	public void onClick_com (View target) {
		addText(",");
	}
	
	public void onClick_spc (View target) {
		addText(" ");
	}
	
	public void onClick_dot (View target) {
		addText(".");
	}
	
	public void appendLog(String text)
	{       
	   File logFile = new File(Environment.getExternalStorageDirectory(), "log/" + fileName + ".txt");
	   if (!logFile.exists())
	   {
	      try
	      {
	         logFile.createNewFile();
	      } 
	      catch (IOException e)
	      {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	   }
	   try
	   {
	      //BufferedWriter for performance, true to set append to file flag
	      BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true)); 
	      buf.append(text);
	      buf.close();
	   }
	   catch (IOException e)
	   {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	   }
	}
	
	public void nextPressed(View target) {
		String s = phraseGen.getPhrase();
		preview.setText(s);
		form.setText("");
		try {
			Integer.parseInt(s.substring(0,1));
		} catch (Exception e)
		{
			appendLog("</NEXT><NEXT id=\"" + s.substring(0, 10) + "\">");
		}
	}

	
	private void drawLabel(float x, float y, CharSequence charSequence)
	{
		if(horizontal == false)	
		{
			if (x<0)
				x=0;
			label.setVisibility(View.VISIBLE);
			label.setText(charSequence);
			label.setX(x);
			label.setY(y);
		}
	}
}
