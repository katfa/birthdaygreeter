package hioa.mappe2.s171183;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;

public class NotificationReciever extends Activity {

	protected void onCreate() {
		Intent intent = getIntent();
		
		String message = intent.getStringExtra("sms_body");
		String number = intent.getStringExtra("address");
		
		Intent smsIntent = new Intent(Intent.ACTION_VIEW);
		smsIntent.setData(Uri.parse("sms:"));
		smsIntent.setType("vnd.android-dir/mms-sms");
		smsIntent.putExtra("address", number);
		smsIntent.putExtra("sms_body", message);
		
		startActivity(smsIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notification_reciever, menu);
		return true;
	}

}
