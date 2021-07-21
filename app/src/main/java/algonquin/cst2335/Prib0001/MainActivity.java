package algonquin.cst2335.Prib0001;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author jenya
 * @version 1.0
 * This page provides functionality for the weather app main page
 */
public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create Text Views, Edit Text and Compound Buttons.
        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText cityText = findViewById(R.id.cityTextField);


        forecastBtn.setOnClickListener(clk -> {

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Getting Forecast")
                    .setMessage("We're calling people in" + cityText + "to look outside their windows and tell us what's the weather like over there.")
                    .setView(new ProgressBar(MainActivity.this))
                    .show();

            Executor newThread = Executors.newSingleThreadExecutor();
//this thread will run on a different cpu
            newThread.execute(() -> {


                try { // try block on different cpu thread
                    String cityName = cityText.getText().toString();
                    // connecting to server URL

                    String serverURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")  //encoding the URL

                            + "&appid=7e943c97096a9784391a981c4d878b22&Units=Metric";

                    URL url = new URL(serverURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());








// this will convert to a string

                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));
                    //convert string to JSON object:

                    JSONObject theDocument = new JSONObject(text);
                    JSONObject coord = theDocument.getJSONObject("coord");
                   double lat = coord.getDouble("lat");
                   double lon = coord.getDouble("lon");

                    //weather array object retrieves weather values
                   JSONArray weatherArray = theDocument.getJSONArray("weather");
                   JSONObject obj0 = weatherArray.getJSONObject(0);
                   JSONObject main = theDocument.getJSONObject("main");
                   double currentTemp = main.getDouble("temp");
                   double min = main.getDouble("temp_min");
                   double max = main.getDouble("temp_max");
                    String humidity = main.getString("humidity");
                    String description = main.getString("description");
                    String iconName = obj0.getString("icon");



                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(in, "UTF-8");

                    while(xpp.next() != XmlPullParser.END_DOCUMENT) {
                        switch (xpp.getEventType()) {
                            case XmlPullParser.START_TAG:
                                if(xpp.getName().equals("temperature"))
                                {
                                    xpp.getAttributeValue(null,  "value");
                                    xpp.getAttributeValue(null, "min");
                                    xpp.getAttributeValue(null, "max");
                                }

                                else if(xpp.getName().equals("weather"))
                                {
                                    xpp.getAttributeValue(null,"value");
                                    xpp.getAttributeValue(null,"icon");
                                }

                                else if(xpp.getName().equals("humidity"))
                                {
                                    humidity = xpp.getAttributeValue(null, "value");
                                }
                                break;

                            case XmlPullParser.END_TAG:
                                break;

                            case XmlPullParser.TEXT:

                                break;
                        }
                    }


                    //Icon view
                    Bitmap image = null;
                    File file = new File(getFilesDir(), iconName + ".png");
                    if (file.exists()) {
                        image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + "png");
                    }
                    else {

                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 100) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());
                            image.compress(Bitmap.CompressFormat.PNG, 100, openFileOutput(iconName+ ".png", Activity.MODE_PRIVATE));

                            //ImageView iv = findViewById(R.id.icon);
                            //iv.setImageBitmap(image);
                        }


                        FileOutputStream fout = null;

                        try{
                            fout = openFileOutput(image + "png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fout );
                            fout.flush();
                            fout.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        }




                    Bitmap finalImage = image;
                    runOnUiThread( ( ) -> {
                        //Text View for Current Temp
                    TextView tv = findViewById(R.id.tempView);
                    tv.setText("The current temperature is" + currentTemp);
                    tv.setVisibility(View.VISIBLE);

                    // Text view for Min Temp
                    tv = findViewById(R.id.minView);
                    tv.setText("The min temperature is" + currentTemp);
                    tv.setVisibility(View.VISIBLE);

                    // Text view for Max Temp
                    tv = findViewById(R.id.maxView);
                    tv.setText("The max temperature is" + currentTemp);
                    tv.setVisibility(View.VISIBLE);

                    // Text view for Humidity
                    tv = findViewById(R.id.humidityView);
                    tv.setText("The current humidity is" + currentTemp);
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.description);
                    tv.setText(description);
                    tv.setVisibility(View.VISIBLE);

                    ImageView iv = findViewById(R.id.icon);
                    iv.setImageBitmap(finalImage);
                    iv.setVisibility(View.VISIBLE);
                    });

                dialog.hide();

                } catch (IOException | JSONException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            });
            //gui thread, connection not allowed


        });

        /**
         * This function checks for the correct password format
         *
         * @param password string to be checked.
         * @return returns true if password matches parameters returns false if complexity does not match parameters.
         */


    }

}





