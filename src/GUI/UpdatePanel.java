package GUI;

import tools.MetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UpdatePanel extends JPanel {
    JLabel notice, ori, des, name, tel;
    JTextField oriName, oriTel, desName, desTel;
    JButton update;
    MetaData[] dataHashedByName, dataHashedByTel;
    int type;

    //更新面板
    public UpdatePanel() {
        notice = new JLabel("if one of origin data is empty, we think it is wildcard");
        name = new JLabel("     name                 ");
        tel = new JLabel("    tel");
        ori = new JLabel("origin:");
        des = new JLabel("destination:");

        oriName = new JTextField(10);
        oriTel = new JTextField(10);
        desName = new JTextField(10);
        desTel = new JTextField(10);

        update = new JButton("update");
        update.addActionListener(new UpdateActionListener());

        setLayout(new GridLayout(5, 1));

        JPanel jp1 = new JPanel();
        jp1.add(notice);

        JPanel jp2 = new JPanel();
        jp2.add(name);
        jp2.add(tel);

        JPanel jp3 = new JPanel();
        jp3.add(ori);
        jp3.add(oriName);
        jp3.add(oriTel);

        JPanel jp4 = new JPanel();
        jp4.add(des);
        jp4.add(desName);
        jp4.add(desTel);

        JPanel jp5 = new JPanel();
        jp5.add(update);

        add(jp1);
        add(jp2);
        add(jp3);
        add(jp4);
        add(jp5);

    }

    public UpdatePanel(MetaData[] hashedByName, MetaData[] hashedByTel, int type) {
        this();
        this.dataHashedByName = hashedByName;
        this.dataHashedByTel = hashedByTel;
        this.type = type;
    }

    class UpdateActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oName = oriName.getText();
            String oTel = oriTel.getText();
            String dName = desName.getText();
            String dTel = desTel.getText();

            boolean missName = false;
            boolean missTel = false;

            if (dName.equals("") && dTel.equals("")) {
                notice.setText("you should write the destination values");
            } else if (oName.equals("") && oTel.equals("")) {
                notice.setText("you should write the origin values");
            } else {
                int updateType;
                MetaData temp;
                if(dName.equals("")) missName = true;
                if(dTel.equals("")) missTel = true;
                if(oName.equals("")) {
                    updateType = MetaData.ALLNAME;
                    temp = MetaData.searchData(type, oTel, oName, oTel, dataHashedByTel, updateType);
                } else if(oTel.equals("")) {
                    updateType = MetaData.ALLTEL;
                    temp = MetaData.searchData(type, oName, oName, oTel, dataHashedByName, updateType);
                } else {
                    updateType = MetaData.NOTALL;
                    temp = MetaData.searchData(type, oName, oName, oTel, dataHashedByName, updateType);
                }

                int count = 0;
                while (temp != null) {
                    if(missName) dName = temp.name;
                    if(missTel) dTel = temp.tel;
                    MetaData.updateData(type, temp.name, dName, temp.name, temp.tel, dName, dTel, dataHashedByName);
                    MetaData.updateData(type, temp.tel, dTel, temp.name, temp.tel, dName, dTel, dataHashedByTel);
                    temp = temp.next;
                    count++;
                }
                JOptionPane.showMessageDialog(null, "update " + count + " records ok", "log", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
