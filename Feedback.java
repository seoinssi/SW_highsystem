package program;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;

public class Feedback extends JFrame {
    private static String id, pw;
    private static JComboBox<String> feedbackProgramComboBox;
    private JFrame feedbackFrame;
    public static boolean boo = false;
    public int hours;

    public Feedback(String id, String pw) {
        feedbackFrame = new JFrame("만족도 조사");
        feedbackFrame.setSize(400, 200);
        feedbackFrame.setLocationRelativeTo(null);
        feedbackFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel feedbackPanel = new JPanel(new BorderLayout());
        feedbackProgramComboBox = new JComboBox<>();
        feedbackProgramComboBox.setFont(new Font("Y이드스트릿체 L", Font.PLAIN, 12));
        JPanel programPanel = new JPanel(new FlowLayout());

        JLabel feedbackProgramLabel = new JLabel("프로그램:");
        feedbackProgramLabel.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 12));
        programPanel.add(feedbackProgramLabel);
        programPanel.add(feedbackProgramComboBox);
        feedbackPanel.add(programPanel, BorderLayout.NORTH);

        JTextArea feedbackTextArea = new JTextArea(5, 20);
        feedbackPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);
        feedbackTextArea.setFont(new Font("Y이드스트릿체 L", Font.PLAIN, 14));

        JButton feedbackSubmitButton = new JButton("제출");
        feedbackSubmitButton.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 14));
        JButton feedbackBackButton = new JButton("돌아가기");
        feedbackBackButton.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(feedbackSubmitButton);
        buttonPanel.add(feedbackBackButton);

        feedbackSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProgram = (String) feedbackProgramComboBox.getSelectedItem();
                String filePath = "./" + pw + ".txt";
                int hours = 0;

                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                    List<String> lines = new ArrayList<>();

                    String line;

                    while ((line = br.readLine()) != null) {
                        if (line.contains(selectedProgram)) {
                            String[] data = line.split(",");
                            data[6] = "만족도조사완료";
                            String hoursString = data[1].trim().replaceAll("[^0-9]", "");
                            hours = Integer.parseInt(hoursString);

                            data[1] = hoursString + "시간";
                            line = String.join(",", data);
                        }
                        lines.add(line);
                    }

                    FileWriter writer = new FileWriter(filePath);
                    for (String updatedLine : lines) {
                        writer.write(updatedLine + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try (BufferedReader abr = new BufferedReader(new FileReader(filePath))) {
                    List<String> alines = new ArrayList<>();
                    String aline;
                    while ((aline = abr.readLine()) != null) {
                        if (aline.contains("체인지시간")) {
                            String[] adata = aline.split(",");

                            int CT = 0;
                            if (adata.length >= 2 && !adata[1].isEmpty()) {
                                try {
                                    CT = Integer.parseInt(adata[1].trim());
                                } catch (NumberFormatException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            CT += hours;
                            adata[1] = String.valueOf(CT);
                            aline = String.join(",", adata);
                        }
                        alines.add(aline);
                    }

                    FileWriter writer = new FileWriter(filePath);
                    for (String aupdatedLine : alines) {
                        writer.write(aupdatedLine + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                new Check(id, pw);
                feedbackFrame.dispose();
            }
        });
        feedbackBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            
                new Check(id, pw);
                feedbackFrame.dispose();
            }
        });
        feedbackPanel.add(buttonPanel, BorderLayout.SOUTH);
        feedbackFrame.add(feedbackPanel);
        feedbackFrame.setVisible(true);
        SwingUtilities.invokeLater(() -> readDataFromFile(feedbackProgramComboBox, pw));
    }
   	
    public static void readDataFromFile(JComboBox<String> feedbackProgramComboBox, String pw) {
        List<String> programNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(pw + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("이수완료")) {
                    String[] data = line.split(",");
                    String programName = data[0].trim();
                    programNames.add(programName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        feedbackProgramComboBox.removeAllItems();

        for (String programName : programNames) {
            feedbackProgramComboBox.addItem(programName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Feedback(id, pw));
    }
}