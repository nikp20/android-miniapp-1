package si.uni_lj.fri.pbd.miniapp1;

public class ContactsModel {
    private String name;
    private boolean isSelected;
    private String email;
    private String number;


    public ContactsModel(String name, boolean isSelected, String email) {
        this.name = name;
        this.isSelected = isSelected;
        this.email = email;
        this.number=number;
    }
    public ContactsModel(String name, String email, String number) {
        this.name = name;
        this.email = email;
        this.number=number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
