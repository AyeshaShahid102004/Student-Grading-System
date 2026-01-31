import java.io.*;
import java.util.*;

public class GradeDataStore {
    public static List<String[]> gradeRecords = new ArrayList<>();

    public static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("grades.dat"))) {
            oos.writeObject(gradeRecords);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("grades.dat"))) {
            gradeRecords = (List<String[]>) ois.readObject();
        } catch (Exception e) {
            gradeRecords = new ArrayList<>(); // If file not found or error
        }
    }
}
