package com.example.quakely;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends Activity implements SensorEventListener {
	  private SensorManager mSensorManager;
	  private Sensor mAccelerometer;
	  float starttime;
	  public TextView text;
	  public View root, someView;
	  public static RelativeLayout Layout;
	  int stream=128;
	  int globalcount=0;
	  long TimeOld=0;
	  TextView text2,text3;
	  int count=0, check=0;
	  float [][] data = new float[3][stream];
	  private Handler mHandler;
	  @Override
	  public final void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mHandler = new Handler();
	    setContentView(R.layout.activity_main);
	    Layout = (RelativeLayout) findViewById(R.id.t2);
	    //text2 = new TextView(this);
	    text = (TextView) findViewById(R.id.textView1);
	    text2 = (TextView) findViewById(R.id.text1);
	    text3 = (TextView) findViewById(R.id.textView2);
	    String value = "1";
		/*while(true)
		{
			new MyAsyncTask(this).execute(value);
		}*/
		//new MyAsyncTask(this).execute(value);
	    //text.setText("Default");
	    //setContentView(text);
	    /*
	    for(count=0;count<1000;count++){
			  text.setText(""+count);
			  setContentView(text);
			  
		  }*/

	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    
	   
	   }
	  
	  public void changeBgColor(final int color) {
		    mHandler.post(new Runnable() {
		        public void run() {
		            Layout.setBackgroundColor(color);
		        }
		    });
		}
	  
	  public void onClick(View view) throws IOException
		{
			//needToDetect = false;
			int id=view.getId();
			if(id==R.id.button1){
			String json = "{"+ "\"data\": ["+ "{"+ "\"latx\": \"1\","+ "\"laty\": \"1\""+ "}"+ "]}";
				//text.setText(json);
				//URL url = new URL("http://students.iitk.ac.in/programmingclub/quakely/result.txt");
				//text.setText("yo");
				store(json);
				//detect();
				
				//new MyAsyncTask(this).execute(value);	
			}	
			//needToDetect=true;
		}
		
		public void store(String str)
	    {
	    	/*HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://students.iitk.ac.in/programmingclub/quakely/report.php");
	        try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	        nameValuePairs.add(new BasicNameValuePair("report", "yo"));
	        HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs);
	        httppost.addHeader(entity.getContentType());
	        httppost.setEntity(entity);
	        httpclient.execute(httppost);
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	        }*/
			//new MyAsyncTask(this).execute(str);
			//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hackyourworld.org/~iitk_vijay/quakely/report.php?report="+str));
			//startActivity(browserIntent);

	   }

	  
	  public final void onStart(Bundle savedInstanceState){
		 /* 
		  text.setText("scdv");
		  setContentView(text);
		  for(count=0;count<1000;count++){
			  text.setText(""+count);
			  setContentView(text);
			  
		  }
		  */
		  //float startTime = System.currentTimeMillis();
	  }
	  
	  private void writeStringToTextFile(String s, File f){
		  File sdCard = Environment.getExternalStorageDirectory();
		  File dir = new File (sdCard.getAbsolutePath());
		  dir.mkdirs();
		  //File file = new File(dir, f);
		  try{
		      FileOutputStream f1 = new FileOutputStream(f,false); //True = Append to file, false = Overwrite
		      PrintStream p = new PrintStream(f1);
		      p.append(s);
		      p.close();
		      f1.close();
		  } catch (FileNotFoundException e) {
		  } catch (IOException e) {
		  }   }

	  @Override

	  public final void onAccuracyChanged(Sensor sensor, int accuracy)
	  {
	    // Do something here if sensor accuracy changes.
	  }

	  public final int checkEarthquake(float data[][])
	  {/*
		  Check if the data corresponds to earthquake or not
		  Return 1 if earthquake
		  
		  float accelold=data[(count-10+stream)%stream][0]*data[(count-10+stream)%stream][0]+data[(count-10+stream)%stream][1]*data[(count-10+stream)%stream][1]+data[(count-10+stream)%stream][2]*data[(count-10+stream)%stream][2];
		  float accelnew=data[count][0]*data[count][0]+data[count][1]*data[count][1]+data[count][2]*data[count][2];
		  
		  if(Math.abs(accelnew-accelold)>15){
			  text.setText(""+(accelnew-accelold));
			  return 1;
			  
		  }
		  return 0;
		  */
		  
		  	int n=stream;
	        double g=9.8;
	        double[] dist = new double[n];
	        double[] freal = new double[n];
	        double[] fimag = new double[n];
	        double[] f=new double[n];
	        double sum,xmax,mu,negs,dist2;
	        
	        sum=0;
	        xmax=0;
	        mu=0;
	        negs=0;
	        dist2=0;
	        /*
	        String s=""+data[0][count]+'\t'+data[1][count]+'\t'+data[2][count]+'\n';
	         File FILENAME = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"data.txt");
	    	
	         writeStringToTextFile(s,FILENAME);
	         */
	        for (int i = 0; i < n; i++)     {
	            dist2+=(data[0][i]*data[0][i])+(data[1][i]*data[1][i]);
	        	dist[i]=(data[0][i]*data[0][i])+(data[1][i]*data[1][i])+((data[2][i]-9.8)*(data[2][i]-9.8));
	        }
	 
	        if(dist2/n<2)
	        	return 0;
	        
	        
	                for (int k = 0; k < n; k++) {
	                        double sumreal = 0;
	                        double sumimag = 0;
	                        for (int t = 0; t < n; t++) {  
	                                sumreal +=  dist[t]*Math.cos(2*Math.PI * t * k / n);
	                                sumimag += -dist[t]*Math.sin(2*Math.PI * t * k / n);
	                        }
	                        freal[k] = sumreal;
	                        fimag[k] = sumimag;
	                        f[k]=Math.sqrt((freal[k]*freal[k])+(fimag[k]*fimag[k]));
	                        sum+=f[k];
	                        xmax=Math.max(xmax,f[k]);
	 
	                }
	                		mu=sum/n;
	                        for(int k=0;k<n;k++)    {
	                        f[k]=(f[k]-mu)/(xmax-mu);
	                        }
	 
	        for (int i = 14; i < 114; i+=5) {
	                //System.out.println(freal[i]+" + i"+fimag[i]);
	                //System.out.println(" + i");
	                double print = Math.max(f[i],Math.max(f[i+1],Math.max(f[i+2],Math.max(f[i+3],f[i+4]))));
	                //printf("%lf\n",print); 
	                if(print<-0.001) negs++;
	        }
	 
	        //System.out.println(negs);
	        if(negs<=11) return 1;
	        else return 0; 
	 
	    }
	        
	        
	  
	  
	  
	 
	  

	 long TimeNew;
	 long delay=0;
	 
	@Override
	  public final void onSensorChanged(SensorEvent event) 
	  {	    	/*
		 Date d = new Date();
		 Calendar rightNow = Calendar.getInstance();
		 long offset = rightNow.get(Calendar.ZONE_OFFSET) +
				    rightNow.get(Calendar.DST_OFFSET);
				long sinceMidnight = (rightNow.getTimeInMillis() + offset) %
				    (24 * 60 * 60 * 1000);
*/
		  
		  
		  TimeNew=event.timestamp;
		  
		  delay = (long) ((TimeNew-TimeOld)/1000000);
		  TimeOld=TimeNew;
		  text2.setText(""+delay);
		  //setContentView(text2);
		  	if(true){
		  		//text.setText("test"+Integer.toString(count));
	  			//setContentView(text);
		    	data[0][count] =  event.values[0];
		    	data[1][count] =  event.values[1];
		    	data[2][count] =  event.values[2];
		    	
		    	//if(data[0][count]*data[0][count]+data[1][count]*data[1][count]>1.5) 
		    		count++;
		    	
		    	/*	
		       	 String s=""+data[0][count]+'\t'+data[1][count]+'\t'+data[2][count]+'\n';
		         File FILENAME = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"data.txt");
		    	
		         writeStringToTextFile(s,FILENAME);
		         */
		    	if(count==stream){
		    		if(checkEarthquake(data)==1)
		    		{
		    			globalcount++;
		    			//Notify server
		    			//text.append("Earthquake");
		    			//text.setBackgroundColor(android.graphics.Color.RED);
		    			//setContentView(text);
		    			    			
		    		}
		    		
		    		else{
		    			globalcount--;
		    			//text.setBackgroundColor(android.graphics.Color.WHITE);
		    			//setContentView(text);
		    		}
		    		count=0;
		    		globalcount=Math.max(globalcount, 0);
		    		globalcount=Math.min(globalcount, 1);
		    		if(globalcount==1)	{
		    			//quake
		    			//Notify server
		    			//text2.append("Earthquake");
		    			//text2.setBackgroundColor(android.graphics.Color.RED);
		    			//setContentView(text2);
		    			//text2.setText("earthquake");
		    			//text2.setBackgroundColor(Color.parseColor("#bdbdbd"));
		    			//text3.setBackgroundColor(Color.parseColor("FFCC00"));
		    			
		    			//Toast.makeText(getApplicationContext(), "EARTHQUAKE DETECTED BY YOUR SMARTPHONE! Please take precautionary measures to reduce harm to self", Toast.LENGTH_LONG).show();
		    			
		    			//LayoutInflater inflater = getLayoutInflater();
		    			//text2.setText("earthquake");
		    			changeBgColor(-256);
		    			//String str = "{"+ "\"data\": ["+ "{"+ "\"latx\": \"1\","+ "\"laty\": \"1\""+ "}"+ "]}";
		    			//store(str);
		    			
		    			
		    		}
		    		else	{
		    			//root.setBackgroundColor(getResources().getColor(android.R.color.white));
		    			//so jaa bc
		    			//text2.setBackgroundColor(android.graphics.Color.WHITE);
		    			//setContentView(text2);
		    		}
		    		
		    		}
		    	
		    }
		    	
		
		    	//float endTime = System.currentTimeMillis();
		    	//float timeneeded =  ((startTime - endTime));
		    	
		    	//text.setText(data[count][0]+"");
		    	//setContentView(text);
		    	
		  	}
		  	
		  	
	  
	  
	 
	  @Override
	  protected void onResume()
	  {
	    super.onResume();
	 
	    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
	  }

	  @Override
	  protected void onPause() {
	    super.onPause();
	    mSensorManager.unregisterListener(this);
	  }
	}

