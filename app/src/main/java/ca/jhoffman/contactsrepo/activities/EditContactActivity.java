package ca.jhoffman.contactsrepo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.dialogs.ConfirmDeleteDialog;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsProvider;

public class EditContactActivity extends AppCompatActivity implements ConfirmDeleteDialog.ConfirmDeleteDialogListener {

    public static final String ITEM_INDEX_EXTRA = "ca.jhoffman.ITEM_INDEX_EXTRA";

    private Contact editingContact;

    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Button deleteButton = (Button)findViewById(R.id.activity_edit_contact_button_delete);
        deleteButton.setOnClickListener(deleteButtonOnClickListener);

        Button editButton = (Button)findViewById(R.id.activity_edit_contact_button_save);
        editButton.setOnClickListener(editButtonOnClickListener);

        int contactId = getIntent().getIntExtra(ITEM_INDEX_EXTRA , -1);
        editingContact = ContactsProvider.getInstance().getItemById(contactId, this);

        if (editingContact == null) {
            finish();
        } else {
            firstnameEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_firstname);
            lastnameEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_lastname);
            emailEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_email);

            firstnameEditText.setText(editingContact.getFirstname());
            lastnameEditText.setText(editingContact.getLastname());
            emailEditText.setText(editingContact.getEmail());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.edit_contact_menu_email_item:
                sendEmail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);

        intent.setData(Uri.parse("mailto:")); // only email apps will handle this intent
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{editingContact.getEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Message from ContactsRepo");
        intent.putExtra(Intent.EXTRA_TEXT, "This is the message\n\n add your custom test here....");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private View.OnClickListener deleteButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new ConfirmDeleteDialog().show(getSupportFragmentManager(), "confirm_delete_dialog");
        }
    };

    private View.OnClickListener editButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String firstname = firstnameEditText.getText().toString();
            String lastname = lastnameEditText.getText().toString();
            String email = emailEditText.getText().toString();

            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
                Toast.makeText(EditContactActivity.this, R.string.activity_edit_contact_invalid_infos_message, Toast.LENGTH_LONG).show();
            } else {

                editingContact.update(firstname, lastname, email);
                ContactsProvider.getInstance().updateContact(editingContact, EditContactActivity.this);
                finish();
            }
        }
    };

    //
    // ConfirmDeleteDialogListener
    //

    @Override
    public void onConfirmedDelete() {
        ContactsProvider.getInstance().deleteItemById(editingContact.getId(), this);
        finish();
    }
}
