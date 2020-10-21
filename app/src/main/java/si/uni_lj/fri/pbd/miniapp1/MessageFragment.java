package si.uni_lj.fri.pbd.miniapp1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class MessageFragment extends Fragment {
    private List<ContactsModel> listContacts;
    private int numElemes;
    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance() { return new MessageFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        numElemes=0;
        View view=inflater.inflate(R.layout.fragment_message, container, false);
        UserModel model = new ViewModelProvider(requireActivity()).get(UserModel.class);
        listContacts=model.getContactsList();
        String[] contacts=new String[listContacts.size()];
        String[] numbers=new String[listContacts.size()];
        int i=0;
        for(ContactsModel c : listContacts){
            if(c.isSelected()) {
                if (i != listContacts.size() - 1) {
                    contacts[numElemes] = c.getEmail() + ";";
                    numbers[numElemes]=c.getNumber()+";";
                    numElemes++;
                }
                else if (i == listContacts.size() - 1){
                    contacts[numElemes] = c.getEmail();
                    numbers[numElemes]=c.getNumber();
                    numElemes++;
                }
            }
            i++;
        }
        int num=0;
        String[] contacts2=new String[numElemes];
        String numbers2="";
        for (String s : contacts) {
            if (s != null) {
                contacts2[num] = s;
                num++;
            }
        }
        int j=0;
        for (String s : numbers) {
            if (s != null) {
                numbers2+= s;
            }
        }
        Button emailButton=(Button) view.findViewById(R.id.email_button);
        Button mmsButton=(Button) view.findViewById(R.id.mms_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numElemes>0) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, contacts2);
                    i.putExtra(Intent.EXTRA_SUBJECT, "Sample subject");
                    i.putExtra(Intent.EXTRA_TEXT, "Sample text");
                    try {
                        startActivity(Intent.createChooser(i, "Send mail:"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(requireActivity(), "There are no selected contacts.", Toast.LENGTH_SHORT).show();
                }

            }



        });
        String finalNumbers = numbers2;
        mmsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(finalNumbers.length()>0) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + finalNumbers));
                    intent.putExtra("sms_body", "Sample text");
                    startActivity(Intent.createChooser(intent, "Send mms:"));
                }
                else{
                    Toast.makeText(requireActivity(), "There are no selected contacts.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;

    }


}
