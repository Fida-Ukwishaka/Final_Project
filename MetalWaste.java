/**
 * Represents a piece of metal waste dumped into the system.
 * This class not only stores information but also performs
 * meaningful operations with that data: checking corrosion risk,
 * deciding if the metal should be recycled immediately, and
 * alerting managers when bins get full or items are too degraded.
 */

public class MetalWaste {

    // ==== STATIC TRACKERS ====
    private static int metalCount = 0;  // counts how many metal items have ever been dumped

    // ==== INSTANCE FIELDS ====
    private String metalType;           // e.g., "Aluminum", "Steel", "Copper"
    private double weight;              // weight in kilograms
    private int yearsUsed;              // used to determine rust probability
    private int binCapacity;            // maximum allowed metal items in the bin
    private int currentBinLoad;         // current number of metal items inside the bin

    private double rustLevel;           // calculated based on yearsUsed
    private boolean requiresImmediateRecycling; // true if rust is too high
    private boolean binFullAlert;       // true if bin is full after adding this item

    // ==== CONSTRUCTOR ====
    public MetalWaste(String metalType, double weight, int yearsUsed,
                      int binCapacity, int currentBinLoad) {

        this.metalType = metalType;
        this.weight = weight;
        this.yearsUsed = yearsUsed;
        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        // Increase global count
        metalCount++;

        // Determine rust and behavior
        this.rustLevel = calculateRustLevel();
        this.requiresImmediateRecycling = determineRecyclingNeed();
        this.binFullAlert = checkBinStatus();
    }

    // ==== FEATURE 1: Determine rust level ====
    /**
     * Rust level is a simple estimation:
     * - Fresh metals (0-1 yrs) → low rust
     * - Long-used metals have higher rust
     *
     * This matters because the recycling facility sorts metals
     * differently depending on corrosion.
     */
    private double calculateRustLevel() {
        double level = yearsUsed * 0.15;  // 15% rust per year
        return Math.min(level, 1.0);      // rust capped at 100%
    }

    // ==== FEATURE 2: Decide if immediate recycling is needed ====
    /**
     * If rust > 0.6, the system decides this item cannot wait long.
     * This informs the waste manager to push it to the "Priority Queue"
     * before it contaminates other metals.
     */
    private boolean determineRecyclingNeed() {
        return rustLevel > 0.6;
    }

    // ==== FEATURE 3: Check if the bin is full ====
    /**
     * If current load + this item > capacity,
     * the system triggers an alert for the manager.
     */
    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    // ==== GETTERS ====
    public double getRustLevel() { return rustLevel; }
    public boolean isImmediateRecyclingRequired() { return requiresImmediateRecycling; }
    public boolean isBinFull() { return binFullAlert; }
    public static int getMetalCount() { return metalCount; }

    // ==== SUMMARY METHOD ====
    /**
     * Gives a readable explanation of what happened when this item was dumped.
     */
    @Override
    public String toString() {
        return "---- METAL WASTE RECORD ----\n" +
               "Type: " + metalType + "\n" +
               "Weight: " + weight + " kg\n" +
               "Years Used: " + yearsUsed + "\n" +
               "Rust Level: " + rustLevel + "\n" +
               "Requires Immediate Recycling: " + requiresImmediateRecycling + "\n" +
               "Bin Full After Dump: " + binFullAlert + "\n" +
               "Total Metal Items Dumped So Far: " + metalCount + "\n";
    }
}
