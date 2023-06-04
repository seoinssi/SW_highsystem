package program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Cancel extends JFrame {
    private JComboBox<String> cancelProgramComboBox;
    private JButton cancelBackButton = new JButton("취소하기");
    private JButton cancelViewButton = new JButton("조회화면");
    public static JFrame frame;
    public static boolean bool = false;
    private String id;

    public Cancel(String pw) {

        frame = new JFrame("신청 취소");
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel cancelPanel = new JPanel(new BorderLayout());
        cancelProgramComboBox = new JComboBox<>();
        cancelProgramComboBox.setFont(new Font("Y이드스트릿체 L", Font.PLAIN, 12));
        JPanel programPanel = new JPanel(new FlowLayout());

		JLabel cancelProgramLabel = new JLabel("신청한 프로그램:");
        cancelProgramLabel.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 12));
        programPanel.add(cancelProgramLabel);
        programPanel.add(cancelProgramComboBox);
        
        cancelBackButton.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 14));
        cancelBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProgram = (String) cancelProgramComboBox.getSelectedItem();
                ProgramData.programList.remove(selectedProgram);
                removeLineFromFile("./" + pw + ".txt", selectedProgram);
                removeLineFromFile("./ManagerConfirm.txt", selectedProgram);
                JOptionPane.showMessageDialog(null, "프로그램 신청이 취소되었습니다.", "신청 취소", JOptionPane.DEFAULT_OPTION);
                frame.dispose();
                new Check(id, pw);
            }
        });
        
        cancelViewButton.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 14));
        cancelViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Check(id, pw);
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(cancelBackButton);
        buttonPanel.add(cancelViewButton);

        cancelPanel.add(programPanel, BorderLayout.NORTH);
        cancelPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(cancelPanel);
        
        frame.setVisible(true);

        loadProgramData("./" + pw + ".txt");
    }

    private void loadProgramData(String filePath) {
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                count++;
                if (count >= 2) {
                    String[] parts = line.split(",");
                    if (parts.length > 0&& parts[parts.length - 1].trim().equals("승인대기")) {
                        cancelProgramComboBox.addItem(parts[0].trim());
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeLineFromFile(String filePath, String lineToRemove) {
        try {
            File inputFile = new File(filePath);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(tempFile);

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (!currentLine.contains(lineToRemove)) {
                    writer.write(currentLine + System.lineSeparator());
                }
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Error deleting the file.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error renaming the file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
