package com.gandler.intellitag.libs.webconn;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public class WebThread extends Thread
{
    private final static String TAG = "WebThread";

    private OnWebRequestsFinished l;
    private WebHandler handler;
    private Queue<WebRequest> requests;
    private Queue<WebRequest> handled;

    public static void handle(WebRequest request)
    {
        new WebThread(request).start();
    }

    public WebThread(Queue<WebRequest> requests)
    {
        this.requests = requests;
        init();
    }

    public WebThread(WebRequest request)
    {
        this.requests = new LinkedList<>();
        this.requests.add(request);
        init();
    }

    private void init()
    {
        this.handler = new WebHandler();
        this.handled = new LinkedList<>();
    }

    public void setOnWebRequestsFinishedListener(OnWebRequestsFinished l)
    {
        this.l = l;
    }

    @Override
    public void run()
    {
        try
        {
            while (!requests.isEmpty())
            {
                WebRequest request = this.requests.poll();
                request.execute();
                this.handled.add(request);
                this.handler.sendMessage(this.handler.obtainMessage());
            }

            if (this.l != null)
                this.l.onWebRequestsFinished();
        }
        catch (Exception e)
        {
            Log.d(TAG, e.toString());
        }
    }

    public class WebHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            WebThread.this.handled.poll().callback();
        }
    }
}
