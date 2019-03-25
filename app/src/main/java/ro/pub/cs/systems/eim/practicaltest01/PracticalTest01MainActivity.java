package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lab04.eim.systems.cs.pub.ro.R;
import ro.pub.cs.systems.eim.PracticalTest01Service;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    Button button;
    Button button1;
    Button button2;
    EditText text1;
    EditText text2;
    int counter1 = 0;
    int counter2 = 0;

    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        button = (Button) findViewById(R.id.buton);
        button1 = (Button) findViewById(R.id.buton1);
        button2 = (Button) findViewById(R.id.buton2);
        text1 = (EditText) findViewById(R.id.text1);
        text2 = (EditText) findViewById(R.id.text2);

        button1.setOnClickListener(button1ClickListener);
        button2.setOnClickListener(button2ClickListener);
        button.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("counter1")) {
                text1.setText(savedInstanceState.getString("counter1"));
            } else {
                text1.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("counter2")) {
                text2.setText(savedInstanceState.getString("counter2"));
            } else {
                text2.setText(String.valueOf(0));
            }
        } else {
            text1.setText(String.valueOf(0));
            text2.setText(String.valueOf(0));
        }


            intentFilter.addAction("ro.pub.cs.systems.eim.arithmeticMean");
    }
    private Button1ClickListener buttonClickListener = new Button1ClickListener();
    private class Button1ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);

            int numberOfClicks = Integer.parseInt(text1.getText().toString()) +
                    Integer.parseInt(text2.getText().toString());
            intent.putExtra("numberOfClicks", numberOfClicks);
            startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
        }
    }

    private ButtonClickListener button1ClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            counter1 = Integer.parseInt(text1.getText().toString());
            counter1++;


            if (counter1 > 3) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", counter1);
                intent.putExtra("secondNumber", counter2);
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //    startForegroundService(intent);
                //} else {
                    startService(intent);
                //}
            }

            text1.setText(String.valueOf(counter1));



            }
        }

    private Button2ClickListener button2ClickListener = new Button2ClickListener();
    private class Button2ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            counter2 = Integer.parseInt(text2.getText().toString());
            counter2++;
            text2.setText(String.valueOf(counter2));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("counter1", text1.getText().toString());
        savedInstanceState.putString("counter2", text2.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("counter1")) {
            text1.setText(savedInstanceState.getString("counter1"));
        } else {
            text1.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("counter2")) {
            text2.setText(savedInstanceState.getString("counter2"));
        } else {
            text2.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
