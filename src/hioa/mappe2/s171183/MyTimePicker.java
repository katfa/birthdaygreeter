package hioa.mappe2.s171183;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class MyTimePicker extends DialogFragment implements OnTimeSetListener {
	 	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current time as the default values for the picker
	        final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);

	        // Create a new instance of TimePickerDialog and return it
	        return new TimePickerDialog(getActivity(), this, hour, minute,
	                DateFormat.is24HourFormat(getActivity()));
	    }

	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
	        SharedPreferences.Editor editor = sharedPrefs.edit();
	        editor.putInt("hour", hourOfDay);
	        editor.putInt("minute", minute);
	        editor.commit();
	        getActivity().finish();
	        
	        startActivity(getActivity().getIntent());
	    }

}
