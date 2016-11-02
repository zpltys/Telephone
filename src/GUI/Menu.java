package GUI;

import tools.LoadDataFromFile;
import tools.MetaData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.*;
import java.applet.Applet;

/*
 * the menu window for controlling all functions
 */

public class Menu extends JFrame {

    Login login;    //for callback
    MetaData[] dataHashedByName, dataHashedByTel;   // two meta data hashed by name and telephone
    int type;       //the same mean as in Login and initialed by Login

    protected void makeJpanel(JPanel jPanel, GridBagLayout gridbag, GridBagConstraints c) {
        gridbag.setConstraints(jPanel, c);
        add(jPanel);
    }

    public Menu() {
    }

    public void init() {
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        setLayout(gridBag);

        c.fill = GridBagConstraints.BOTH;

        MenuBar menuBar = new MenuBar();
        MutiView mutiView = new MutiView();
        menuBar.setMutiView(mutiView);
        menuBar.init();

        c.weightx = 1.0;
        makeJpanel(menuBar, gridBag, c);

        c.weightx = 7.0;
        makeJpanel(mutiView, gridBag, c);


        setSize(600, 250);
        setTitle("Menu");
        setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
        setVisible(true);
    }

    public static void main(String args[]) {
        Menu ex1 = new Menu();
    }

    //左边的控制栏
    class MenuBar extends JPanel {
        private void addContain(JButton b) {
            JPanel jP = new JPanel();
            jP.add(b);
            this.add(jP);
        }

        MutiView cm;

        public void setMutiView(MutiView aa) {
            cm = aa;
        }

        public void init() {
            JButton add, update, delete, search, close;

            add = new JButton("add");
            update = new JButton("update");
            delete = new JButton("delete");
            search = new JButton("search");
            close = new JButton("close");

            setLayout(new GridLayout(5, 1));

            AddActionListener addListener = new AddActionListener();
            addListener.setMutiView(cm);
            add.addActionListener(addListener);

            UpdateActionListener updateListener = new UpdateActionListener();
            updateListener.setMutiView(cm);
            update.addActionListener(updateListener);

            DeleteActionListener deleteListener = new DeleteActionListener();
            deleteListener.setMutiView(cm);
            delete.addActionListener(deleteListener);

            SearchActionListener searchListener = new SearchActionListener();
            searchListener.setMutiView(cm);
            search.addActionListener(searchListener);

            CloseActionListener closeListener = new CloseActionListener();
            closeListener.setMenu(Menu.this);
            close.addActionListener(closeListener);

            addContain(add);
            addContain(update);
            addContain(delete);
            addContain(search);
            addContain(close);
        }
    }

    //右边的功能区
    class MutiView extends JPanel {
        public CardLayout card;

        public MutiView() {
            card = new CardLayout();
            setLayout(card);

            add(new AddPanel(dataHashedByName, dataHashedByTel, type), "add");
            add(new UpdatePanel(dataHashedByName, dataHashedByTel, type), "update");
            add(new DeletePanel(dataHashedByName, dataHashedByTel, type), "delete");
            add(new SearchPanel(dataHashedByName, dataHashedByTel, type), "search");

            card.first(this);
        }
    }
}

//功能区的按键监听
class MutiActionListener {
    Menu.MutiView mutiView;

    void setMutiView(Menu.MutiView m) {
        mutiView = m;
    }
}

class AddActionListener extends MutiActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        mutiView.card.show(mutiView, "add");
    }
}
class UpdateActionListener extends MutiActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        mutiView.card.show(mutiView, "update");
    }
}
class DeleteActionListener extends MutiActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        mutiView.card.show(mutiView, "delete");
    }
}
class SearchActionListener extends MutiActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        mutiView.card.show(mutiView, "search");
    }
}


//关闭按钮的监听
class CloseActionListener implements ActionListener {
    Menu m;

    void setMenu(Menu mm) {
        m = mm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LoadDataFromFile.pushDateIntoFile(m.dataHashedByName);
        m.dispose();
        m.login.setVisible(true);
        m.login.clearPwd();
    }
}


