package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.luolc.emojirain.EmojiRainLayout;
import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TapAndEarn extends AppCompatActivity {
    private EmojiRainLayout mContainer;
   static int CountCoins;
   TextView CoinEarnText;
   String currentDayIs;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String UserMobileNumber;
    InterstitialAd mAd;
    ProgressDialog pd;
    int backButtonCount=0;
    static int flag=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_and_earn);
        getSupportActionBar().hide();

        pref = this.getSharedPreferences("SpinGreedy", MODE_PRIVATE);
        editor = pref.edit();
        UserMobileNumber=pref.getString("Email",null);

        CountCoins=0;
        flag=1;
        CoinEarnText=(TextView) findViewById(R.id.CoinEarnText);
        mContainer = (EmojiRainLayout) findViewById(R.id.group_emoji_container);

        //----------------Admob App Id-----------------------
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));
        //----------------Load Banner----------------------------
//        AdView adView = new AdView(getApplicationContext());
//        adView.setAdSize(AdSize.SMART_BANNER);
//        adView.setAdUnitId("ca-app-pub-8862043394871407/7511110313");
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

        // add emoji sources
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);

        // set emojis per flow, default 6
        mContainer.setPer(6);

        // set total duration in milliseconds, default 8000
        mContainer.setDuration(1000);

        // set average drop duration in milliseconds, default 2400
        mContainer.setDropDuration(1000);

        // set drop frequency in milliseconds, default 500
        mContainer.setDropFrequency(400);
        //ShowFullAd();
        StartAppSDK.init(this, "208177306", true);
        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);

// Define StartApp Mrec
        Mrec startAppMrec = new Mrec(this);
        RelativeLayout.LayoutParams mrecParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        mrecParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mrecParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        StartAppAd.showAd(this);



    }

    public void PushMeBtn(View view)
    {

        if(CountCoins==0)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Add the line which you want to run after 5 sec.
                UpdateUserBalance(String.valueOf(CountCoins),"TapEarnGame");
                Intent intent=new Intent(getApplicationContext(),SpinResultActivity.class);
                intent.putExtra("CoinsEarn",CountCoins);
                startActivity(intent);
                StartAppAd.showAd(getApplicationContext());
                finish();
                Log.i("In RUN","YES");
               // flag=0;

            }
        },5000);
        CountCoins++;
        mContainer.startDropping();
        CoinEarnText.setText("Coins Earn: "+String.valueOf(CountCoins));
    }
    public void ShowFullAd()
    {
        pd = new ProgressDialog(TapAndEarn.this);
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
                pd.dismiss();
                // Code to be executed when the ad is displayed.
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
             //   Toast.makeText(getApplicationContext(), "Congratulation!!! Amount Credited!...", Toast.LENGTH_LONG).show();


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
