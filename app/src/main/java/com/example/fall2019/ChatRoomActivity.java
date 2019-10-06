package com.example.fall2019;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;



public class ChatRoomActivity extends AppCompatActivity {

    BaseAdapter myAdapter;
    ArrayList<Message> objects = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        //You only need 2 lines in onCreate to actually display data:
        ListView theList = findViewById(R.id.theList);
        theList.setAdapter( myAdapter = new MyListAdapter() );
        theList.setOnItemClickListener( ( lv, vw, pos, id) ->{

            Toast.makeText( ChatRoomActivity.this,
                    "You clicked on:" + pos, Toast.LENGTH_SHORT).show();

        } );



        EditText textBox =  findViewById(R.id.editTextChat);





        //String sendMessage = findViewById(R.id.sendText).toString();
        //String receivedMessage = findViewById(R.id.receivedText).toString();


        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener( clik ->
        {
            EditText message =  findViewById(R.id.editTextChat);
            String mess = (String) message.getText().toString();
            textBox.setText("");

            objects.add(new Message(mess, true));
            myAdapter.notifyDataSetChanged(); //update yourself
        });

        Button receivedButton = findViewById(R.id.receivedButton);
        receivedButton.setOnClickListener( clik ->
        {
            EditText message =  findViewById(R.id.editTextChat);
            String mess = (String) message.getText().toString();
            textBox.setText("");


            objects.add(new Message(mess, false));
            myAdapter.notifyDataSetChanged(); //update yourself
        });





        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {

            //objects.add("Item " + (1+objects.size()) );
            myAdapter.notifyDataSetChanged(); //update yourself
            refresher.setRefreshing(false);  //get rid of spinning wheel;
        });
    }

    //Need to add 4 functions here:
    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return objects.size();  } //This function tells how many objects to show

        public String getItem(int position) {
            return objects.get(position).toString();  }  //This returns the string at position p

        public long getItemId(int p) {
            return p; } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent)
        {
            View thisRow = recycled;

            if(recycled == null) {
                if(objects.get(p).isSend) {
                    thisRow = getLayoutInflater().inflate(R.layout.send, null);
                    TextView itemText = thisRow.findViewById(R.id.sendText);
                    itemText.setText( getItem(p) );

                }
                else {
                    thisRow = getLayoutInflater().inflate(R.layout.received, null);
                    TextView itemText = thisRow.findViewById(R.id.receivedText);
                    itemText.setText(getItem(p));
                }
            }



            return thisRow;
        }

    }



    private class Message {
        private String mess;
        private Boolean isSend;

        Message(String mess, Boolean isSend) {
            this.mess=mess;
            this.isSend=isSend;
        }

        public String getMessage() {
            return mess;
        }

        public boolean IsSend() {
            return isSend;
        }
        @Override
        public String toString() {
            return mess;
        }
    }
}
