package algonquin.cst2335.Prib0001;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.android.material.snackbar.Snackbar;




public class ChatRoom extends AppCompatActivity {
    boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        isTablet = findViewById(R.id.detailsRoom) != null; //isTablet: false

        MessageListFragment chatFragment = new MessageListFragment();

        chatFragment = new MessageListFragment();
        FragmentManager fMgr = getSupportFragmentManager();
        FragmentTransaction tx = fMgr.beginTransaction();
        tx.add(R.id.fragmentRoom, chatFragment);
        tx.commit();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom, new MessageListFragment()).commit();
    }


    public void userClickedMessage(MessageListFragment.ChatMessage chatMessage, int position) {
        MessageDetailsFragment mdFragment = new MessageDetailsFragment(chatMessage, position);

        if (isTablet) {
getSupportFragmentManager().beginTransaction().replace(R.id.detailsRoom, mdFragment).commit();
        } else {
getSupportFragmentManager().beginTransaction().add(R.id.fragmentRoom, mdFragment).commit();
        }
    }

    public void notifyMessageDeleted(MessageListFragment.ChatMessage chosenMessage, int chosenPosition) {
AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to delete this message: " + chosenMessage.getMessage())
                .setTitle("Danger")
                .setNegativeButton("Cancel", (dialog, c) -> { })
                .setPositiveButton("Delete", (dialog,cl) -> {
                    MessageListFragment.ChatMessage removedMessage = messages.get(chosenPosition);
                    messages.remove(chosenPosition);
                    adt.notifyItemRemoved(chosenPosition);
                    Snackbar.make(send, "You deleted message #" + chosenPosition, Snackbar.LENGTH_SHORT)
                            .setAction("UNDO", clk ->{
                                messages.add(chosenPosition, removedMessage);
                                adt.notifyItemInserted(chosenPosition);

                            }).show();
                })
                .create().show();
    }
}


