public class Hdfc extends Bank
{
    @Override
    public int displayAllCustomers() {
        return super.displayAllCustomers();
    }

    @Override
    public int displayAllIssuedCards() {
        return super.displayAllIssuedCards();
    }

    @Override
    public int addNewCustomer(String name, int PAN) {
        return super.addNewCustomer(name, PAN);
    }

    @Override
    public int issueCreditCard() {
        return super.issueCreditCard();
    }

    @Override
    public void displayBlockedCards() {
        super.displayBlockedCards();
    }

    @Override
    public int blockCard() {
        return super.blockCard();
    }

    @Override
    public int creditCardApply(String name, int pan, String cardType) {
        return super.creditCardApply(name, pan, cardType);
    }

    @Override
    public int viewBalance(int cardNumber) {
        return super.viewBalance(cardNumber);
    }

    @Override
    public int blockCreditCard(int cardNo, int pan) {
        return super.blockCreditCard(cardNo, pan);
    }

    @Override
    public int deposit(int cardNo, int amount) {
        return super.deposit(cardNo, amount);
    }

    @Override
    public int purchase(int cardNo, int amount) {
        return super.purchase(cardNo, amount);
    }
}
