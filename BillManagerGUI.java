import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BillManagerGUI {
    private final List<Bill> bills = new ArrayList<>();

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(bills);
    }

    public List<Bill> getBillsByUser(String user) {
        return bills.stream()
                .filter(b -> b.getUserName().equalsIgnoreCase(user))
                .collect(Collectors.toList());
    }

    public List<Bill> getBillsByUtility(String utility) {
        return bills.stream()
                .filter(b -> b.getUtilityType().equalsIgnoreCase(utility))
                .collect(Collectors.toList());
    }

    public void importBillsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // skip header
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String userName = parts[0].trim();
                    String utility = parts[1].trim();
                    double units = Double.parseDouble(parts[2].trim());
                    double amount = Double.parseDouble(parts[3].trim());
                    Bill bill = new Bill(userName, utility, units, amount);
                    bills.add(bill);  // ✅ THIS IS CORRECT
                }

            }
            System.out.println("Bills imported successfully from " + filename);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    public boolean exportBillsToFile(String filename, boolean append) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, append))) {
            for (Bill bill : bills) {
                writer.printf("%s,%s,%.2f,%.2f%n", bill.getUserName(), bill.getUtilityType(), bill.getUnitsConsumed(), bill.getAmount());
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Map<String, Double> generateReportByUser(String type) {
        Map<String, List<Bill>> grouped = bills.stream()
                .collect(Collectors.groupingBy(Bill::getUserName));
        return computeAggregatedReport(grouped, type);
    }

    public Map<String, Double> generateReportByUtility(String type) {
        Map<String, List<Bill>> grouped = bills.stream()
                .collect(Collectors.groupingBy(Bill::getUtilityType));
        return computeAggregatedReport(grouped, type);
    }

    private Map<String, Double> computeAggregatedReport(Map<String, List<Bill>> grouped, String type) {
        Map<String, Double> report = new LinkedHashMap<>();
        switch (type.toLowerCase()) {
            case "max", "min" -> {
                Map<String, Double> totals = grouped.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> e.getValue().stream().mapToDouble(Bill::getAmount).sum()));
                Optional<Map.Entry<String, Double>> result = type.equals("max")
                        ? totals.entrySet().stream().max(Map.Entry.comparingByValue())
                        : totals.entrySet().stream().min(Map.Entry.comparingByValue());
                result.ifPresent(entry -> report.put(entry.getKey(), entry.getValue()));
            }
            case "total" -> grouped.forEach((key, list) -> {
                double total = list.stream().mapToDouble(Bill::getAmount).sum();
                report.put(key, total);
            });
            case "avg" -> grouped.forEach((key, list) -> {
                double avg = list.stream().mapToDouble(Bill::getAmount).average().orElse(0);
                report.put(key, avg);
            });
        }
        return report;
    }
}
