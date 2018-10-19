package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.ads.MobileAds;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;


import org.jsoup.Jsoup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pd;
    int TaskCount;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String UserMobileNumber,currentDayIs;
    TextView UserCoins;
    String currentVersion;
    int backButtonCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        pref=this.getSharedPreferences("SpinGreedy",MODE_PRIVATE);
        editor=pref.edit();
        UserMobileNumber=pref.getString("Email",null);
        currentDayIs = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        UserCoins=(TextView)findViewById(R.id.CoinsCount);
        GetUserBalanceVolly();
      //  StartAppAd.showAd(this);
        //----------------Admob App Id-----------------------
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));
        //----------------Load Banner----------------------------
//        AdView adView = new AdView(getApplicationContext());
//        adView.setAdSize(AdSize.SMART_BANNER);
//        adView.setAdUnitId("ca-app-pub-8862043394871407/9805159211");
//        AdView mAdView = (AdView) findViewById(R.id.adView_1);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        //////////////////////////APPCURRENT VERSION///////////////////////////////////////////////////////
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("Current Verison:", currentVersion);
        new GetVersionCode().execute();
        StartAppSDK.init(this, "208177306", true);
        StartAppAd.setAutoInterstitialPreferences(
                new AutoInterstitialPreferences()
                        .setSecondsBetweenAds(30)
        );
//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();

    }
    public void ContactUs(View view)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","spingreedy@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "User Mobile:"+UserMobileNumber);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Type Your Problem Here...");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
    public void UpdateNow(View view)
    {
        //////////////////////////APPCURRENT VERSION///////////////////////////////////////////////////////
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.i("Current Verison:", currentVersion);
        new GetVersionCode().execute();
    }
    public void Share(View view)
    {
        Intent intent=new Intent(this,ReferAndEarn.class);
        startActivity(intent);
        StartAppAd.showAd(this);
    }
    public void RateUs(View view)
    {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.innocruts.spingreedy"));
        startActivity(i);

    }
    public void ProfileBtn(View view)
    {
        Intent intent=new Intent(this,ProfileActivity.class);
        startActivity(intent);
        StartAppAd.showAd(this);
    }
    public void SpinWheelBtn(View view)
    {
        GetClickCount("SpeenWheel");

    }
    public void ScratchGameBtn(View view)
    {
        GetClickCount("ScratchGame");

    }
    public void FreeCoins(View view)
    {
        GetClickCount("TapEarnGame");

    }
    public void AppInstall(View view)
    {
        Intent intent=new Intent(this,AppInstall.class);
        startActivity(intent);
    }

    public void GetUserBalanceVolly() {

        pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.show();
        // Log.i("InGetUser",UserEmail);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://spingreedy.tk/GetUserBalance.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.

                Log.i("Balance jjIS:",response.toString());
//                editor.putInt("WalletAmount", Integer.parseInt(response.toString()));
//                editor.commit();
//                CoinsEarn= Integer.parseInt(response.toString());
                String[] balance = response.toString().split("-");
                Log.i("Balance Is:",balance[0]);

                UserCoins.setText(balance[0]);

                  pd.dismiss();
                // NotificationSend();
                // UserBalanceText.setText(String.valueOf(UserEarnCoin));
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                  pd.dismiss();
                //This code is executed if there is an error.
                //     Log.i("ErroInConnection:",error.getMessage());
                // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                //  Log.i("Error jjIS:",error.getMessage());

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                Log.i("MobileIs",UserMobileNumber);
                MyData.put("mobile", UserMobileNumber.trim());
                //MyData.put("balance", String.valueOf(BalanceUpdate));
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    public void GetClickCount(final String Task) {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Checking...");
        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://spingreedy.tk/GetClickCount.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                pd.dismiss();
                Log.i("ResponceClick", response.toString());
                TaskCount=Integer.parseInt(response.toString());
                if(Task.compareTo("SpeenWheel")==0) {
                    if (TaskCount <=10) {
                        Intent intent=new Intent(MainActivity.this,SpinWheel.class);
                        startActivity(intent);
                        StartAppAd.showAd(getApplicationContext());
                    } else {
                        Toast.makeText(MainActivity.this,"Your Todays Spin Task is Completed",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Task.compareTo("ScratchGame")==0)
                {
                    if (TaskCount <=5) {
                        Intent intent=new Intent(MainActivity.this,ScratchGame.class);
                        startActivity(intent);
                        StartAppAd.showAd(getApplicationContext());
                    } else {
                        Toast.makeText(MainActivity.this,"Your Todays Scratch Code Completed",Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Task.compareTo("TapEarnGame")==0)
                {
                    if (TaskCount<=5) {
                        Intent intent=new Intent(MainActivity.this,TapAndEarn.class);
                        startActivity(intent);
                        StartAppAd.showAd(getApplicationContext());
                    } else {
                        Toast.makeText(MainActivity.this,"Todays Tap-Earn Completed",Toast.LENGTH_SHORT).show();
                    }
                }
//                else if(Task.compareTo("Scratch")==0)
//                {
//                    if (TaskCount <=1) {
//                        Intent intent=new Intent(MainActivity.this,GiftBoxPopup.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this,"Your Scratch Task is Completed",Toast.LENGTH_SHORT).show();
//                    }
//                }
                //completeclick.setText(String.valueOf(ClickCountIs));


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                //This code is executed if there is an error.
                Log.i("ErrorFound",error.getMessage());
                // Toast.makeText(getApplicationContext(), "Try Again...", Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                Log.i("InMap:", "Yes");
                MyData.put("mobile", UserMobileNumber.trim());
                MyData.put("task", Task);
                // MyData.put("balance", "1");
                Log.i("CurrentDateIs",String.valueOf(currentDayIs));
                MyData.put("currentDate",String.valueOf(currentDayIs));//Add the data you'd like to send to the server.
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


    }



    public void GetBalance(View view)
    {
        GetUserBalanceVolly();
    }
    ////////////////////Check For App Updte////////////////////////////////////////
    private class GetVersionCode extends AsyncTask<Void, String, String> {
        @Override
        protected String doInBackground(Void... voids) {

            String newVersion = null;
            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName() + "&hl=it")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div[itemprop=softwareVersion]")
                        .first()
                        .ownText();
                return newVersion;
            } catch (Exception e) {
                return newVersion;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {


            super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            this.finish();


            // ShowFullAd();
        } else {
            // Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
