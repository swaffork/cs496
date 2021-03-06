// With much help from http://www.survivingwithandroid.com/2013/05/build-weather-app-json-http-android.html
package com.example.myfirstapp;

import com.example.myfirstapp.Location;
import com.example.myfirstapp.Weather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import static com.example.myfirstapp.R.id.condDescr;

public class MainActivity extends AppCompatActivity {
    public final static String LOCATION = "com.example.myfirstapp.LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /* Called when he the user clicks the Send button
    * Note this method MUST be public, have a void return value, and have view
    * as the only parameter. */
    public void getWeather(View view) {
        /* Intent is object that provides runtime binding between separate
         * components (like two activities). Represents an app's "intent to do
         * something."
         *  this: Context parameter (Activity is subclass of Context)
         *  .class: the class of app component where the system will deliver the Intent*/
        Intent intent = new Intent(this, SearchLocationActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String location = editText.getText().toString();
        /* adds the EditText's value to the intent; we define the key as
         * EXTRA_MESSAGE to retrieve the text value. */
        intent.putExtra(LOCATION, location);
        startActivity(intent);
    }
}
