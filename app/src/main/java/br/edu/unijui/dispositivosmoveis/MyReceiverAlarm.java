package br.edu.unijui.dispositivosmoveis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiverAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Recebido pelo Alarm Manager ", Toast.LENGTH_SHORT).show();
    }
}