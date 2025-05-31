import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BillManagerConsole {
    private final List<Bill> bills = new ArrayList<>();

    public void addBill(Bill bill) {
        bills.add(bill);
        System.out.println("Bill added successfully.");
    }



    public void importBillsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;  // Skip the header
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String userName = parts[0].trim();
                    String utility = parts[1].trim();
                    double Units = Double.parseDouble(parts[2].trim());
                    double amount = Double.parseDouble(parts[3].trim());
                    bills.add(new Bill(userName, utility, Units, amount));
                }
            }
            System.out.println("Bills imported successfully from " + filename);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void exportBillsToFile(String filename, boolean append) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename,append))) {
            for (Bill bill : bills) {
                writer.printf("%s,%s,%.2f,%.2f%n", bill.getUserName(), bill.getUtilityType(), bill.getUnitsConsumed(), bill.getAmount());
            }
            System.out.println("Bills exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public void displayBill(Bill bill) {
        System.out.println("User: " + bill.getUserName());
        System.out.println("Utility: " + bill.getUtilityType());
        System.out.println("Units: " + bill.getUnitsConsumed());
        System.out.println("Amount: " + bill.getAmount());
        System.out.println("-----------------------------");
    }

    public void getAllBills() {
        if (bills.isEmpty()) {
            System.out.println("No bills found.");
        } else {
            bills.forEach(this::displayBill);
        }
    }

    public void getBillsByUser(String user) {
        List<Bill> userBills = bills.stream()
                .filter(b -> b.getUserName().equalsIgnoreCase(user))
                .toList();
        if (userBills.isEmpty()) {
            System.out.println("No bills found for user: " + user);
        } else {
            userBills.forEach(this::displayBill);
        }
    }

    public void getBillsByUtility(String utility) {
        List<Bill> utilityBills = bills.stream()
                .filter(b -> b.getUtilityType().equalsIgnoreCase(utility))
                .toList();
        if (utilityBills.isEmpty()) {
            System.out.println("No bills found for utility: " + utility);
        } else {
            utilityBills.forEach(this::displayBill);
        }
    }

    public void generateReportByUser(String type) {
        System.out.println("\n--- Report by User (" + type + ") ---");

        Map<String, List<Bill>> grouped = bills.stream()
                .collect(Collectors.groupingBy(Bill::getUserName));

        if (type.equalsIgnoreCase("max") || type.equalsIgnoreCase("min")) {
            Map<String, Double> totals = grouped.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue().stream().mapToDouble(Bill::getAmount).sum()));

            Optional<Map.Entry<String, Double>> result = type.equalsIgnoreCase("max") ?
                    totals.entrySet().stream().max(Map.Entry.comparingByValue()) :
                    totals.entrySet().stream().min(Map.Entry.comparingByValue());

            result.ifPresentOrElse(
                    entry -> System.out.printf("%s - %s Bill Amount: %.2f%n", entry.getKey(), type.equals("max") ? "Max" : "Min", entry.getValue()),
                    () -> System.out.println("No data available.")
            );
        } else {
            for (String user : grouped.keySet()) {
                List<Bill> userBills = grouped.get(user);
                double result = calculateStat(userBills, type);
                System.out.printf("%s: %.2f%n", user, result);
            }
        }
    }

    public void generateReportByUtility(String type) {
        System.out.println("\n--- Report by Utility (" + type + ") ---");

        Map<String, List<Bill>> grouped = bills.stream()
                .collect(Collectors.groupingBy(Bill::getUtilityType));

        if (type.equalsIgnoreCase("max") || type.equalsIgnoreCase("min")) {
            Map<String, Double> totals = grouped.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue().stream().mapToDouble(Bill::getAmount).sum()));

            Optional<Map.Entry<String, Double>> result = type.equalsIgnoreCase("max") ?
                    totals.entrySet().stream().max(Map.Entry.comparingByValue()) :
                    totals.entrySet().stream().min(Map.Entry.comparingByValue());

            result.ifPresentOrElse(
                    entry -> System.out.printf("%s - %s Bill Amount: %.2f%n", entry.getKey(), type.equals("max") ? "Max" : "Min", entry.getValue()),
                    () -> System.out.println("No data available.")
            );
        } else {
            for (String utility : grouped.keySet()) {
                List<Bill> utilityBills = grouped.get(utility);
                double result = calculateStat(utilityBills, type);
                System.out.printf("%s: %.2f%n", utility, result);
            }
        }
    }

    private double calculateStat(List<Bill> bills, String type) {
        return switch (type.toLowerCase()) {
            case "sum" -> bills.stream().mapToDouble(Bill::getAmount).sum();
            case "avg" -> bills.stream().mapToDouble(Bill::getAmount).average().orElse(0);
            default -> 0;
        };
    }
}

