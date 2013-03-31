package hioa.mappe2.s171183;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class BirthdayChecker extends Service {

	public static final long CHECK_INTERVAL = 24 * 60 * 60 * 1000; // 24 hours
	private Handler mHandler = new Handler();
	private Timer mTimer = null;
	private DBAdapter dbAdapter = new DBAdapter(this);

	@Override
	public void onCreate() {
		// cancel if already exists
		if (mTimer != null) {
			mTimer.cancel();
		} else {
			mTimer = new Timer();
		}

		// schedule Task
		mTimer.scheduleAtFixedRate(new BirthdayCheckTask(), 0, CHECK_INTERVAL);
		
		
	}

	class BirthdayCheckTask extends TimerTask {
		
		@Override
		public void run() {
			mHandler.post(new Runnable(){
				public void run(){
					SimpleDateFormat df = new SimpleDateFormat("MMMM dd");
					String today = df.format(Calendar.getInstance().getTime()).toString();
					
					List<Contact> birthdayCelebrants = dbAdapter.getContactsByBirthday(today);
					
					if(birthdayCelebrants.size() > 0) {
						for(Contact c : birthdayCelebrants){
							NotificationCreator nc = new NotificationCreator(
									getApplicationContext(), c.getFirstName(), c.getPhoneNumber(), birthdayCelebrants.indexOf(c));
							nc.createNotification();
						}
					}
				}
			});
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
