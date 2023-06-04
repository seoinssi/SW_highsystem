package program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Signup extends JFrame{
    private JPanel signupPanel = new JPanel(new GridBagLayout());

    private JLabel signupIdLabel = new JLabel("등록할 이름 ");
    private JLabel signupPw1Label = new JLabel("등록할 학번 ");
    private JLabel signupPw2Label = new JLabel("학번 확인 ");
    private JTextField signupIdText = new JTextField(10);
    private JTextField signupPw1Text = new JTextField(10);
    private JTextField signupPw2Text = new JTextField(10);
    private JButton signupButton = new JButton("회원가입하기");
    private JButton signupBackButton = new JButton("뒤로");
    public static String signupID, signupPw1;
    public Signup() {
        super("회원가입");

        signupIdLabel.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 12));
        signupPw1Label.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 12));
        signupPw2Label.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 12));
        signupButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        signupBackButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupIdLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupIdText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupPw1Label, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupPw2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupPw1Text, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; 
        signupPanel.add(signupPw2Text, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER; 
        signupPanel.add(signupButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER; 
        signupPanel.add(signupBackButton, gbc);
        
        setSize(550,350);
        this.setLocationRelativeTo(null);

        this.setContentPane(signupPanel);

        this.setVisible(true);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String signupID = signupIdText.getText().trim();
            	String signupPw1 = signupPw1Text.getText().trim();
                String signupPw2 = signupPw2Text.getText().trim();
                
                if(signupID.length()==0) {
                    JOptionPane.showMessageDialog(null, "이름을 입력하셔야 됩니다.", "이름을 입력",JOptionPane.DEFAULT_OPTION);
                    return;
                }
                if (signupPw1.length() == 0) {
                    JOptionPane.showMessageDialog(null, "학번을 입력하셔야 됩니다.", "학번을 입력", JOptionPane.DEFAULT_OPTION);
                    return;
                }
                if (signupPw2.length() == 0) {
                    JOptionPane.showMessageDialog(null, "학번을 한번 더 입력해주세요.", "학번 확인", JOptionPane.DEFAULT_OPTION);
                    return;
                }
                if (!signupPw1.equals(signupPw2)) {
                    JOptionPane.showMessageDialog(null, "학번이 일치하지 않습니다.", "학번 확인", JOptionPane.DEFAULT_OPTION);
                    return;
                }
                SignupData signupData = new SignupData();
                String SUdata = signupID + " " + signupPw1;
                if (signupData.isRegistered(SUdata)) {
                    JOptionPane.showMessageDialog(null, "이미 등록된 회원입니다.", "회원 가입 실패", JOptionPane.DEFAULT_OPTION);
                    signupIdText.setText(null);
                    signupPw1Text.setText(null);
                    signupPw2Text.setText(null);
                    return;
                }
                if (!signupData.isRegistered(SUdata)) {
                	signupData.writeData(SUdata);
                    JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다. 로그인 해주시길 바랍니다!", "회원 가입 성공", JOptionPane.DEFAULT_OPTION);
                    dispose();
                    new SwingLogin();
                    return;
                }
            }
        });

        signupBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SwingLogin();
            }
        });
    }

    public static void main(String[] args) {
        new Signup();
    }
}