package si.uni_lj.fri.pbd.miniapp1;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserModel extends ViewModel {
    private List<ContactsModel> contactsList=new ArrayList<>();
    public void setCheckList(HashMap<Integer, Boolean> checkList) {
        this.checkList = checkList;
    }


    public HashMap<Integer, Boolean> getCheckList() {
        return checkList;
    }

    private HashMap<Integer, Boolean> checkList=new HashMap<>();

    public UserModel(List<ContactsModel> contactsList) {
        /*for(int i=0;i<checkList.size();i++){
            checkList.put(i, false);
        }*/
        this.contactsList = contactsList;
    }
    public UserModel(){

    }

    public List<ContactsModel> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<ContactsModel> contactsList) {
        this.contactsList = contactsList;
    }
    public void addToContactsList(ContactsModel contactsModel) {
        this.contactsList.add(contactsModel);
    }
}
