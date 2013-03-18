package hioa.mappe2.s171183;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	
	private static final int CALENDAR_VIEW = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		
		bindInputFields();
		bindButtons();
	}
	
	private void bindButtons(){
		calendarButton = (Button)findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this, CalendarActivity.class);
				startActivityForResult(intent, CALENDAR_VIEW);
			}
		});
		
		saveContact = (Button) findViewById(R.id.saveButton);
		saveContact.setOnClickListener(new OnClickListener() {
			Context context = getBaseContext();
			
			@Override
			public void onClick(View v) {
				Contact contact;
				
				//adds custom string if lastname is left blank
				String lastNameSubstitute = "";
				if(lastNameInput.getText().toString() == ""){
					lastNameSubstitute = "---";
				}
				
				if(lastNameSubstitute == ""){
					contact = new Contact(firstNameInput.getText().toString(), lastNameInput.getText().toString(),
											  phoneNumberInput.getText().toString(), birthdayText.getText().toString()); 
				} else {
					contact = new Contact(firstNameInput.getText().toString(), lastNameSubstitute,
											  phoneNumberInput.getText().toString(), birthdayText.getText().toString()); 
				}
				
				if(contactIsValid(contact)) {
					dbAdapter.insert(contact);
				} else {
					showErrorDialog();
				}	
				
				Toast.makeText(context, "Total contacts increased to " + dbAdapter.getTotalContacts(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void bindInputFields(){
		firstNameInput = (EditText)findViewById(R.id.firstNameBox);
		lastNameInput = (EditText)findViewById(R.id.lastNameBox);
		phoneNumberInput = (EditText)findViewById(R.id.phoneNumberBox);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		switch(requestCode) {
		case CALENDAR_VIEW:
			if(resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				birthdayText = (TextView)findViewById(R.id.birthdayText);
				birthdayText.setText(bundle.getString("selectedDate"));
				break;
				
			}
		}
	}

	private boolean contactIsValid(Contact contact) {
		return contact.getFirstName() != "" || contact.getPhoneNumber() != "" || contact.getBirthday() != "";
	}
	
	private void showErrorDialog(){}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
