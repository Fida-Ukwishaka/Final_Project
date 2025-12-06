public class PaerWasteDriver {
    public static void main(String[] args) {

        new PaperWaste("Newspaper", 0.8, 5, 20, 10);
        new PaperWaste("Cardboard", 2.5, 12, 20, 15);
        new PaperWaste("Tissue Paper", 0.2, 8, 20, 18);
        new PaperWaste("Laminated Paper", 0.4, 40, 20, 5);
        new PaperWaste("Office Paper", 1.0, 9, 20, 9);

        // Print all paper records
        System.out.println("=== ALL PAPER WASTE ===");
        PaperWaste.getAllPapers().forEach(System.out::println);

        // Print category collections
        System.out.println("\n=== PAPER CATEGORY VIEW ===");
        PaperWaste.getPaperCategories().forEach((category, list) -> {
            System.out.println("\n" + category + ":");
            list.forEach(item -> System.out.println("- " + item));
        });
    }
}

