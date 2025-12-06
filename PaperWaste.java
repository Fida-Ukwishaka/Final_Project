import java.util.*;
public class PaperWaste {

    // ===== GLOBAL COLLECTIONS =====
    private static ArrayList<PaperWaste> allPapers = new ArrayList<>();
    private static TreeMap<String, ArrayList<PaperWaste>> paperCategories = new TreeMap<>();

    private static int paperCount = 0;

    // ===== PAPER TYPE DATABASE =====
    private static final Set<String> RECYCLABLE_TYPES = Set.of(
            "Newspaper", "Cardboard", "Office Paper", "Paperboard", "Kraft Paper"
    );

    private static final Set<String> NON_RECYCLABLE_TYPES = Set.of(
            "Waxed Paper", "Tissue Paper", "Laminated Paper", "Foil-lined Paper"
    );

    // ===== INSTANCE FIELDS =====
    private String paperType;
    private double weight;
    private double moisturePercentage;

    private int binCapacity;
    private int currentBinLoad;

    private boolean isContaminated;
    private boolean eligibleForReuse;
    private boolean binFullAlert;

    private String category;   // "Recyclable" or "Non-Recyclable"

    // ===== CONSTRUCTOR =====
    public PaperWaste(String paperType, double weight, double moisturePercentage,
                      int binCapacity, int currentBinLoad) {

        this.paperType = paperType;
        this.weight = weight;
        this.moisturePercentage = moisturePercentage;
        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        paperCount++;

        // contamination & reuse logic
        this.isContaminated = checkContamination();
        this.eligibleForReuse = determineReusePotential();
        this.binFullAlert = checkBinStatus();

        // classify paper automatically
        this.category = classifyPaperType(paperType);

        storeInCollections();
    }

    // ===== CATEGORY DETECTION =====
    private String classifyPaperType(String type) {
        if (RECYCLABLE_TYPES.contains(type)) return "Recyclable";
        if (NON_RECYCLABLE_TYPES.contains(type)) return "Non-Recyclable";
        return "Recyclable"; // default to safer, gentler assumption
    }

    // ===== SAVE TO COLLECTIONS =====
    private void storeInCollections() {
        allPapers.add(this);

        paperCategories.putIfAbsent(category, new ArrayList<>());
        paperCategories.get(category).add(this);
    }

    // ===== LOGIC METHODS =====
    private boolean checkContamination() {
        return moisturePercentage > 35.0;
    }

    private boolean determineReusePotential() {
        return moisturePercentage <= 10.0 && !isContaminated;
    }

    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    // ===== GETTERS =====
    public static ArrayList<PaperWaste> getAllPapers() {
        return allPapers;
    }

    public static TreeMap<String, ArrayList<PaperWaste>> getPaperCategories() {
        return paperCategories;
    }

    // ===== SUMMARY =====
    @Override
    public String toString() {
        return "\n---- PAPER WASTE RECORD ----\n" +
                "Type: " + paperType + "\n" +
                "Category: " + category + "\n" +
                "Weight: " + weight + " kg\n" +
                "Moisture Level: " + moisturePercentage + "%\n" +
                "Contaminated: " + isContaminated + "\n" +
                "Eligible for Reuse: " + eligibleForReuse + "\n" +
                "Bin Full After Dump: " + binFullAlert + "\n" +
                "Total Paper Items So Far: " + paperCount + "\n";
    }
}