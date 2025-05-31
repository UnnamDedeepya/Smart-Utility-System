public class BillCalculator {
    public static double calculateAmount(String utilityType, double unitsConsumed) {
        double rate;

        switch (utilityType.toLowerCase()) {
            case "electricity":
                rate = 5.0;
                break;
            case "water":
                rate = 2.0;
                break;
            case "gas":
                rate = 3.0;
                break;
            case "internet":
                rate = 2.5;
                break;
            default:
                rate = 0;
                break;
        }

        return rate * unitsConsumed;
    }
}

