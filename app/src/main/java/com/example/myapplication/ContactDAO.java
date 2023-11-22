package com.example.myapplication;

        import android.content.ContentValues;
        import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;

         import java.util.ArrayList;
 import java.util.List;

        public class ContactDAO {
    private DBHelper dbHelper;
    private List<Contact> contacts;
    public List<String> names;

            ContactDAO(MainActivity context) {
                dbHelper = new DBHelper(context);
                contacts = new ArrayList<>();
                names = new ArrayList<>();
                load();
            }

            private void load() {
                names.clear();
                contacts.clear();
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(Contact.TABLE_NAME, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME_NAME));
                        String email = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME_EMAIL));
                        String phone = cursor.getString(cursor.getColumnIndex(Contact.COLUMN_NAME_PHONE));
                        contacts.add(new Contact(name, email, phone));
                        names.add(name);
                    }
                cursor.close();
            }

            public void insert(Contact contact) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Contact.COLUMN_NAME_NAME, contact.getName());
                values.put(Contact.COLUMN_NAME_EMAIL, contact.getEmail());
                values.put(Contact.COLUMN_NAME_PHONE, contact.getPhone());
                db.insert(Contact.TABLE_NAME, null, values);
                contacts.add(contact);
                names.add(contact.getName());
            }

            public void update(Contact contact) {
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();

                values.put(Contact.COLUMN_NAME_NAME, contact.getName());
                values.put(Contact.COLUMN_NAME_EMAIL, contact.getEmail());
                values.put(Contact.COLUMN_NAME_PHONE, contact.getPhone());

                String selection = Contact.COLUMN_NAME_NAME + " = ? ";
                String[] selectionArgs = {contact.getName()};

                db.update(Contact.TABLE_NAME, values, selection, selectionArgs);

                Contact c = getByName(contact.getName());
                c.setName(contact.getName());
                c.setEmail(contact.getEmail());
                c.setPhone(contact.getPhone());
            }


            public void delete(Contact contact) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selection = Contact.COLUMN_NAME_NAME + " = ?";
                String[] selectionArgs = {contact.getName()};
                db.delete(Contact.TABLE_NAME, selection, selectionArgs);
                contacts.remove(contact);
                names.remove(contact.getName());
            }

            public Boolean contactExist(String name) {
                return getByName(name) != null;
            }

            public Contact getByName(String name) {
                for (Contact contact : contacts) {
                        if (contact.getName().equals(name)) {
                                return contact;
                            }
                    }
                return null;
            }

            public int size() {
                return contacts.size();
            }
}

