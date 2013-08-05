package dk.masif.listenquranurdu;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import dk.masif.listenquranurdu.R;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity extends Activity {
	String temp = "";
	CheckBox ck;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	    
		setContentView(R.layout.activity_main);
		createLayoutDynamically();
		
		ck=(CheckBox)findViewById(R.id.check);
		
		//if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN)
		
		
		
		ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
		 	
		// TODO Auto-generated method stub
		if(buttonView.isChecked())
		{
			
			Toast.makeText(getApplicationContext(), "With arabic selected!", Toast.LENGTH_LONG).show();
				
		}
		
		}
		});


		
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		
		NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);		
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		    // Do whatever
			if (!mMobile.isConnected()) {
				if(!mWifi.isConnected())
				{				
				 showMessage("Please connect to internet!");
				 
				}
			}
			
			else if (!mWifi.isConnected()) {
				if (!mMobile.isConnected())
				{
					showMessage("Please connect to internet!!");
				}
								
			}
			
			
	}
	
	public void showMessage(String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(msg);
        builder.setInverseBackgroundForced(true);
        /*builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                    }
                });*/
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                            int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	    
	public boolean onOptionsItemSelected(MenuItem item) {
   	    switch (item.getItemId()) {
   	    
   	    case R.id.about:
   	    	showMessage("ListenQuranUrdu V2.0 \nDeveloped by M.Asif, DK");
   	    	/*
   	    	Toast.makeText(getApplicationContext(),
                    "Listen Quran Urdu V1.0 \nDeveloped by M.Asif", Toast.LENGTH_LONG)
                    .show();
            */
   	    break;
   	    case R.id.courtesy:
   	    	showMessage("quranurdu.com\nqurandownload.com");
   	    	
   	    	
   	    }
		return true;
   	}
	    
	    private void createLayoutDynamically() {
	    	AssetManager am = getApplicationContext().getAssets(); 
	    		
	    	try {
				InputStream is = am.open("quran_urdu.txt");
				InputStreamReader inputStreamReader = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(inputStreamReader);
				String line;
				
								
				//final String tempLine;
			    int lineNumber = 0;
			    while ((line = br.readLine()) != null) {
			        //System.out.printf("%04d: %s%n", ++lineNumber, line);
			    	final Button myButton = new Button(this);
		            //myButton.setText(line);
			    	final String fileName = line.split("\t")[0].trim();
			    	String fileNumber = "";
			    	if(fileName.contains("s-"))
			    	{
			    	fileNumber = fileName.split("s-")[0];
		            myButton.setText(fileName.split("s-")[0]+"\t"+" Sura "+fileName.split("s-")[1].split(".mp3")[0]);
			    	}
			    	else
			    	{	//myButton.setText(fileName);	
			    		fileNumber = fileName.split("s")[0];
			    		myButton.setText(fileName.split("s")[0]+"\t"+" Sura "+fileName.split("s")[1].split(".mp3")[0]);
			    	}
		            
			    	final String suraNumber = fileNumber;
		            ++lineNumber;
		            myButton.setId(lineNumber);
		            myButton.setTextColor(Color.WHITE);
		            myButton.setGravity(Gravity.LEFT);	            
		            LinearLayout layout = (LinearLayout) findViewById(R.id.inner_layout);
		            layout.addView(myButton);
		            //((Button)findViewById(myButton.getId())).getBackground().setColorFilter(Color.GRAY, Mode.MULTIPLY);
		                       	
		            	myButton.setOnClickListener(new View.OnClickListener() {
		                public void onClick(View view) {
		                    
		                	((Button)findViewById(myButton.getId())).getBackground().setColorFilter(Color.CYAN, Mode.MULTIPLY);
		                	playClick(getApplicationContext(),fileName,suraNumber);
		                    Toast.makeText(getApplicationContext(),
		                            "Playing Sura " + fileName, Toast.LENGTH_SHORT)
		                            .show();
		     	        }
		            });
	    
			    }
			    
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		 public void playClick(Context context, final String name, final String number){
			 	
			 	
			 	temp = String.format("http://www.qurandownload.com/listen-to-quran-in-your-language/urdu-translation/%s.mp3", number);
			 	if(ck.isChecked())
			 	{
			 		temp = String.format("http://download2.quranurdu.com/Al Quran with Urdu Translation by Imam Al Sadais and Shraim/%s", name);
			 	}
			 	
				Uri a = Uri.parse(temp);    
				
			    Intent viewMediaIntent = new Intent();   
		        viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);   
		        viewMediaIntent.setDataAndType(a, "audio/*");           
		        viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		        startActivity(viewMediaIntent);
		    	//SystemClock.sleep(mp.getDuration());
		        
		    }  

}

