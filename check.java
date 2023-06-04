package program;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Check extends JFrame {
    private static String id, pw;
    private JTable checkTable;
    private JScrollPane checkScrollPane;
    private JButton checkBackButton = new JButton("메인화면");
    private JButton checkFeedbackButton = new JButton("만족도조사");
    private JButton checkCancelButton = new JButton("신청 취소하기");
    public static JFrame frame;
    public static int changeTime;

    private JPanel panel3 = new JPanel(new FlowLayout());
    private static JLabel holdingTimeLabel;
    private Object[][] Adata;
    private String[] columnNames;

    public static void main(String[] args) {
        new Check(id, pw);
    }

    public static void updateHoldingTimeLabel() {
        holdingTimeLabel.setText("보유시간: " + changeTime + "시간");
    }

    public Check(String id, String pw) {
        List<String[]> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./" + pw + ".txt"))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains(",")) {
                    String[] data = line.split(",");
                    if (data.length == 7) {
                        dataList.add(data);
                        row++;
                    }
                }
            }
            Adata = new Object[row][7];

            for (int i = 0; i < row; i++) {
                Adata[i] = dataList.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("./" + pw + ".txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("체인지시간")) {
                    String[] Cdata = line.split(",");
                    changeTime = Integer.parseInt(Cdata[1].trim());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        columnNames = new String[]{"프로그램 이름", "체인지시간", "모집 인원", "날짜", "시간", "장소", "상태"};

        holdingTimeLabel = new JLabel("보유시간: " + changeTime + "시간");
        holdingTimeLabel.setFont(new Font("Y이드스트릿체 B", Font.PLAIN, 12));

        frame = new JFrame("비교과 내역");
        frame.setSize(700, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel(new FlowLayout());
        JLabel IDL = new JLabel(id);
        JLabel PWL = new JLabel(pw);
        panel1.add(IDL);
        panel1.add(PWL);
        panel1.add(checkBackButton);
        panel1.add(checkFeedbackButton);
        panel1.add(checkCancelButton);

        JPanel panel2 = new JPanel(new BorderLayout());

        panel3.add(holdingTimeLabel);
        frame.add(panel3, BorderLayout.SOUTH);
        checkTable = new JTable(Adata, columnNames);
        checkScrollPane = new JScrollPane(checkTable);
        panel2.add(checkScrollPane, BorderLayout.CENTER);
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.setVisible(true);

        checkBackButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        checkBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new program.Program1(id, pw);
                frame.dispose();
            }
        });

        checkFeedbackButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        checkFeedbackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Feedback.boo != true) {
                    frame.dispose();
                    new Feedback(id, pw);
                }
            }
        });

        checkCancelButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        checkCancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Cancel.bool != true) {
                    frame.dispose();
                    new Cancel(pw);
                }
            }
        });
    }

    public void updatecheckTable() {
        DefaultTableModel model = (DefaultTableModel) checkTable.getModel();
        model.setDataVector(Adata, columnNames);
    }
}
