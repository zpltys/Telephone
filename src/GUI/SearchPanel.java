package GUI;

import tools.MetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by xinmei0016 on 16/10/25.
 */
public class SearchPanel extends JPanel {
    JLabel notice, src, name, tel;
    JTextField srcName, srcTel;
    JButton search;
    MetaData[] dataHashedByName, dataHashedByTel;
    int type;

    public SearchPanel() {
        notice = new JLabel("if one of origin data is empty, we think it is wildcard");
        name = new JLabel("     name                 ");
        tel = new JLabel("    tel");
        src = new JLabel("source: ");

        srcName = new JTextField(10);
        srcTel = new JTextField(10);

        search = new JButton("search");
        search.addActionListener(new SearchListener());

        setLayout(new GridLayout(4, 1));

        JPanel jp1 = new JPanel();
        jp1.add(notice);

        JPanel jp2 = new JPanel();
        jp2.add(name);
        jp2.add(tel);

        JPanel jp3 = new JPanel();
        jp3.add(src); jp3.add(srcName); jp3.add(srcTel);

        JPanel jp4 = new JPanel();
        jp4.add(search);

        add(jp1); add(jp2); add(jp3); add(jp4);

    }

    public SearchPanel(MetaData[] hashedByName, MetaData[] hashedByTel, int t) {
        this();
        this.dataHashedByName = hashedByName;
     //   if(hashedByName == null) System.out.println("hehe");
        this.dataHashedByTel = hashedByTel;
        this.type = t;
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String oName = srcName.getText();
            String oTel = srcTel.getText();
            String result = "name\ttelephone\n";

     //       System.out.println("zs-log: oName:" + oName);
     //       System.out.println("zs-log: oTel:" + oTel);
/*
            for (int i = 0; i < dataHashedByTel.length; i++) {
                MetaData metaData = dataHashedByTel[i];
                if(metaData != null) {
                    System.out.println("hashByTel: index:" + i);
                    while (metaData != null) {
                        System.out.println("hashByTel: name:" + metaData.name);
                        System.out.println("hashByTel: tel:" + metaData.tel);
                        metaData = metaData.next;
                    }
                }
            }

            for (int i = 0; i < dataHashedByName.length; i++) {
                MetaData metaData = dataHashedByName[i];
                if(metaData != null) {
                    System.out.println("zs-log: index:" + i);
                    while (metaData != null) {
                        System.out.println("zs-log: name:" + metaData.name);
                        System.out.println("zs-log: tel:" + metaData.tel);
                        metaData = metaData.next;
                    }
                }
            }
*/
            if (oName.equals("") && oTel.equals("")) {
                for (int i = 0; i < dataHashedByTel.length; i++) {
                    MetaData metaData = dataHashedByTel[i];
                    if(metaData != null) {
                        while (metaData != null) {
                            if(!metaData.isDel) result = result + metaData.name + "\t" + metaData.tel + "\n";
                            metaData = metaData.next;
                        }
                    }
                }
            } else {
                int updateType;
                MetaData temp;
                if(oName.equals("")) {
                    updateType = MetaData.ALLNAME;
                    temp = MetaData.searchData(type, oTel, oName, oTel, dataHashedByTel, updateType);
                } else if(oTel.equals("")) {
                    updateType = MetaData.ALLTEL;
              //      System.out.println("zs-log sadcfd");
           //         if(dataHashedByName == null) System.out.println("zs-log woc");

                    temp = MetaData.searchData(type, oName, oName, oTel, dataHashedByName, updateType);
                } else {
                    updateType = MetaData.NOTALL;
                    temp = MetaData.searchData(type, oName, oName, oTel, dataHashedByName, updateType);
                }

        //        if(temp == null) System.out.println("asfcw");

                while (temp != null) {
                    String name_ = temp.name;
                    String tel_ = temp.tel;
                    result = result + name_ + "\t" + tel_ + "\n";
                    temp = temp.next;
                }
            }

            JOptionPane.showMessageDialog(null, result, "search result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
