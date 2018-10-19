package com.innocruts.spingreedy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.startapp.android.publish.adsCommon.StartAppAd;

import java.util.HashMap;
import java.util.Map;

public class PaytmTransfer extends AppCompatActivity {

    EditText UserMobilePaytmNumber,PaytmAmountTransfer;
    String UserPaytmNumber,PaytmAmount;
    ProgressDialog pd;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    String UserName,MobileNumber;
    int WalletAmount;
    String RedeemType;

    String Msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm_transfer);
        pref = getApplicationContext().getSharedPreferences("SpinGreedy", MODE_PRIVATE);
        editor = pref.edit();

        UserName = pref.getString("Username", "-");
        MobileNumber = pref.getString("Email", "-");
        Intent intent=getIntent();
        RedeemType=intent.getStringExtra("RedeemType");
//        WalletAmount=intent.getIntExtra("UserWallet",0);
        UserMobilePaytmNumber=(EditText)findViewById(R.id.mobile_number);
     //   PaytmAmountTransfer=(EditText)findViewById(R.id.Amount);

        UserPaytmNumber=UserMobilePaytmNumber.getText().toString();
        Log.i("WalletAmount", String.valueOf(WalletAmount));
        //PaytmAmount=PaytmAmountTransfer.getText().toString();
        if(RedeemType.compareTo("Paytm_10")==0) {
            PaytmAmount="10";
        }
        else if(RedeemType.compareTo("Paytm_20")==0)
        {
            PaytmAmount="20";

        }
        else if(RedeemType.compareTo("Paytm_50")==0)
        {
            PaytmAmount="50";

        }
        else if(RedeemType.compareTo("PayPal_2")==0)
        {
            PaytmAmount="2";

        }
        else if(RedeemType.compareTo("PayPal_5")==0)
        {
            PaytmAmount="5";

        }
        StartAppAd.showAd(getApplicationContext());

        findViewById(R.id.Submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verify();
                Snackbar.make(view, "Give 5 star rating for instant Redeem.", Snackbar.LENGTH_LONG)
                        .setAction("OK", null).show();
            }
        });
        GetUserBalanceVolly();
    }

    public void Verify()
    {
     //   GetUserBalanceVolly();
        UserPaytmNumber=UserMobilePaytmNumber.getText().toString();
//        PaytmAmount=PaytmAmountTransfer.getText().toString();

        Log.i("WalletAmount:", String.valueOf(WalletAmount));
        if(RedeemType.compareTo("PayPal_2")==0||RedeemType.compareTo("PayPal_5")==0) {
            if ((WalletAmount / 200000) >= Integer.parseInt(PaytmAmount)) {

                if (Integer.parseInt(PaytmAmount) <= (WalletAmount / 200000)) {
                    VollySaveBidDataMethod();
                    Toast.makeText(getApplicationContext(), "Thank You! Amount Credited in next 24-48 hours..", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "You Did not have sufficient Balance", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "You Did not have sufficient Coins", Toast.LENGTH_SHORT).show();

            }
        }
        else {
            if ((WalletAmount / 2500) >= Integer.parseInt(PaytmAmount)) {

                if (Integer.parseInt(PaytmAmount) <= (WalletAmount / 2500)) {
                    VollySaveBidDataMethod();
                    Toast.makeText(getApplicationContext(), "Thank You! Amount Credited in next 24-48 hours..", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "You Did not have sufficient Balance", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "You Did not have sufficient Coins", Toast.LENGTH_SHORT).show();

            }
        }
    }

//    public void VollyMethod() {
//
//        pd = new ProgressDialog(PaytmTransfer.this);
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
//        String url = "https://incrts.tk/cutnews/sends.php";
//        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //This code is executed if the server responds, whether or not the response contains data.
//                //The String 'response' contains the server's response.
//
//                pd.dismiss();
//
//                Toast.makeText(getApplicationContext(),"Thank You! Confirmation Sms Send to your mobile!", Toast.LENGTH_SHORT).show();
//                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();
//                // VollyMethodSaveUser();
//
//            }
//        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
//            @Override
//            public void onErrorResponse(VolleyError error) {
//             //   pd.dismiss();
//                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(intent);
//                finish();
//                //This code is executed if there is an error.
//               // Toast.makeText(PaytmTransfer.this,error.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            protected Map<String, String> getParams() {
//                Map<String, String> MyData = new HashMap<String, String>();
//                Msg="Your amount Rs."+PaytmAmount+" will transfer to paytm wallet ("+UserPaytmNumber+") in next 48-72 hours.Thank you for using Pokemoney app";
////Msg.replace(" ","%20")
//                //Your Selected player is+"+PlayerList.get(0)+","+
//                // PlayerList.get(1)+","+PlayerList.get(2)+".
//                MyData.put("to", UserPaytmNumber);
//                MyData.put("msg", Msg);//Add the data you'd like to send to the server.
//                return MyData;
//            }
//        };
//        MyRequestQueue.add(MyStringRequest);
//
//    }

    public void VollySaveBidDataMethod() {

        if(RedeemType.compareTo("PayPal_2")==0||RedeemType.compareTo("PayPal_2")==0) {
            WalletAmount = (WalletAmount) - Integer.parseInt(String.valueOf(Integer.parseInt(PaytmAmount) * 100000));
        }
        else
        {
            WalletAmount = (WalletAmount) - Integer.parseInt(String.valueOf(Integer.parseInt(PaytmAmount) * 2500));

        }
        Log.i("WalletAmount", String.valueOf(WalletAmount));
//        pd = new ProgressDialog(PaytmTransfer.this);
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://spingreedy.tk/SavePaytmTransferRecord.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.i("ResponceIsL",response.toString());
               // pd.dismiss();
                Toast.makeText(getApplicationContext(),"Thank You , Amount will credited in Next 24 Hours",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
               // editor.putInt("WalletAmount",WalletAmount).commit();
              //  VollyMethod();


            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
              //  pd.dismiss();
                //This code is executed if there is an error.
                Log.i("ErrorMsg","Error"+error.getMessage());
                Toast.makeText(PaytmTransfer.this, "Try Again..."+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                Log.i("InMap:",UserPaytmNumber+"---"+PaytmAmount+"----"+WalletAmount+"===="+RedeemType);
                MyData.put("usermobile", MobileNumber);
                MyData.put("paytmnumber", UserPaytmNumber);//Add the data you'd like to send to the server.
                MyData.put("balance", String.valueOf(WalletAmount));//Add the data you'd like to send to the server.
                MyData.put("type", "Self");//Add the data you'd like to send to the server.
                if(RedeemType.compareTo("Paytm_10")==0) {
                    MyData.put("amount", "10");
                }
                else if(RedeemType.compareTo("Paytm_20")==0)
                {
                    MyData.put("amount", "20");
                }
                else if(RedeemType.compareTo("Paytm_50")==0)
                {
                    MyData.put("amount", "50");
                }
                else if(RedeemType.compareTo("PayPal_2")==0)
                {
                    MyData.put("amount", "$2");
                }
                else if(RedeemType.compareTo("PauPal_5")==0)
                {
                    MyData.put("amount", "$5");
                }

                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

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

                // UserCoins.setText(balance[0]);
                WalletAmount= Integer.parseInt(balance[0]);
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
                Log.i("MobileIs",MobileNumber);
                MyData.put("mobile", MobileNumber.trim());
                //MyData.put("balance", String.valueOf(BalanceUpdate));
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

//    public void UpdateUserBalance() {
//
//        pd = new ProgressDialog(PaytmTransfer.this);
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
//        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
//        String url = "https://earnapp.tk/AppBackend/SavePaytmTransferRecord.php";
//        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //This code is executed if the server responds, whether or not the response contains data.
//                //The String 'response' contains the server's response.
//                Log.i("ResponceIsL",response.toString());
//                pd.dismiss();
//                WalletAmount=WalletAmount- Integer.parseInt(PaytmAmount);
//                editor.putInt("WalletAmount", (int) WalletAmount).commit();
//               // VollyMethod();
//
//
//            }
//        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pd.dismiss();
//                //This code is executed if there is an error.
//                Toast.makeText(PaytmTransfer.this, "Try Again...", Toast.LENGTH_SHORT).show();
//
//            }
//        }) {
//            protected Map<String, String> getParams() {
//                Map<String, String> MyData = new HashMap<String, String>();
//                Log.i("InMap:","Yes");
//                MyData.put("usermobile", MobileNumber);
//                MyData.put("amount", PaytmAmount);//Add the data you'd like to send to the server.
//                MyData.put("paytmnumber", UserPaytmNumber);//Add the data you'd like to send to the server.
//
//
//                return MyData;
//            }
//        };
//        MyRequestQueue.add(MyStringRequest);
//
//    }
}
