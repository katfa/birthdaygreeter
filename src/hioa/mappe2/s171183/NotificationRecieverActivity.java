package hioa.mappe2.s171183;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NotificationRecieverActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_reciever);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_notification_reciever, menu);
		return true;
	}

}
