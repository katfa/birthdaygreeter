package hioa.mappe2.s171183;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyScheduleReceiver extends BroadcastReceiver {

	// Restart service every 10 seconds
	private static final long REPEAT_TIME = 1000 * 10;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("service", "Startservicereceiver called");
		System.out.println("Startservicereceiver");

		
		Intent i = new Intent(context, BirthdayChecker.class);
		context.startService(i);
	}

}
