package com.innocruts.spingreedy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.luolc.emojirain.EmojiRainLayout;
import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

public class SpinResultActivity extends AppCompatActivity {

    TextView WinLoseText,CoinText;
    String WinLose,CoinsEarn;
    private EmojiRainLayout mContainer;
    Button PlayAgainButton;
    InterstitialAd mAd;
    int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_result);

      //  getSupportActionBar().hide();
        Intent intent=getIntent();
        CoinsEarn=intent.getStringExtra("Coin_earn");
//        Log.i("Coins Are",Coins);
        WinLoseText=(TextView)findViewById(R.id.WinLossText);
        CoinText=(TextView)findViewById(R.id.CoinsText);
        CoinText.setText(CoinsEarn);

        PlayAgainButton=(Button)findViewById(R.id.PlayAgainBtn) ;
        mContainer = (EmojiRainLayout) findViewById(R.id.group_emoji_container);

        // add emoji sources
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);
        mContainer.addEmoji(R.drawable.coin);

        // set emojis per flow, default 6
        mContainer.setPer(10);

        // set total duration in milliseconds, default 8000
        mContainer.setDuration(3000);

        // set average drop duration in milliseconds, default 2400
        mContainer.setDropDuration(3000);

        // set drop frequency in milliseconds, default 500
        mContainer.setDropFrequency(800);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PlayAgainButton.setVisibility(View.VISIBLE);
                // Add the line which you want to run after 5 sec.

            }
        },3000);

        //----------------Admob App Id-----------------------
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.AdmobApppId));
        //----------------Load Banner----------------------------
        AdView adView = new AdView(getApplicationContext());
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-8862043394871407/7511110313");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
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

//        AdView adView1 = new AdView(getApplicationContext());
//        adView1.setAdSize(AdSize.SMART_BANNER);
//        adView1.setAdUnitId("ca-app-pub-8862043394871407/9805159211");
//        AdView mAdView1 = (AdView) findViewById(R.id.adView_1);
//        AdRequest adRequest1 = new AdRequest.Builder().build();
//        mAdView1.loadAd(adRequest1);


      //  ShowFullAd();
    }

    public void CollectCoinsButton(View view)
    {
        UpdateUserBalanceClass updateUserBalanceClass=new UpdateUserBalanceClass();
       // updateUserBalanceClass.UpdateUserBalance(Coins,"SpeenWheel",);
    }

    public void PlayAgainBtn(View view)
    {
        //ShowFullAd();
        Toast.makeText(getApplicationContext(), "Congratulation!!! Amount Credited!...", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        StartAppAd.showAd(getApplicationContext());
        finish();
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
                mContainer.startDropping();
                PlayAgainButton.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 100) {


         //   super.onBackPressed();
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
//            this.finish();
            Toast.makeText(this, "Click On Collect Coins and Play Again Button to Continue", Toast.LENGTH_SHORT).show();



            // ShowFullAd();
        } else {
            Toast.makeText(this, "Click On Collect Coins and Play Again Button to Continue", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
