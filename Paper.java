import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Paper extends Waste {

    private double moisturePercentage;
    private int binCapacity;
    private int currentBinLoad;

    private boolean isContaminated;
    private boolean eligibleForReuse;
    private boolean binFullAlert;

    private static List<Paper> allPapers = new ArrayList<>();
    private static Map<String, List<Paper>> paperCategories = new TreeMap<>();

    public Paper(String paperType, double weight, double moisturePercentage,
                      int binCapacity, int currentBinLoad) {

        super(types.PAPER);
        setItem(paperType);
        setWeight(weight);
        this.moisturePercentage = moisturePercentage;

        this.binCapacity = binCapacity;
        this.currentBinLoad = currentBinLoad;

        this.isContaminated = moisturePercentage > 35.0;
        this.eligibleForReuse = moisturePercentage <= 10.0 && !isContaminated;
        setIsRecyclable(!isContaminated);

        this.binFullAlert = checkBinStatus();

        storeInCollections();
        Waste.addCount();
    }

    private boolean checkBinStatus() {
        return (currentBinLoad + 1) >= binCapacity;
    }

    private void storeInCollections() {
        allPapers.add(this);

        String key = getItem().toLowerCase();
        paperCategories.putIfAbsent(key, new ArrayList<>());
        paperCategories.get(key).add(this);
    }

    public void saveToFile() {
        try (FileWriter writer = new FileWriter("paper_data.txt", true)) {
            writer.write(this.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "\n---- PAPER WASTE ----" +
                "\nType: " + getItem() +
                "\nWeight: " + getWeight() + " kg" +
                "\nMoisture: " + moisturePercentage + "%" +
                "\nContaminated (>35%): " + isContaminated +
                "\nReusable (<10% moisture & clean): " + eligibleForReuse +
                "\nRecyclable: " + getIsRecyclable() +
                "\nBin Full: " + binFullAlert +
                "\nTotal Paper Items: " + Waste.getCount();
    }
}
