import java.io.*;
import java.util.*;

public class MetalFile {

    private List<Metal> metals = new ArrayList<>();

    public void loadFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading metal file: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        String[] data = line.split(",");

        Metal mw = new Metal(
                data[0].trim(),
                Double.parseDouble(data[1].trim()),
                Integer.parseInt(data[2].trim()),
                Integer.parseInt(data[3].trim()),
                Integer.parseInt(data[4].trim())
        );

        metals.add(mw);
    }

    public List<Metal> getMetals() {
        return metals;
    }
}
