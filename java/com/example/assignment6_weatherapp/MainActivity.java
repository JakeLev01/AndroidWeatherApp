package com.example.assignment6_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    public static JSONObject jsonObject = null;
    TextView currentTemp,currentHumidity,currentWindSpeed,currentPressure,currentTime;
    TextView nowHour,nowTemp,hourOneTime,hourOneTemp,hourTwoTime,hourTwoTemp,hourThreeTime,hourThreeTemp,hourFourTime,hourFourTemp,hourFiveTime,hourFiveTemp;
    TextView nowDay,nowTempHigh,nowTempLow, dayOneTime, dayOneHigh,dayOneLow, dayTwoTime, dayTwoHigh, dayTwoLow, dayThreeTime, dayThreeHigh, dayThreeLow,dayFourTime, dayFourHigh, dayFourLow, dayFiveTime, dayFiveHigh, dayFiveLow, daySixTime, daySixHigh, daySixLow, daySevenTime, daySevenHigh, daySevenLow;
    SimpleDateFormat day;
    SimpleDateFormat hour;
    SimpleDateFormat currentTimeFormat;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        day = new java.text.SimpleDateFormat("EEE");
        hour = new java.text.SimpleDateFormat("h a");
        currentTimeFormat = new java.text.SimpleDateFormat("EEE, MMM d, yyyy");
        day.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        hour.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        currentTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT-5"));

        String urlLink = "https://api.openweathermap.org/data/2.5/onecall?lat=30.267153&lon=-97.743057&units=imperial&appid=2792957f89377b17cd33fc9087f940e5";
        Thread networkConnect = new Thread(new NetworkThread());
        networkConnect.start();

        while(jsonObject == null){
            continue;
        }
        networkConnect.interrupt();
        setCurrentData();
    }

    private void setCurrentData(){
        setContentView(R.layout.activity_main);
        currentTemp = findViewById(R.id.currentTemp);
        currentHumidity = findViewById(R.id.currentHumidity);
        currentWindSpeed = findViewById(R.id.currentWindSpeed);
        currentPressure = findViewById(R.id.currentPressure);
        currentTime = findViewById(R.id.currentTime);

        try{
            JSONObject jsonCurrent = jsonObject.getJSONObject("current");
            String temperature = Integer.toString(jsonCurrent.getInt("temp")) ;
            String tempMessage = "Temperature: " + temperature + "℉";

            String humidity = Integer.toString(jsonCurrent.getInt("humidity")) ;
            String humidityMessage = "Humidity: " + humidity + "%";

            String windSpeed = Integer.toString(jsonCurrent.getInt("wind_speed")) ;
            String windSpeedMessage = "Wind Speed: " + windSpeed + " mph";

            String pressure = Integer.toString(jsonCurrent.getInt("pressure")) ;
            String pressureMessage = "Pressure: " + pressure + " hPa";


            Date date = new Date(jsonCurrent.getLong("dt")*1000);
            String dateTime = currentTimeFormat.format(date);

            currentTemp.setText(tempMessage);
            currentHumidity.setText(humidityMessage);
            currentWindSpeed.setText(windSpeedMessage);
            currentPressure.setText(pressureMessage);
            currentTime.setText(dateTime);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void dailyButtonPress(View view){
        setContentView(R.layout.daily);
        nowDay = findViewById(R.id.zeroDayTime);
        nowTempHigh = findViewById(R.id.zeroDayHigh);
        nowTempLow = findViewById(R.id.zeroDayLow);
        dayOneTime = findViewById(R.id.oneDayTime);
        dayOneHigh = findViewById(R.id.oneDayHigh);
        dayOneLow = findViewById(R.id.oneDayLow);
        dayTwoTime = findViewById(R.id.twoDayTime);
        dayTwoHigh = findViewById(R.id.twoDayHigh);
        dayTwoLow = findViewById(R.id.twoDayLow);
        dayThreeTime = findViewById(R.id.threeDayTime);
        dayThreeHigh = findViewById(R.id.threeDayHigh);
        dayThreeLow = findViewById(R.id.threeDayLow);
        dayFourTime = findViewById(R.id.fourDayTime);
        dayFourHigh = findViewById(R.id.fourDayHigh);
        dayFourLow  = findViewById(R.id.fourDayLow);
        dayFiveTime = findViewById(R.id.fiveDayTime);
        dayFiveHigh = findViewById(R.id.fiveDayHigh);
        dayFiveLow = findViewById(R.id.fiveDayLow);
        daySixTime = findViewById(R.id.sixDayTime);
        daySixHigh = findViewById(R.id.sixDayHigh);
        daySixLow = findViewById(R.id.sixDayLow);
        daySevenTime =findViewById(R.id.sevenDayTime);
        daySevenHigh = findViewById(R.id.sevenDayHigh);
        daySevenLow = findViewById(R.id.sevenDayLow);
        try{
            JSONArray dailyJson = jsonObject.getJSONArray("daily");

            JSONObject dayZero = dailyJson.getJSONObject(0);
            JSONObject dayZeroTemp = dayZero.getJSONObject("temp");
            Date dayZeroDate = new Date(dayZero.getLong("dt")*1000);
            String dayTime = day.format(dayZeroDate);
            //nowDay.setText(dayTime);
            String dayZeroHighTemp = Integer.toString(dayZeroTemp.getInt("max"));
            String dayZeroLowTemp = Integer.toString(dayZeroTemp.getInt("min"));
            dayZeroHighTemp = "High: " + dayZeroHighTemp + "℉";
            dayZeroLowTemp = "Low: " + dayZeroLowTemp + "℉";
            nowTempHigh.setText(dayZeroHighTemp);
            nowTempLow.setText(dayZeroLowTemp);

            JSONObject dayOne = dailyJson.getJSONObject(1);
            JSONObject dayOneTemp = dayOne.getJSONObject("temp");
            Date dayOneDate = new Date(dayOne.getLong("dt")*1000);
            String dayTimeOne = day.format(dayOneDate);
            dayOneTime.setText(dayTimeOne);
            String dayOneHighTemp = Integer.toString(dayOneTemp.getInt("max"));
            String dayOneLowTemp = Integer.toString(dayOneTemp.getInt("min"));
            dayOneHighTemp = "High: " + dayOneHighTemp + "℉";
            dayOneLowTemp = "Low: " + dayOneLowTemp + "℉";
            dayOneHigh.setText(dayOneHighTemp);
            dayOneLow.setText(dayOneLowTemp);

            JSONObject dayTwo = dailyJson.getJSONObject(2);
            JSONObject dayTwoTemp = dayTwo.getJSONObject("temp");
            Date dayTwoDate = new Date(dayTwo.getLong("dt")*1000);
            String dayTimeTwo = day.format(dayTwoDate);
            dayTwoTime.setText(dayTimeTwo);
            String dayTwoHighTemp = Integer.toString(dayTwoTemp.getInt("max"));
            String dayTwoLowTemp = Integer.toString(dayTwoTemp.getInt("min"));
            dayTwoHighTemp = "High: " + dayTwoHighTemp + "℉";
            dayTwoLowTemp = "Low: " + dayTwoLowTemp + "℉";
            dayTwoHigh.setText(dayTwoHighTemp);
            dayTwoLow.setText(dayTwoLowTemp);

            JSONObject dayThree = dailyJson.getJSONObject(3);
            JSONObject dayThreeTemp = dayThree.getJSONObject("temp");
            Date dayThreeDate = new Date(dayThree.getLong("dt")*1000);
            String dayTimeThree = day.format(dayThreeDate);
            dayThreeTime.setText(dayTimeThree);
            String dayThreeHighTemp = Integer.toString(dayThreeTemp.getInt("max"));
            String dayThreeLowTemp = Integer.toString(dayThreeTemp.getInt("min"));
            dayThreeHighTemp = "High: " + dayThreeHighTemp + "℉";
            dayThreeLowTemp = "Low: " + dayThreeLowTemp + "℉";
            dayThreeHigh.setText(dayThreeHighTemp);
            dayThreeLow.setText(dayThreeLowTemp);

            JSONObject dayFour = dailyJson.getJSONObject(4);
            JSONObject dayFourTemp = dayFour.getJSONObject("temp");
            Date dayFourDate = new Date(dayFour.getLong("dt")*1000);
            String dayTimeFour = day.format(dayFourDate);
            dayFourTime.setText(dayTimeFour);
            String dayFourHighTemp = Integer.toString(dayFourTemp.getInt("max"));
            String dayFourLowTemp = Integer.toString(dayFourTemp.getInt("min"));
            dayFourHighTemp = "High: " + dayFourHighTemp + "℉";
            dayFourLowTemp = "Low: " + dayFourLowTemp + "℉";
            dayFourHigh.setText(dayFourHighTemp);
            dayFourLow.setText(dayFourLowTemp);

            JSONObject dayFive = dailyJson.getJSONObject(5);
            JSONObject dayFiveTemp = dayFive.getJSONObject("temp");
            Date dayFiveDate = new Date(dayFive.getLong("dt")*1000);
            String dayTimeFive = day.format(dayFiveDate);
            dayFiveTime.setText(dayTimeFive);
            String dayFiveHighTemp = Integer.toString(dayFiveTemp.getInt("max"));
            String dayFiveLowTemp = Integer.toString(dayFiveTemp.getInt("min"));
            dayFiveHighTemp = "High: " + dayFiveHighTemp + "℉";
            dayFiveLowTemp = "Low: " + dayFiveLowTemp + "℉";
            dayFiveHigh.setText(dayFiveHighTemp);
            dayFiveLow.setText(dayFiveLowTemp);

            JSONObject daySix = dailyJson.getJSONObject(6);
            JSONObject daySixTemp = daySix.getJSONObject("temp");
            Date daySixDate = new Date(daySix.getLong("dt")*1000);
            String dayTimeSix = day.format(daySixDate);
            daySixTime.setText(dayTimeSix);
            String daySixHighTemp = Integer.toString(daySixTemp.getInt("max"));
            String daySixLowTemp = Integer.toString(daySixTemp.getInt("min"));
            daySixHighTemp = "High: " + daySixHighTemp + "℉";
            daySixLowTemp = "Low: " + daySixLowTemp + "℉";
            daySixHigh.setText(daySixHighTemp);
            daySixLow.setText(daySixLowTemp);

            JSONObject daySeven = dailyJson.getJSONObject(7);
            JSONObject daySevenTemp = daySeven.getJSONObject("temp");
            Date daySevenDate = new Date(daySeven.getLong("dt")*1000);
            String dayTimeSeven = day.format(daySevenDate);
            daySevenTime.setText(dayTimeSeven);
            String daySevenHighTemp = Integer.toString(daySevenTemp.getInt("max"));
            String daySevenLowTemp = Integer.toString(daySevenTemp.getInt("min"));
            daySevenHighTemp = "High: " + daySevenHighTemp + "℉";
            daySevenLowTemp = "Low: " + daySevenLowTemp + "℉";
            daySevenHigh.setText(daySevenHighTemp);
            daySevenLow.setText(daySevenLowTemp);





        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void hourlyButtonPress(View view){
        setContentView(R.layout.hourly);
        nowHour = findViewById(R.id.zeroHourTime);
        nowTemp = findViewById(R.id.zeroHourTemp);
        hourOneTime = findViewById(R.id.firstHourTime);
        hourOneTemp = findViewById(R.id.firstHourTemp);
        hourTwoTime = findViewById(R.id.secondHourTime);
        hourTwoTemp = findViewById(R.id.secondHourTemp);
        hourThreeTime = findViewById(R.id.thirdHourTime);
        hourThreeTemp = findViewById(R.id.thirdHourTemp);
        hourFourTime = findViewById(R.id.fourthHourTime);
        hourFourTemp = findViewById(R.id.fourthHourTemp);
        hourFiveTime = findViewById(R.id.fifthHourTime);
        hourFiveTemp = findViewById(R.id.fifthHourTemp);

        try {
            JSONArray hourlyJson = jsonObject.getJSONArray("hourly");

            JSONObject hourZero = hourlyJson.getJSONObject(0);
//            Date hourZeroDate = new Date(hourZero.getLong("dt")*1000);
//            String nowTime = hour.format(hourZeroDate);
//            nowHour.setText(nowTime);
            String tempZero = Integer.toString(hourZero.getInt("temp"));
            tempZero = tempZero + "℉";
            nowTemp.setText(tempZero);

            JSONObject hourOne = hourlyJson.getJSONObject(1);
            Date hourOneDate = new Date(hourOne.getLong("dt")*1000);
            String timeOne = hour.format(hourOneDate);
            hourOneTime.setText(timeOne);
            String tempOne = Integer.toString(hourOne.getInt("temp"));
            tempOne = tempOne + "℉";
            hourOneTemp.setText(tempOne);

            JSONObject hourTwo = hourlyJson.getJSONObject(2);
            Date hourTwoDate = new Date(hourTwo.getLong("dt")*1000);
            String timeTwo = hour.format(hourTwoDate);
            hourTwoTime.setText(timeTwo);
            String tempTwo = Integer.toString(hourTwo.getInt("temp"));
            tempTwo = tempTwo + "℉";
            hourTwoTemp.setText(tempTwo);

            JSONObject hourThree = hourlyJson.getJSONObject(3);
            Date hourThreeDate = new Date(hourThree.getLong("dt")*1000);
            String timeThree = hour.format(hourThreeDate);
            hourThreeTime.setText(timeThree);
            String tempThree = Integer.toString(hourThree.getInt("temp"));
            tempThree = tempThree + "℉";
            hourThreeTemp.setText(tempThree);

            JSONObject hourFour = hourlyJson.getJSONObject(4);
            Date hourFourDate = new Date(hourFour.getLong("dt")*1000);
            String timeFour = hour.format(hourFourDate);
            hourFourTime.setText(timeFour);
            String tempFour = Integer.toString(hourFour.getInt("temp"));
            tempFour = tempFour + "℉";
            hourFourTemp.setText(tempFour);

            JSONObject hourFive = hourlyJson.getJSONObject(5);
            Date hourFiveDate = new Date(hourFive.getLong("dt")*1000);
            String timeFive = hour.format(hourFiveDate);
            hourFiveTime.setText(timeFive);
            String tempFive = Integer.toString(hourFive.getInt("temp"));
            tempFive = tempFive + "℉";
            hourFiveTemp.setText(tempFive);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void backButtonPress(View view){
        setCurrentData();
    }


}