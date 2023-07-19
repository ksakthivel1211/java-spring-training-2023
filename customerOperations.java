public interface customerOperations {
    /**
     *
     * @param name - Customer name as input
     * @param Pan - Customer pan number as input
     * @param cardType - card type as input whether it is platinum or gold or diamond
     * @return returns a success or failure message on credit card apply operation
     */
    int creditCardApply(String name,int Pan,String cardType);

    /**
     *
     * @param cardNumber - credit card number is taken as input
     * @return return the balance in the credit card of the input credit card number
     */
    int viewBalance(int cardNumber);

    /**
     *
     * @param cardNumber - credit card number as input
     * @param Pan - pan number as input
     * @return returns a success or failure message on applying for credit card block request
     */
    int blockCreditCard(int cardNumber,int Pan);

    /**
     *
     * @param cardNo - card number as input
     * @param amount - amount to be with drawn as input
     * @return returns a success or failure message on event of amount withdraw
     */
    int purchase(int cardNo,int amount);
    /**
     *
     * @param cardNo - card number as input
     * @param amount - amount to be deposited as input
     * @return returns a success or failure message on event of amount deposit
     */
    int deposit(int cardNo,int amount);
}
