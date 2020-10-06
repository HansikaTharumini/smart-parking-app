package com.example.myapplication;

import android.widget.EditText;

public class User
{
  private String nic;
  private String name;
  private String contact;
  private String email;


  public User(EditText nic, EditText name, EditText contact, EditText email) {

  }
  public User( String nic, String name, String contact,String email) {
    this.nic = nic;
    this.name = name;
    this.contact = contact;
    this.email = email;

  }

  public String getNic() {
    return nic;
  }

  public void setNic(String nic) {
    this.nic = nic;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


}

