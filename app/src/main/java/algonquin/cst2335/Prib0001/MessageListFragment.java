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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment {
    ArrayList<ChatMessage> messages = new ArrayList<>();

    RecyclerView chatList;
 ChatMessage.MyRowViews.MyChatAdapter adt; //adt:null
    SQLiteDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);

        //views and buttons
        Button send = chatLayout.findViewById(R.id.sendbutton);
        Button receive = chatLayout.findViewById(R.id.receivebutton);
        EditText messageText = chatLayout.findViewById(R.id.editTextTextPersonName);
        RecyclerView chatList = chatLayout.findViewById(R.id.myrecycler);


        //adapters

        adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));

        MyOpenHelper opener = new MyOpenHelper(getContext());
         db = opener.getWritableDatabase();


        send.setOnClickListener(clk -> {
            MyChatAdapter.ChatMessage thisMessage = new ChatMessage(messageText.getText().toString(), 1, new Date());
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() - 1);
            messageText.setText("");

        });


        receive.setOnClickListener(clk -> {
            ChatMessage thisMessage = new ChatMessage(messageText.getText().toString(), 2, new Date());
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() - 1);
            messageText.setText("");
        });

        return chatLayout;
    }
    // not sure what the issue is with this class but it isn't getting properly referenced.
    public class ChatMessage {

        public int getMessage;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        String currentDateandTime = sdf.format(new Date());
        String message;
        int sendOrReceive;
        Date timeSent;

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

        public Date getTimeSent() {
            return timeSent;
        }


    private class MyRowViews extends RecyclerView.ViewHolder {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextView messageText;
        TextView timeText;
        int position = -1;

        public MyRowViews(View itemView) {
            super(itemView);

            itemView.setOnClickListener(click -> {
                ChatRoom parentActivity = (ChatRoom) getContext();
                int position = getAbsoluteAdapterPosition();
                parentActivity.userClickedMessage(messages.get(position), position);
            });
            public void setPosition ( int p){
                position = p;
            }
        }



        private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews> {


            @Override
            public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType) {
                int layoutID;
                if (viewType == 1)
                    layoutID = R.layout.sent_message;
                else
                    layoutID = R.layout.receive_message;
                return new MyRowViews(getLayoutInflater().inflate(R.layout.sent_message, parent, false));
            }

            @Override
            public void onBindViewHolder(MyRowViews holder, int position) {
                holder.messageText.setText(messages.get(position).getMessage);
                holder.timeText.setText((CharSequence) messages.get(position).getTimeSent());
                holder.setPosition(position);
            }

            @Override
            public int getItemCount() {
                return 0;
            }



            }
        }
    }
}








