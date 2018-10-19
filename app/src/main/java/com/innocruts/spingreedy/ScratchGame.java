package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cooltechworks.views.ScratchTextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ScratchGame extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String UserMobileNumber;
    String currentDayIs;
    int random;
    InterstitialAd mAd;
    ProgressDialog pd;
    int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_game);
        getSupportActionBar().hide();

        //ScratchTextView scratchTextView = new ScratchTextView(this);
       // TextView textView=(TextView) scratchTextView.findViewById(R.id.scratchcode);

        pref = this.getSharedPreferences("SpinGreedy", MODE_PRIVATE);
        editor = pref.edit();
        UserMobileNumber=pref.getString("Email",null);
        Random r = new Random();
        random=r.nextInt(100-10)+10;
        final ScratchTextView scratchTextView1=(ScratchTextView) findViewById(R.id.scratchcode);
        scratchTextView1.setText(String.valueOf(random));
//----------------Admob App Id-----------------------
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));
        //----------------Load Banner----------------------------
        AdView adView = new AdView(getApplicationContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-8862043394871407/7511110313");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//
//        AdView adView1 = new AdView(getApplicationContext());
//        adView1.setAdSize(AdSize.SMART_BANNER);
//        adView1.setAdUnitId("ca-app-pub-8862043394871407/9805159211");
//        AdView mAdView1 = (AdView) findViewById(R.id.adView_1);
//        AdRequest adRequest1 = new AdRequest.Builder().build();
//        mAdView1.loadAd(adRequest1);
        //ShowFullAd();
        StartAppAd.showAd(this);
        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

// Define StartApp Mrec
        Mrec startAppMrec = new Mrec(this);
        RelativeLayout.LayoutParams mrecParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

 //Add to main Layout
         mainLayout.addView(startAppMrec, mrecParameters);
        scratchTextView1.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView tv) {
                //on reveal

               // UpdateUserBalance(String.valueOf(random),"Scratch");
                // randomCoupan=0;

                UpdateUserBalance(String.valueOf(random),"ScratchGame");
                Intent intent=new Intent(getApplicationContext(),SpinResultActivity.class);
                intent.putExtra("CoinsEarn",random);
                startActivity(intent);
                finish();
            }


            @Override
            public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {

            }
        });

    }
    public void ShowFullAd()
    {  pd = new ProgressDialog(ScratchGame.this);
        pd.setMessage("Sending Confirmation....");
        pd.show();
        mAd=new InterstitialAd(this);

        mAd.setAdUnitId("ca-app-pub-8862043394871407/3239750869");
        mAd.loadAd(new AdRequest.Builder().addTestDevice("B8391EB6F96DF89F51011980B61E388D").build());
        if (mAd.isLoaded()) {
            mAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        mAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                if (mAd.isLoaded()) {
                    mAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                Log.i("Ads", "onAdLoaded");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.i("Ads", "onAdFailedToLoad");
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
                pd.dismiss();
                Log.i("Ads", "onAdOpened");
            }
            @Override
            public void onAdClicked() {
                // Code to be executed when the ad is displayed.


            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
//                int JokeCount=pref.getInt("JokeCount",0);
//
//                if(JokeCount==10)
//                {
//                    UpdateUserBalance("4","Joke");
//                    editor.putInt("JokeCount",0).commit();
//                }
//                else {
//
//                }
            }


            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.
                Log.i("Ads", "onAdClosed");
            }
        });

    }

    public void CollectCoins(View view)
    {
        Intent intent=new Intent(getApplicationContext(),SpinResultActivity.class);
        intent.putExtra("CoinsEarn",random);
        startActivity(intent);
        StartAppAd.showAd(getApplicationContext());
        finish();
    }

    public void UpdateUserBalance(final String TaskAmount, final String Task) {

        currentDayIs = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new Date());
//        pd = new ProgressDialog(getApplicationContext());
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://spingreedy.tk/UpdateUserBalance.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //  pd.dismiss();
                Log.i("ResponceIsL", response.toString());
              //  Toast.makeText(getApplicationContext(), "Congratulation!!! Amount Credited!...", Toast.LENGTH_LONG).show();


//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    public void run() {
//                        // Actions to do after 10 seconds
//                        Toast.makeText(getApplicationContext(), "Congratulation!!! Amount Credited!...", Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                        startActivity(intent);
//                    }
//                }, 20000);

            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("ErrorFound",error.getMessage());
                //  pd.dismiss();
                //This code is executed if there is an error.
                // Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                Log.i("UserMobieIs:", UserMobileNumber);
                MyData.put("mobile",UserMobileNumber.trim());
                MyData.put("balance", TaskAmount);
                MyData.put("task",Task);
                MyData.put("currentDate", String.valueOf(currentDayIs));//Add the data you'd like to send to the server.
                // MyData.put("paytmnumber", UserPaytmNumber);//Add the data you'd like to send to the server.


                return MyData;
            }
        };
//                if(status)
//                {

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(
                (int) TimeUnit.SECONDS.toMillis(2000),
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyRequestQueue.add(MyStringRequest);

        // requestQueue.add(stringRequest);
//                    i++;
//                    status =false;
//
//                }
//            }while(i>0);
    }



}
