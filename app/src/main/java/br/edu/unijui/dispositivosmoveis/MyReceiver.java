package br.edu.unijui.dispositivosmoveis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
                Toast.makeText(context, "Dispositivo conectado à energia", Toast.LENGTH_SHORT).show();
            } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(context, "Dispositivo desconectado da energia", Toast.LENGTH_SHORT).show();
            }
        }
    }
}