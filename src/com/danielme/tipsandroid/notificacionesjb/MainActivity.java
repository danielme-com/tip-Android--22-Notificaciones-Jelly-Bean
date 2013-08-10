package com.danielme.tipsandroid.notificacionesjb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author danielme.com
 * 
 */
@SuppressLint("NewApi")
public class MainActivity extends Activity 
{

	private static final String DESDE_NOTIFICACION = "desdeNotificacion";
	
	private static final int BIG_PICTURE = 1;
	
	private static final int BIG_TEXT = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (getIntent().getExtras() != null)
		{
			boolean mostrarToast = getIntent().getExtras().getBoolean(DESDE_NOTIFICACION, false);
			if (mostrarToast)
			{
				Toast.makeText(this, R.string.desdeNoti, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	 public void bigPicture(View view) 
	 {			 
		 mostrarNotificacion(BIG_PICTURE);
	 }
	 
	 public void bigText(View view) 
	 {			 
		mostrarNotificacion(BIG_TEXT);
	 }
	 
	 public void inbox(View view) 
	 {			 
		mostrarNotificacion(0);
	 }		
	 

	private void mostrarNotificacion(int type)
	{
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		//intent hacia la activity que se ejecutará cuando se pulse la notificación. Enviamos un parámetro para saber que llegamos
		//a la activity desde la notificación y mostrar un Toast
		Intent notificacionIntent = new Intent(getApplicationContext(), MainActivity.class);
		notificacionIntent.putExtra(DESDE_NOTIFICACION, true);
		PendingIntent notificacionPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificacionIntent, 0);
	     
		//usamos siempre el builder de la libreria de compatibilidad y no el de Jelly Bean
		Builder builder = new NotificationCompat.Builder(this);
		//titulo de la notificación
	    builder.setContentTitle(getString(R.string.titulo))
	    //mensaje que aparece en la barra de estado al efectuarse la notificación
	    .setTicker(getText(R.string.ticker))
	    //icono que se muestra en la barra de estado, debe ser de muy pequeño tamaño para que se vea entero
	    .setSmallIcon(R.drawable.small)
	    //icono que se muestra dentro de la notificación
	    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
	    //el texto con el contenido, sólo se mostrará si no estamos en Jelly Bean y por lo tanto no se puede aplicar el estilo
	    .setContentText(getString(R.string.contenido))
	    .setContentIntent(notificacionPendingIntent);
	     
	     Notification notificacion = null;
	     if (type == BIG_PICTURE)
	     {	 
	    	 //mostramos uno de los walppaper por defecto de Ubuntu ;)
	    	notificacion = new NotificationCompat.BigPictureStyle(builder)
	    	.bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.delicate_petals_ubuntu_wallpaper)).build();
	     }
	     else if (type == BIG_TEXT)
	     {
	    	 notificacion = new NotificationCompat.BigTextStyle(builder)
	    	 .bigText(getText(R.string.lorem)).build();
	     }
	     else
	     {
	    	 //cuatro líneas y una quinta con un separador. el texto dbe ser muy corto pues no hay saltos
	    	  notificacion = new NotificationCompat.InboxStyle(builder)
	    	    .addLine(getText(R.string.primera)).addLine(getText(R.string.segunda))
	    	    .addLine(getText(R.string.tercera)).addLine(getText(R.string.cuarta))
	    	    .setSummaryText(getText(R.string.quinta)).build();
	     }
			
	    //el atributo flags de la notificación nos permite ciertas opciones
	    notificacion.flags |= Notification.FLAG_AUTO_CANCEL;//oculta la notificación una vez pulsada
	    //idem para defaults
	    notificacion.defaults |= Notification.DEFAULT_SOUND; //sonido
	    //añadimos efecto de vibración, necesitamos el permiso <uses-permission android:name="android.permission.VIBRATE" />
	    notificacion.defaults |= Notification.DEFAULT_VIBRATE;

	    notificationManager.notify(0, notificacion);
	}		

}