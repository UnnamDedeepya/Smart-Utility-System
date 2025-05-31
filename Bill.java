public class Bill {
    private final String userName;
    private final String utilityType;
    private final double unitsConsumed;
    private final double amount;

    public Bill(String userName, String utilityType, double unitsConsumed, double amount) {
        this.userName = userName;
        this.utilityType = utilityType;
        this.unitsConsumed = unitsConsumed;
        this.amount = amount;
    }


    public String getUserName() {
        return userName;
    }


    public String getUtilityType() {
        return utilityType;
    }


    public double getUnitsConsumed() {
        return unitsConsumed;
    }


    public double getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "Name: " + userName +
                ", Utility: " + utilityType +
                ", Units: " + unitsConsumed +
                ", Amount: ₹" + String.format("%.2f", amount);
    }

    public void getAllBills() {
        System.out.println("User: " + userName + ", Utility: " + utilityType + ", Units: " + unitsConsumed + ", Amount: Rs." + amount);
    }
}