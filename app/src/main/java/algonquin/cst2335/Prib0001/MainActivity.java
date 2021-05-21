package algonquin.cst2335.Prib0001;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create Text Views, Edit Text and Compound Buttons.
        TextView myText = findViewById(R.id.textview);
        EditText myedit = findViewById(R.id.myedittext);
        Button btn = findViewById(R.id.mybutton);
        Switch mySwitch = findViewById(R.id.myswitch);
        RadioButton myRadioButton = findViewById(R.id.myradiobutton);
        CheckBox myCheckBox = findViewById(R.id.mycheckbox);

        //Image Views and image button instances
        ImageView myimage = findViewById(R.id.logo_algonquin);
        ImageButton myImageButton = findViewById(R.id.myimagebutton);

        // On click listener for button clicks
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        //On click will invoke the getText() + setText() methods to state your edit text has changed.
                String editString = myedit.getText().toString();
                myText.setText("Your edit text has:" + editString);
            }
        });


// Toast on button click methods
        myRadioButton.setOnCheckedChangeListener( (RadioButton, isChecked) -> { Toast.makeText(getApplicationContext(), "You clicked on the radio button and it is now:" + isChecked, Toast.LENGTH_SHORT).show();  } );
        myCheckBox.setOnCheckedChangeListener( (CheckBox, isChecked) -> { Toast.makeText(getApplicationContext(), "You clicked on the check box and it is now:" + isChecked, Toast.LENGTH_LONG).show();  } );
        mySwitch.setOnCheckedChangeListener( (Switch, isChecked) -> { Toast.makeText(getApplicationContext(), "You clicked on the switch and it is now:" + isChecked, Toast.LENGTH_SHORT).show();  } );


        myImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"The width=" + myImageButton.getWidth() + " and height=" + myImageButton.getHeight(), Toast.LENGTH_LONG).show();
            }
        }); {

        }

// Default Toast
Toast.makeText(getApplicationContext(), "Text", Toast.LENGTH_LONG).show();








    }
}