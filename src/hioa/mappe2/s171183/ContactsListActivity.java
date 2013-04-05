package hioa.mappe2.s171183;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsListActivity extends FragmentActivity {

	private DBAdapter dbAdapter;
	private ArrayAdapter<Contact> listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_list);
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		ArrayList<Contact> contacts = dbAdapter.selectAll();
		ListView listView = (ListView)findViewById(R.id.contactsList);
		listAdapter = new ContactListAdapter(this.getBaseContext(), this, getSupportFragmentManager(), R.layout.row_layout, R.id.contactName, contacts);
		listView.setAdapter(listAdapter);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		menu.removeItem(R.id.menu_change_time);
		menu.removeItem(R.id.menu_exit);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case R.id.menu_back:
			Intent i = new Intent(this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			return true;
		}
		return false;
	}
	
	public void deleteContact(Contact c){
		dbAdapter.deleteContact(c);
		finish();
		startActivity(getIntent());
	}

	private class ContactListAdapter extends ArrayAdapter<Contact> {
		
		private Context context;
		private ArrayList<Contact> contacts;
		private ContactsListActivity cLActivity;
		private FragmentManager fManager;
		
		public ContactListAdapter(Context context, ContactsListActivity cLActivity, FragmentManager fManager, int textViewResourceId, int textViewId, ArrayList<Contact> values){
			super(context, textViewResourceId, textViewId, values);
			this.context = context;
			this.contacts = values;
			this.cLActivity = cLActivity;
			this.fManager = fManager;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row = inflater.inflate(R.layout.row_layout, parent, false);
			TextView contactName = (TextView)row.findViewById(R.id.contactName);
			Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
			contactName.setTypeface(tf);

			TextView contactBirthday = (TextView)row.findViewById(R.id.contactBirthday);
			Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
			contactBirthday.setTypeface(tfRegular);
			ImageView deleteButton = (ImageView)row.findViewById(R.id.deleteIcon);
			ImageView editButton = (ImageView)row.findViewById(R.id.editIcon);

			contactName.setText(contacts.get(position).toString());
			contactBirthday.setText(contacts.get(position).getBirthday());
			deleteButton.setTag(contacts.get(position).getId());
			editButton.setTag(contacts.get(position).getId());
		
			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					int contactId = Integer.parseInt(view.getTag().toString());
					showDeleteConfirmation(contactId);
				}
			});
			
			
			editButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					int contactId = Integer.parseInt(view.getTag().toString());
					Contact contact = dbAdapter.getContact(contactId);
					
					Intent intent = new Intent(ContactsListActivity.this, RegisterActivity.class);
					intent.putExtra("id", contact.getId());
					intent.putExtra("firstname", contact.getFirstName());
					intent.putExtra("lastname", contact.getLastName());
					intent.putExtra("phonenumber", contact.getPhoneNumber());
					intent.putExtra("birthday", contact.getBirthday());
					
					startActivity(intent);
				}
			});
			
			return row;
		}
		
		private void showDeleteConfirmation(int contactId){
			Contact c = dbAdapter.getContact(contactId);
			DialogFragment deleteDialog = new DialogCreator(c, cLActivity);
			deleteDialog.show(fManager, "Delete");
		}
	}
}
