import java.util.*;

public class MetalWaste {

    //  COLLECTIONS 
    private static ArrayList<MetalWaste> allMetals = new ArrayList<>();
    private static TreeMap<String, ArrayList<MetalWaste>> magneticCategories = new TreeMap<>();

    private static int metalCount = 0;

    //  METAL TYPE 
    private static final Set<String> MAGNETIC_METALS = Set.of(
            "Iron", "Steel", "Nickel", "Cobalt"
    );

    private static final Set<String> NON_MAGNETIC_METALS = Set.of(
            "Aluminum", "Copper", "Brass", "Gold", "Silver"
    );

    //INSTANCE VARIABLES
    private String metalType;
    private double weight;
    private int yearsUsed;
    private int binCapacity;
    private int currentBinLoad;

    // EVALUATED BASED ON METAL TYPE
    private double rustLevel;
    private boolean requiresImmediateRecycling;
    private boolean binFullAlert;
    private boolean isMagnetic;

    // CONSTRUCTOR 
    public MetalWaste(String metalType, double weight, int yearsUsed,
                      int binCapacity, int currentBinLoad) {

        this.metalType = metalType;
        this.weight = weight;
        this.yearsUsed = yearsUsed;
        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        // Information on
        //  magnetism based on metal type
        this.isMagnetic = detectMagnetism(metalType);

        metalCount++;
        this.rustLevel = calculateRustLevel();
        this.requiresImmediateRecycling = determineRecyclingNeed();
        this.binFullAlert = checkBinStatus();

        storeInCollections();
    }

    //DETECTING MAGNETISM 
    private boolean detectMagnetism(String type) {
        String cleaned = type.trim().toLowerCase();

        if (MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned))) {
            return true;
        }
        if (NON_MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned))) {
            return false;
        }

        // Default: unknown metals are treated as non-magnetic 
        return false;
    }

    // STORING IN COLLECTIONS
    private void storeInCollections() {
        allMetals.add(this);

        String key = isMagnetic ? "Magnetic" : "Non-Magnetic";

        magneticCategories.putIfAbsent(key, new ArrayList<>());
        magneticCategories.get(key).add(this);
    }

    // COLLECTION GETTERS
    public static ArrayList<MetalWaste> getAllMetals() {
        return allMetals;
    }

    public static TreeMap<String, ArrayList<MetalWaste>> getMagneticCategories() {
        return magneticCategories;
    }

    // CALCULATIONS 
    private double calculateRustLevel() {
        double level = yearsUsed * 0.15;
        return Math.min(level, 1.0);
    }

    private boolean determineRecyclingNeed() {
        return rustLevel > 0.6;
    }

    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    // SUMMARY 
    @Override
    public String toString() {
        return "\n---- METAL WASTE RECORD ----\n" +
               "Type: " + metalType + "\n" +
               "Weight: " + weight + " kg\n" +
               "Years Used: " + yearsUsed + "\n" +
               "Magnetic: " + isMagnetic + "\n" +
               "Rust Level: " + String.format("%.2f", rustLevel) + "\n" +
               "Requires Immediate Recycling: " + requiresImmediateRecycling + "\n" +
               "Bin Full After Dump: " + binFullAlert + "\n" +
               "Total Metal Items So Far: " + metalCount + "\n";
    }
}