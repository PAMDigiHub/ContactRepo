package ca.jhoffman.contactsrepo.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jhoffman on 2016-09-27.
 */

public class ContactsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "ca.jhoffman.contactsrepo.db";
    private static final int DATABASE_VERSION = 1;

    public ContactsDbHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactsContract.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactsContract.SQL_DELETE_IF_EXISTS);

        onCreate(db);
    }

    public static Contact contactFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.COLUMN_ID));
        String firstname = cursor.getString(cursor.getColumnIndex(ContactsContract.COLUMN_FIRSTNAME));
        String lastname = cursor.getString(cursor.getColumnIndex(ContactsContract.COLUMN_LASTNAME));
        String email = cursor.getString(cursor.getColumnIndex(ContactsContract.COLUMN_EMAIL));

        return new Contact(id, firstname, lastname, email);
    }

    private ContentValues getContactValues(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.COLUMN_FIRSTNAME, contact.getFirstname());
        values.put(ContactsContract.COLUMN_LASTNAME, contact.getLastname());
        values.put(ContactsContract.COLUMN_EMAIL, contact.getEmail());

        return values;
    }

    public Cursor getOrdered(String criteria, SortOrder order) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = ContactsContract.ALL_COLUMNS;
        String sort =   "lower("+ContactsContract.COLUMN_FIRSTNAME + ") "+ order.getSqliteValue() +"," +
                        "lower("+ContactsContract.COLUMN_LASTNAME + ") " + order.getSqliteValue();

        String lowercaseCriteria = "%"+criteria.toLowerCase()+"%";
        String selection = String.format("lower(%s) LIKE ? OR lower(%s) LIKE ? OR lower(%s) LIKE ?",
                                         ContactsContract.COLUMN_FIRSTNAME, ContactsContract.COLUMN_LASTNAME, ContactsContract.COLUMN_EMAIL);
        String[] selectionArgs = { lowercaseCriteria, lowercaseCriteria, lowercaseCriteria };

        Cursor cursor = db.query(
            ContactsContract.TABLE,                     // The table to query
            projection,                                 // The columns to return
            selection,                                  // The columns for the WHERE clause
            selectionArgs,                              // The values for the WHERE clause
            null,                                       // don't group the rows
            null,                                       // don't filter by row groups
            sort                                        // The sort order
        );

        return cursor;
    }

    public void insertContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();

        db.insert(ContactsContract.TABLE, null, getContactValues(contact));
    }

    public void update(Contact contact) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = ContactsContract.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(contact.getId()) };

        db.update(
            ContactsContract.TABLE,
            getContactValues(contact),
            selection,
            selectionArgs);
    }

    public Contact findById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = ContactsContract.ALL_COLUMNS;
        String selection = ContactsContract.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor cursor = db.query(
            ContactsContract.TABLE,                     // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            selectionArgs,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            null                                 // The sort order
        );

        if (cursor.moveToFirst() == true) {
            //contact found
            return contactFromCursor(cursor);
        } else {
            return null;
        }
    }

    public void deleteById(int id) {
        SQLiteDatabase db = getReadableDatabase();

        String selection = ContactsContract.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        db.delete(ContactsContract.TABLE, selection, selectionArgs);
    }

    private static class ContactsContract {
        public static final String TABLE = "contacts";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_LASTNAME = "lastname";
        public static final String COLUMN_EMAIL = "email";
        public static final String[] ALL_COLUMNS = {COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME, COLUMN_EMAIL};

        public static final String SQL_CREATE = "CREATE TABLE " + TABLE + " (" +
                COLUMN_ID +        " INTEGER PRIMARY KEY," +
                COLUMN_FIRSTNAME + " TEXT," +
                COLUMN_LASTNAME +  " TEXT," +
                COLUMN_EMAIL +     " TEXT)";

        public static final String SQL_DELETE_IF_EXISTS =  "DROP TABLE IF EXISTS " + TABLE;
    }
}
