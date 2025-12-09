import java.io.*;
import java.util.*;

public class PaperFile {

    private List<Paper> papers = new ArrayList<>();

    public void loadFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading paper file: " + e.getMessage());
        }
    }

    private void processLine(String line) {
        String[] data = line.split(",");

        Paper pw = new Paper(
                data[0].trim(),
                Double.parseDouble(data[1].trim()),
                Double.parseDouble(data[2].trim()),
                Integer.parseInt(data[3].trim()),
                Integer.parseInt(data[4].trim())
        );

        papers.add(pw);
    }

    public List<Paper> getPapers() {
        return papers;
    }
}

