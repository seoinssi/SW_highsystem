package program;

import javax.swing.*;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class Program1 extends JFrame {
    private JButton program1SubmitButton, program1ViewButton, program1LogoutButton, program1VPButton;
    private JLabel program1Label;
    public static String program1LineToWrite;

    public static String program1Variable1,program1Variable2,program1Variable3,program1Variable4,program1Variable5,program1Variable6,variable7;
    public Program1(String id, String pw) {

        program1Label = new JLabel();
        program1Label.setFont(new Font("Y이드스트릿체 L", Font.PLAIN, 12));
        program1Label.setVerticalAlignment(SwingConstants.TOP);

        new ProgramData();
        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 10, 10);

        setTitle("비교과 프로그램 신청");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel program1Panel = new JPanel(flowLayout);
        JLabel program1IdLabel1 = new JLabel("이름: ");
        JLabel program1IdLabel2 = new JLabel(id);
        JLabel program1IdLabel3 = new JLabel("학번: ");
        JLabel program1IdLabel4 = new JLabel(pw);
        program1Panel.add(program1IdLabel1);
        program1Panel.add(program1IdLabel2);
        program1Panel.add(program1IdLabel3);
        program1Panel.add(program1IdLabel4);

        JPanel program1Panel1 = new JPanel(flowLayout);
        JComboBox<String> PGComboBox = new JComboBox<>(ProgramData.programList.toArray(new String[0]));

        JLabel programLabel1 = new JLabel("신청 가능 프로그램:");
        program1Panel1.add(programLabel1);
        program1Panel1.add(PGComboBox);
        JPanel program1Panel2 = new JPanel(flowLayout);
        program1Panel1.add(program1Label);
        program1VPButton = new JButton("상세보기");
        program1Panel2.add(program1VPButton);

        program1VPButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String STP = (String) PGComboBox.getSelectedItem();
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
                        String program1Variable1 = data[0].trim();
                        String program1Variable2 = data[1].trim();
                        String program1Variable3 = data[2].trim();
                        String program1Variable4 = data[3].trim();
                        String program1Variable5 = data[4].trim();
                        String program1Variable6 = data[5].trim();
                        program1LineToWrite = line;

                        program1Label.setText("<html>프로그램 이름: " + program1Variable1 + "<br/>" +
                                "체인지시간: " + program1Variable2 + "<br/>" +
                                "모집인원: " + program1Variable3 + "<br/>" +
                                "날짜: " + program1Variable4 + "<br/>" +
                                "시간: " + program1Variable5 + "<br/>" +
                                "장소: " + program1Variable6 + "</html>");
                    } else {
                        program1Label.setText("일치하는 프로그램을 찾을 수 없습니다.");
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        });

        add(program1Panel);
        add(program1Panel1);
        add(program1Panel2);

        JScrollPane scrollPane = new JScrollPane(program1Label);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);

        program1SubmitButton = new JButton("신청하기");

        program1SubmitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(program1SubmitButton);

        program1SubmitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ST = (String) PGComboBox.getSelectedItem();
                String Sline = "";

                boolean alreadyApplied = false;

                try (Scanner scanner = new Scanner(new File("./ProgramData.txt"))) {
                    StringBuilder sb = new StringBuilder();
                    boolean found = false;

                    while (scanner.hasNextLine()) {
                        Sline = scanner.nextLine();
                        String[] Part = Sline.split(",");

                        if (Part.length > 0 && Part[0].trim().equals(ST)) {
                            found = true;
                            sb.append(Sline);
                        }
                    }
                    if (found) {
                    	String previousLines = sb.toString();
                        File userFile = new File("./" + pw + ".txt");

                        try (Scanner fileScanner = new Scanner(userFile)) {
                            while (fileScanner.hasNextLine()) {
                                String fileLine = fileScanner.nextLine();
                                if (fileLine.equals(previousLines+"승인대기")) {
                                    alreadyApplied = true;
                                    break;
                                }
                                if (fileLine.equals(previousLines+"이수완료")) {
                                    alreadyApplied = true;
                                    break;
                                }
                                if (fileLine.equals(previousLines+"만족도조사완료")) {
                                    alreadyApplied = true;
                                    break;
                                }
                                if (fileLine.equals(previousLines+"승인거절")) {
                                    alreadyApplied = true;
                                    break;
                                }
                            }
                            if (alreadyApplied) {
                                JOptionPane.showMessageDialog(null, "이미 해당 프로그램을 신청하셨습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        try (FileWriter fw = new FileWriter(userFile, true);
                        		FileWriter confirmFileWriter = new FileWriter("./ManagerConfirm.txt", true)) {
                            
                        	fw.write(previousLines);
                            fw.write("승인대기");
                            fw.write(System.lineSeparator());
                            
                            String[] data = previousLines.split(",");
                            String programName = data[0].trim();
                            String managerConfirm = "승인대기";
                            confirmFileWriter.write(id + "," + pw + "," + programName + "," + managerConfirm);
                            confirmFileWriter.write(System.lineSeparator());
                            
                            JOptionPane.showMessageDialog(null, "신청이 완료되었습니다!", "비교과 프로그램 신청", JOptionPane.DEFAULT_OPTION);
                        } catch (IOException ex) {
                            System.out.println("파일에 내용을 추가할 수 없습니다.");
                        }
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("파일을 찾을 수 없습니다.");
                }
            }
        });

        add(new JLabel(""));
        add(program1SubmitButton);

        program1ViewButton = new JButton("내역조회");
        program1ViewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(program1ViewButton);
        program1ViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Check(id, pw);
            }
        });

        program1LogoutButton = new JButton("로그아웃");
        program1LogoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(program1LogoutButton);
        program1LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SwingLogin();
            }
        });

        setVisible(true);
    }
}
