package edu.bistu.computer.contactstest;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "myTag";
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = getContentResolver();

        Button readContactsInfo = (Button) findViewById(R.id.read_contacts_info);
        Button addContacts = (Button) findViewById(R.id.add_contacts);
        Button deleteContacts = (Button) findViewById(R.id.delete_contacts);
        Button updateContacts = (Button) findViewById(R.id.update_contacts);
        Button selectContacts = (Button) findViewById(R.id.select_contacts);

        readContactsInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);
                assert cursor != null;
                while (cursor.moveToNext()) {
                    StringBuilder builder = new StringBuilder();

                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    builder.append("id:").append(id);

                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    builder.append("name:").append(name);


                    Cursor phoneNumbers = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);

                    assert phoneNumbers != null;
                    while (phoneNumbers.moveToNext()) {
                        String phoneNumber = phoneNumbers.getString(phoneNumbers.getColumnIndex(ContactsContract.CommonDataKinds
                                .Phone.NUMBER));
                        builder.append("phoneNumber:").append(phoneNumber);
                    }

                    phoneNumbers.close();

                    Cursor email = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id, null, null);
                    assert email != null;
                    while (email.moveToNext()) {
                        String emailText = email.getString(email.getColumnIndex(ContactsContract.CommonDataKinds
                                .Email.DATA));
                        builder.append("email:").append(emailText);
                    }
                    email.close();
                    Log.v(TAG, builder.toString());
                }
                cursor.close();
            }
        });

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "张三";
                String phoneNumber = "110";
                String email = "111111@qq.com";

                ContentValues values = new ContentValues();
                Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
                long id = ContentUris.parseId(uri);


                values.clear();
                values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                values.put(ContactsContract.RawContacts.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, name);
                resolver.insert(ContactsContract.Data.CONTENT_URI, values);

                values.clear();
                values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                values.put(ContactsContract.RawContacts.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
                values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                resolver.insert(ContactsContract.Data.CONTENT_URI, values);

                values.clear();
                values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                values.put(ContactsContract.RawContacts.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
                values.put(ContactsContract.CommonDataKinds.Email.DATA, email);
                values.put(ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK);
                resolver.insert(ContactsContract.Data.CONTENT_URI, values);
            }
        });

        deleteContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "张三";
                Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI,
                        new String[]{ContactsContract.RawContacts.Data._ID}, "display_name=?", new String[]{name}, null);
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    resolver.delete(ContactsContract.RawContacts.CONTENT_URI, "display_name=?", new String[]{name});
                    resolver.delete(ContactsContract.Data.CONTENT_URI, "raw_contact_id=?", new String[]{id + ""});
                }
            }
        });

        updateContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "张三";
                String phone = "999999";
                ContentValues values = new ContentValues();
                values.put("data1", phone);
                resolver.update(ContactsContract.Data.CONTENT_URI, values, "mimetype=? and display_name=?",
                        new String[]{"vnd.android.cursor.item/phone_v2", name});
            }
        });

        selectContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "110";
                Uri uri = Uri.parse("content://com.android.contacts/data/phones/filter/" + number);
                Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(0);
                    Log.i(TAG, name);
                }
                cursor.close();
            }
        });
    }
}
