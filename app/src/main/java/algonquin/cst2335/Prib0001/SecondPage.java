package algonquin.cst2335.Prib0001;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;

public class SecondPage extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = findViewById(R.id.profileImage);
        if(requestCode == 3456)
        {
            if(resultCode == RESULT_OK)
            {
                Bitmap thumbnail = data.getParcelableExtra("data");
                FileOutputStream fOut = null;
                try{
                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                profileImage.setImageBitmap(thumbnail);
            }
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE );
        String numberSaved = prefs.getString("Number", "");
        TextView numberField = findViewById(R.id.phoneNumber);

        numberField.setText(numberSaved);


        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView text = findViewById(R.id.textView);
        text.setText("Welcome back" + emailAddress);


        Button callNumber = findViewById(R.id.callNumber);
        Button changePictureButton = findViewById(R.id.changePictureButton);

        ImageView profileImage = findViewById(R.id.profileImage);
        String path = getFilesDir().getPath() + "/Picture.png";
        File file = new File(path);
        // set profile picture if file exists

        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(path);
            profileImage.setImageBitmap(theImage);
        }
        callNumber.setOnClickListener(e -> {
            TextView phoneNumber = findViewById(R.id.phoneNumber);
            String phoneNumberDigit = phoneNumber.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumberDigit));
            startActivity(call);
        });
        changePictureButton.setOnClickListener(e ->{
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 1143);
                }
                );



    }

    }
