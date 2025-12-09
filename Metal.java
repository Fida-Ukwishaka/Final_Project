import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Metal extends Waste {

    private int yearsUsed;
    private double rustLevel;
    private int binCapacity;
    private int currentBinLoad;
    private boolean binFullAlert;

    // Collections inherited usage
    private static List<Metal> allMetals = new ArrayList<>();
    private static List<String> MAGNETIC_METALS = Arrays.asList("iron", "steel", "nickel", "cobalt");
    private static List<String> NON_MAGNETIC_METALS = Arrays.asList("aluminum", "copper", "titanium", "lead", "tin");

    private static Map<String, List<Metal>> magneticCategories = new TreeMap<>();

    public Metal(String metalType, double weight, int yearsUsed,
                      int binCapacity, int currentBinLoad) {

        super(types.METAL);        // required by base class
        setItem(metalType);        // store raw type name
        setWeight(weight);
        setDensity(0);             // not determined here
        this.yearsUsed = yearsUsed;

        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        this.rustLevel = calculateRustLevel();
        setIsRecyclable(determineRecyclingNeed());

        // detect magnetism based on name
        setIsMagnetic(detectMagnetism(metalType));

        this.binFullAlert = checkBinStatus();

        storeInCollections();
        Waste.addCount();
    }

    private double calculateRustLevel() {
        double level = yearsUsed * 0.15; // 15% rust per year
        return Math.min(level, 1.0);     // max rust = 100%
    }

    private boolean determineRecyclingNeed() {
        return rustLevel > 0.6;
    }

    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    private boolean detectMagnetism(String typeName) {
        String cleaned = typeName.trim().toLowerCase();

        if (MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned)))
            return true;

        if (NON_MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned)))
            return false;

        return false; // default
    }

    private void storeInCollections() {
        allMetals.add(this);

        String key = getItem().toLowerCase();
        magneticCategories.putIfAbsent(key, new ArrayList<>());
        magneticCategories.get(key).add(this);
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("metal_data.txt", true)) {
            writer.write(this.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "\n---- METAL WASTE ----" +
                "\nType: " + getItem() +
                "\nWeight: " + getWeight() +
                "\nYears Used: " + yearsUsed +
                "\nRust Level: " + rustLevel +
                "\nMagnetic: " + getIsMagnetic() +
                "\nNeeds Recycling (>60% rust): " + getIsRecyclable() +
                "\nBin Full: " + binFullAlert +
                "\nTotal Metal Items: " + Waste.getCount();
    }
}
