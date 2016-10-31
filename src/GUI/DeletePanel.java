package GUI;

import tools.MetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xinmei0016 on 16/10/25.
 */
public class DeletePanel extends JPanel {
    JLabel notice, src, name, tel;
    JTextField srcName, srcTel;
    JButton delete;
    MetaData[] dataHashedByName, dataHashedByTel;
    int type;

    public DeletePanel() {
        notice = new JLabel("if one of origin data is empty, we think it is wildcard");
        name = new JLabel("     name                 ");
        tel = new JLabel("    tel");
        src = new JLabel("source: ");

        srcName = new JTextField(10);
        srcTel = new JTextField(10);

        delete = new JButton("delete");
        delete.addActionListener(new DeleteActionListener());

        setLayout(new GridLayout(4, 1));

        JPanel jp1 = new JPanel();
        jp1.add(notice);

        JPanel jp2 = new JPanel();
        jp2.add(name);
        jp2.add(tel);

        JPanel jp3 = new JPanel();
        jp3.add(src); jp3.add(srcName); jp3.add(srcTel);

        JPanel jp4 = new JPanel();
        jp4.add(delete);

        add(jp1); add(jp2); add(jp3); add(jp4);

    }

    public DeletePanel(MetaData[] hashedByName, MetaData[] hashedByTel, int t) {
        this();
        this.dataHashedByName = hashedByName;
        this.dataHashedByTel = hashedByTel;
        this.type = t;
    }

    class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String oName = srcName.getText();
            String oTel = srcTel.getText();

            if (oName.equals("") && oTel.equals("")) {
                notice.setText("you should write the values");
            } else {
                int updateType;
                MetaData temp;
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

                while (temp != null) {
              //      System.out.println("zs-log: name:" + temp.name + " tel:" + temp.tel);
                    MetaData.deleteData(type, temp.name, temp.name, temp.tel, dataHashedByName);
                    MetaData.deleteData(type, temp.tel, temp.name, temp.tel, dataHashedByTel);
                    temp = temp.next;
                }
            }
        }
    }
}
