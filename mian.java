public class mian {
    public static void main(String[] args) {

        MetalFile metalReader = new MetalFile();
        metalReader.loadFile("metal_waste.txt");

        PaperFile paperReader = new PaperFile();
        paperReader.loadFile("paper_waste.txt");

        System.out.println("---- METAL WASTE ----");
        for (Metal m : metalReader.getMetals()) {
            System.out.println(m);
        }

        System.out.println("---- PAPER WASTE ----");
        for (Paper p : paperReader.getPapers()) {
            System.out.println(p);
        }
    }
}
