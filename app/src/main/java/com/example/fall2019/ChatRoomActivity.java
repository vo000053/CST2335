package com.example.fall2019;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
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


    ArrayList<Message> objects = new ArrayList<>();
    private static int ACTIVITY_VIEW_CONTACT = 33;
    int positionClicked = 0;
    MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);



        //get a database:
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String[] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_MESSAGE, MyDatabaseOpenHelper.COL_ISSEND};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int isSendColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ISSEND);
        int messageColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_MESSAGE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);

        //iterate over the results, return true if there is a next item:
        while (results.moveToNext()) {
            Boolean isSend = false;
            if(results.getInt(isSendColumnIndex)==1) {
                isSend = true;
            }
            String message = results.getString(messageColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            objects.add(new Message(id, message, isSend));
        }
        ListView theList = findViewById(R.id.theList);
        myAdapter = new MyListAdapter();
        theList.setAdapter(myAdapter);
/*
        theList.setOnItemClickListener((lv, vw, pos, id) ->


                Toast.makeText(ChatRoomActivity.this,
                        "You clicked on:" + pos, Toast.LENGTH_SHORT).show()

        );
*/

        EditText textBox = findViewById(R.id.editTextChat);

        //String sendMessage = findViewById(R.id.sendText).toString();
        //String receivedMessage = findViewById(R.id.receivedText).toString();


        Button sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(clik ->
        {

            EditText mess = findViewById(R.id.editTextChat);
            String message = (String) mess.getText().toString();
            if (mess.length() != 0) {

                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();

                newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);

                newRowValues.put(MyDatabaseOpenHelper.COL_ISSEND, 1);
                //insert in the database:
                long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

                objects.add(new Message(newId, message, true));

                myAdapter.notifyDataSetChanged();
            }
            textBox.setText("");
        });

        Button receivedButton = findViewById(R.id.receivedButton);
        receivedButton.setOnClickListener(clik ->
        {
            EditText mess = findViewById(R.id.editTextChat);
            String message = (String) mess.getText().toString();


            if (mess.length() != 0) {

                //add to the database and get the new ID
                ContentValues newRowValues = new ContentValues();

                newRowValues.put(MyDatabaseOpenHelper.COL_MESSAGE, message);

                newRowValues.put(MyDatabaseOpenHelper.COL_ISSEND, 0);
                //insert in the database:
                long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

                objects.add(new Message(newId, message, false));

                myAdapter.notifyDataSetChanged();
            }

            textBox.setText("");
        });


        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {
            db.execSQL("DELETE FROM MESSAGES;");
            objects.clear();
            //objects.add("Item " + (1+objects.size()) );
            myAdapter.notifyDataSetChanged(); //update yourself
            refresher.setRefreshing(false);  //get rid of spinning wheel;
        });


}
        //Need to add 4 functions here:
        private class MyListAdapter extends BaseAdapter {

            public int getCount() {
                return objects.size();
            } //This function tells how many objects to show

            public Message getItem(int position) {
                return objects.get(position);
            }  //This returns the string at position p

            public long getItemId(int p) {
                return p;
            } //This returns the database id of the item at position p

            public View getView(int p, View recycled, ViewGroup parent) {
                //View thisRow = recycled;
                LayoutInflater inflater = getLayoutInflater();

                View newView;

                Message thisRow = getItem(p);



                    if (objects.get(p).isSend()) {
                        newView = inflater.inflate(R.layout.send, null);

                        TextView itemText = newView.findViewById(R.id.sendText);
                        itemText.setText(thisRow.getMessage());
                    }
                    else {
                        newView = inflater.inflate(R.layout.received, null);
                        TextView itemText = newView.findViewById(R.id.receivedText);
                        itemText.setText(thisRow.getMessage());
                    }



                return newView;
            }
        }


    }