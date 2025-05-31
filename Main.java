import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BillManagerConsole manager = new BillManagerConsole();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Smart Utility Billing System =====");
            System.out.println("1. Add Bill");
            System.out.println("2. Show All Bills");
            System.out.println("3. Show Bills by User");
            System.out.println("4. Show Bills by Utility");
            System.out.println("5. Export Bills to CSV");
            System.out.println("6. Import Bills from CSV");
            System.out.println("7. Generate Reports");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter utility name (Electricity, Water, Gas): ");
                    String utility = scanner.nextLine();
                    System.out.print("Enter units consumed: ");
                    double units = scanner.nextDouble();
                    double amount = BillCalculator.calculateAmount(utility, units);
                    Bill bill = new Bill(name, utility, units, amount);
                    manager.addBill(bill);
                    System.out.println("Bill added: " + bill);
                }

                case 2 -> manager.getAllBills();

                case 3 -> {
                    System.out.print("Enter user name: ");
                    String user = scanner.nextLine();
                    manager.getBillsByUser(user);

                }

                case 4 -> {
                    System.out.print("Enter utility name: ");
                    String utility = scanner.nextLine();
                    manager.getBillsByUtility(utility);
                }

                case 5 -> {
                    System.out.print("Enter filename to export (with .csv): ");
                    String filename = scanner.nextLine();
                    System.out.println("1. Append to existing file");
                    System.out.println("2. Overwrite existing file");
                    int exportChoice = scanner.nextInt();
                    scanner.nextLine();
                    boolean append = exportChoice == 1;
                    manager.exportBillsToFile(filename, append);
                }

                case 6 -> {
                    System.out.print("Enter filename to import (with .csv): ");
                    String filename = scanner.nextLine();
                    manager.importBillsFromFile(filename);
                }

                case 7 -> showReportMenu(manager, scanner);

                case 8 -> {
                    System.out.println("Exiting...");
                    return;
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public static void showReportMenu(BillManagerConsole manager, Scanner scanner) {
        while (true) {
            System.out.println("\n--- Reports Menu ---");
            System.out.println("1. Total Amount by Utility");
            System.out.println("2. Total Amount by User");
            System.out.println("3. Average Amount by Utility");
            System.out.println("4. Average Amount by User");
            System.out.println("5. Highest Bill by Utility");
            System.out.println("6. Highest Bill by User");
            System.out.println("7. Lowest Bill by Utility");
            System.out.println("8. Lowest Bill by User");
            System.out.println("9. Back to Main Menu");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> manager.generateReportByUtility("sum");
                case 2 -> manager.generateReportByUser("sum");
                case 3 -> manager.generateReportByUtility("avg");
                case 4 -> manager.generateReportByUser("avg");
                case 5 -> manager.generateReportByUtility("max");
                case 6 -> manager.generateReportByUser("max");
                case 7 -> manager.generateReportByUtility("min");
                case 8 -> manager.generateReportByUser("min");
                case 9 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}