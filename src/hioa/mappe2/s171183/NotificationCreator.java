package hioa.mappe2.s171183;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

public class NotificationCreator extends Activity {
	private Context context;
	private String name;
	private String phonenumber;
	private int index;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public NotificationCreator(Context context, String firstname, String phonenumber, int index){
		this.context = context;
		name = firstname;
		this.phonenumber = phonenumber;
		this.index = index;
	}

	public void createNotification(){
		// sending different requestIDs to get different extras in the notification receiver
		int requestID = (int) System.currentTimeMillis();
		
		// Sets up intent triggered by notification click.
		
		Intent intent = new Intent(context, NotificationReciever.class);
		
		intent.putExtra("sms_body","Happy birthday, " + name + "!");
		intent.putExtra("address", phonenumber);
		
		PendingIntent pIntent =	PendingIntent.getActivity(context, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
		Uri notificationSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		
		Notification notification = new Notification.Builder(context)
										.setContentTitle("It's " + name + "'s birthday!")
										.setContentText("Click here to send them a message")
										.setSmallIcon(R.drawable.app_notification)
										.setContentIntent(pIntent)
										.setSound(notificationSound).build();
		
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
		
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(index, notification);		
		
	}
}
