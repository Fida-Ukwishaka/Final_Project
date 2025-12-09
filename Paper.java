import java.util.*;

public class Paper extends Waste {

    private String paperType;  

    // Collections
    private static List<Paper> allPapers = new ArrayList<>();
    private static Map<String, List<Paper>> paperCategories = new TreeMap<>();

    // Predefined paper types
    private static final List<String> newspaper = Arrays.asList("newspaper");
    private static final List<String> cardboard = Arrays.asList("cardboard");
    private static final List<String> office_paper = Arrays.asList("office paper");
    private static final List<String> magazine = Arrays.asList("magazine");
    private static final List<String> junk_mail = Arrays.asList("junk mail", "flyer");
    private static final List<String> wrapping_paper = Arrays.asList("wrapping paper");
    private static final List<String> book_paper = Arrays.asList("book", "notebook");

    public Paper (String paperType, double weight) {
        super(types.PAPER, weight);
        this.paperType = paperType;

        // Override recyclable
        setIsRecyclable(true);

        storeInCollections();
        Waste.addCount();
    }

    private void storeInCollections() {
        allPapers.add(this);

        String key = paperType.toLowerCase();
        paperCategories.putIfAbsent(key, new ArrayList<>());
        paperCategories.get(key).add(this);
    }

    @Override
    public String toString() {
        return "Paper {" +
               "Type=" + paperType +
               ", Weight=" + getWeight() + " kg" +
               ", Recyclable=" + getIsRecyclable() +
               "}";
    }
}
