package org.yearup;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp
{
    //Global variables
    final static public Scanner scanner = new Scanner(System.in);

    //change this into ArrayList vs hashMap for easier access to data
    public void run()
    {
        homeScreen();

    }

    public static void loadTransactions()
    //Print this to screen when starting project to showcase the current transactions
    //need to read and write to the file
    {
        //create the arrays
        ArrayList<Account> accounts = new ArrayList<>();

        //load the array
        FileInputStream stream;
        Scanner fileScanner = null;

        try
        {
            stream = new FileInputStream("transactions.csv");
            fileScanner = new Scanner(stream);

            fileScanner.nextLine();

            while (fileScanner.hasNext())
            {
                String line = fileScanner.nextLine();

                //create variable for each line
                String[] columns = line.split("\\|");
                LocalDate date = LocalDate.parse(columns[0]);
                LocalTime time = LocalTime.parse(columns[1]);
                String description = columns[2];
                String vendor = columns[3];
                double amount = Double.parseDouble(columns[4]);

                //create account and add to arrayList
                var account = new Account(date, time, description, vendor, amount);
                accounts.add(account);

            }
            //print loadAccount to console
            for (var account : accounts)
            {
                System.out.println(account);
            }
        } catch (FileNotFoundException e)
        {
            System.out.println("Error: " + e);
        } finally
        {
            //close the stream
            if (fileScanner != null)
            {
                fileScanner.close();
            }
        }
        //return the array

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
                    //prompt user for debit information and save to csv
                }
                case "L" ->
                {
                    //Display the ledger screen
                }
                case "X" ->
                {
                    System.out.println("Exit");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
        }

    }

    private void addDeposit()
    {

        try
        {
            Scanner input = new Scanner(System.in);

            System.out.println();
            System.out.println("Please enter your deposit information.");

            //LocalDate currentDate = LocalDate.now();
            //LocalTime currentTime = LocalTime.now();

            //System.out.print("Deposit date: " + currentDate);
            //System.out.print("Deposit time: " + currentTime);

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            System.out.println("Deposit date: " + date);
            System.out.println("Deposit time: " + time);

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Enter amount: ");
            double amount = scanner.nextDouble();

           /* amount = 0;
            boolean validAmount = false;

            while (!validAmount) {
                System.out.println("Enter amount: ");
                amount = input.nextDouble();

                if (amount <= 0) {
                    System.out.println("Amount is insufficient. Enter a number greater than 0.");
                } else {
                    validAmount = true;
                }
            }
            */

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
            String respond = scanner.nextLine();

            switch (respond)
            {
                case "A" ->
                {
                    //

                }
                case "D" ->
                {
                    //prompt user for debit information and save to csv
                }
                case "P" ->
                {
                    //Display the ledger screen
                }
                case "R" ->
                {
                    System.out.println();
                    //go to Reports;

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

    public void Reports()
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
