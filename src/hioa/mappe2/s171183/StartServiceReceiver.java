package hioa.mappe2.s171183;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StartServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("service", "Startservicereceiver called");
		Intent service = new Intent(context, BirthdayChecker.class);
		context.startService(service);
	}

	
}
