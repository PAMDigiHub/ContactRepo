package ca.jhoffman.contactsrepo.model.adapters;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ca.jhoffman.contactsrepo.activities.ContactPagerFragment;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsDbHelper;
import ca.jhoffman.contactsrepo.model.ContactsProvider;
import ca.jhoffman.contactsrepo.model.SortOrder;

/**
 * Created by jhoffman on 2016-10-02.
 */

public class ContactsViewPagerAdapter extends FragmentStatePagerAdapter {


    private Cursor cursor;

    public ContactsViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        cursor = ContactsProvider.getInstance().getOrderedContacts(context, "", SortOrder.ASC);
    }

    @Override
    public Fragment getItem(int position) {

        cursor.moveToPosition(position);
        Contact contact = ContactsDbHelper.contactFromCursor(cursor);

        return ContactPagerFragment.instantiate(contact);
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }
}
