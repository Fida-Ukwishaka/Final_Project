import java.util.*;

public class Metal extends Waste {

    private String metalType;       // e.g., Steel, Aluminum
    private boolean magnetic;       
    private boolean isRusted;       

    // Collections
    private static List<Metal> allMetals = new ArrayList<>();
    private static Map<String, List<Metal>> magneticCategories = new TreeMap<>();
    private static final List<String> MAGNETIC_METALS = Arrays.asList("iron", "steel", "nickel", "cobalt");
    private static final List<String> NON_MAGNETIC_METALS = Arrays.asList("aluminum", "copper", "titanium", "lead", "tin");

    // Constructor
    public Metal(String metalType, double weight, boolean magnetic, double LightSpectrum) {
        super(types.METAL, weight);
        setWavelength(LightSpectrum);
        this.metalType = metalType;

        // Detect magnetism 
        this.magnetic = detectMagnetism(metalType);

        // Rust detection 
        this.isRusted = checkRust(getWavelength());

        // Override recyclable 
        setIsRecyclable(!isRusted);

        storeInCollections();
        Waste.addCount();
    }

    // Rust detection using wavelength (600-700 nm = rusted)
    private boolean checkRust(double wavelength) {
        return wavelength >= 600 && wavelength <= 700;
    }

    private boolean detectMagnetism(String typeName) {
        String cleaned = typeName.trim().toLowerCase();
        if (MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned))) return true;
        if (NON_MAGNETIC_METALS.stream().anyMatch(m -> m.equalsIgnoreCase(cleaned))) return false;
        return false; // default
    }

    private void storeInCollections() {
        allMetals.add(this);

        String key = metalType.toLowerCase();
        magneticCategories.putIfAbsent(key, new ArrayList<>());
        magneticCategories.get(key).add(this);
    }

    //getters
    public String getMetalType() { 
        return metalType; 
    }
    public boolean getMagnetic() { 
        return magnetic; 
    }
    public boolean getIsRusted() { 
        return isRusted; 
    }
    public static List<Metal> getAllMetals() { 
        return allMetals; 
    }
    public static Map<String, List<Metal>> getMagneticCategories() { 
        return magneticCategories; 
    }

    // Setters
    public void setMetalType(String metalType) { 
        this.metalType = metalType; 
    }
    public void setMagnetic(boolean magnetic) { 
        this.magnetic = magnetic; 
    }
    public void setIsRusted(boolean isRusted) { 
        this.isRusted = isRusted; 
    }

    @Override
    public String toString() {
        return "Metal {" +
               "Type=" + metalType +
               ", Weight=" + getWeight() +
               ", Magnetic=" + magnetic +
               ", Rusted=" + isRusted +
               ", Recyclable=" + getIsRecyclable() +
               "}";
    }
}
