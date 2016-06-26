package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.rmi.RemoteException;
import java.security.Key;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LogFrame {
	private static final int Width = 400;
	private static final int Height = 250;
	JTextField nameField;
	JPasswordField passField;
	JButton button1 ;
	JFrame frame = new JFrame();
	public void setIcon(String file, JButton iconButton) { 
		ImageIcon icon = new ImageIcon(file); 
		Image temp = icon.getImage().getScaledInstance(iconButton.getWidth(), 
		iconButton.getHeight(), icon.getImage().SCALE_DEFAULT); 
		icon = new ImageIcon(temp); 
		iconButton.setIcon(icon); 
		} 
	
	public LogFrame(){
		Font nameFont = new Font("Comic Sans MS",Font.BOLD,20);
		Font logFont = new Font("",Font.PLAIN,30);
		frame.setTitle("Login");
		frame.setSize(Width, Height);
		Container container = frame.getContentPane();
		JLabel nameLabel = new JLabel("Name    ");
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(200, 30));
		nameField.setMaximumSize(nameField.getPreferredSize());
		Box nameBox = Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(Box.createHorizontalStrut(20));
		nameBox.add(nameField);
		JLabel passLabel = new JLabel("Passward");
		passField =  new JPasswordField();
		passField.setPreferredSize(new Dimension(200, 30));
		
		passField.setMaximumSize(passField.getPreferredSize());
		Box passBox = Box.createHorizontalBox();
		nameField.addKeyListener(new downKeyListener());
		passField.addKeyListener(new logKeyListener());
		passBox.add(passLabel);
		passBox.add(Box.createHorizontalStrut(20));
		passBox.add(passField);
		nameField.setFont(logFont);
		passField.setFont(logFont);
		passLabel.setFont(nameFont);
		nameLabel.setFont(nameFont);
		ImageIcon icon = new ImageIcon("login.jpg");
		Image image = icon.getImage();
		icon = new ImageIcon(image.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		ImageIcon icon2 = new ImageIcon("signUp.jpg");
		Image image2 = icon2.getImage();
		icon2 = new ImageIcon(image2.getScaledInstance(120, 40, image.SCALE_SMOOTH));
		button1 = new JButton(icon);
		button1.setMargin(new Insets(0, 0, 0, 0));
		button1.addActionListener(new clinkListener());
		JButton button2 = new JButton(icon2);
		button2.setMargin(new Insets(0, 0, 0, 0));
	
		button2.addActionListener(new SignListener());
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(button1);
		buttonBox.add(Box.createHorizontalStrut(40));
		buttonBox.add(button2);
		Box panelBox = Box.createVerticalBox();
		panelBox.add(nameBox);
		panelBox.add(passBox);
		panelBox.add(Box.createVerticalGlue());
		panelBox.add(buttonBox);
		container.add(panelBox,BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.show();
	
		// TODO Auto-generated construc"tor stub
	}
	class SignUp {     //内部类，实现注册界面
		JFrame signframe;
		JButton signButton;
		JButton concelButton;
		JTextField signNameField;
		JPasswordField signPasswordField;
		JPasswordField confirmPasswordField;
		public SignUp() {
			
			signframe = new JFrame("Sign up");
			signNameField = new JTextField(10);
			signNameField.setMaximumSize(signNameField.getPreferredSize());
			signPasswordField = new JPasswordField(10);
			signPasswordField.setMaximumSize(signPasswordField.getPreferredSize());
			confirmPasswordField = new JPasswordField(10);
			confirmPasswordField.setMaximumSize(confirmPasswordField.getPreferredSize());  //确认密码窗口
			JLabel nameLabel = new JLabel("Name");
			Box nameBox = Box.createHorizontalBox();
			nameBox.add(nameLabel);
			nameBox.add(Box.createHorizontalStrut(30));
			nameBox.add(signNameField);
			JLabel passLabel = new JLabel("Password");
			Box passBox = Box.createHorizontalBox();
			passBox.add(passLabel);
			passBox.add(Box.createHorizontalStrut(8));
			passBox.add(signPasswordField);
			JLabel confirmLabel = new JLabel("Confirm");
			Box confirmBox = Box.createHorizontalBox();
			confirmBox.add(confirmLabel);
			confirmBox.add(Box.createHorizontalStrut(20));
			confirmBox.add(confirmPasswordField);
			
			signButton = new JButton("sign up");
			signButton.addActionListener(new signupListener());
			concelButton = new JButton("concel");
			concelButton.addActionListener(new concelListener());
			Box buttonBox = Box.createHorizontalBox();
			buttonBox.add(signButton);
			buttonBox.add(Box.createHorizontalStrut(40));
			buttonBox.add(concelButton);
			Box panelBox = Box.createVerticalBox();
			panelBox.add(nameBox);
			panelBox.add(passBox);
			panelBox.add(confirmBox);
			panelBox.add(Box.createVerticalGlue());
			panelBox.add(buttonBox);
			signframe.getContentPane().add(panelBox,BorderLayout.CENTER);
			signframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			signframe.setSize(300,300);
			signframe.addWindowListener(new windowCloseListener());
			signframe.setLocationRelativeTo(null);
			signframe.setVisible(true);
			// TODO Auto-generated constructor stub
		}
		class signupListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean isExist = false;
				String userNameString  = signNameField.getText();
				String passString = String.valueOf(signPasswordField.getPassword());
				String confirmString = String.valueOf(confirmPasswordField.getPassword());
				String userInf = userNameString+"_"+passString;
				try {
					String []uStrings = RemoteHelper.getInstance().getIOService().readFile("Admin","userlist").substring(1).split(" ");
					OK:
					for(String s:uStrings){
						String []tempStrings =s.split("_");
						for(String a:tempStrings){
							if(a.equals(userNameString)){
								isExist = true;       //检查已注册列表，不允许重名
								break OK;
							}
						}
					}
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				if((userNameString.length()<1) || (passString.length()<1) || (confirmString.length()<1)){ //不允许为空
					JOptionPane.showMessageDialog(null, "The username or password should not be null!");
				}
				else if(isExist){    //重名提示
					JOptionPane.showMessageDialog(null, "The username has already existed!");
				}
				else if(!passString.equals(confirmString)){  //确认密码错误提示
					JOptionPane.showMessageDialog(null, "Please type the same password in two field!");
				}
				else {
					try {     //注册成功，写入信息
						RemoteHelper.getInstance().getIOService().writeFile(userInf, "Admin","userlist");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int i = JOptionPane.showConfirmDialog(null, "Success!Do you want to login directly?","Congratulation!",JOptionPane.YES_NO_OPTION);
					if(i==0){    //判断是否要直接登录
						nameField.setText(userNameString);
						passField.setText(passString);
						button1.doClick();
						signframe.dispose();
						frame.setVisible(true);
					}
					else if(i==1){
						signframe.dispose();
						frame.setVisible(true);
					}
				}
		
			}
			
		}
		class windowCloseListener extends WindowAdapter{
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				concelButton.doClick();
			}
		}
		class concelListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				signframe.dispose();
				frame.setVisible(true);
			}
			
		}
	}
	class SignListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			frame.setVisible(false);
			SignUp signUp = new SignUp();
		}
		
	}
	class clinkListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String userName = nameField.getText();
			String user_InfString = userName+"_"+String.valueOf(passField.getPassword());
			String checkString="0+0";
			
				try {                    //读取注册信息列表
					checkString = RemoteHelper.getInstance().getIOService().readFile("Admin", "userlist");
					System.out.println(checkString);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(checkString.length()<1){ //若无人注册，报错
					JOptionPane.showMessageDialog(null, "No one has signed up");
				}
				else{
				Boolean isRight = false;
				String[]checkStrings =checkString.substring(1).split(" ");
				for(String s:checkStrings){    //检查账户密码是否正确
					System.out.println(s);
					if(s.equals(user_InfString)){
						MainFrame frame = new MainFrame(userName);
						isRight = true;
					}
				}
				if(!isRight){
					JOptionPane.showMessageDialog(null, "Your password is Wrong or user doesn't exit!");
				}
					// TODO: handle exception
			
		      }
		}
		
	}
	class logKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar() == KeyEvent.VK_ENTER){
				button1.doClick();
			}
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	class downKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyChar()==KeyEvent.VK_ENTER){
				passField.requestFocus();
			}
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}