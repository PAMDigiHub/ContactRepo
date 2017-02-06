package ca.jhoffman.contactsrepo.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.ContactsProvider;

/**
 * Created by jhoffman on 16-09-18.
 */
public class ConfirmDeleteDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.activity_edit_contact_delete_confirmation_message)
                .setTitle(R.string.activity_edit_contact_delete_confirmation_title)
                .setCancelable(true);

        builder.setPositiveButton(R.string.activity_edit_contact_delete_confirmation_yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((ConfirmDeleteDialogListener)getActivity()).onConfirmedDelete();
            }
        });
        builder.setNegativeButton(R.string.activity_edit_contact_delete_confirmation_no, null);

        return builder.create();
    }

    //
    //Interface ConfirmDeleteDialogListener
    //

    public interface ConfirmDeleteDialogListener {
        void onConfirmedDelete();
    }
}