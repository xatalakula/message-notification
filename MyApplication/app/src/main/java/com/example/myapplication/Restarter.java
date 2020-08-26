package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class Restarter extends BroadcastReceiver {

    public static String messBody = "";
    public static String messAddress = "";

    private final String Tag = "SMSBroadcastReceiver";
    private final String IntentAction = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("receiver","Vao receiver");
        Toast.makeText(context, "Vao receiver", Toast.LENGTH_SHORT).show();

        if (intent.getAction() == "com.alr.text")
        {
            Toast.makeText(context, "Data not available", Toast.LENGTH_SHORT).show();
            return;
        }
        //read incomming sms
        if (intent.getAction() == IntentAction)
        {
            SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < messages.length; i++)
            {

                sb.append(String.format("SMS From: " + messages[i].getOriginatingAddress() + "\n Body: " + messages[i].getMessageBody()));
                messBody = messBody + messages[i].getMessageBody();
                messAddress = messages[i].getOriginatingAddress();
            }
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
            //MainActivity.textView.Text = messBody + "-----" + messAddress;
            //if(messAddress == "Ericsson")
            //{
            if (messBody.contains("Cause"))
            {
                Toast.makeText(context, "Nhan duoc cause", Toast.LENGTH_SHORT).show();
                Log.e("receiver", "nhan duoc cause");
            }
            return;
            //}
        }

        Log.i("Broadcast Listened", "Service tried to stop");
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, MyService.class));
        } else {
            context.startService(new Intent(context, MyService.class));
        }
    }
}
