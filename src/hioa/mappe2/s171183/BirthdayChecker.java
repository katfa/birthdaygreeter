package hioa.mappe2.s171183;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class BirthdayChecker extends Service {

	public static final long CHECK_INTERVAL = 10 * 1000; // 10 seconds
	private Handler mHandler = new Handler();
	private Timer mTimer = null;

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
					Toast.makeText(getApplicationContext(), "TOAST!",
							Toast.LENGTH_SHORT).show();
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
