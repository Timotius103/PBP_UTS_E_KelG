package platformpbp.uajy.quickresto;

public class UserClass {
    private String fullName;
    private String phone;
    private String mail;
    private String pass;

    public UserClass(){
    }

    public UserClass(String fullName, String phone, String mail, String pass) {
        this.fullName = fullName;
        this.phone = phone;
        this.mail = mail;
        this.pass = pass;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
