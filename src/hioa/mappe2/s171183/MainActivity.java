package hioa.mappe2.s171183;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private Button addContact;
	private Button viewContacts;
	private Button showNotification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bindButtons();

	}
	
	private void bindButtons(){
		
		addContact = (Button) findViewById(R.id.addContact);
		addContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		viewContacts = (Button)findViewById(R.id.viewContacts);
		viewContacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ContactsListActivity.class);
				startActivity(intent);
			}
		});
		
	
		showNotification = (Button)findViewById(R.id.showNotification);
		showNotification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NotificationCreator nc = new NotificationCreator(MainActivity.this, "Kat", "90134595");
				nc.createNotification();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
