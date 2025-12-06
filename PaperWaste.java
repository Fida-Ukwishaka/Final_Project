
public class PaperWaste {

    private static int paperCount = 0; // total number of paper items dumped

    // ==== INSTANCE FIELDS ====
    private String paperType; // e.g., "Newspaper", "Cardboard", "Office Paper"
    private double weight; // weight in kilograms
    private double moisturePercentage; // determines contamination level

    private int binCapacity;
    private int currentBinLoad;

    private boolean isContaminated; // too wet to recycle
    private boolean eligibleForReuse; // if clean & dry enough
    private boolean binFullAlert;

    // ==== CONSTRUCTOR ====
    public PaperWaste(String paperType, double weight, double moisturePercentage,
            int binCapacity, int currentBinLoad) {

        this.paperType = paperType;
        this.weight = weight;
        this.moisturePercentage = moisturePercentage;
        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        // Keep track of total papers dumped
        paperCount++;

        // Evaluate contamination
        this.isContaminated = checkContamination();

        // Determine if paper can be repurposed
        this.eligibleForReuse = determineReusePotential();

        // Check if bin is full
        this.binFullAlert = checkBinStatus();
    }

    // ==== FEATURE 1: Check contamination ====
    /**
     * If moisture is above 35%, paper is considered contaminated.
     * Contaminated paper can no longer be recycled and must be "diverted"
     * to an alternative waste stream.
     */
    private boolean checkContamination() {
        return moisturePercentage > 35.0;
    }

    // ==== FEATURE 2: Decide if paper can be reused ====
    /**
     * Very dry and unsoiled paper can be reused for art, note-making,
     * or packaging before recycling.
     *
     * This adds a "secondary life" feature that improves sustainability.
     */
    private boolean determineReusePotential() {
        return moisturePercentage <= 10.0 && !isContaminated;
    }

    // ==== FEATURE 3: Check if the bin is full ====
    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    // ==== GETTERS ====
    public boolean isContaminated() {
        return isContaminated;
    }

    public boolean isEligibleForReuse() {
        return eligibleForReuse;
    }

    public boolean isBinFull() {
        return binFullAlert;
    }

    public static int getPaperCount() {
        return paperCount;
    }

    // ==== SUMMARY METHOD ====
    @Override
    public String toString() {
        return "---- PAPER WASTE RECORD ----\n" +
                "Type: " + paperType + "\n" +
                "Weight: " + weight + " kg\n" +
                "Moisture Level: " + moisturePercentage + "%\n" +
                "Contaminated (Not Recyclable): " + isContaminated + "\n" +
                "Eligible for Reuse: " + eligibleForReuse + "\n" +
                "Bin Full After Dump: " + binFullAlert + "\n" +
                "Total Paper Items Dumped So Far: " + paperCount + "\n";
    }
}
