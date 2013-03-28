package hioa.mappe2.s171183;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
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
		for(Contact c : contacts){
			System.out.println(c.toString() + " id > " + c.getId());
		}
		
		ListView listView = (ListView)findViewById(R.id.contactsList);
		listAdapter = new ContactListAdapter(this.getBaseContext(), this, getSupportFragmentManager(), R.layout.row_layout, R.id.contactName, contacts);
		listView.setAdapter(listAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_contacts_list, menu);
		return true;
	}
	
	public void deleteContact(Contact c){
		dbAdapter.deleteContact(c);
		finish();
		startActivity(getIntent());
	}

	private class ContactListAdapter extends ArrayAdapter<Contact> {
		
		private Context context;
		private ArrayList<String> names;
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
			ImageView deleteButton = (ImageView)row.findViewById(R.id.deleteIcon);
			
			contactName.setText(contacts.get(position).toString());
			deleteButton.setTag(contacts.get(position).getId());

			deleteButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					System.out.println("CLICK");
					int contactId = Integer.parseInt(view.getTag().toString());
					showDeleteConfirmation(contactId);
				}
			});
			
			
			return row;
		}
		
		private void showDeleteConfirmation(int contactId){
			Contact c = dbAdapter.getContact(contactId);
			DialogFragment deleteDialog = new DeleteDialog(c, cLActivity);
			deleteDialog.show(fManager, "Delete");
		}
	}
}
