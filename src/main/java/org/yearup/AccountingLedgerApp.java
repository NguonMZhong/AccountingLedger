package org.yearup;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp
{
    //Global variables
    static public Scanner scanner = new Scanner(System.in);

    //change this into ArrayList vs hashMap for easier access to data
    public void run()
    {
        homeScreen();

    }

    public static ArrayList<Account> loadTransactions()
    {
        //create the arrays
        ArrayList<Account> accounts = new ArrayList<>();

        //load the array
        //FileInputStream stream;
        //Scanner fileScanner = null;

        try (Scanner scanner = new Scanner(new File("transactions.csv"))) {
            scanner.nextLine(); // skip header row
            int lineNum = 2; // start at line 2 (since we skipped the header)
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) {
                    continue; // skip empty lines
                }
                String[] fields = line.split("\\|");
                if (fields.length != 5) {
                    System.out.printf("Error: Line %d has %d fields instead of 5: %s\n", lineNum, fields.length, line);
                    continue; // skip lines with wrong number of fields
                }
                try {
                    LocalDate date = LocalDate.parse(fields[0]);
                    LocalTime time = LocalTime.parse(fields[1]);
                    String description = fields[2];
                    String vendor = fields[3];
                    double amount = Double.parseDouble(fields[4]);
                    Account account = new Account(date, time, description, vendor, amount);
                    accounts.add(account);
                } catch (DateTimeParseException | NumberFormatException e) {
                    System.out.println("Error: Line " + lineNum + " has invalid data: " + line);
                }
                lineNum++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not open file: " + e.getMessage());
        }
        /*// Print loaded accounts to console
        for (var account : accounts) {
            System.out.println(account);
        }

         */
        return accounts;


    }

    public void homeScreen()
    {
        while (true)
        {
            System.out.println();
            System.out.println("Please selection an option below.");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter: ");
            String respond = scanner.nextLine();

            switch (respond.toUpperCase())
            {
                case "D" ->
                {
                    System.out.println("Navigating to add your deposits...");
                    addDeposit();

                }
                case "P" ->
                {
                    System.out.println("Navigating to make your payment...");
                    addPayment();
                }
                case "L" ->
                {
                    System.out.println("Displaying the ledger menu...");
                    ledger();
                }
                case "X" ->
                {
                    System.out.println("Exit");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

    }

    private void addDeposit()
    {
        try
        {
            //Scanner input = new Scanner(System.in);

            System.out.println();
            System.out.println("Please enter your deposit information.");

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            System.out.println("Deposit date: " + date);
            System.out.println("Deposit time: " + time);

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            // Consume the remaining newline character
            //scanner.nextLine();

            //log as negative number for payment

            double amount = 0;
            boolean validAmount = false;

            while (!validAmount) {
                System.out.print("Enter amount: ");
                amount = scanner.nextDouble();
                scanner.nextLine();

                if (amount <= 0) {
                    System.out.println("Amount is insufficient. Enter a number greater than 0.");
                } else {
                    validAmount = true;
                }
            }


            //create Account Object
            var accounts = new Account(date, time, description, vendor, amount);

            //Create ArrayList to hold Account objects
            ArrayList<Account> accountList = new ArrayList<>();

            accountList.add(accounts);


            try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.csv", true)))
            {
                writer.println(accounts.toString());
            }
        } catch (IOException e)
        {
            System.out.println("Error writing file: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("An error occured: " + e.getMessage());
        }

        finally
        {
            System.out.println();
            System.out.println("You have successfully enter your deposit information!");
            System.out.println("Returning to Home Page...");
        }
    }

    public void addPayment()
    {
        boolean inputError = false;

        try
        {
            //Scanner input = new Scanner(System.in);

            System.out.println();
            System.out.println("Please enter your debit information.");

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            System.out.println("Deposit date: " + date);
            System.out.println("Deposit time: " + time);

            System.out.print("Enter a description or reason for the debit: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            // Consume the remaining newline character
            //scanner.nextLine();

            //log as negative number for payment

            double amount = 0;
            boolean validAmount = false;

            while (!validAmount) {
                System.out.println("Enter debit amount: ");
                amount = scanner.nextDouble();
                scanner.nextLine();

                if (amount <= 0) {
                    System.out.println("Amount is insufficient. Enter a number greater than 0.");
                } else {
                    validAmount = true;
                }
            }

            amount = -amount;


            //create Account Object
            var accounts = new Account(date, time, description, vendor, amount);

            //Create ArrayList to hold Account objects
            ArrayList<Account> accountList = new ArrayList<>();

            accountList.add(accounts);


            try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.csv", true)))
            {
                writer.println(accounts.toString());
            }
        } catch (IOException e)
        {
            System.out.println("Error writing file: " + e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("An error occurred: " + e.getMessage());
        }
        finally
        {
            System.out.println();
            System.out.println("You have successfully enter your debit information!");
            System.out.println("Returning to Home Page...");
        }
    }


    public void ledger()
    {
        while (true)

        {
            System.out.println();
            System.out.println("Please select a ledger option below.");
            System.out.println("A) All (Display all entries.");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home Screen");
            System.out.print("Enter: ");
            String respond = scanner.nextLine();

            switch (respond.toUpperCase())
            {
                case "A" ->
                {
                    System.out.println("Displaying all entries...");
                    ArrayList<Account> accounts = loadTransactions();
                    for ( var account : accounts)
                    {
                        System.out.println(account);
                    }

                }
                case "D" ->
                {
                    System.out.println("Displaying your deposit information...");
                    showDeposits();
                }
                case "P" ->
                {
                    System.out.println("Displaying your payment information...");
                    showPayments();
                }
                case "R" ->
                {
                    System.out.println("Displaying reports. Please hold...");
                    showReports();
                    return;
                }
                case "H" ->
                {
                    System.out.println("Returning to home page...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
        }

    }

    public void  showDeposits()
    {
        //display information from addDeposits from csv file
        //should only display the positive amount
        ArrayList<Account> accounts = loadTransactions();
        for (var account : accounts)
        {
            if(account.getAmount() > 0)
            {
                System.out.println(account);
            }
        }

    }
    public void showPayments()
    {
        //display information input from addPayments from csv file
        //should only display the negative amount
        ArrayList<Account> accounts = loadTransactions();
        for (var account : accounts)
        {
            if (account.getAmount() <0)
            {
                System.out.println(account);
            }
        }
    }
    public void showReports()
    {
        //allow use to run pre-defined reports or to run a custom search
        //month to date
        //previous month
        while (true)

        {
            System.out.println();
            //this will need to allow user to run pre-defined reports or run a custom search
            System.out.println("Please select the reports you want to run.");
            System.out.println("1) Month To Date.");
            System.out.println("2) Previous Month");
            System.out.println("3) Year to Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            int reply = scanner.nextInt();

            switch (reply)
            {
                case 1 ->
                {
                    //

                }
                case 2 ->
                {
                    //prompt user for debit information and save to csv
                }
                case 3 ->
                {
                    //Display the ledger screen
                }
                case 4 ->
                {
                    System.out.println();
                    //go to Reports;

                }
                case 5 ->
                {
                    System.out.println("Returning to home page...");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
        }

    }

}
