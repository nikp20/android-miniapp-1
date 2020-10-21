package si.uni_lj.fri.pbd.miniapp1;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {

    private Context recycleContext;
    private List<ContactsModel> recycleListContacts;
    private UserModel model;

    class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView contact_name;
        public CheckBox checkBox;

        public CardViewHolder(View itemView) {
            super(itemView);
            contact_name=itemView.findViewById(R.id.contact_name);
            checkBox=itemView.findViewById(R.id.checkBox);
        }
    }
    public RecyclerAdapter(Context context, List<ContactsModel> contacts, UserModel model){
        this.model=model;
        recycleListContacts=contacts;
        recycleContext=context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contacts_model, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder viewHolder, int i) {
        TextView contact_name;
        contact_name=viewHolder.contact_name;
        String nameAndEmail=recycleListContacts.get(i).getName()+"\n"+recycleListContacts.get(i).getEmail();
        contact_name.setText(nameAndEmail);
        if(!model.getCheckList().containsKey(i)){
            model.getCheckList().put(i, false);
        }
        if(!model.getCheckList().isEmpty() ) {
            viewHolder.checkBox.setChecked(model.getCheckList().get(i));
        }
        else
            viewHolder.checkBox.setChecked(false);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                model.getCheckList().put(i, isChecked);
                model.getContactsList().get(i).setSelected(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return model.getContactsList().size();
    }
}
