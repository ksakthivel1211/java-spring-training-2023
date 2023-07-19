import java.util.ArrayList;
import java.util.Scanner;

/**
 * AbcBank class has the main implementation of the customer operations and administration operation
 * and also has the array list of customers, credit cards and requests
 */
public class Bank implements administratorOperations,customerOperations
{
    Scanner sc = new Scanner(System.in);
    ArrayList<Customer> customers=new ArrayList<>();
    ArrayList<CreditCard> creditCards = new ArrayList<>();
    ArrayList<Requests> requests = new ArrayList<>();

    @Override
    public int displayAllCustomers() {
        if(customers.size()>0)
        {
            for (Customer customerObj:customers) {
                System.out.println("Customer name: \n"+customerObj.name +"\nPan number :\n"+ customerObj.PAN);
            }
        }
        else {
            System.out.println("There are no customers yet");
        }
        return 1;
    }
    @Override
    public int displayAllIssuedCards() {
        if(creditCards.size()>0)
        {
            System.out.println("Issued cards:");
            for (CreditCard creditCardObj:creditCards) {
                if(creditCardObj.status)
                    System.out.println("Card number: "+creditCardObj.cardNumber+"\nCard type"+ creditCardObj.type);
            }
        }
        else
        {
            System.out.println("There are no credit cards issued");
        }
        return 1;
    }

    @Override
    public int addNewCustomer(String name, int PAN) {
        Customer customerObj = new Customer(name, PAN);
        customers.add(customerObj);
        System.out.println("New customer added :" + name);
        return 1;
    }

    @Override
    public int issueCreditCard() {

        int choice=1,pan,arrSize;

        while (choice==1) {
            for (Requests requestObj : requests) {
                if (requestObj.type.equals("creditCardApply")) {
                    System.out.println("This are the customers requested to be issue cards:\n Customer name:" + requestObj.name + "\n PAN number: " + requestObj.PAN);
                }
            }
            System.out.println("Enter the customer PAN number to issue credit card:");
            pan= sc.nextInt();

            for (Requests requestObj : requests) {
                int cardLimit=0;
                for (CreditCard creditCardObj:creditCards) {

                    if(creditCardObj.pan==pan)
                    {
                        cardLimit = cardLimit + 1;
                    }
                }

                if(cardLimit<5){
                    if (requestObj.PAN==pan) {
                        CreditCard creditCardObj = new CreditCard(creditCards.size()+1001,requestObj.PAN,true,requestObj.cardType);
                        creditCards.add(creditCardObj);
                        System.out.println("Credit card has been successfully created\n Card Number:"+creditCardObj.cardNumber
                                +"\nCustomer name:"+ requestObj.name);
                        break;
                    }
                }

                else {
                    System.out.println("The customer already has maximum number number of credit cards..");
                }

            }

            arrSize = requests.size()-1;
            while(arrSize>=0)
            {
                if((requests.get(arrSize)).PAN==pan){
                    requests.remove(arrSize);
                    break;
                }
                arrSize--;
            }

            System.out.println("1. View credit card issue requests  \n2. exit");
            choice = sc.nextInt();
        }
        return 1;
    }

    @Override
    public void displayBlockedCards() {
        int blockCount =0;
        System.out.println("Blocked Cards:\n");
        for (CreditCard creditCardObj:creditCards) {
            if(!(creditCardObj.status))
                System.out.println("Blocked Card number: "+creditCardObj.cardNumber+"\n");
            blockCount++;
        }
        if(blockCount>0)
        {
            System.out.println("No credit cards are blocked :)");
        }
    }