class MyAsyncTask extends AsyncTask<String, Integer, String>{	
	
	private MainActivity mainactivity;
	String result ;
	//private TextView text;
	
	public MyAsyncTask(MainActivity mainactivity)
	{
		this.mainactivity=mainactivity;
	}
	
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		result = detect();
		//text.setText(res);
		return result;
	}

	protected void onPostExecute(String result){
		
		//String one = "1";
		if(result.equals("1"))
		{	mainactivity.text.setText("Earthquake Alert! Save yourself. ");
			mainactivity.changeBgColor(-65536);
			//mainactivity.Layout.setBackgroundColor(Color.parseColor("#FF0000"));
		}
		else
		{   mainactivity.text.setText("Relax. No earthquake!");
			//mainactivity.root.setBackgroundColor(mainactivity.getResources().getColor(android.R.color.white));
		}
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		String value="you";
			new MyAsyncTask(mainactivity).execute(value);
		//doInBackground();
		//pb.setVisibility(View.GONE);
		//Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
	}
	protected void onProgressUpdate(Integer... progress){
		//pb.setProgress(progress[0]);
	}
	
	public String detect()
	{
		String total = "";
	DefaultHttpClient httpclient = new DefaultHttpClient();
	HttpGet httppost;
		httppost = new HttpGet("http://www.hackyourworld.org/~iitk_vijay/quakely/report.php");
	//else
		//httppost = new HttpGet("http://www.hackyourworld.org/~iitk_vijay/quakely/report.php?latx=1&laty=1");
	try{
	HttpResponse response = httpclient.execute(httppost);
	        HttpEntity ht = response.getEntity();

	        BufferedHttpEntity buf = new BufferedHttpEntity(ht);

	        InputStream is = buf.getContent();


	        BufferedReader r = new BufferedReader(new InputStreamReader(is));

	        
	        String line;
	        while ((line = r.readLine()) != null) {
	            total=total+line;
	            
	            //Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT);
	            //toast.show();        
	         }
	        
	        
	} catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
    } catch (IOException e) {
        // TODO Auto-generated catch block
    }
	return total;
	        
}
}