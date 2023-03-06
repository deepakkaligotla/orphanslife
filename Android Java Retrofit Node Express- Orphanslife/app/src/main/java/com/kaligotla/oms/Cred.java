package com.kaligotla.oms;

public class Cred {

    private String email, password;

    public Cred() {
    }

    public Cred(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Cred{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
