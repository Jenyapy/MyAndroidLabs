package algonquin.cst2335.Prib0001;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

public class ChatRoom extends AppCompatActivity {
    RecyclerView chatList;
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myRecycler);
        chatList.setAdapter(new ChatAdapter() );


    }


   private class ChatAdapter extends RecyclerView.Adapter{



       @Override
       public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
           return null;
       }

       @Override
       public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

       }

       @Override
       public int getItemCount() {
           return 0;
       }
   }





   private class ChatMessage
    {
    String message;
    int sendOrReceive;
    Date timeSent;



    }
}
