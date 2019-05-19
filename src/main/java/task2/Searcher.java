package task2;

import java.io.*;
import java.util.*;

//                      grep [-v] [-i] [-r] word Example.txt               grep [-v] [-i] по Example.txt  grep [-i] [-r] Старик рыбачил один Example.txt сказал старик.

class Searcher {

    public static void main(String[] args) {
        System.out.println(lookFor("grep [-i] по Example.txt"));
    }

    String input(String val) {
        if (val == null) {
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            in.close();
            return lookFor(command);
        } else return lookFor(val);
    }

    private static String lookFor(String input) {

        String[] temp = input.split(" ");

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

        //-r (regex) вместо слова задаёт регулярное выражение для поиска (на консоль выводятся только строки, содержащие данное выражение)
        //-v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
        //-i игнорировать регистр слов

        return reader(regex, invert, ignore, temp, leng);
    }


    private static String reader(Boolean regex, Boolean invert, Boolean ignore, String[] value, int leng) {
        StringBuilder end = new StringBuilder();
        String search = value[value.length - 2];
        String sourse = "Input\\" + value[value.length - 1];
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
                for (int i = 0; i < word.length; i++) {
                    if (!regex) {
                        if (ignore) {
                            if (word[i].equalsIgnoreCase(search)) {
                                list.set(num, true);
                                break;
                            }
                        } else if (Objects.equals(word[i], search)) {
                            list.set(num, true);
                            break;
                        }
                    } else {

                        int counter = 0;
                        for (int j = 0; j < value.length - leng; j++) {
                            if (word.length <= i + value.length - leng) {
                                break;
                            }
                            int i1 = value.length - leng + j;
                            if (ignore) {
                                if (word[i + j].equalsIgnoreCase(value[i1])) {
                                    counter++;
                                    if (counter == value.length - leng) {
                                        list.set(num, true);
                                        break;
                                    }
                                } else j = value.length - leng;
                            } else if (Objects.equals(word[i + j], value[i1])) {
                                counter++;
                                if (counter == value.length - leng) {
                                    list.set(num, true);
                                    break;
                                }
                            } else j = value.length - leng;
                        }
                    }
                }
                num++;
                line = reader.readLine();


            }
            num = 0;

            while (line2 != null) {
                if (!invert) {
                    if (list.get(num)) {
                        end.append(line2);

                    }
                } else if (!list.get(num)) {
                    end.append(line2);
                }
                line2 = reader2.readLine();
                num++;
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return end.toString();

    }
}
