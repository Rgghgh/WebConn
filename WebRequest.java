package com.gandler.intellitag.libs.webconn;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest
{
    private int requestCode;
    private String url;
    private String post;
    private String result;
    private OnWebResultListener callback;

    public WebRequest(int requestCode, String url)
    {
        this.requestCode = requestCode;
        this.url = url;
    }

    public WebRequest(int requestCode, String url, String post)
    {
        this.requestCode = requestCode;
        this.url = url;
        this.post = post;
    }

    public void setPostQuery(String post)
    {
        this.post = post;
    }

    public void setOnWebResultListener(OnWebResultListener l)
    {
        this.callback = l;
    }

    public void execute() throws IOException
    {
        StringBuilder ans = new StringBuilder();
        URL url = new URL(this.url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (this.post != null)
        {
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            DataOutputStream wr = new DataOutputStream(os);
            wr.writeBytes(this.post);
            wr.close();
            os.close();
        }

        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null)
            ans.append(line);

        reader.close();
        isr.close();
        is.close();
        conn.disconnect();

        this.result = ans.toString();
    }

    protected void callback()
    {
        if (this.result != null && this.callback != null)
            this.callback.onWebResult(this.requestCode, this.result);
    }

    public String getUrl()
    {
        return this.url;
    }

    public String getPost()
    {
        return this.post;
    }
}
