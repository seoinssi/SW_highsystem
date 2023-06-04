package program;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ManagerConfirm extends JFrame {
    private JTable managerconfirmTable;
    private JScrollPane managerconfirmScrollPane; 
    private JButton managerconfirmBackButton = new JButton("메인화면");
    private JButton managerconfirmFeedBackButton = new JButton("승인");
    private JButton managerconfirmCancelButton = new JButton("거절");
    public static JFrame frame;
    
    public static void main(String[] args) {
        new Main();
    }
    
    public ManagerConfirm() {
        
        frame = new JFrame("요청 목록");
        frame.setSize(700, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new FlowLayout());
        panel1.add(managerconfirmBackButton);
        panel1.add(managerconfirmFeedBackButton);
        panel1.add(managerconfirmCancelButton);

        JPanel panel2 = new JPanel(new BorderLayout());
        String[] columnNames = {"이름", "학번", "프로그램 이름", "상태"};
        
        DefaultTableModel managerconfirmTableModel = new DefaultTableModel(columnNames, 0);
        List<String[]> data = readDataFromFile("./ManagerConfirm.txt");
        for (String[] rowData : data) {
            managerconfirmTableModel.addRow(rowData);
        }
        
        managerconfirmTable = new JTable(managerconfirmTableModel);
        managerconfirmScrollPane = new JScrollPane(managerconfirmTable);
        panel2.add(managerconfirmScrollPane, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setVisible(true);

        managerconfirmBackButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        managerconfirmBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	frame.dispose();
                new Manager();
            }
        });
        
        managerconfirmFeedBackButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        managerconfirmFeedBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = managerconfirmTable.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) managerconfirmTable.getModel();
                    
                    String studentName = (String) model.getValueAt(selectedRow, 0);
                    String studentNumber = (String) model.getValueAt(selectedRow, 1);
                    String programName = (String) model.getValueAt(selectedRow, 2);

                    model.setValueAt("이수완료", selectedRow, 3);

                    updateStatusInTextFile(studentNumber, studentName, programName, "이수완료");
                    updateStatusInTextFile("./ManagerConfirm.txt", studentName, studentNumber, programName, "이수완료");
                }
            }
        });
        
        managerconfirmCancelButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        managerconfirmCancelButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                int selectedRow = managerconfirmTable.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) managerconfirmTable.getModel();
                    
                    String studentName = (String) model.getValueAt(selectedRow, 0);
                    String studentNumber = (String) model.getValueAt(selectedRow, 1);
                    String programName = (String) model.getValueAt(selectedRow, 2);

                    model.setValueAt("승인거절", selectedRow, 3);

                    updateStatusInTextFile(studentNumber, studentName, programName, "승인거절");
                    updateStatusInTextFile("./ManagerConfirm.txt", studentName, studentNumber, programName, "승인거절");
                }
            }
        });
    }
    
    private List<String[]> readDataFromFile(String filePath) {
        List<String[]> data = new ArrayList<>();
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rowData = line.split(",");
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
    
    private void updateStatusInTextFile(String studentNumber, String studentName, String programName, String status) {
        String pwfilePath = "./" + studentNumber + ".txt";

        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(pwfilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && parts[0].equals(programName)) {
                    parts[6] = status;
                }
                updatedLines.add(String.join(",", parts));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (FileWriter writer = new FileWriter(pwfilePath)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
    private void updateStatusInTextFile(String filePath, String studentName, String studentNumber, String programName, String status) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(studentName) || !parts[1].equals(studentNumber) || !parts[2].equals(programName)) {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String updatedLine : updatedLines) {
                writer.write(updatedLine + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}