import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * @author sakthivel
 */

public class Banking {
    public static Hdfc hdfcObj = new Hdfc();
    public static SbiBank sbiObj = new SbiBank();
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            while(true) {
                Bank bankObj = null;
                System.out.println("Choose your bank:\n1.HDFC Bank\n2.SBI Bank\n3.Exit");
                int bankChoice = sc.nextInt();
                if(bankChoice==1)
                {
                    bankObj = hdfcObj;
                }
                else if (bankChoice==2)
                {
                    bankObj = sbiObj;
                }
                else if (bankChoice==3)
                {
                    System.out.println("BYE FROM BANKING");
                    break;
                }
                else{
                    System.out.println("Invalid Input");
                }
                int userChoice=0;
                while (userChoice!=3) {
                    System.out.println("Welcome to banking bank.. :) \nSelect the user type: \n 1. Bank Administrator \n 2. Customer \n 3. Exit");
                    userChoice = sc.nextInt();
                    switch (userChoice) {
                        case 1: {
                            int adminChoice = 0;
                            while (adminChoice != 7) {
                                bankObj.displayAdministratorChoices();
                                adminChoice = sc.nextInt();

                                switch (adminChoice) {
                                    case 1: {
                                        bankObj.displayAllCustomers();
                                        break;
                                    }
                                    case 2: {
                                        bankObj.displayAllIssuedCards();
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("Name ");
                                        String name = sc.next();
                                        System.out.println("PAN number ");
                                        int PAN = sc.nextInt();
                                        bankObj.addNewCustomer(name, PAN);
                                        break;
                                    }
                                    case 4: {
                                        bankObj.issueCreditCard();
                                        break;
                                    }
                                    case 5: {
                                        bankObj.displayBlockedCards();
                                        break;
                                    }
                                    case 6: {
                                        bankObj.blockCard();
                                        break;
                                    }
                                    case 7:
                                        break;
                                    default:
                                        System.out.println("invalid input");
                                }
                            }
                            break;
                        }
                        case 2: {
                            int customerChoice = 0;
                            while (customerChoice != 6) {
                                bankObj.displayUserChoices();
                                customerChoice = sc.nextInt();

                                switch (customerChoice) {
                                    case 1: {
                                        System.out.println("Enter your name");
                                        String name = sc.next();
                                        System.out.println("Enter your pan number");
                                        int pan = sc.nextInt();
                                        System.out.println("Enter the type of credit card that you want:\n 1.Platinum \n 2.Gold \n 3.Silver");
                                        String cardType = sc.next();
                                        bankObj.creditCardApply(name, pan, cardType);
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("Enter the card number to view the balance");
                                        int cardNo = sc.nextInt();
                                        bankObj.viewBalance(cardNo);
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("Enter your credit card number to block it");
                                        int cardNumber = sc.nextInt();
                                        System.out.println("Enter your pan number");
                                        int pan = sc.nextInt();
                                        bankObj.blockCreditCard(cardNumber, pan);
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("Enter the card number");
                                        int cardNo = sc.nextInt();
                                        System.out.println("Enter the amount to deposit");
                                        int amount = sc.nextInt();
                                        bankObj.deposit(cardNo, amount);
                                        break;
                                    }
                                    case 5: {
                                        System.out.println("Enter the card number");
                                        int cardNo = sc.nextInt();
                                        System.out.println("Enter the amount to withdraw");
                                        int amount = sc.nextInt();
                                        bankObj.purchase(cardNo, amount);
                                        break;
                                    }
                                    case 6: {
                                        break;
                                    }
                                    default:
                                        System.out.println("invalid input");
                                }
                            }
                            break;
                        }
                        case 3: {
                            System.out.println("Bye");
                            break;
                        }
                        default:
                            System.out.println("Invalid choice");
                    }
                }
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Invalid Input , Try again");
        }

    }
}