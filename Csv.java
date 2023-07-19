import java.util.*;

public class Csv {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CsvProcess csvObj = new CsvProcess();
        int userChoice = 0, operationChoices = 0;
        while (true) {
            System.out.println("CSV file operation system:\n 1.Upload a CSV file\n 2.Exit");
            userChoice = sc.nextInt();
            if (userChoice == 1) {
                int readStatus = csvObj.readCsv();
                if (readStatus == 1) {
                    System.out.println("File is read Successfully\n");
                    while (operationChoices != 8) {
                        System.out.println("Operations onn the read file: \n1.Find the unique regions \n2.Find the unique years \n3.Find the total birth and death count" +
                                "\n4.Find the total birth and death count year wise \n5.Find the total birth and death count for a specific region \n6.Find which year " +
                                "has the highest birth and death rate \n7.Find which  has the highest birth and death rate\n8.Exit");
                        operationChoices = sc.nextInt();

                        switch (operationChoices) {
                            case 1: {
                                csvObj.uniqueRegions();
                                break;
                            }
                            case 2: {
                                csvObj.uniqueYears();
                                break;
                            }
                            case 3: {
                                csvObj.totalCount();
                                break;
                            }
                            case 4: {
                                csvObj.totalCountForYear();
                                break;
                            }
                            case 5: {
                                csvObj.totalCountForRegion();
                                break;
                            }
                            case 6: {
                                csvObj.maxCountYear();
                                break;
                            }
                            case 7: {
                                csvObj.maxCountForRegion();
                                break;
                            }
                            case 8: {
                                break;
                            }
                            default: {
                                System.out.println("Invalid input");
                            }
                        }
                    }
                } else {
                    System.out.println("File not read");
                }
            } else if (userChoice == 2) {
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }


    }
}