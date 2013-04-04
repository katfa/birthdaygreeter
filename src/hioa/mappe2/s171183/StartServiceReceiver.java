package hioa.mappe2.s171183;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		int savedHour = preferences.getInt("hour", 0);
		int savedMinute = preferences.getInt("minute", 0);
		Log.d("service", "Startservicereceiver called");
		Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, savedHour);
        cal.set(Calendar.MINUTE, savedMinute);
        
  
        Intent service = new Intent(context, BirthdayChecker.class);
		PendingIntent reminder = PendingIntent.getService(context, 0, service, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, reminder);

	}

	
}
