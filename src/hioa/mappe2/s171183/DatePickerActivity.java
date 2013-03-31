package hioa.mappe2.s171183;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerActivity extends Activity {

	static final int DATE_DIALOG_ID = 0;
	private int pickedMonth;
	private int pickedDay;
	
	private int currentYear, currentMonth, currentDay;
	
	private String[] months = {"January", "February", "March", "April", 
	                           "May", "June", "July", "August", "September",
	                           "October", "November", "December"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showDatePickerDialog();
	}
	
	@SuppressWarnings("deprecation")
	public void showDatePickerDialog(){
		Calendar today = Calendar.getInstance();
        currentYear = today.get(Calendar.YEAR);
        currentMonth = today.get(Calendar.MONTH);
        currentDay = today.get(Calendar.DAY_OF_MONTH);
   		showDialog(DATE_DIALOG_ID);
	}
	
	protected Dialog onCreateDialog(int id){
		switch(id){
		case DATE_DIALOG_ID: return new DatePickerDialog(this, dateSetListener, currentYear, currentMonth, currentDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener dateSetListener =
			new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int month,
						int dayOfMonth) {

					pickedMonth = month;
					pickedDay = dayOfMonth;
					
					String selectedDate = months[pickedMonth] + " " + pickedDay;
					
					Bundle bundle = new Bundle();
					bundle.putString("selectedDate", selectedDate);
					
					Intent intent = new Intent();
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
					
				}
			};

}
