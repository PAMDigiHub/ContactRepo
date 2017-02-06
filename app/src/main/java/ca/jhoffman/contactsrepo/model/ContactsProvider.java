package ca.jhoffman.contactsrepo.model;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by jhoffman on 16-09-08.
 */
public class ContactsProvider {
    private static ContactsProvider instance = new ContactsProvider();

    public static ContactsProvider getInstance() {
        return instance;
    }

    private ContactsProvider() {}

    private ContactsDbHelper db(Context context) {
        return new ContactsDbHelper(context);
    }

    public Cursor getOrderedContacts(Context context, String criteria, SortOrder order) {
        return db(context).getOrdered(criteria, order);
    }


    public void insertDummyData(Context context) {
        //Dummy data
        addItem(new Contact("Angelica","Bridges","abridge@mail.com"), context);
        addItem(new Contact("Marlene","Conner","mconner@mail.com"), context);
        addItem(new Contact("Jenna","Rhodes","jrhodes@mail.com"), context);
        addItem(new Contact("Earnest","Freeman","efreeman@mail.com"), context);
        addItem(new Contact("Laura","Carson","lcarson@mail.com"), context);
        addItem(new Contact("Ignacio","Coleman","icoleman@mail.com"), context);
        addItem(new Contact("Pat","Yates","pyates@mail.com"), context);
        addItem(new Contact("Georgia","Mclaughlin","gmclaughlin@mail.com"), context);
        addItem(new Contact("Kevin","King","kking@mail.com"), context);
        addItem(new Contact("Casey","Brady","cbrady@mail.com"), context);
    }

    /**
     * @param id
     * @return the contact object with the specified id or null if id does not exists
     */
    public Contact getItemById(int id, Context context) {
        return db(context).findById(id);
    }

    public void addItem(Contact contact, Context context) {
        if (contact != null) {
            db(context).insertContact(contact);
        }
    }

    public void deleteItemById(int id, Context context) {
        db(context).deleteById(id);
    }

    public void updateContact(Contact contact, Context context) {
        db(context).update(contact);
    }
}
