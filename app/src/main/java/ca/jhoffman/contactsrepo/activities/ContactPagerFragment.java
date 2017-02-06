package ca.jhoffman.contactsrepo.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.Contact;
import ca.jhoffman.contactsrepo.model.ContactsProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactPagerFragment extends Fragment {
    private static final String KEY_ARG_ID = "ca.jhoffman.contactsrepo.KEY_ARG_ID";

    public ContactPagerFragment() {
        // Required empty public constructor
    }

    public static ContactPagerFragment instantiate(Contact contact) {
        ContactPagerFragment contactPagerFragment = new ContactPagerFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ARG_ID, contact.getId());
        contactPagerFragment.setArguments(args);

        return contactPagerFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_pager, container, false);
        int contactId = getArguments().getInt(KEY_ARG_ID);
        Contact contact = ContactsProvider.getInstance().getItemById(contactId, getActivity());

        EditText firstnameEditText = (EditText)view.findViewById(R.id.form_contact_infos_edittext_firstname);
        firstnameEditText.setText(contact.getFirstname());
        setEditTextReadOnly(firstnameEditText);

        EditText lastnameEditText = (EditText)view.findViewById(R.id.form_contact_infos_edittext_lastname);
        lastnameEditText.setText(contact.getLastname());
        setEditTextReadOnly(lastnameEditText);

        EditText emailEditText = (EditText)view.findViewById(R.id.form_contact_infos_edittext_email);
        emailEditText.setText(contact.getEmail());
        setEditTextReadOnly(emailEditText);

        return view;
    }

    private void setEditTextReadOnly(EditText editText) {
        editText.setTextIsSelectable(true);
        editText.setKeyListener(null);
    }
}
