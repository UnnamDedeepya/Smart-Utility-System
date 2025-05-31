# 🔌 Smart Utility Management System (Java Console + GUI)

A Java-based application to manage utility bills for users, featuring both **console** and **Swing GUI** interfaces. The system allows users to add bills, view them by user or utility, generate reports (total, average, highest, lowest), and import/export data from CSV files.

---

## ✨ Features

- ✅ Add utility bills (electricity, water, etc.)
- ✅ View bills by:
  - User
  - Utility type
  - All bills
- 📊 Generate reports:
  - Total / Average amount by User or Utility
  - Highest / Lowest bill aggregation
- 📁 Import and Export data as CSV (append or overwrite)
- 🖥️ Dual Interface:
  - Console (Main.java)
  - GUI using Java Swing (SmartUtilityGUI.java)
- 🧪 Built with full object-oriented programming principles

---

## 📂 Project Structure

```
SmartUtilityManagementSystem/
├── Bill.java                 # Bill entity class
├── BillCalculator.java      # Calculates amount based on utility and units
├── BillManagerConsole.java  # Handles bill logic for console app
├── BillManagerGUI.java      # Handles bill logic for GUI
├── Main.java                # Console version (text-based menu)
├── SmartUtilityGUI.java     # GUI version with Java Swing
├── sample_bills.csv         # Sample CSV file for import/export
```

---

## 🚀 Getting Started

### 🔧 Prerequisites
- Java 8 or higher
- IDE (Eclipse, IntelliJ IDEA, or any Java-supporting editor)

### 🛠️ How to Run

#### ▶️ Console Version
1. Compile and run `Main.java`
2. Use the menu to interact with the system

```bash
javac Main.java
java Main
```

#### 🖱️ GUI Version
1. Compile and run `SmartUtilityGUI.java`

```bash
javac SmartUtilityGUI.java
java SmartUtilityGUI
```

---

## 📈 Sample Report Output (GUI)

```
===== Report =====
Electricity: ₹400.00
Water: ₹250.00
```

---

## 📥 Import File Format (CSV)

Make sure your `.csv` file follows this format:
```
Name,Utility,Units,Amount
John,Electricity,100,500
Alice,Water,50,250
```

---

## 💡 Learning Objectives

- Practice **OOP principles** (encapsulation, modularity)
- Gain experience with **Java Swing GUI**
- Implement **file handling (CSV I/O)**
- Master logic for **data filtering and aggregation**
- Debug and fix real-world integration issues


