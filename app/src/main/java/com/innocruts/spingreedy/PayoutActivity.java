package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.HashMap;
import java.util.Map;

public class PayoutActivity extends AppCompatActivity {

    InterstitialAd mAd;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String UserMobileNumber;
    ProgressDialog pd;
   static String UserCoins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);

        pref = this.getSharedPreferences("SpinGreedy", MODE_PRIVATE);
        editor = pref.edit();
        UserMobileNumber=pref.getString("Email",null);
      //  GetUserBalanceVolly();
     //   ShowFullAd();
        StartAppAd.showAd(this);
    }
    public void ShowFullAd()
    {
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
                Log.i("Ads", "onAdOpened");
                // SpinBtn.setVisibility(View.VISIBLE);

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
    public void Paytm_10(View view)
    {
        Intent intent=new Intent(this,PaytmTransfer.class);
        intent.putExtra("RedeemType","Paytm_10");
        intent.putExtra("UserWallet",UserCoins);
        startActivity(intent);
    }
    public void Paytm_20(View view)
    {
        Intent intent=new Intent(this,PaytmTransfer.class);
        intent.putExtra("RedeemType","Paytm_20");
        intent.putExtra("UserWallet",UserCoins);
        startActivity(intent);
    }
    public void Paytm_50(View view)
    {
        Intent intent=new Intent(this,PaytmTransfer.class);
        intent.putExtra("RedeemType","Paytm_50");
        intent.putExtra("UserWallet",UserCoins);
        startActivity(intent);
    }
    public void PayPal_2(View view)
    {
        Intent intent=new Intent(this,PaytmTransfer.class);
        intent.putExtra("RedeemType","PayPal_2");
        intent.putExtra("UserWallet",UserCoins);
        startActivity(intent);
    }
    public void PayPal_5(View view)
    {
        Intent intent=new Intent(this,PaytmTransfer.class);
        intent.putExtra("RedeemType","PayPal_5");
        intent.putExtra("UserWallet",UserCoins);
        startActivity(intent);
    }



}
