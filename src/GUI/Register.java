package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;

import tools.Md5;


//注册界面
public class Register extends JFrame {
    private String fileName;
    private JFrame login;

    private JLabel notice, userLabel, pwdLabel, pwdreteyLabel;
    private JPanel userPanel, pwdPanel, pwdretryPanel, noticePanel, buttonPanel;
    private JButton finish, cancel;
    private JTextField user;
    private JPasswordField pwd, pwdretry;

    public void setFileName(String name) {
        fileName = name;
    }

    public void setLogin(Login l) {
        login = l;
    }

    public Register() {
        notice = new JLabel("please retey your password!");
        userLabel = new JLabel("user name: ");
        pwdLabel = new JLabel("password: ");
        pwdreteyLabel = new JLabel("   retey:    ");

        userPanel = new JPanel();
        pwdPanel = new JPanel();
        pwdretryPanel = new JPanel();
        noticePanel = new JPanel();
        buttonPanel = new JPanel();

        finish = new JButton("Finish");
        finish.addActionListener(new FinishActionListener());

        cancel = new JButton("Cancel");
        cancel.addActionListener(new CancleActionListener());

        user = new JTextField(15);
        pwd = new JPasswordField(15);
        pwdretry = new JPasswordField(15);

        this.setLayout(new GridLayout(5, 1));
        this.add(userPanel);
        this.add(pwdPanel);
        this.add(pwdretryPanel);
        this.add(buttonPanel);
        this.add(noticePanel);

        userPanel.add(userLabel);
        userPanel.add(user);

        pwdPanel.add(pwdLabel);
        pwdPanel.add(pwd);

        pwdretryPanel.add(pwdreteyLabel);
        pwdretryPanel.add(pwdretry);

        buttonPanel.add(finish);
        buttonPanel.add(cancel);

        noticePanel.add(notice);

        setTitle("Register");//标题
        setSize(300, 200);//大小
        setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
        setVisible(true);//显示窗体

        //锁定窗体
        this.setResizable(false);

    }

    public static void main(String[] args) {
        Register j = new Register();
        j.setFileName("UserInfo");
    }

    class FinishActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pwd1 = new String(pwd.getPassword());
            String pwd2 = new String(pwdretry.getPassword());
            String name = user.getText();
            Boolean equal = pwd1.equals(pwd2);

            if(name.equals("")) {
                notice.setText("user name cannot be empty!");
                return;
            }

            if(pwd1.equals("")) {
                notice.setText("password cannot be empty!");
                return;
            }

            if(equal) {
                FileWriter fw = null;
                try {
                    fw = new FileWriter(fileName, true);
                } catch (Exception e1) {
                    System.err.println("zs-err: no file:" + fileName);
                    e1.printStackTrace();
                }

                String encry = null;
                try {
                    encry = Md5.getEncryptedPwd(pwd1);
                    fw.write(name + "\t" + encry + "\n");
                    fw.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                Register.this.dispose();
                login.setVisible(true);
            } else {
                notice.setText("not match, please retry");
                pwd.setText("");
                pwdretry.setText("");
            }
        }
    }

    class CancleActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Register.this.dispose();
            login.setVisible(true);
        }
    }

}
