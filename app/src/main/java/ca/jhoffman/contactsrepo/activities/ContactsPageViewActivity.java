package ca.jhoffman.contactsrepo.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import ca.jhoffman.contactsrepo.R;
import ca.jhoffman.contactsrepo.model.adapters.ContactsViewPagerAdapter;

public class ContactsPageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_page_view);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.activity_contacts_pageview_viewpager);
        final ContactsViewPagerAdapter adapter = new ContactsViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        ImageButton backPagerButton = (ImageButton) findViewById(R.id.activity_contacts_pageview_back_pager);
        backPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        ImageButton forwardPagerButton = (ImageButton) findViewById(R.id.activity_contacts_pageview_forward_pager);
        forwardPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });
    }
}
