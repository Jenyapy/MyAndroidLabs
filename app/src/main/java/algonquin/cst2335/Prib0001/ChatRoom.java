package algonquin.cst2335.Prib0001;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.android.material.snackbar.Snackbar;




public class ChatRoom extends AppCompatActivity {


    RecyclerView chatList;

    MyChatAdapter adt;
    ArrayList<MyChatAdapter.ChatMessage> messages = new ArrayList<>();
    SQLiteDatabase db;
    private Button send;
    private Button receive;
    String message;
    int sendOrReceive;
    Date timeSent;
    public int getMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlayout);

        EditText editText = findViewById(R.id.editTextTextPersonName);
        MyOpenHelper opener = new MyOpenHelper(this);
        SQLiteDatabase db = opener.getWritableDatabase();
        Cursor results = db.rawQuery("SELECT * FROM " + MyOpenHelper.TABLE_NAME + ";", null);


        send = findViewById(R.id.sendbutton);
        receive = findViewById(R.id.receivebutton);
        int colId = results.getColumnIndex("_id");
        int colMessage = results.getColumnIndex(MyOpenHelper.col_message);
        int colSend = results.getColumnIndex(MyOpenHelper.col_send_receive);
        int colTime = results.getColumnIndex(MyOpenHelper.col_time_sent);

        editText.setText("");
        ArrayList<MyChatAdapter.ChatMessage> messages = new ArrayList<>();
        chatList = findViewById(R.id.myrecycler);
        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));
        send.setOnClickListener(click ->{
           ChatMessage cm = new ChatMessage(editText.getText().toString(),1,new Date());
            ContentValues newRow = new ContentValues();
            newRow.put(MyOpenHelper.col_message, message);
            newRow.put(MyOpenHelper.col_send_receive, sendOrReceive);
            newRow.put(MyOpenHelper.col_time_sent, String.valueOf(timeSent));
           long id =  db.insert(MyOpenHelper.TABLE_NAME,MyOpenHelper.col_message,newRow);


adt.notifyItemInserted(messages.size()-1);
        });




    }





    private class MyRowViews extends RecyclerView.ViewHolder{

        AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
        TextView messageText;
        TextView timeText;
        int position = -1;
        public MyRowViews( View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                messages.remove(position);
                builder.setMessage("Do you want to delete the message:" + messageText.getText());
                builder.setTitle("Question:");
                builder.setNegativeButton("Yes", (dialog, cl) -> {
                });
                builder.setNegativeButton("No", (dialog, cl) -> {
                });
                position = getAbsoluteAdapterPosition();
               MyChatAdapter.ChatMessage removedMessage = messages.get(position);
                messages.remove(position);
                adt.notifyItemRemoved(position);
                Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                        .setAction("undo", clk -> {
                            messages.add(position, removedMessage);
                            adt.notifyItemInserted(position);});


      //  db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message,newRow);

            });

            builder.create().show();

            messageText = itemView.findViewById(R.id.receivemessage);
            timeText = itemView.findViewById(R.id.time);
        }
        public void setPosition(int p) {position = p;}
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews>{



        @Override
        public ChatRoom.MyRowViews onCreateViewHolder( ViewGroup parent, int viewType) {
           LayoutInflater inflater = getLayoutInflater();
           View loadedRow = inflater.inflate(
                   viewType == 1? R.layout.sent_message: R.layout.receive_message, parent, false);

            int layoutID;
            if(viewType ==1)
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;

            ChatRoom.MyRowViews initRow = new ChatRoom.MyRowViews(loadedRow);
            return initRow;
        }

        @Override
        public void onBindViewHolder( MyRowViews holder, int position) {
            holder.messageText.setText(messages.get(position).getMessage);
            holder.timeText.setText((CharSequence) messages.get(position).getTimeSent());
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            return 0;
        }


        private class ChatMessage
        {
            String message;
            int sendOrReceive;
            Date timeSent;
            public int getMessage;
            long id;
            String currentDateandTime = sdf.format(new Date());

    public void setid(long l) {id = l;}
    public long getId() {return id;}






            ContentValues newRow = new ContentValues();
            public ChatMessage(String message, int sendOrReceive, Date timeSent) {
                this.message = message;
                this.sendOrReceive = sendOrReceive;
                this.timeSent = timeSent;

            }

            public String getMessage() {

                return message;
            }

            public int getSendOrReceive() {
                return sendOrReceive;
            }

            public String getTimeSent() {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                currentDateandTime = sdf.format(timeSent);
                return currentDateandTime;

                }
            }
        }
    }

