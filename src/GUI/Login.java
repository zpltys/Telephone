package GUI;

/**
 * Created by zpltys on 16/10/24.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import java.util.*;

import tools.LoadDataFromFile;
import tools.Md5;
import tools.MetaData;

public class Login extends JFrame {

    private String FileName = "UserInfo";

    private JPanel namePanel, pwdPanel, buttonPanel;
    private JTextField name;
    private JPasswordField password;
    private JLabel nameLabel, pwdLabel;
    private JButton login, register;

    private int type = 2;

    public Login() {
        namePanel = new JPanel();
        pwdPanel = new JPanel();
        buttonPanel = new JPanel();

        name = new JTextField(15);
        password = new JPasswordField(15);

        nameLabel = new JLabel("Usercount: ");
        pwdLabel = new JLabel("Password: ");

        login = new JButton("login");
        register = new JButton("register");

        login.addActionListener(new LoginActionListener());
        register.addActionListener(new RegisterActionLister());

        this.setLayout(new GridLayout(3, 1));
        this.add(namePanel);
        this.add(pwdPanel);
        this.add(buttonPanel);

        namePanel.add(nameLabel);
        namePanel.add(name);

        pwdPanel.add(pwdLabel);
        pwdPanel.add(password);

        buttonPanel.add(login);
        buttonPanel.add(register);

        setTitle("Login...");//标题
        setSize(400, 150);//大小
        setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
        setVisible(true);//显示窗体

        //锁定窗体
        this.setResizable(false);
    }

    private Boolean vaildPwd (String user, String password) throws Exception {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(FileName));
        } catch (Exception e) {
            System.out.println("zs-log no file:" + FileName);
        }

        String line = null;
        Map<String, String> user2pwd = new HashMap<String, String>();
        while ((line = reader.readLine()) != null) {
            String[] strs = line.split("\t");
            user2pwd.put(strs[0], strs[1]);
        }

        String entried = user2pwd.get(user);
        return entried != null && Md5.validPassword(password, entried);
    }

    public static void main(String[] args) {
        JFrame haha = new Login();
    }

    class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = name.getText();
            String pwd = new String(password.getPassword());

        //    System.out.println("zs-log: user: " + user);
        //    System.out.println("zs-log: pwd: " + pwd);

            Boolean isMatch = false;
            try {
                isMatch = vaildPwd(user, pwd);
            } catch (Exception e1) {
                System.err.println("zs-err: read error");
            }


            if(isMatch) {
                Menu menu = new Menu();
                LoadDataFromFile.setUser(user);

                Login.this.setVisible(false);
                menu.dataHashedByName = LoadDataFromFile.loadDateHashedByName(type);
                menu.dataHashedByTel = LoadDataFromFile.loadDateHashedByTel(type);
                menu.type = type;
                menu.login = Login.this;

                menu.init();
            } else {
                pwdLabel.setText("error password, please retry");
            }
        }
    }

    class RegisterActionLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Login.this.setVisible(false);
            Register register = new Register();
            register.setFileName(FileName);
            register.setLogin(Login.this);
            pwdLabel.setText("Password: ");
        }
    }

}
