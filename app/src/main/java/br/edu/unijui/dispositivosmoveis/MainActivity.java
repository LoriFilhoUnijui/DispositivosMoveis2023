package br.edu.unijui.dispositivosmoveis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceivers();
        createNotificationChannel();
        createListener();
    }

    private void registerReceivers(){
        MyReceiver myReceiver = new MyReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(myReceiver, intentFilter);
    }

    private void createListener(){
        findViewById(R.id.button_explicit_intent).setOnClickListener((__) -> explicitIntent());
        findViewById(R.id.button_implicit_intent).setOnClickListener((__) -> implicitIntent());
        findViewById(R.id.button_notification).setOnClickListener((__) -> showNotification());
        findViewById(R.id.button_broadcast_receiver).setOnClickListener((__) -> broadcastReceiver());
        findViewById(R.id.button_service).setOnClickListener((__) -> service());
        findViewById(R.id.button_alarm_manager).setOnClickListener((__) -> alarmManager());
        findViewById(R.id.button_handler).setOnClickListener((__) -> handler());
    }


    /**
     * Exemplo para trocar de atividade com Intent (implícito).  Um Intent é um objeto que é usado
     * para enviar mensagens assíncronas entre componentes do sistema, como Activities,
     * Services e BroadcastReceivers. Ele fornece um mecanismo para iniciar atividades,
     * iniciar serviços, transmitir informações e realizar várias outras operações.<br><br>
     * Existem dois tipos principais de Intent:
     *<br><br>
     * Intent explícito: É usado para iniciar um componente específico dentro do aplicativo,
     * fornecendo o nome da classe do componente.<br><br>
     * Intent implícito: É usado para solicitar uma ação específica a ser executada por
     * qualquer componente do sistema que possa lidar com essa ação.
     */
    private void implicitIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://unijui.edu.br"));
        startActivity(intent);
    }

    /**
     * Exemplo para trocar de atividade com Intent (explícito).  Um Intent é um objeto que é usado
     * para enviar mensagens assíncronas entre componentes do sistema, como Activities, Services e
     * BroadcastReceivers. Ele fornece um mecanismo para iniciar atividades, iniciar serviços,
     * transmitir informações e realizar várias outras operações. <br><br>
     * Existem dois tipos principais de Intent:
     *<br><br>
     * Intent explícito: É usado para iniciar um componente específico dentro do aplicativo,
     * fornecendo o nome da classe do componente. <br><br>
     * Intent implícito: É usado para solicitar uma ação específica a ser executada por
     * qualquer componente do sistema que possa lidar com essa ação.
     */
    private void explicitIntent() {
        Intent intent = new Intent(this, EmptyActivity.class);
        startActivity(intent);
    }

    /**
     * Exemplo para criar um canal de notificação
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            String channelName = "Channel Name";
            String channelDescription = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Exemplo para mostrar uma notificação a partir do canal criado em createNotificationChannel.
     * <br><br>
     * As notificações são uma maneira importante de fornecer informações e interagir com os usuários em aplicativos Android
     */
    private void showNotification() {
        String channelId = "channel_id";
        int notificationId = 1;

        Intent intent = new Intent(this, EmptyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.baseline_notifications_none_24)
                .setContentTitle("Título da Notificação")
                .setContentText("Conteúdo da Notificação")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.POST_NOTIFICATIONS}, 123);
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }


    /**
     * Exemplo simples de Broadcast Receiver (Ver Classe {@link MyReceiver}). <br><br>
     *
     * O Broadcast Receiver permite que o seu aplicativo receba e responda a eventos do
     * sistema ou eventos personalizados definidos pelo desenvolvedor
     */
    private void broadcastReceiver() {
        Toast.makeText(this, "Conecte o telefone na energia e veja o broadcast", Toast.LENGTH_SHORT).show();
    }

    /**
     * Exemplo simples de Service (ver classe {@link MyService}).<br><br>
     *
     * O Service executa tarefas em segundo plano, independentemente da interface do usuário.
     * Ele pode ser usado para realizar operações longas ou repetitivas, como fazer download
     * de arquivos, reproduzir música em segundo plano ou executar operações de rede.
     */
    private void service() {
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }

    /**
     * Exemplo simples de uso de Alarm Manager (Ver classe {@link MyReceiverAlarm}. <br><br>
     *
     * O Alarm Manager permite agendar a execução de tarefas em um momento específico ou com
     * base em um intervalo de tempo. Ele é comumente usado para executar tarefas em segundo plano,
     * mesmo quando o aplicativo não está em execução.
     */
    private void alarmManager(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyReceiverAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (10 * 1000), pendingIntent);

        Toast.makeText(this, "Você setou um alarme para daqui 10 segundos", Toast.LENGTH_SHORT).show();
    }

    /**
     * Exemplo simples de Handle. <br><br>
     *
     * O Handler permite agendar e executar código em uma determinada Thread.
     * É frequentemente usado em conjunto com a classe Looper para lidar com a comunicação
     * assíncrona entre Threads. O Handler é útil para atualizar a interface do
     * usuário (visto que threads 'normais' não tem permissão para modificar UI principal),
     * processar eventos em segundo plano e realizar operações com atraso.
     */
    private void handler(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(Math.random() > 0.5) {
                    findViewById(R.id.button_handler).setBackgroundColor(Color.GREEN);
                } else {
                    findViewById(R.id.button_handler).setBackgroundColor(Color.RED);
                }
            }
        };
        handler.post(runnable);

    }
}