package GUI;

import tools.MetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xinmei0016 on 16/10/25.
 */
public class AddPanel extends JPanel {
    JTextField name, tel;
    JLabel notice, nameLabel, telLabel;
    JButton addButton;
    MetaData[] dataHashedByName, dataHashedByTel;
    int type;

    public AddPanel() {

        name = new JTextField(15);
        tel = new JTextField(15);

        notice = new JLabel("please add a record by click 'add' button");
        nameLabel = new JLabel("name: ");
        telLabel = new JLabel("tel: ");

        addButton = new JButton("Add");
        addButton.addActionListener(new AddActionListener());

        setLayout(new GridLayout(4, 1));

        JPanel panel1 = new JPanel();
        panel1.add(notice);

        JPanel panel2 = new JPanel();
        panel2.add(nameLabel);
        panel2.add(name);

        JPanel panel3 = new JPanel();
        panel3.add(telLabel);
        panel3.add(tel);

        JPanel panel4 = new JPanel();
        panel4.add(addButton);

        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);

    }

    public AddPanel(MetaData[] hashedByName, MetaData[] hashedByTel, int t) {
        this();
        this.dataHashedByName = hashedByName;
        this.dataHashedByTel = hashedByTel;
        this.type = t;
    }

    class AddActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name_ = name.getText();
            String tel_ = tel.getText();
            MetaData.putData(type, name_, name_, tel_, dataHashedByName);
            MetaData.putData(type, tel_, name_, tel_, dataHashedByTel);
        }
    }

    public static void main(String args[]) {
        JFrame jf = new JFrame();
        jf.add(new AddPanel());
        jf.setSize(400, 200);
        jf.setVisible(true);
    }
}
