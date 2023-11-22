package com.example.myapplication;

        import android.app.AlertDialog.Builder;
  import android.content.DialogInterface;
  import android.os.Bundle;
  import androidx.appcompat.app.AppCompatActivity;
  import android.view.View;
  import android.view.View.OnClickListener;
  import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.EditText;
 import android.widget.ListView;

         public class MainActivity extends AppCompatActivity implements OnClickListener {
     private EditText editTextName;
     private EditText editTextEmail;
     private EditText editTextPhone;
     private ArrayAdapter<String> adapter;
     private ContactDAO contactDAO;

     @Override
     public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                contactDAO = new ContactDAO(this);

                editTextName = findViewById(R.id.editTextName);
                editTextEmail = findViewById(R.id.editTextEmail);
                editTextPhone = findViewById(R.id.editTextPhone);
                findViewById(R.id.buttonOk).setOnClickListener(this);
                findViewById(R.id.buttonClear).setOnClickListener(this);

                ListView listContacts = findViewById(R.id.listContacts);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, contactDAO.names);

                listContacts.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                listContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             public void onItemClick(AdapterView<?> parent, View view,
                                     int position, long id) {
                                String name = (String) parent.getItemAtPosition(position);
                                Contact contact = (contactDAO.getByName(name));
                                editTextName.setText(contact.getName());
                                editTextEmail.setText(contact.getEmail());
                                editTextPhone.setText(contact.getPhone());
                            }
         });
                listContacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                            int pos, long id) {
                                String name = (String) arg0.getItemAtPosition(pos);
                                Contact contact = (contactDAO.getByName(name));
                                dialogErase(getResources().getString(R.string.error_title),
                                                getResources().getString(R.string.error_noname) + " " + name + "?", contact);
                                return true;
                            }
         });
            }

             @Override
     public void onClick(View v) {
                switch (v.getId()) {
                        case R.id.buttonOk:
                                if (editTextName.getText().toString().trim().equals("")) {
                                        dialog(getResources().getString(R.string.error_title),
                                                        getResources().getString(R.string.error_noname));
                                    } else {
                                        Contact contact = new Contact(
                                                        editTextName.getText().toString(),
                                                        editTextEmail.getText().toString(),
                                                        editTextPhone.getText().toString());
                                        if (contactDAO.contactExist(contact.getName())) {
                                                contactDAO.update(contact);
                                            } else {
                                                contactDAO.insert(contact);
                                            }
                                        adapter.notifyDataSetChanged();
                                    }
                                break;
                        case R.id.buttonClear:
                                editTextName.setText("");
                                editTextEmail.setText("");
                                editTextPhone.setText("");
                                break;
                    }
            }

             public void dialog(String title, String content) {
                Builder builder = new Builder(MainActivity.this);
                builder.setTitle(title);
                builder.setMessage(content);
                builder.setNeutralButton("Ok", null);
                builder.show();
            }

             public void dialogErase(String title, String content, final Contact contact) {
                Builder builder = new Builder(MainActivity.this);
                builder.setTitle(title);
                builder.setMessage(content);
                builder.setCancelable(false);
                builder.setPositiveButton("ok",
                                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                                        contactDAO.delete(contact);
                                        adapter.notifyDataSetChanged();
                                    }
                });
                builder.setNegativeButton("cancel",
                                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                                        // do nothing
                                    }
                });
                builder.show();
            }
}
