package hioa.mappe2.s171183;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

@SuppressLint("ValidFragment")
public class DialogCreator extends DialogFragment {

	private ContactsListActivity cLActivity;
	private MainActivity mActivity;
	private Contact contact;

	public DialogCreator(Contact contact, ContactsListActivity activity) {
		cLActivity = activity;
		this.contact = contact;
	}

	public DialogCreator(MainActivity activity) {
		mActivity = activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		if (cLActivity != null) {
			String firstname = contact.getFirstName();
			return new AlertDialog.Builder(getActivity())
					.setTitle(getString(R.string.delete_confirm_title))
					.setMessage("Do you want to delete " + firstname + "?")
					.setPositiveButton(getString(R.string.delete_yes),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {
									cLActivity.deleteContact(contact);

								}
							})
					.setNegativeButton(getString(R.string.delete_no),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();
		}

		if (mActivity != null) {
			return new AlertDialog.Builder(getActivity())
					.setTitle(getString(R.string.about_title))
					.setMessage(getString(R.string.about_body))
					.setPositiveButton(getString(R.string.ok),null).create();
		}

		return null;

	}

}
