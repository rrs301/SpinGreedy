package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReferAndEarn extends AppCompatActivity {

    private static final String TAG = "RecyclerViewExample";
    TextView InviteText, ReferText, ReferCode;
    String UserName, referCode;
    InterstitialAd mAd;


    String MainUser,ReferUser;
    String UserReferString;
    private static final String ARG_SECTION_NUMBER = "section_number";
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);
        pref = this.getSharedPreferences("SpinGreedy", MODE_PRIVATE);
        editor = pref.edit();
    //    UserMobileNumber=pref.getString("Email",null);
        getSupportActionBar().hide();
        InviteText = (TextView)findViewById(R.id.inviteText);
        ReferText = (TextView) findViewById(R.id.ReferText);
        referCode = pref.getString("ReferCode", "RAH3047");
//            referCode = sharedPreferences.getString("ReferCode", null);

        InviteText.setText("Invite your friend to SpinGreedy App");

        ReferCode = (TextView) findViewById(R.id.ReferCode);
        ReferCode.setText("Refer Code: " +
                referCode.toUpperCase()+"     COPY");
       new AsyncHttpTask().execute();
        findViewById(R.id.ShortUrlbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendInvite();
            }
        });

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

     //   ShowFullAd();
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

    public void CopyReferCode(View view)
    {
        Toast.makeText(this,"Refer Code Copied",Toast.LENGTH_SHORT).show();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("SpinGreedyReferCode", referCode);
        clipboard.setPrimaryClip(clip);
    }
    public void SendInvite() {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String Body = "Hi friends, Download SpinGreedy App and Earn Paytm cash";
        String shareBody = Body.replaceAll("<[^>]*>", "");

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Download SpinGreedy App, Best Earning App");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "" + shareBody + "Download App Now " + buildDynamicLink());
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        ShowFullAd();
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
    private String buildDynamicLink(/*String link, String description, String titleSocial, String source*/) {
        //more info at https://firebase.google.com/docs/dynamic-links/create-manually

//        String path = FirebaseDynamicLinks.getInstance().createDynamicLink()
//                .setDynamicLinkDomain("cf32x.app.goo.gl")
//                .setLink(Uri.parse("http://innocruts.com/"))
//                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build()) //com.melardev.tutorialsfirebase
//                .setSocialMetaTagParameters(new DynamicLink.SocialMetaTagParameters.Builder().setTitle("Share this App").setDescription("blabla").build())
//                .setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder().setSource("AndroidApp").build())
//                .buildDynamicLink().getUri().toString();
//
//        return path;
        return "https://spingreedy.page.link/?" +
                "link=" + /*link*/
                "https://spingreedy.com/"+referCode +
                "&apn=" + /*getPackageName()*/
                "com.innocruts.spingreedy";//+
        //  return "http://easypanda.ml and Enter Refercode as "+referCode;
//                "&st=" + /*titleSocial*/
//                "Share+this+App" +
//                "&sd=" + /*description*/
//                "looking+to+learn+how+to+use+Firebase+in+Android?+this+app+is+what+you+are+looking+for." +
//                "&utm_source=" + /*source*/
//                "GT568N";
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

//        final ProgressDialog ringProgressDialog = ProgressDialog.show(getApplicationContext(), "Please wait ...", "Do Not Touch...", true);

        @Override
        protected void onPreExecute() {
         //   ringProgressDialog.setCancelable(true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {
                    }

                  //  ringProgressDialog.dismiss();
                }
            }).start();

        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:", "Yes");
            try {


                URL url = new URL("https://spingreedy.tk/FriendRefer.php");
                //  Log.i("URLI:","http://freetym.tk/AppBackend/rechargeApi.php?mobile="+RMobileNumber+"&op="+ROpCode+"&amnt="+RAmount+"&uid="+Uid);

                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    String ResponceIs = response.toString();
                    UserReferString=response.toString();
                    MainUser= ResponceIs.substring(0,1);
                    ReferUser=ResponceIs.substring(2,3);
                    Log.i("CODEIS:",MainUser+ReferUser);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                // Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            //    progressBar.setVisibility(View.GONE);
          //  ringProgressDialog.dismiss();
            //  progressDialog.dismiss();
            if (result == 1) {
                // Status="SUCCESS";

//                ReferText.setText("It's simple, just invite a friend to join Adlink "+
//                        "and a earn  Rs."+MainUser+"  would be " +
//                        "added to your wallet when 10 friend joins.");
                ReferText.setText(UserReferString);
//                adapter = new CoupanDealAdapter(context, feedsList, mRecyclerView);
//                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
