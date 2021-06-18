package algonquin.cst2335.Prib0001;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity {
    RecyclerView chatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<ChatMessage> messages = new ArrayList<>();
        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myRecycler);
        MyChatAdapter adt = new MyChatAdapter();
        chatList.setAdapter(new ChatAdapter() );
chatList.setAdapter(adt);
    chatList.setLayoutManager(new LinearLayoutManager(this));




    }

    private class MyRowViews extends RecyclerView.ViewHolder{
// class will hold widgets on row

        TextView messageText;
        TextView timeText;
        public MyRowViews( View itemView) {
            super(itemView);

            itemView.setOnClickListener(click ->{
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setTitle("Question:");
                builder.setPositiveButton("No", (dialog, cl) -> { });
                builder.setNegativeButton("Yes", (dialog, cl) -> { });
                message.remove(position);
                adt.notifyItemRemoved(position);


                messages.remove(position);
                adt.notifyItemRemoved(setPosition);


            });
            .create().show();
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
        public void setPosition(int p) {position = p;}
    }


   private class ChatAdapter extends RecyclerView.Adapter{



       @Override
       public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

           LayoutInflater inflater = getLayoutInflater();
           int layoutID;
           if(ViewType == 1)
               layoutID = R.layout.sent_message;
           else
               layoutID = R.layout.receive_message;
           View loadedRow = inflater.inflate(layoutID, parent,false);

           MyRowViews initRow = new MyRowViews(loadedRow);

           return initRow;
       }

       @Override
       public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           MyRowViews thisRowLayout = (MyRowViews) holder;
            ((MyRowViews) holder).messageText.setText(messages.get(position).getMessage());
            ((MyRowViews) holder).timeText.setText(messages.get(position).getTimeSent());
            ((MyRowViews) holder).setPosition(position);


       }

       @Override
       public int getItemCount() {
           return 10;// how many items to show
       }
   }





   private class ChatMessage
    {
    String message;
    int sendOrReceive;
    Date timeSent;


        public ChatMessage(String message, int sendOrReceive, Date timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

            String currentDateandTime = sdf.format(new Date());


        }
    }
}
