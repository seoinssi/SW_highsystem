package program;

import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class ManagerCheck extends JFrame {
    private JButton managercheckSubmitButton, managercheckLogoutButton, managercheckVPButton;
    private JLabel managercheckPlabel;
    public static String managercheckLineToWrite;
    public static String managercheckVariable1,managercheckVariable2,managercheckVariable3,managercheckVariable4,managercheckVariable5,managercheckVariable6,variable7;
    public ManagerCheck() {
        managercheckPlabel = new JLabel();
        managercheckPlabel.setFont(new Font("Y이드스트릿체 L", Font.PLAIN, 12));
        managercheckPlabel.setVerticalAlignment(SwingConstants.TOP);

        new ProgramData();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);

        setTitle("비교과 프로그램 조회(관리자)");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel programPanel = new JPanel(flowLayout);

        JPanel programPanel1 = new JPanel(flowLayout);
        JComboBox<String> managercheckPGComboBox = new JComboBox<>(ProgramData.programList.toArray(new String[0]));

        JLabel managercheckProgramLabel1 = new JLabel("등록된 프로그램:");
        programPanel1.add(managercheckProgramLabel1);
        programPanel1.add(managercheckPGComboBox);
        JPanel programPanel2 = new JPanel(flowLayout);
        programPanel1.add(managercheckPlabel);
        managercheckVPButton = new JButton("상세보기");
        programPanel2.add(managercheckVPButton);

        managercheckVPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String STP = (String) managercheckPGComboBox.getSelectedItem();
                String line = "";

                try (Scanner scanner = new Scanner(new File("./ProgramData.txt"))) {
                    StringBuilder sb = new StringBuilder();
                    boolean found = false;

                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        String[] parts = line.split(",");

                        if (parts.length > 0 && parts[0].trim().equals(STP)) {
                            found = true;
                            sb.append(line).append("\n");
                        }
                    }

                    if (found) {
                        String previousLines = sb.toString();
                        String[] data = previousLines.split(",");
                        String managercheckVariable1 = data[0].trim();
                        String managercheckVariable2 = data[1].trim();
                        String managercheckVariable3 = data[2].trim();
                        String managercheckVariable4 = data[3].trim();
                        String managercheckVariable5 = data[4].trim();
                        String managercheckVariable6 = data[5].trim();

                        managercheckLineToWrite = line;

                        managercheckPlabel.setText("<html>프로그램 이름: " + managercheckVariable1 + "<br/>" +
                                "체인지시간: " + managercheckVariable2 + "<br/>" +
                                "모집인원: " + managercheckVariable3 + "<br/>" +
                                "날짜: " + managercheckVariable4 + "<br/>" +
                                "시간: " + managercheckVariable5 + "<br/>" +
                                "장소: " + managercheckVariable6 + "</html>");
                    } else {
                        managercheckPlabel.setText("일치하는 프로그램을 찾을 수 없습니다.");
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        });

        add(programPanel);
        add(programPanel1);
        add(programPanel2);

        JScrollPane scrollPane = new JScrollPane(managercheckPlabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        managercheckSubmitButton = new JButton("삭제하기");

        managercheckSubmitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(managercheckSubmitButton);

        managercheckSubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedProgram = (String) managercheckPGComboBox.getSelectedItem();
                boolean found = false;
                File inputFile = new File("./ProgramData.txt");
                File tempFile = new File("./ProgramDataTemp.txt");

                try (Scanner scanner = new Scanner(inputFile);
                     FileWriter writer = new FileWriter(tempFile)) {

                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] parts = line.split(",");

                        if (parts.length > 0 && parts[0].trim().equals(selectedProgram)) {
                            found = true;
                        } else {
                            writer.write(line + System.lineSeparator());
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("파일 처리 중 오류가 발생했습니다.");
                    ex.printStackTrace();
                }

                if (found) {
                    if (inputFile.delete()) {
                        if (tempFile.renameTo(inputFile)) {
                            JOptionPane.showMessageDialog(null, "프로그램이 삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
                            managercheckPlabel.setText("");
                            ProgramData.programList.remove(selectedProgram);
                            managercheckPGComboBox.removeItem(selectedProgram);
                        } else {
                            JOptionPane.showMessageDialog(null, "프로그램 삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "프로그램 삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "일치하는 프로그램을 찾을 수 없습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(new JLabel(""));
        add(managercheckSubmitButton);

        managercheckLogoutButton = new JButton("등록화면");
        managercheckLogoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(managercheckLogoutButton);
        managercheckLogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Manager();
            }
        });

        setVisible(true);
    }
}
