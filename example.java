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
        // SOME CODE
    }

    @Override
    public void onClick(View v)
    {
        String text = this.etMsg.getText().toString();
        this.etMsg.setText("");

        WebRequest request = new WebRequest(0, "http://exmaple.com?optionalGet=value");
        request.setOnWebResultListener(this);
        request.setPostQuery("post1=value1&post2=value2");
        WebThread.handle(request);
    }

    @Override
    public void onWebResult(int requestCode, String result)
    {
        this.msgs.add(new Message(result, true));
        this.adapter.notifyDataSetChanged();
    }
}
