package task2;

import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.util.Arrays;

//grep [-v] [-i] [-r] word Example.txt
public class Searcher {
    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        in.close();
        String[] temp = command.split(" ");
        String search = temp[temp.length - 2];
        String sourse = "Input\\" + temp[temp.length - 1];

        //try {
        //    File file = new File(sourse);
        //    //создаем объект FileReader для объекта File
        //    FileReader fr = new FileReader(file);
        //    //создаем BufferedReader с существующего FileReader для построчного считывания
        //    BufferedReader reader = new BufferedReader(fr);
        //    // считаем сначала первую строку
        //    String line = reader.readLine();
        //    while (line != null) {
        //        System.out.println(line);
        //        // считываем остальные строки в цикле
        //        line = reader.readLine();
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

    }
}