package com.rgghgh.gcmdemo.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.rgghgh.gcmdemo.R;
import com.rgghgh.gcmdemo.gcm.GcmRegistrationService;
import com.rgghgh.gcmdemo.gcm.GcmSender;
import com.rgghgh.gcmdemo.webconn.OnWebResultListener;
import com.rgghgh.gcmdemo.webconn.WebRequest;
import com.rgghgh.gcmdemo.webconn.WebThread;

import java.util.ArrayList;


public class ChatActivity extends Activity implements View.OnClickListener, OnWebResultListener
{
    private ArrayList<Message> msgs;
    private MessageAdapter adapter;

    private String myNumber;
    private String toNumber;

    private ListView lvChat;
    private EditText etMsg;
    private Button btnSend;

    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /* ------ GET INTENT DATA ------ */
        Intent in = getIntent();
        this.myNumber = in.getStringExtra(MainActivity.EXTRA_PHONE);
        this.toNumber = in.getStringExtra(MainActivity.EXTRA_TO);

        /* ------ INIT VIEWS ------ */
        this.lvChat = (ListView) findViewById(R.id.lvChat);
        this.etMsg = (EditText) findViewById(R.id.etMsg);
        this.btnSend = (Button) findViewById(R.id.btnSend);

        this.btnSend.setOnClickListener(this);

        /* ------ SETUP MESSAGES DISPLAY ------ */
        this.msgs = new ArrayList<>();
        this.adapter = new MessageAdapter(this, this.msgs);
        this.lvChat.setAdapter(this.adapter);
        this.lvChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        /* ------ INCOMING ------ */

        receiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                String message = intent.getStringExtra("message");
                msgs.add(new Message(message, false));
                adapter.notifyDataSetChanged();
            }
        };

        registerReceiver(receiver, new IntentFilter("dataToChatActivity"));
    }

    @Override
    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        String text = this.etMsg.getText().toString();
        this.etMsg.setText("");

        WebRequest request = new WebRequest(0, MainActivity.URL + "message");
        request.setOnWebResultListener(this);
        request.setPostQuery("phone=" + toNumber + "&msg=" + text);
        WebThread.handle(request);

    }

    @Override
    public void onWebResult(int requestCode, String result)
    {
        this.msgs.add(new Message(result, true));
        this.adapter.notifyDataSetChanged();
    }
}
