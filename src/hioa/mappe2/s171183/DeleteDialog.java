package hioa.mappe2.s171183;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class DeleteDialog extends DialogFragment {
	
	private ContactsListActivity cLActivity;
	private Contact contact;

	
	public DeleteDialog(Contact contact, ContactsListActivity contactListActivity){
		this.cLActivity = contactListActivity;
		this.contact = contact;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState){
		String firstname = 	contact.getFirstName();	
		return new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.delete_confirm_title))
				.setMessage("Do you want to delete " + firstname + "?")
				.setPositiveButton(getString(R.string.delete_yes), 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								cLActivity.deleteContact(contact);
								
							}
						})
				.setNegativeButton(getString(R.string.delete_no),
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								
							}
						})
				.create();
						
	}

}
