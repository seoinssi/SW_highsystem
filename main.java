package program;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {
	private JButton mainLoginButton, mainSubmitButton, mainCheckButton;
	private JLabel mainLabel;

	public Main() {
		setTitle("비교과 프로그램 화면");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
		getContentPane().setLayout(null);

		mainLabel = new JLabel("HIGH 시스템");
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setBounds(0, 50, 700, 50);
		mainLabel.setFont(new Font("긱블말랑이", Font.BOLD, 45));
		add(mainLabel);

		mainCheckButton = new JButton("내역조회");
		mainCheckButton.setBounds(230, 350, 240, 65);
		mainCheckButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 25));
		mainCheckButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그인 후 이용 가능합니다.", "로그인 확인", JOptionPane.DEFAULT_OPTION);
			}
		});
		add(mainCheckButton);

		mainSubmitButton = new JButton("신청");
		mainSubmitButton.setBounds(230, 250, 240, 65);
		mainSubmitButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 25));
		mainSubmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "로그인 후 이용 가능합니다.", "로그인 확인", JOptionPane.DEFAULT_OPTION);
			}
		});
		add(mainSubmitButton);

		mainLoginButton = new JButton("로그인");
		mainLoginButton.setBounds(230, 150, 240, 65);
		mainLoginButton.setFont(new Font("Y이드스트릿체 B", Font.BOLD, 25));
		mainLoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new SwingLogin();
			}
		});
		add(mainLoginButton);

		setVisible(true);
	}
	public static void main(String[] args) {
		new Main();
	}
}