package mx.dezkareid.lora;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import mx.dezkareid.lora.sound.SoundReceiver;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private SoundReceiver mReceiver;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        list.setAdapter(adapter);
        mReceiver = new SoundReceiver(mHandler);

    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (0 < msg.what) {
            } else {
                if (msg.getData() != null) {
                    String s = msg.getData().getString("Text");
                    if (s != null){
                        int code = Integer.parseInt(s);
                        if (code == 46){
                            listItems.add(message);
                            adapter.notifyDataSetChanged();
                            message = "";
                            list.setSelection(listItems.size()-1);
                        }else{
                            message += Character.toString ((char) code);
                        }
                    }
                }
            }
        }
    };


    @Override
    protected void onPause() {
        mReceiver.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mReceiver.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mReceiver.destroy();
        super.onDestroy();
    }

}
