package tools;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

// a tool class to make data persist and load user's data
public class LoadDataFromFile {
    static String user;

    public static void setUser(String temp) {
        user = temp;
    }

    public static MetaData[] loadDateHashedByName(int type) {
        String dir = ".data/" + user;

        BufferedReader br;
        MetaData[] DataByName = new MetaData[Hash.mod];

        try {
            br = new BufferedReader(new FileReader(dir));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                String name = temp[0];
                String tel = temp[1];

                MetaData.putData(type, name, name, tel, DataByName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DataByName;
    }

    public static MetaData[] loadDateHashedByTel(int type) {
        String dir = ".data/" + user;

        BufferedReader br;
        MetaData[] DataByTel = new MetaData[Hash.mod];

        try {
            br = new BufferedReader(new FileReader(dir));
            String line = null;

            while ((line = br.readLine()) != null) {
                String[] temp = line.split(" ");
                String name = temp[0];
                String tel = temp[1];

                MetaData.putData(type, tel, name, tel, DataByTel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DataByTel;
    }

    public static void pushDateIntoFile(MetaData[] metaDatas) {
        String dir = ".data/" + user;
        FileWriter fw;
        try {
            fw = new FileWriter(dir);

            for (int i = 0; i < metaDatas.length; i++) {
                MetaData m = metaDatas[i];
                while (m != null) {
                    if(!m.isDel) fw.write(m.name + " " + m.tel + "\n");
                    m = m.next;
                }
            }
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String str[]) {
        setUser("zpltys");
        MetaData[] m = loadDateHashedByName(1);

        for(int i = 0; i < m.length; i++) {
            if(m[i] != null) {
                System.out.println("zs-log: i: " + i + " name: " + m[i].name + "  tel: " + m[i].tel);
            }
        }
    }
}
