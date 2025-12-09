import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {

    public static List<Waste> loadWasteFile(String fileName) {

        List<Waste> loadedWaste = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {

            Map<String, String> map = new HashMap<>();

            while (scanner.hasNextLine()) {

                String line = scanner.nextLine().trim();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }

                // New waste block begins with "TYPE="
                if (line.startsWith("TYPE=")) {

                    // If previous block exists → convert it into an object
                    if (!map.isEmpty()) {
                        Waste w = createWasteFromMap(map);
                        if (w != null) {
                            loadedWaste.add(w);
                        }
                        map.clear();
                    }
                }

                // Split label=value
                if (line.contains("=")) {
                    String[] keyValue = line.split("=");

                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim().toUpperCase();
                        String value = keyValue[1].trim();
                        map.put(key, value);
                    }
                }
            }

            // Convert last block
            if (!map.isEmpty()) {
                Waste w = createWasteFromMap(map);
                if (w != null) loadedWaste.add(w);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find file -> " + fileName);
        }

        return loadedWaste;
    }


    // Converts the labelled map into the correct Waste subclass
    
    private static Waste createWasteFromMap(Map<String, String> map) {

        String type = map.get("TYPE");
        double weight = Double.parseDouble(map.getOrDefault("WEIGHT", "0"));

        switch (type.toUpperCase()) {

            case "METAL":
                String metalType = map.get("METAL_TYPE");
                double wavelength = Double.parseDouble(map.get("WAVELENGTH"));
                return new Metal(metalType, weight, false, wavelength);

            case "PAPER":
                String paperType = map.get("PAPER_TYPE");
                return new Paper(paperType, weight);

            case "ORGANIC":
                String organicName = map.get("ORGANIC_TYPE");
                return new Organic(organicName, weight);

            case "PLASTIC":
                String plasticType = map.get("PLASTIC_TYPE");
                int code = Integer.parseInt(map.get("RESIN_CODE"));
                return new Plastic(plasticType, weight, code);

            default:
                System.out.println("Unknown waste type: " + type);
                return null;
        }
    }
}
