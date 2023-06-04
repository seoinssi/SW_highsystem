package program;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignupData {
    private File file;

    public SignupData() {
        file = new File("./SignupData.txt");
        try {
            if (file.createNewFile()) {
                System.out.println("파일이 생성되었습니다. :" + file.getName());
            } else {
                System.out.println("이미 존재하는 파일입니다.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean isRegistered(String SUdata) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String Adata = scanner.nextLine();
                if (Adata.equals(SUdata)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void writeData(String SUdata) {
        try {
            FileWriter fw = new FileWriter(file, true);
            fw.write(SUdata);
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            System.out.println("파일 생성 중 오류가 발생했습니다.");
            e.printStackTrace();
        }

        String[] dataParts = SUdata.split(" ");
        if (dataParts.length < 2) {
            return;
        }

        String SU1pw = dataParts[1];

        String filePath = "./" + SU1pw + ".txt";
        File userFile = new File(filePath);

        try {
            if (userFile.createNewFile()) {
                FileWriter fw = new FileWriter(userFile);
                fw.write(SUdata + "\n" + "체인지시간,0" + "\n");
                fw.close();
                System.out.println("파일이 생성되었습니다: " + userFile.getName());
            } else {
                System.out.println("파일이 이미 존재합니다: " + userFile.getName());
            }
        } catch (IOException e) {
            System.out.println("파일 생성 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
    }

    public static Example[] readData() {
        List<Example> examples = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File("./SignupData.txt"));

            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(" ");
                Example example = new Example(tokens[0], tokens[1]);
                examples.add(example);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return examples.toArray(new Example[0]);
    }


    public static class Example {
        String DTid;
        String DTpw;

        public Example(String id, String pw) {
            this.DTid = id;
            this.DTpw = pw;
        }
    }
}
