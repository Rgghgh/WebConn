package example

// some imports ...

public class ExampleActivity extends Activity implements OnWebResultListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // SOME CODE
        
        makeAWebRequest("http://exmaple.com?optionalGet=value"); // http or https
    }

    public void makeAWebRequest(String url)
    {
        String text = this.etMsg.getText().toString();
        this.etMsg.setText("");

        WebRequest request = new WebRequest(0, url); 
        // 0 is the requestCode. Can be used to indetify a request in the Result listener when more than one is made at once.
        request.setOnWebResultListener(this); // this implements OnWebResultListener interface
        request.setPostQuery("post1=value1&post2=value2"); // optional...
        WebThread.handle(request);
    }

    @Override
    public void onWebResult(int requestCode, String result)
    {
        log.d("WebResult", result);
    }
}
