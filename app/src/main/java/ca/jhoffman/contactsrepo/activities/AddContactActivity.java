package ca.jhoffman.contactsrepo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsProvider;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button addbutton = (Button) findViewById(R.id.activity_add_contact_button_add);
        addbutton.setOnClickListener(addButtonOnClickListener);
    }

    private View.OnClickListener addButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstnameEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_firstname);
            String firstname = firstnameEditText.getText().toString();

            EditText lastnameEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_lastname);
            String lastname = lastnameEditText.getText().toString();

            EditText emailEditText = (EditText)findViewById(R.id.form_contact_infos_edittext_email);
            String email = emailEditText.getText().toString();

            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty()) {
                Toast.makeText(AddContactActivity.this, R.string.activity_add_contact_invalid_infos_message, Toast.LENGTH_LONG).show();
            } else {
                Contact newContact = new Contact(firstname, lastname, email);
                ContactsProvider.getInstance().addItem(newContact, AddContactActivity.this);

                finish();
            }
        }
    };
}
