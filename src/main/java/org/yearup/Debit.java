package org.yearup;

public class Debit
{
    private String cardNumber;
    private String expireDate;
    private String csv;

    public Debit(String cardNumber, String expireDate, String csv)
    {
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.csv = csv;
    }
    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getExpireDater()
    {
        return expireDate;
    }

    public void setExpireDate(String expireDater)
    {
        this.expireDate = expireDater;
    }

    public String getCsv()
    {
        return csv;
    }

    public void setCsv(String csv)
    {
        this.csv = csv;
    }
    //@Override
    public String toString() {
        return String.format("%s|%s|%s", cardNumber,expireDate,csv);
    }
}
