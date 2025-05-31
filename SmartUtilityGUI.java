import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class SmartUtilityGUI extends JFrame {
    private final BillManagerGUI manager;

    public SmartUtilityGUI() {
        manager = new BillManagerGUI();
        setTitle("Smart Utility Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton addButton = new JButton("Add Bill");
        JButton viewButton = new JButton("View Bills");
        JButton reportButton = new JButton("Generate Report");
        JButton importButton = new JButton("Import Bills");
        JButton exportButton = new JButton("Export Bills");


        addButton.addActionListener(e -> addBill());
        viewButton.addActionListener(e -> viewBills());
        reportButton.addActionListener(e -> generateReport());
        importButton.addActionListener(e -> importBills());
        exportButton.addActionListener(e -> exportBills());

        panel.add(addButton);
        panel.add(viewButton);
        panel.add(reportButton);
        panel.add(importButton);
        panel.add(exportButton);

        add(panel);
        setVisible(true);
    }

    private void addBill() {
        String name = JOptionPane.showInputDialog(this, "Enter user name:");
        String utility = JOptionPane.showInputDialog(this, "Enter utility type:");
        String unitsStr = JOptionPane.showInputDialog(this, "Enter units consumed:");

        try {
            double units = Double.parseDouble(unitsStr);
            double amount = BillCalculator.calculateAmount(utility, units);
            Bill bill = new Bill(name, utility, units, amount);
            manager.addBill(bill);
            JOptionPane.showMessageDialog(this, "Bill added:\n" + bill);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void viewBills() {
        String[] options = {"By User", "By Utility", "All Bills"};
        System.out.println("Total bills available: " + manager.getAllBills().size());
        int choice = JOptionPane.showOptionDialog(this, "Choose viewing option:", "View Bills",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        List<Bill> filtered;

        switch (choice) {
            case 0 -> {
                String user = JOptionPane.showInputDialog(this, "Enter user name:");
                filtered = manager.getBillsByUser(user);
            }
            case 1 -> {
                String utility = JOptionPane.showInputDialog(this, "Enter utility type:");
                filtered = manager.getBillsByUtility(utility);
            }
            default -> {
                filtered = manager.getAllBills();
            }
        }

        StringBuilder sb = new StringBuilder();

        for (Bill bill : filtered) {
            sb.append(bill).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString().isEmpty() ? "No bills found." : sb.toString());

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "Bills", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateReport() {
        String[] options = {
                "Total by Utility", "Total by User", "Average by Utility", "Average by User",
                "Highest by Utility", "Highest by User", "Lowest by Utility", "Lowest by User"
        };
        int choice = JOptionPane.showOptionDialog(this, "Select report type:", "Report",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        Map<String, Double> reportData = switch (choice) {
            case 0 -> manager.generateReportByUtility("total");
            case 1 -> manager.generateReportByUser("total");
            case 2 -> manager.generateReportByUtility("avg");
            case 3 -> manager.generateReportByUser("avg");
            case 4 -> manager.generateReportByUtility("max");
            case 5 -> manager.generateReportByUser("max");
            case 6 -> manager.generateReportByUtility("min");
            case 7 -> manager.generateReportByUser("min");
            default -> null;
        };

        if (reportData == null) {
            JOptionPane.showMessageDialog(this, "Invalid choice.");
            return;
        }

        // Format the report as a string
        StringBuilder result = new StringBuilder("===== Report =====\n");
        for (Map.Entry<String, Double> entry : reportData.entrySet()) {
            result.append(entry.getKey())
                    .append(": ₹")
                    .append(String.format("%.2f", entry.getValue()))
                    .append("\n");
            // Show in a scrollable dialog
            JTextArea textArea = new JTextArea(result.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            JOptionPane.showMessageDialog(this, scrollPane, "Report Result", JOptionPane.INFORMATION_MESSAGE);
        }
        }





    private void importBills() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            manager.importBillsFromFile(file.getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Bills imported from file. Total bills: " + manager.getAllBills().size());
        }
    }

    private void exportBills() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String[] options = {"Append", "Overwrite"};
            int choice = JOptionPane.showOptionDialog(this, "Append or Overwrite?", "Export Option",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            boolean append = (choice == 0);
            manager.exportBillsToFile(file.getAbsolutePath(), append);
            JOptionPane.showMessageDialog(this, "Bills exported successfully.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartUtilityGUI::new);
    }
}
