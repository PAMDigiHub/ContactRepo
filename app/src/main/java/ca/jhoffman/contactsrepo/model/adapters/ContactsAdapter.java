package ca.jhoffman.contactsrepo.model.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsDbHelper;
import ca.jhoffman.contactsrepo.model.ContactsProvider;

/**
 * Created by jhoffman on 16-09-08.
 */
public class ContactsAdapter extends CursorAdapter {
    public ContactsAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contacts_list_row, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Contact contact = ContactsDbHelper.contactFromCursor(cursor);
        if (contact != null) {
            TextView fullnameTextView = (TextView) view.findViewById(R.id.contacts_list_row_textview_fullname);

            String positionText = (cursor.getPosition() + 1)+"- "; //+ 1 because zero-based
            fullnameTextView.setText(positionText + contact.getFullname());

            TextView emailTextView = (TextView) view.findViewById(R.id.contacts_list_row_textview_email);
            emailTextView.setText(contact.getEmail());
        }
    }
}
