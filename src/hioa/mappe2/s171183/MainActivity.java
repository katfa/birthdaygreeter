package hioa.mappe2.s171183;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

	private Button addContact, viewContacts, addContactByPhonebook,
			aboutButton;
	static final int PICK_CONTACT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bindButtons();
		doStartService();

	}

	private void bindButtons() {

		addContact = (Button) findViewById(R.id.addContact);
		addContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});

		addContactByPhonebook = (Button) findViewById(R.id.phonebook);
		addContactByPhonebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, PICK_CONTACT);
			}
		});

		viewContacts = (Button) findViewById(R.id.viewContacts);
		viewContacts.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ContactsListActivity.class);
				startActivity(intent);
			}
		});
		
		aboutButton = (Button) findViewById(R.id.about);
		aboutButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment aboutDialog = new DialogCreator(MainActivity.this);
				aboutDialog.show(getSupportFragmentManager(), "about");
			}
			
		});

	}
	
	private void doStartService() {
		Intent service = new Intent();
		service.setAction("hioa.mappe2.s171183.trigger");
		sendBroadcast(service);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

		switch (requestCode) {
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK) {

				Uri contactData = data.getData();
				Cursor cursor = managedQuery(contactData, null, null, null,
						null);

				if (cursor.moveToFirst()) {
					String id = cursor
							.getString(cursor
									.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
					String hasPhone = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
					if (hasPhone.equalsIgnoreCase("1")) {
						Cursor phones = getContentResolver()
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = " + id, null, null);
						phones.moveToFirst();
						String number = phones.getString(phones
								.getColumnIndex("data1"));
						intent.putExtra("number", number);
					}
					String name = cursor
							.getString(cursor
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					intent.putExtra("name", name);
				}
			}
			break;
		}

		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.menu_exit: finish();
		return true;
		}
		return false;
		
	}

}
