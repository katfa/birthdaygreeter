package hioa.mappe2.s171183;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
		
		// Sets up intent triggered by notification click.
		
		Intent intent = new Intent(context, NotificationRecieverActivity.class);
		
		intent.putExtra("sms_body","Happy birthday, " + name + "!");
		intent.putExtra("address", phonenumber);
		
		PendingIntent pIntent =	PendingIntent.getActivity(context, 0, intent, 0);
			
		
		Notification notification = new Notification.Builder(context)
										.setContentTitle("It's " + name + "'s birthday!")
										.setContentText("Click here to send them a message")
										.setSmallIcon(R.drawable.app_notification)
										.setContentIntent(pIntent).build();
		
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
		
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(index, notification);
		
		
	}
}
