package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppInstall extends AppCompatActivity {

    private static final String TAG = "RecyclerViewExample";
    public static int condition = 1;
    private List<FeedItem> feedsList;
    //  final String url;
    String urlme;
    private RecyclerView mRecyclerView;
    private InstallAppAdapter adapter;
    private ProgressBar progressBar;
    String UserMobileNumber;
    InterstitialAd mInterstitialAd;
    private static final String ARG_SECTION_NUMBER = "section_number";
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_install);

        urlme = "https://spingreedy.tk/GetAppInstallOffer.php";
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //mRecyclerView.setLayoutManager(layoutManager);
//        progressBar = (ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);

        new AsyncHttpTask().execute();
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
          //  progressBar.setVisibility(View.VISIBLE);
            //  setProgressBarIndeterminateVisibility(true);
            //   Log.i("In Pre:", "Yes");
          //  pd = new ProgressDialog(getApplicationContext());
            //pd.setMessage("Please Wait...");
           // pd.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            Log.i("In DOIN:", "Yes");
            try {


                URL url = new URL(urlme);


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
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
         //   progressBar.setVisibility(View.GONE);
       //     pd.dismiss();

            if (result == 1) {
                adapter = new InstallAppAdapter(AppInstall.this, feedsList, mRecyclerView);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(context, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        Log.i("InParse", "YesNONONO");
        try {
            //   Log.i("InParseIn", "Yes");

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            feedsList = new ArrayList();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                FeedItem item = new FeedItem();
                item.setAppInstallImage(post.optString("image"));
                item.setAppInstallText(post.optString("title"));
                String Url=post.optString("url");

                    item.setAppInstallUrl(Url);


                item.setAppInstallDescription(post.optString("description"));
                item.setAppInstallPayout(post.optString("payout"));
                //item.(post.optString("address"));

                feedsList.add(item);
                Log.i("AppImage:", post.optString("image"));
                Log.i("Appurl:", post.optString("url"));
                Log.i("Appurl:", post.optString("title"));
                Log.i("Appurl:", post.optString("description"));
                Log.i("Appurl:", post.optString("payout"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
