public class MyString {
    public static void main(String args[]) {
        String hello = "hello";
        System.out.println(countChar(hello, 'h'));
        System.out.println(countChar(hello, 'l'));
        System.out.println(countChar(hello, 'z'));
        System.out.println(spacedString(hello));
    }

    public static int countChar(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

    public static boolean subsetOf(String str1, String str2) {
        for (int i = 0; i < str1.length(); i++) {
            boolean found = false;
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    str2 = str2.substring(0, j) + '-' + str2.substring(j + 1);
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public static String spacedString(String str) {
        String newString = "" + str.charAt(0);
        for (int i = 1; i < str.length(); i++) {
            newString += " " + str.charAt(i);
        }
        return newString;
    }

    public static String randomStringOfLetters(int n) {
        String randomLetters = "";
        for (int i = 0; i < n; i++) {
            int index = (int) (Math.random() * 26);
            randomLetters += "abcdefghijklmnopqrstuvwxyz".charAt(index);
        }
        return randomLetters;
    }

    public static String remove(String str1, String str2) {
        String result = "";
        for (int i = 0; i < str1.length(); i++) {
            char currentChar = str1.charAt(i);
            if (str2.indexOf(currentChar) != -1) {
                str2 = str2.replaceFirst(String.valueOf(currentChar), "");
            } else {
                result += currentChar;
            }
        }
        return result;
    }

    public static String insertRandomly(char ch, String str) {
        int randomIndex = (int) (Math.random() * (str.length() + 1));
        String result = str.substring(0, randomIndex) + ch + str.substring(randomIndex);
        return result;
    }
}
