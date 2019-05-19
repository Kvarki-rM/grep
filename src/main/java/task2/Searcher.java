package task2;

import java.io.*;
import java.util.*;
import java.util.Scanner;

//                      grep [-v] [-i] [-r] word Example.txt               grep [-v] [-i] по Example.txt

public class Searcher {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        in.close();
        String[] temp = command.split(" ");
        String search = temp[temp.length - 2];
        String sourse = "Input\\" + temp[temp.length - 1];

        int leng = 2;
        for (String it : temp) {
            if ((it.equals("[-r]")) || (it.equals("[-v]")) || (it.equals("[-i]"))) {
                leng++;
            }
        }

        boolean regex = false;
        boolean invert = false;
        boolean ignore = false;
        for (int i = 0; i < temp.length - 2; i++) {
            if (temp[i].equals("[-r]")) {
                regex = true;
                if (temp.length - leng <= 1) throw new IllegalArgumentException("Нет [-r]");
            }
            if (temp[i].equals("[-v]")) invert = true;
            if (temp[i].equals("[-i]")) ignore = true;
        }

        for (int i = 0; i < temp.length - 2; i++) {

        }

        //-r (regex) вместо слова задаёт регулярное выражение для поиска (на консоль выводятся только строки, содержащие данное выражение)
        //-v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
        //-i игнорировать регистр слов

        try {
            File file = new File(sourse);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            FileReader fr2 = new FileReader(file);
            BufferedReader reader2 = new BufferedReader(fr2);
            String line2 = reader2.readLine();

            int num = 0;
            ArrayList<Boolean> list = new ArrayList<>();

            while (line != null) {
                list.add(false);
                String[] word = line.split(" ");
                for (int i = 0; i < word.length; i++)
                    if (ignore) {
                        if (word[i].equalsIgnoreCase(search)) {
                            list.set(num, true);
                            break;
                        }
                    } else if (Objects.equals(word[i], search)) {
                        list.set(num, true);
                        break;
                    }
                num++;
                line = reader.readLine();


            }
            num = 0;

            while (line2 != null) {
                if (!invert) {
                    if (list.get(num)) System.out.println(line2);
                } else if (!list.get(num)) System.out.println(line2);
                line2 = reader2.readLine();
                num++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
