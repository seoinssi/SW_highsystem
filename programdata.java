package program;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProgramData {
    private File file;
    public static List<String> programList;
    
    public ProgramData() {
        file = new File("./ProgramData.txt");
        
        programList = new ArrayList<>();
        
        if (file.exists()) {
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    if (parts.length > 0) {
                        programList.add(parts[0].trim());
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("");
        }
    }

    public void writeData(String data) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(data);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
