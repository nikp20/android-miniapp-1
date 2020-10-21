package si.uni_lj.fri.pbd.miniapp1;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class ContactsFragment extends Fragment {

    private List<ContactsModel> contactList=new ArrayList<>();
    private HashSet<String> addedNames=new HashSet<>();
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private View view;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() { return new ContactsFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        UserModel model = new ViewModelProvider(requireActivity()).get(UserModel.class);
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        System.out.println(settings.getBoolean("my_first_time", true));
        if (settings.getBoolean("my_first_time", true)) {
            showContacts();
            model.setContactsList(contactList);
            settings.edit().putBoolean("my_first_time", false).apply();
        }
        adapter = new RecyclerAdapter(getContext(), model.getContactsList(), model);

       /* if(model.getContactsList().size()!=contactList.size()){
            model.setContactsList(contactList);
        }*/
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void getContacts(){
        CheckBox checkBox=view.findViewById(R.id.checkBox);
        Cursor cur=getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null);
        while(cur.moveToNext()) {
            String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor emailCur = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?", new String[]{id}, null);
            Cursor phone = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?", new String[]{id}, null);
            if (phone != null && phone.getCount() > 0 && emailCur != null && emailCur.getCount() > 0){
                if(phone.moveToFirst() && emailCur.moveToFirst()) {
                    int phoneNum = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int emailIdx = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                    String number = phone.getString(phoneNum);
                    String email = emailCur.getString(emailIdx);
                    if (!addedNames.contains(name)) {
                        ContactsModel contactsModel = new ContactsModel(name, email, number);
                        contactList.add(contactsModel);
                        addedNames.add(name);
                    }
                }
            }
            if(phone!=null)
                phone.close();
            if(emailCur!=null)
                emailCur.close();
        }

        cur.close();
    }
    private void showContacts() {
        // Check whether the permission is already granted or not.
        if (checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point wait for callback in onRequestPermissionsResult overriden method
        }
        else {
            getContacts();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(getContext(), "Until you grant the permission, the names cannot be displayed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
