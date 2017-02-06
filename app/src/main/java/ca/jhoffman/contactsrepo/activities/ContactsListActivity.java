package ca.jhoffman.contactsrepo.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsDbHelper;
import ca.jhoffman.contactsrepo.model.ContactsProvider;
import ca.jhoffman.contactsrepo.model.PreferencesManager;
import ca.jhoffman.contactsrepo.model.SortOrder;
import ca.jhoffman.contactsrepo.model.adapters.ContactsAdapter;

public class ContactsListActivity extends AppCompatActivity {
    private static final String KEY_SORT_ORDER = "ca.jhoffman.contactsrepo.KEY_SORT_ORDER";
    private static final String KEY_SEARCH_CRITERIA = "ca.jhoffman.contactsrepo.KEY_SEARCH_CRITERIA";

    private ContactsAdapter contactsAdapter;

    private String searchCriteria;
    private SortOrder sortOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.activity_contacts_list_fab);
        fab.setOnClickListener(addContactFabClickListener);

        PreferencesManager preferencesManager = new PreferencesManager(this);
        if (preferencesManager.isFirstLaunch() == true) {
            ContactsProvider.getInstance().insertDummyData(this);

            preferencesManager.setIsFirstLaunch(false);
        }

        SearchView searchView = (SearchView) findViewById(R.id.activity_contacts_list_searchview);
        searchView.setOnQueryTextListener(queryTextListener);

        Spinner sortSpinner = (Spinner) findViewById(R.id.activity_contacts_list_sort_spinner);
        sortSpinner.setOnItemSelectedListener(onSortSpinnerItemSelected);

        ListView contactsListView = (ListView)findViewById(R.id.activity_contacts_list_listview);
        contactsListView.setOnItemClickListener(onContactRowClickListener);

        if (savedInstanceState != null) {
            searchCriteria = savedInstanceState.getString(KEY_SEARCH_CRITERIA, "");
            sortOrder = SortOrder.createFromInt(savedInstanceState.getInt(KEY_SORT_ORDER, 0));
        } else {
            searchCriteria = "";
            sortOrder = SortOrder.ASC;
        }

        Cursor orderedContactsCursor = ContactsProvider.getInstance().getOrderedContacts(this, searchCriteria, sortOrder);
        contactsAdapter = new ContactsAdapter(this, orderedContactsCursor);
        contactsListView.setAdapter(contactsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshCursor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts_list_menu_pageview_item:
                startActivity(new Intent(ContactsListActivity.this, ContactsPageViewActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private View.OnClickListener addContactFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(ContactsListActivity.this, AddContactActivity.class);
            startActivity(i);
        }
    };

    private AdapterView.OnItemClickListener onContactRowClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Cursor itemCursor = contactsAdapter.getCursor();
            itemCursor.moveToPosition(position);
            Contact contact = ContactsDbHelper.contactFromCursor(itemCursor);

            Intent i = new Intent(ContactsListActivity.this, EditContactActivity.class);
            i.putExtra(EditContactActivity.ITEM_INDEX_EXTRA, contact.getId());
            startActivity(i);
        }
    };

    private SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            Log.d("CR", "submit: " + query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Log.d("CR", "change: " + newText);
            searchCriteria = newText;
            refreshCursor();
            return true; //True: handled by listener
        }
    };

    private AdapterView.OnItemSelectedListener onSortSpinnerItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            sortOrder = SortOrder.createFromInt(position);
            refreshCursor();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_SORT_ORDER, sortOrder.getValue());
        outState.putString(KEY_SEARCH_CRITERIA, searchCriteria);

        super.onSaveInstanceState(outState);
    }

    private void refreshCursor() {
        contactsAdapter.changeCursor(ContactsProvider.getInstance().getOrderedContacts(this, searchCriteria, sortOrder));
        contactsAdapter.notifyDataSetChanged();
    }
}
