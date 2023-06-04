package program;

import javax.swing.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class Manager extends JFrame {

    private JButton managerOnButton, managerOviewButton, managerLogoutButton, managerOKButton;
    private JComboBox<String> managerPlaceComboBox;
    private JTextField managerPDNText = new JTextField(10);
    private JTextField managerPDCTText = new JTextField(10);
    private JTextField managerPDSText = new JTextField(10);
    private JTextField managerPDDText = new JTextField(10);
    String EXT = "ex)40명";
    public Manager() {
        String[] comboBoxOptions = {"w6-109호", "w15-109호"};
        
        setTitle("비교과 프로그램 등록");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel mainlabel = new JLabel("관리자 화면입니다.");
        mainlabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel MPanel1 = new JPanel();
        MPanel1.setLayout(new BoxLayout(MPanel1,BoxLayout.X_AXIS));
        MPanel1.add(new JLabel("프로그램 이름 :"));
        MPanel1.add(managerPDNText);
        managerPDNText.setText("ex)배드민턴");
        managerPDNText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (managerPDNText.getText().equals("ex)배드민턴")) {
                    managerPDNText.setText("");
                }
            }
        });

        managerPDNText.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (managerPDNText.getText().isEmpty()) {
                    managerPDNText.setText("ex)배드민턴");
                }
            }
        });
        JPanel MPanel2 = new JPanel();
        MPanel2.setLayout(new BoxLayout(MPanel2,BoxLayout.X_AXIS));
        MPanel2.add(new JLabel("체인지시간 :"));
        MPanel2.add(managerPDCTText);
        managerPDCTText.setText("ex)4시간");
        managerPDCTText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (managerPDCTText.getText().equals("ex)4시간")) {
                	managerPDCTText.setText("");
                }
            }
        });

        managerPDCTText.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (managerPDCTText.getText().isEmpty()) {
                	managerPDCTText.setText("ex)4시간");
                }
            }
        });
        
        JPanel MPanel3 = new JPanel();
        MPanel3.setLayout(new BoxLayout(MPanel3,BoxLayout.X_AXIS));
        MPanel3.add(new JLabel("모집인원 :"));
        MPanel3.add(managerPDSText);
        managerPDSText.setText("ex)40명");
        managerPDSText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (managerPDSText.getText().equals("ex)40명")) {
                	managerPDSText.setText("");
                }
            }
        });

        managerPDSText.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (managerPDSText.getText().isEmpty()) {
                	managerPDSText.setText("ex)40명");
                }
            }
        });
        UtilDateModel model = new UtilDateModel();
        Properties props = new Properties();
        props.put("text.today", "Today");
        props.put("text.month", "Month");
        props.put("text.year", "Year");

        JDatePickerImpl datePicker = new JDatePickerImpl(new JDatePanelImpl(model, props), new DateLabelFormatter());
        datePicker.setBounds(20, 100, 200, 30);
        add(datePicker);
        
        JPanel MPanel4 = new JPanel();
        MPanel4.setLayout(new BoxLayout(MPanel4,BoxLayout.X_AXIS));
        MPanel4.add(new JLabel("날짜 :"));
        MPanel4.add(datePicker);
        MPanel4.add(new JLabel("시간 :"));
        MPanel4.add(managerPDDText);
        managerPDDText.setText("ex)10:00~14:00");
        managerPDDText.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (managerPDDText.getText().equals("ex)10:00~14:00")) {
                	managerPDDText.setText("");
                }
            }
        });

        managerPDDText.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (managerPDDText.getText().isEmpty()) {
                	managerPDDText.setText("ex)10:00~14:00");
                }
            }
        });
        JPanel MPanel5 = new JPanel();
        MPanel5.setLayout(new BoxLayout(MPanel5,BoxLayout.X_AXIS));
        MPanel5.add(new JLabel("장소 :"));
        managerPlaceComboBox = new JComboBox<>(comboBoxOptions);
        MPanel5.add(managerPlaceComboBox);
        
        JPanel panel4 = new JPanel();
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
        panel4.add(MPanel1);
        panel4.add(MPanel2);
        panel4.add(MPanel3);
        panel4.add(MPanel4);
        panel4.add(MPanel5);
        
        add(panel4,BorderLayout.CENTER);
        
        managerOnButton = new JButton("등록하기");
        managerOnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(managerOnButton);
        managerOnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String EXTPDS = managerPDSText.getText().trim();
            	if (EXT.equals(EXTPDS)) {
            		JOptionPane.showMessageDialog(null, "내용을 전부 채워주세요", "프로그램 등록", JOptionPane.DEFAULT_OPTION);
            		return;
            	}
                String managerPDN = managerPDNText.getText().trim();
                String managerPDCT = managerPDCTText.getText().trim();
                String managerPDS = managerPDSText.getText().trim();
                String managerPDT = managerPDDText.getText().trim();
                String selectedDate = datePicker.getJFormattedTextField().getText();
                String selectedPlace = (String) managerPlaceComboBox.getSelectedItem();
                
                ProgramData programdata = new ProgramData();
                String PDdata = managerPDN + "," + managerPDCT + "," + managerPDS + "," + selectedDate + "," + managerPDT + "," +selectedPlace + "," + "\n";
                programdata.writeData(PDdata);
                JOptionPane.showMessageDialog(null, "등록에 성공하였습니다!", "프로그램 등록", JOptionPane.DEFAULT_OPTION);
                managerPDNText.setText(null);
                managerPDCTText.setText(null);
                managerPDSText.setText(null);
                managerPDDText.setText(null);
                return;
            }
        });

        managerOviewButton = new JButton("등록된 프로그램 조회");
        managerOviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(managerOviewButton);
        managerOviewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
                new program.ManagerCheck();
            }
        });
        managerOKButton = new JButton("요청목록");
        managerOKButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(managerOKButton);
        managerOKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new program.ManagerConfirm();
            }
        });
        
        managerLogoutButton = new JButton("로그아웃");
        managerLogoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(managerLogoutButton);
        managerLogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new program.SwingLogin();
            }
        });        
        setVisible(true);
    }
}