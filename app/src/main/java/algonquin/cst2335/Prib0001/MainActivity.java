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

/**
 * @author jenya
 * @version 1.0
 * This page provides functionality for the avtivity_main.xml
 */
public class MainActivity extends AppCompatActivity {
    /**
     * this holds the texts at centre of screen
     */
    private TextView tv = null;
    /**
     * this holds the edit text below the text view
     */
    private EditText et = null;
    /**
     * this holds the login button below the edit text
     */
    private Button btn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Create Text Views, Edit Text and Compound Buttons.
        TextView tv = findViewById(R.id.logintextview);
        EditText et = findViewById(R.id.editText);

        Button btn = findViewById(R.id.button);


        btn.setOnClickListener(clk -> {
            String password = et.getText().toString();

            checkPasswordComplexity(password);
        });
    }

    /**
     * This function checks for the correct password format
     *
     * @param password string to be checked.
     * @return returns true if password matches parameters returns false if complexity does not match parameters.
     */


    private boolean checkPasswordComplexity(String password) {

        boolean foundUpperCase;
        boolean foundLowerCase;
        boolean foundNumber;
        boolean foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;


        if (!foundUpperCase) {
            Toast.makeText(MainActivity.this, "password missing upper case character.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(MainActivity.this, "password missing lower case character.", Toast.LENGTH_LONG).show();
            return false;

        } else if (!foundNumber) {
        } else if (!foundSpecial) {
        } else
            return true;
        return true;


    }

    /**
     *
     * @param c checks for special characters.
     * @return returns true if special characters are found and false if not found.
     */

        boolean isSpecialCharacter ( char c)

        {
            switch(c)
            {
                case '#':
                case '?':
                case '*':
                case '!':
                case '@':
                case '$':
                case '%':
                case'^':
                    return true;
                default:
                    return false;
            }

        }

}



