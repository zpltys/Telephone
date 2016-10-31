package tools;

import java.util.Objects;
import java.util.SimpleTimeZone;

/**
 * Created by xinmei0016 on 16/10/26.
 */

public class MetaData {
    public String name, tel;
    public MetaData next;
    public Boolean isDel;

    final public static int NOTALL = 1;
    final public static int ALLNAME = 2;
    final public static int ALLTEL = 3;

    public MetaData() {
        next = null;
        isDel = false;
    }

    public MetaData(String name, String tel) {
        this();
        this.name = name;
        this.tel = tel;
    }

    @Override
    public boolean equals(Object an) {
        MetaData a = (MetaData) an;

        return name.equals(a.name) && tel.equals(a.tel);
    }

    public static boolean putData(int type, String hashString, String name, String tel,
                                  MetaData[] metaDatas) {
        int place = Hash.hash(hashString);
        MetaData newData = new MetaData(name, tel);

        if (type == 1) {
            int start = place;
            while (metaDatas[place] != null && !metaDatas[place].isDel && !metaDatas[place].equals(newData)) {
                place = (place + 1) % Hash.mod;
                if (place == start) return false;
            }
            metaDatas[place] = newData;
        } else if (type == 2) {
            MetaData temp = metaDatas[place];
            if (temp == null) metaDatas[place] = new MetaData(name, tel);
            else {
                MetaData fa = metaDatas[place];
                temp = fa.next;
                while (temp != null && !fa.equals(newData)) {
                    fa = temp;
                    temp = temp.next;
                }
                if (temp == null) {
                    fa.next = new MetaData(name, tel);
                }
            }
        }

        return true;
    }

    public static void main(String [] args) {
        MetaData a, b;
        a = new MetaData("a", "a");
        b = new MetaData("a", "a");
        System.out.println("is equal? :" + a.equals(b));
    }

    //if delete ok, return true, if don't have this record, return false
    public static boolean deleteData(int type, String hashString, String name, String tel,
                                     MetaData[] metaDatas) {
        int place = Hash.hash(hashString);

        if (type == 1) {
            int start = place;

   //         System.out.println("zs-log: index" + place);
 //           System.out.println("zs-log: isNull:" + (metaDatas[start] == null));
 //           System.out.println("zs-log: place:" + place);
  //          System.out.println("zs-log: name:" + metaDatas[place].name);
    //        System.out.println("zs-log: tel:" + tel);

            while (metaDatas[place] != null) {
                if (metaDatas[place].equals(new MetaData(name, tel))) {
         //           System.out.println("zs-log");
                    metaDatas[place].isDel = true;
                    return true;
                }
                place = (place + 1) % Hash.mod;
                if (place == start) return false;
            }
        } else if (type == 2) {
            if(metaDatas[place] == null) return true;
            MetaData fa = metaDatas[place];
            if(fa.name.equals(name) && fa.tel.equals(tel)) metaDatas[place] = fa.next;
            else {
                MetaData son = fa.next;
                while (son != null) {
                    if (son.equals(new MetaData(name, tel))) {
                        fa.next = son.next;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static MetaData searchData(int type, String hashString, String name,
                                      String tel, MetaData[] metaDatas, int searchMod) {
        int place = Hash.hash(hashString);
        MetaData head, now;
        head = null; now = null;
        int searchTime = 0;

        if(type == 1) {
            int start = place;
            while (metaDatas[place] != null) {
         //       System.out.println("zs-log: " + place);
                switch (searchMod) {
                    case NOTALL: {
                        searchTime++;
                        if (metaDatas[place].equals(new MetaData(name, tel))) {
                            System.out.println("zs-log: type " + type + " search: " + searchTime);
                            if(metaDatas[place].isDel) return null;
                            return new MetaData(name, tel);
                        }
                        break;
                    }

                    case ALLNAME: {
                        if(metaDatas[place].tel.equals(tel) && !metaDatas[place].isDel) {
                            if(head == null) {
                                head = new MetaData(metaDatas[place].name, metaDatas[place].tel);
                                now = head;
                            }
                            else {
                                now.next = new MetaData(metaDatas[place].name, metaDatas[place].tel);
                                now = now.next;
                            }
                        }
                        break;
                    }

                    case ALLTEL: {
                        if(metaDatas[place].name.equals(name) && !metaDatas[place].isDel) {
                            if(head == null) {
                                head = new MetaData(metaDatas[place].name, metaDatas[place].tel);
                                now = head;
                            }
                            else {
                                now.next = new MetaData(metaDatas[place].name, metaDatas[place].tel);
                                now = now.next;
                            }
                        }
                        break;
                    }

                }

                place = (place + 1) % Hash.mod;
                if(place == start) {
                    System.out.println("zs-log: type " + type + " search: " + searchTime);
                    return null;
                }
            }

        } else if (type == 2) {
            MetaData fa = metaDatas[place];
            while (fa != null) {
                switch (searchMod) {
                    case NOTALL: {
                        searchTime++;
                        if(fa.equals(new MetaData(name, tel))) {
                            System.out.println("zs-log: type " + type + " search: " + searchTime);
                            return new MetaData(name, tel);
                        }
                        break;
                    }

                    case ALLNAME: {
                        if(fa.tel.equals(tel)) {
                            if(head == null) {
                                head = new MetaData(fa.name, fa.tel);
                                now = head;
                            }
                            else {
                                now.next = new MetaData(fa.name, fa.tel);
                                now = now.next;
                            }
                        }
                        break;
                    }

                    case ALLTEL: {
                        if(metaDatas[place].name.equals(name) && !metaDatas[place].isDel) {
                            if(head == null) {
                                head = new MetaData(fa.name, fa.tel);
                                now = head;
                            }
                            else {
                                now.next = new MetaData(fa.name, fa.tel);
                                now = now.next;
                            }
                        }
                        break;
                    }
                }

                fa = fa.next;
            }
            System.out.println("zs-log: type " + type + " search: " + searchTime);
        }

        return head;
    }

    public static void updateData(int type, String hashString, String anHashString,
                                  String oriName, String oriTel,
                                  String desName, String desTel,
                                  MetaData[] metaDatas) {
        deleteData(type, hashString, oriName, oriTel, metaDatas);
        putData(type, anHashString, desName, desTel, metaDatas);
    }

}

