package com.example.myapplication;

import android.widget.EditText;

public class Payment {
    private int Bookid;
    private float Amount;
    private String ExpireDate;
    private int CardNumber;
    private int CVVNumber;

    public Payment(int bookv, float amount, String expirev, int cardnumv, int cvvv) {
        this.Amount=amount;
        this.Bookid=bookv;
        this.ExpireDate=expirev;
        this.CardNumber=cardnumv;
        this.CVVNumber=cvvv;
    }

    public int getBookid() {
        return Bookid;
    }

    public void setBookid(int bookid) {
        Bookid = bookid;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public int getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(int cardNumber) {
        CardNumber = cardNumber;
    }

    public int getCVVNumber() {
        return CVVNumber;
    }

    public void setCVVNumber(int CVVNumber) {
        this.CVVNumber = CVVNumber;
    }
}
