package hioa.mappe2.s171183;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private TextView birthdayText;
	private Button calendarButton;
	private Button saveContact;
	
	private EditText firstNameInput;
	private EditText lastNameInput;
	private EditText phoneNumberInput;
	
	private DBAdapter dbAdapter;
	private int contactId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		bindInputFields();
		bindButtons();
		
		TextView title = (TextView)findViewById(R.id.addContactTitle);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
		title.setTypeface(tf);
		
		Intent i = getIntent();
		
		// If intent comes from phonebook
		if (i.hasExtra("name") && i.hasExtra("number")) {
			firstNameInput.setText(i.getStringExtra("name"));
			phoneNumberInput.setText(i.getStringExtra("number"));
		}
		// If intent comes from ContactListActivity (edit contact)
		else if (i.hasExtra("id")) {
			firstNameInput.setText(i.getStringExtra("firstname"));
			if(i.getStringExtra("lastname").equals("---")) {
				lastNameInput.setText("");
			} else {
				lastNameInput.setText(i.getStringExtra("lastname"));
			}
			
			phoneNumberInput.setText(i.getStringExtra("phonenumber"));
			birthdayText.setText(i.getStringExtra("birthday"));
			contactId = i.getIntExtra("id", 0);
		}
	}
	
	private void bindButtons(){
		calendarButton = (Button)findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this, DatePickerActivity.class);
				startActivityForResult(intent, 0);
			}
		});
		
		saveContact = (Button) findViewById(R.id.saveButton);
		saveContact.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				saveContactInfo();
			}
		});
	}
	
	private void bindInputFields(){
		firstNameInput = (EditText)findViewById(R.id.firstNameBox);
		lastNameInput = (EditText)findViewById(R.id.lastNameBox);
		phoneNumberInput = (EditText)findViewById(R.id.phoneNumberBox);
		birthdayText = (TextView)findViewById(R.id.birthdayText);
	}
	
	private void clearInputFields(){
		firstNameInput.setText("");
		lastNameInput.setText("");
		phoneNumberInput.setText("");
		birthdayText.setText("");
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		switch(requestCode) {
		case 0:
			if(resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				birthdayText.setText(bundle.getString("selectedDate"));
				break;
				
			}
		}
	}
	
	private boolean noEmptyFields(){
		return firstNameInput.getText().toString().length() != 0 && 
				phoneNumberInput.getText().toString().length() != 0 &&
				birthdayText.getText().toString().length() != 0;
	}
	
	private void saveContactInfo(){
		Contact contact;
		Context context = getBaseContext();
		if(noEmptyFields()){
			
			//adds custom string if lastname is left blank
			String lastNameSubstitute = "";
			if(lastNameInput.getText().toString().length() == 0){
				lastNameSubstitute = "---";
			}
			
			if(lastNameSubstitute.equals("")){
				contact = new Contact(firstNameInput.getText().toString(), lastNameInput.getText().toString(),
										  phoneNumberInput.getText().toString(), birthdayText.getText().toString()); 
			} else {
				contact = new Contact(firstNameInput.getText().toString(), lastNameSubstitute,
										  phoneNumberInput.getText().toString(), birthdayText.getText().toString()); 
			}
		
			//on update
			if(contactId != 0){
				contact = dbAdapter.getContact(contactId);
				contact.setBirthday(birthdayText.getText().toString());
				contact.setFirstName(firstNameInput.getText().toString());
				contact.setPhoneNumber(phoneNumberInput.getText().toString());
				if(lastNameSubstitute.equals("")){
					contact.setLastName(lastNameInput.getText().toString());
				} else {
					contact.setLastName(lastNameSubstitute);
				}

				dbAdapter.updateContact(contact);
				Toast.makeText(context, "Contact updated", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(this, ContactsListActivity.class);
				startActivity(i);
			} else {
				//on save new contact
				dbAdapter.insert(contact);
				clearInputFields();
				Toast.makeText(context, "Total contacts increased to " + dbAdapter.getTotalContacts(), Toast.LENGTH_SHORT).show();
			}
		} else {

			Toast.makeText(context, "NO EMPTY FIELDS ALLOWED UNLESS MARKED WITH \'OPTIONAL\'", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
