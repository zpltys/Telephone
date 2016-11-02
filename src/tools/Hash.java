package tools;

public class Hash {
    final static int mod = 11003;         //the mode for hash

    // hash function to transfer string to int
    public static int hash(String str) {
        char[] s = str.toCharArray();
        int ans = 0;
        for (char c : s) {
            ans = ((ans << 8) + c);
            ans = ans % mod;
        }

        return ans;
    }

    public static void main(String args[]) {
        String test = "weefve";
        System.out.println(Hash.hash(test));

        System.out.println(Hash.hash(test));
    }
}
