package lab04.eim.systems.cs.pub.ro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button showHideButton;
    Button saveButton;
    Button cancelButton;
    LinearLayout additionalContainer;
    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText companyEditText;
    EditText imEditText;
    EditText jobTitleEditText;
    EditText websiteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showHideButton = (Button) findViewById(R.id.show_hide_add_fields_button);
        saveButton = (Button) findViewById(R.id.save_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        additionalContainer = (LinearLayout)findViewById(R.id.additional_fields_container);
        nameEditText = (EditText)findViewById(R.id.name);
        phoneEditText = (EditText) findViewById(R.id.phone_number);
        emailEditText = (EditText) findViewById(R.id.mail);
        addressEditText = (EditText) findViewById(R.id.address);
        companyEditText = (EditText) findViewById(R.id.company_edit_text);
        imEditText = (EditText) findViewById(R.id.im_edit_text);
        jobTitleEditText = (EditText) findViewById(R.id.job_title_edit_text);
        websiteEditText = (EditText) findViewById(R.id.website_edit_text);

        showHideButton.setOnClickListener(buttonClickListener);
        saveButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);



    }


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.show_hide_add_fields_button:
                    if (additionalContainer.getVisibility() == View.VISIBLE) {
                        showHideButton.setText(getResources().getString(R.string.show_hide_text));
                        additionalContainer.setVisibility(View.INVISIBLE);
                    } else if (additionalContainer.getVisibility() == View.INVISIBLE) {
                        showHideButton.setText(getResources().getString(R.string.hide_text));
                        additionalContainer.setVisibility(View.VISIBLE);
                    }

                    break;

                case R.id.save_button:
                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String jobTitle = jobTitleEditText.getText().toString();
                    String company = companyEditText.getText().toString();
                    String website = websiteEditText.getText().toString();
                    String im = imEditText.getText().toString();

                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    if (name != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                    }
                    if (phone != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                    }
                    if (email != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                    }
                    if (address != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                    }
                    if (jobTitle != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                    }
                    if (company != null) {
                        intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                    }
                    ArrayList<ContentValues> contactData = new ArrayList<>();
                    if (website != null) {
                        ContentValues websiteRow = new ContentValues();
                        websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                        websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                        contactData.add(websiteRow);
                    }
                    if (im != null) {
                        ContentValues imRow = new ContentValues();
                        imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                        imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                        contactData.add(imRow);
                    }
                    intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                    startActivity(intent);
                    break;
                case R.id.cancel_button:
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    finish();
                    break;

            }
        }

    }
}
