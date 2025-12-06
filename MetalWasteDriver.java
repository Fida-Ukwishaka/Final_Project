public class MetalWasteDriver {

    public static void main(String[] args) {

        new MetalWaste("Steel", 3.5, 4, 10, 8);
        new MetalWaste("Aluminum", 1.2, 1, 10, 9);
        new MetalWaste("Copper", 2.8, 7, 10, 4);
        new MetalWaste("Iron", 5.0, 3, 10, 6);
        new MetalWaste("Brass", 1.0, 2, 10, 5);

        // Display all metals
        System.out.println("=== ALL METALS DUMPED ===");
        MetalWaste.getAllMetals().forEach(System.out::println);

        // Display categories
        System.out.println("\n=== CATEGORY VIEW (TreeMap) ===");
        MetalWaste.getMagneticCategories().forEach((key, list) -> {
            System.out.println("\n" + key + ":");
            list.forEach(item -> System.out.println("- " + item));
        });
    }
}
