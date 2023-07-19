/**
 * Credit card class has all the information of customers credit card
 */
public class CreditCard {
    public int cardNumber;
    public int balance;
    public boolean status;
    public String type;
    public int pan;
    public CreditCard(int cardNumber ,int pan, boolean status, String type) {
        this.cardNumber = cardNumber;
        switch (type){
            case "Platinum" -> this.balance = 10000;
            case "Gold" -> this.balance = 5000;
            case "Silver" -> this.balance = 1000;
            default -> this.balance = 0;
        }
        this.status = status;
        this.pan = pan;
        this.type = type;
    }
}
