package hioa.mappe2.s171183;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

public class CalendarActivity extends Activity {

	private int pickedMonth;
	private int pickedDay;
	
	private String[] months = {"January", "February", "March", "April", 
	                           "May", "June", "July", "August", "September",
	                           "October", "November", "December"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CalendarView calendar = new CalendarView(this);
		calendar.setOnDateChangeListener(myDateSetListener);
		setContentView(calendar);
	}

	private CalendarView.OnDateChangeListener myDateSetListener =
			new CalendarView.OnDateChangeListener() {
				
				@Override
				public void onSelectedDayChange(CalendarView view, int year, int month,
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
