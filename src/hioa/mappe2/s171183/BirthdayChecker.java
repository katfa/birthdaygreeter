package hioa.mappe2.s171183;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

@SuppressLint("SimpleDateFormat")
public class BirthdayChecker extends Service {
	private DBAdapter dbAdapter = new DBAdapter(this);

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SimpleDateFormat df = new SimpleDateFormat("MMMM dd");
		String today = df.format(Calendar.getInstance().getTime()).toString();

		ArrayList<Contact> celebrants = dbAdapter
				.getContactsByBirthday(today);
		
		if (celebrants.size() > 0) {
			for (Contact c : celebrants) {
				NotificationCreator nc = new NotificationCreator(this,c.getFirstName(),
						c.getPhoneNumber(), celebrants.indexOf(c));
				nc.createNotification();
			}
		}
		
	
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}