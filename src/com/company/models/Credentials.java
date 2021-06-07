package com.company.models;

public class Credentials {
    private String username;
    private String password;
    private String email;
    private String cellNumber;

    public Credentials(String username, String password, String email, String cellNumber) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setCellNumber(cellNumber);
    }

    public boolean verifyCredentials(Credentials other) {
        if (other.getPassword() == getPassword()) {
            return other.getUsername() == getUsername() || other.getEmail() == getEmail();
        }
        return false;
    }

    public boolean verifyCredentials(String usernameOrEmail, String password) {

        return (usernameOrEmail.equals(getUsername()) || usernameOrEmail.equals(getEmail())) && password.equals((getPassword()));
        //return (usernameOrEmail == this.username || usernameOrEmail == this.email) && password == this.password;
    }

    //region Getter and Setters
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String value) {
        this.username = value;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String value) {
        cellNumber = value;
    }

    //endregion
}