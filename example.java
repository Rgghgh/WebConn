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
