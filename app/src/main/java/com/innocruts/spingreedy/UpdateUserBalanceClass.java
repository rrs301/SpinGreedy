package com.innocruts.spingreedy;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateUserBalanceClass {

    Context context;
    public void UpdateUserBalance(final String TaskAmount, final String Task,final String UserMobileNumber) {

        final String currentDayIs = new java.text.SimpleDateFormat("yyyy/MM/dd").format(new Date());
//        pd = new ProgressDialog(getApplicationContext());
//        pd.setMessage("Sending Confirmation....");
//        pd.show();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        String url = "https://spingreedy.tk/UpdateUserBalance.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                //  pd.dismiss();
                Log.i("ResponceIsL", response.toString());
                Toast.makeText(context, "Congratulation!!! Amount Credited!...", Toast.LENGTH_LONG).show();


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
