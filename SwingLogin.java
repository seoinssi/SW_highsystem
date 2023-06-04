package program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SwingLogin extends JFrame {
    private JPanel swingloginPanel = new JPanel(new GridBagLayout());

    private JLabel swingloginIdLabel = new JLabel("이름 ");
    private JLabel swingloginPwLabel = new JLabel("학번 ");
    private JTextField swingloginIdText = new JTextField(10);
    private JPasswordField swingloginPwText = new JPasswordField(10);
    private JButton swingloginLoginButton = new JButton("로그인");
    private JButton swingloginSignupButton = new JButton("회원가입");

    public SwingLogin() {
        super("로그인");

        swingloginIdLabel.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 12));
        swingloginPwLabel.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 12));
        swingloginLoginButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));
        swingloginSignupButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        swingloginPanel.add(swingloginIdLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        swingloginPanel.add(swingloginIdText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        swingloginPanel.add(swingloginPwLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        swingloginPanel.add(swingloginPwText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        swingloginPanel.add(swingloginLoginButton, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        swingloginPanel.add(swingloginSignupButton, gbc);

        setSize(550, 350);
        this.setLocationRelativeTo(null);

        this.setContentPane(swingloginPanel);

        this.setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        swingloginSignupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Signup();
            }
        });

        swingloginLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = swingloginIdText.getText().trim();
                char[] pw = swingloginPwText.getPassword();

                SignupData.Example[] examples = SignupData.readData();

                if (id.equals("관리자A") && new String(pw).equals("001")) {
                    JOptionPane.showMessageDialog(null, "관리자로 로그인 합니다.", "관리자 계정 확인", JOptionPane.DEFAULT_OPTION);
                    dispose();
                    new Manager();
                    return;
                } else {
                    boolean loginSuccess = false;
                    for (SignupData.Example example : examples) {
                        if (example != null && id.equals(example.DTid) && new String(pw).equals(example.DTpw)) {
                            JOptionPane.showMessageDialog(null, "로그인 성공", "로그인 확인", JOptionPane.DEFAULT_OPTION);
                            dispose();
                            new Program1(id, new String(pw));
                            loginSuccess = true;
                            break;
                        }
                    }
                    if (!loginSuccess) {
                        JOptionPane.showMessageDialog(null, "로그인 실패", "로그인 확인", JOptionPane.ERROR_MESSAGE);
                        swingloginIdText.setText(null);
                        swingloginPwText.setText(null);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        new SwingLogin();
    }
}
