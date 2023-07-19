public interface administratorOperations {
        /**
         *
         * @return returns customer details as result
         */
        int displayAllCustomers();

        /**
         *
         * @return returns the details of all the issued cards
         */
        int displayAllIssuedCards();

        /**
         *
         * @param name - Customer name as input
         * @param PAN - Customer pan number as input
         * @return returns a success or failure message on customer add operation
         */
        int addNewCustomer(String name, int PAN);

        /**
         *
         * @return returns a success or failure message on credit card creation
         */
        int issueCreditCard();
        /**
         * Displays the list of all the blocked cards
         */
        void displayBlockedCards();

        /**
         *
         * @return returns a success or failure message on event of clocking a credit card
         */
        int blockCard();
    }

