package hioa.mappe2.s171183;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotificationCreator extends Activity {
	private MainActivity mActivity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public NotificationCreator(MainActivity mainActivity){
		mActivity = mainActivity;
	}
	public void createNotification(){
		
		//Prepares intent if notification is selected
		Intent intent = new Intent(mActivity, NotificationRecieverActivity.class);
		PendingIntent pIntent =	PendingIntent.getActivity(mActivity, 0, intent, 0);
		
		Notification notification = new Notification.Builder(mActivity.getApplicationContext())
										.setContentTitle("It's someone's birthday!")
										.setContentText("Click here to send them a message")
										.setSmallIcon(R.drawable.app_notification)
										.setContentIntent(pIntent).build();
		
		NotificationManager notificationManager = (NotificationManager)mActivity.getSystemService(NOTIFICATION_SERVICE);
		
	    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}
}
