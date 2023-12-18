package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView a, b, c, d, e, f, g, h, i, j;

    int[] img = {
            R.drawable.humidity, R.drawable.thermometer, R.drawable.wind,};
    EditText cityid;
    RecyclerView recycleviewid;
    Button getinfo;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    //private final String url = "https://disease.sh/v3/covid-19/all";
    private final String appid = "1a844b383d1efd4353c8ee2463e63dcd";
    DecimalFormat df = new DecimalFormat("#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityid = findViewById(R.id.citynameid);
        getinfo = findViewById(R.id.infogetbtn);

        a = findViewById(R.id.weatherdegree);
        b = findViewById(R.id.situationfeelslike);
        c = findViewById(R.id.windvalue);
        d = findViewById(R.id.winddirection);
        e = findViewById(R.id.humidityvalue);
        f = findViewById(R.id.pressurevalue);
        g = findViewById(R.id.visibilityvalue);


        getinfo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String tempurl = "";
        String city = cityid.getText().toString().trim();
        //String country = countryid.getText().toString().trim();

        if (city.equals("")) {
            cityid.setError("City field cannot be empty");
            cityid.requestFocus();
        } else {
            /*if (!country.equals("")) {
                tempurl = url + "?q=" + city + "," + country + "&appid=" + appid;

            } else {*/
            tempurl = url + "?q=" + city + "&appid=" + appid;

        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, tempurl, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response); // Convert the response String to JSONObject
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    JSONArray weatherArray = jsonObject.getJSONArray("weather");
                    int visibilityObject = jsonObject.getInt("visibility");
                    JSONObject windObject = jsonObject.getJSONObject("wind");


                    JSONObject weatherObject = null;
                    if (weatherArray.length() > 0) {
                        weatherObject = weatherArray.getJSONObject(0);

                        String weatherDescription = weatherObject.getString("main");
                        String weatherIcon = weatherObject.getString("icon");
                    }


                    // Extract temperature related data
                    double temp = mainObject.getDouble("temp");
                    double feelsLike = mainObject.getDouble("feels_like");
                    int pressure = mainObject.getInt("pressure");
                    int humidity = mainObject.getInt("humidity");

                    // Extracted weather related data
                    String weatherDescription = weatherObject.getString("main");
                    String weatherIcon = weatherObject.getString("icon");

                    //Extracted wind related data
                    double windSpeed = 3.9; windObject.getDouble("speed");
                    Log.d("tanjil", String.valueOf(windSpeed));

                    // Extract wind degree from the "wind" object
                    int windDegree = windObject.getInt("deg");

                    // Determine wind direction
                    String windDirection = getWindDirection(windDegree);









                    //Temp Math
                    double xtemp = temp - 273.15;
                    double fl = feelsLike - 273.15;

                    //visibility math
                    double visibilityObject1 = (visibilityObject / 1000.0); // Using 1000.0 to ensure floating-point division


                    //setText the data
                    a.setText(df.format(xtemp) + "°c");
                    b.setText(weatherDescription);
                    c.setText(String.valueOf(df.format(windSpeed)) + " Km/h");
                    d.setText(windDirection);
                    e.setText(df.format(humidity) + "%");
                    f.setText(df.format(pressure) + " mb");
                    g.setText(String.valueOf(visibilityObject1) + " km");





                        /*recycleviewid.setText("Tempreture: " + df.format(xtemp) + " °C" + "\n" +
                                    "Feels Like: " + df.format(fl) + " °C" + "\n" +
                                    "Min Temperature: " + df.format(mntemp) + " °C" + "\n" +
                                    "Max Temperature: " + df.format(mxtemp) + " °C" + "\n" +
                                    "Pressure: " + df.format(pressure) + " mb" + "\n" +
                                    "Humidity: " + df.format(humidity) + " %" + "\n" +
                                    "Weather Description: " + (weatherDescription) + ""

                        );*/


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    // Method to determine wind direction based on degrees
    private static String getWindDirection(int degree) {
        if (degree >= 337.5 || degree < 22.5) {
            return "North";
        } else if (degree >= 22.5 && degree < 67.5) {
            return "Northeast";
        } else if (degree >= 67.5 && degree < 112.5) {
            return "East";
        } else if (degree >= 112.5 && degree < 157.5) {
            return "Southeast";
        } else if (degree >= 157.5 && degree < 202.5) {
            return "South";
        } else if (degree >= 202.5 && degree < 247.5) {
            return "Southwest";
        } else if (degree >= 247.5 && degree < 292.5) {
            return "West";
        } else {
            return "Northwest";
        }
    }










}
