package org.yearup;

import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp
{
    static public Scanner scanner = new Scanner(System.in);
    public void run()
    {
        homeScreen();

    }
    public static ArrayList<Account> loadTransactions()
    {
        //create the arrays
        ArrayList<Account> accounts = new ArrayList<>();

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
        return accounts;
    }

    public void homeScreen()
    {
        while (true)
        {
            System.out.println();
            System.out.println("~ Home Screen ~\n");
            System.out.println("Please selection an option below.");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter: ");
            String respond = scanner.nextLine();
            System.out.println();

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

            var accounts = new Account(date, time, description, vendor, amount);

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
            System.out.println("You have successfully enter your deposit information!");
            System.out.println("Returning to Home Page...");
        }
    }

    public void addPayment()
    {
        //boolean inputError = false;

        try
        {
            System.out.println();
            System.out.println("Please enter your debit information.");

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();

            System.out.println("Deposit date: " + date);
            System.out.println("Deposit time: " + time);

            System.out.print("Enter a description: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

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
            System.out.println("A) All (Display all entries)");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home Screen");
            System.out.print("Enter: ");
            String respond = scanner.nextLine();
            System.out.println();

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
                    break;
                }
                case "P" ->
                {
                    System.out.println("Displaying your payment information...");
                    showPayments();
                    break;
                }
                case "R" ->
                {
                    System.out.println("Displaying reports. Please hold...");
                    showReports();
                    break;
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
            System.out.println("0) Back");
            System.out.print("Enter: ");
            int reply = scanner.nextInt();
            System.out.println();

            switch (reply)
            {
                case 1 ->
                {
                    monthToDate();

                }
                case 2 ->
                {
                    previousMonth();
                    break;
                }
                case 3 ->
                {
                    yearToDate();
                    break;

                }
                case 4 ->
                {
                    previousYear();
                    break;
                }
                case 5 ->
                {
                    searchByVendor();
                    break;
                }
                case 0 ->
                {
                    System.out.println("Returning to home page...");
                    scanner.nextLine();
                    homeScreen();
                }
                default -> System.out.println("Invalid option. Please try again.\n");
            }
        }

    }
    private void monthToDate()
    {
        System.out.println();
        System.out.println("Month to Date report \n");
        //Load transaction from csv file
        ArrayList<Account> accounts = loadTransactions();

        //filter transaction from current month
        LocalDate now = LocalDate.now();
        for (var account : accounts)
        {
            //if the date is within this month print it to the console from transaction file.
            if (account.getDate().getMonth() == now.getMonth())
            {
                //load the transaction with all the current date of this month
                System.out.println(account);
            }
        }

    }
    public void previousMonth()
    {
        System.out.println("Previous Month Report \n");

        ArrayList<Account> accounts = loadTransactions();

        // Get the current date and calculate the start and end dates of the previous month
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());

        // Filter transactions for the previous month
        for (Account account : accounts) {
            LocalDate transactionDate = account.getDate();
            if (transactionDate.isAfter(ChronoLocalDate.from(firstDayOfPreviousMonth.atStartOfDay()))
                    && transactionDate.isBefore(ChronoLocalDate.from(lastDayOfPreviousMonth.plusDays(1).atStartOfDay()))) {
                System.out.println(account);
            }
        }

    }
    public void yearToDate()
    {
        System.out.println("List of transaction made this year. \n");

        ArrayList<Account> accounts = loadTransactions();

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        //filter transactions made this year
        for (var account : accounts)
        {
            LocalDate transactionDate = account.getDate();
            if (transactionDate.getYear() == currentYear)
            {
                System.out.println(account);
            }
        }
    }
    public void previousYear()
    {
        System.out.println("List of transaction made in the previous years. \n");

        ArrayList<Account> accounts = loadTransactions();

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousYear = today.minusYears(1).withDayOfYear(1);
        LocalDate lastDayOfPreviousYear = today.minusYears(1).withDayOfYear(today.minusYears(1).lengthOfYear());

        //filter transactions made this year
        for (var account : accounts)
        {
            LocalDate previousYearDate = account.getDate();
            if (previousYearDate.isBefore(ChronoLocalDate.from(firstDayOfPreviousYear.atStartOfDay()))
                    && previousYearDate.isBefore(ChronoLocalDate.from(lastDayOfPreviousYear.plusDays(1).atStartOfDay())))
            {
                System.out.println(account);
            }
        }

    }
    public void searchByVendor()
    {
        System.out.println();
        System.out.println("Enter the vendor you wish to search for.\n");
        System.out.print("Enter: ");
        scanner.nextLine();
        String searchVendor = scanner.nextLine();

        ArrayList<Account> accounts = loadTransactions();
        boolean matchFound = false;

        for (var account : accounts)
        {
            if (account.getVendor().equalsIgnoreCase(searchVendor))
            {
                System.out.println(account);
                matchFound = true;
            }
        }

        if (!matchFound)
        {
            System.out.println("No transactions found for vendor " + searchVendor);
        }


    }

}