    @Override
    public int blockCard() {
        int choice=1,cardNo,panNumber;
        while (choice!=3) {
            System.out.println("1. View blocked card requests \n2.View issued cards to block \n3. exit");
            choice = sc.nextInt();
            if(choice==1||choice==2)
            {
                if(choice==1)
                {
                    System.out.println("This are the cards requested to be blocked:\n");
                    for (Requests requestObj : requests) {
                        if (requestObj.type.equals("creditCardBlock")) {
                            for (CreditCard creditObj : creditCards) {
                                if (requestObj.PAN == creditObj.pan) {
                                    System.out.println("Card number:" + requestObj.cardNo + "\n");
                                }
                            }
                        }
                    }
                } else  {
                    System.out.println("Issued cards:\n");
                    for (CreditCard creditCardObj:creditCards) {
                        if(creditCardObj.status)
                            System.out.println("Card number: "+creditCardObj.cardNumber+"\n");
                    }
                }

                System.out.println("Enter the card number to be blocked:");
                cardNo= sc.nextInt();
                System.out.println("Enter the customers pan number");
                panNumber = sc.nextInt();
                for (CreditCard creditObj : creditCards) {
                    if (creditObj.cardNumber == cardNo) {
                        if(creditObj.pan == panNumber)
                        {
                            boolean confirm = false;
                            System.out.println("Confirm to block number: "+ cardNo);
                            confirm = sc.nextBoolean();
                            if(confirm)
                            {
                                creditObj.status=false;
                                System.out.println("Card number:" + cardNo + " is blocked\n");
                            }
                            else {
                                System.out.println("Card blocking has failed");
                            }
                        }

                    }
                }
            }
            else if(choice!=3){
                System.out.println("Invalid Input");
            }
        }
        return 1;
    }

    @Override
    public int creditCardApply(String name, int pan,String cardType) {
        for (Customer customerObj:customers) {
            if(name.equals(customerObj.name)&&pan==customerObj.PAN)
            {
                int cardLimit=0;
                for (CreditCard creditCardObj:creditCards) {
                    if(creditCardObj.pan==customerObj.PAN)
                    {
                        cardLimit = cardLimit + 1;
                    }
                }
                if(cardLimit<5)
                {
                    Requests requestObj = new Requests(name,pan,"creditCardApply",cardType);
                    requests.add(requestObj);
                    System.out.println("Your request for credit card has been registered");
                }
                else
                {
                    System.out.println("A person can have only 5 credit card , Unaku avlodaan limit uh");
                }
            }
        }
        return 1;
    }

    @Override
    public int viewBalance(int cardNumber) {
        for (CreditCard creditCardObj:creditCards) {

            if(creditCardObj.cardNumber==cardNumber)
                System.out.println("Card Balance: "+creditCardObj.balance);
            else
                System.out.println("Card number is wrong");
        }
        return 1;
    }
    @Override
    public int blockCreditCard(int cardNo ,int pan) {
        for (CreditCard creditCardObj:creditCards) {

            if(creditCardObj.cardNumber==cardNo)
            {
                Requests requestObj = new Requests(cardNo,pan,"creditCardBlock");
                requests.add(requestObj);
                System.out.println("Your request for blocking your credit card has been registered \n Requested card number : "+ cardNo);
            }
        }
        return 1;
    }
    @Override
    public int purchase(int cardNo,int amount) {
        for (CreditCard creditCardObj : creditCards) {
            if(creditCardObj.status) {
                if (creditCardObj.cardNumber == cardNo) {
                    creditCardObj.balance = creditCardObj.balance - amount;
                    System.out.println("Rupees" + amount + " has been debited from your account");
                }
            }
            else
            {
                System.out.println("your card is been blocked, unable to withdraw");
            }
        }
        return 1;
    }

    @Override
    public int deposit(int cardNo, int amount) {
        for (CreditCard creditCardObj : creditCards) {
            if(creditCardObj.status)
            {
                if (creditCardObj.cardNumber == cardNo) {
                    creditCardObj.balance = creditCardObj.balance + amount;
                    System.out.println("Rupees" + amount + " has been credited to your account");
                }
            }
            else
            {
                System.out.println("your card is been blocked, unable to deposit");
            }
        }
        return 1;
    }
    void displayUserChoices()
    {
        System.out.println("Welcome user to bank of banking: \n Enna pannanu nu select pannunga? \n " +
                "1. apply for new credit card \n 2. view balance \n 3. close/block credit cards \n 4. Deposit amount \n 5. Withdraw amount \n 6. Logout");
    }
    void displayAdministratorChoices()
    {
        System.out.println("Welcome admin bro: \n Enna pannanu nu select pannunga? \n 1. view all customer data \n " +
                "2. view all customer issued cards \n 3. Add a new customer \n 4. issue new credit card \n " +
                "5. view blocked cards \n 6. close/block credit cards \n 7. Logout");
    }
}
