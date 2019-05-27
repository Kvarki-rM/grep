package Grep;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Grep {

    @Option(name = "-r")
    private boolean regex;

    @Option(name = "-v")
    private boolean invert;

    @Option(name = "-i")
    private boolean ignore;

    @Argument
    private List<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println(new Grep().reader(args));
    }

    //-r (regex) вместо слова задаёт регулярное выражение для поиска (на консоль выводятся только строки, содержащие данное выражение)
    //-v инвертирует условие фильтрации (выводится только то, что ему НЕ соответствует)
    //-i игнорировать регистр слов

    String reader(String[] args) throws IOException {
        StringBuilder end = new StringBuilder();
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            String sourse = arguments.get(2);
            String search = arguments.get(1);
            File file = new File(sourse);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            boolean check = false;

            while (line != null) {
                String[] word = line.split(" ");
                for (String s : word) {
                    if (!regex) {
                        if (ignore) {
                            if (s.equalsIgnoreCase(search)) {
                                check = true;
                                break;
                            }
                        } else if (Objects.equals(s, search)) {
                            check = true;
                            break;
                        }
                    } else {
                        Pattern r;
                        if (ignore) r = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
                        else r = Pattern.compile(search);
                        Matcher m = r.matcher(line);

                        if (m.find()) {
                            check = true;
                            break;
                        }

                    }
                }
                if (invert && !check)
                    //  System.out.println(line);
                    end.append(line).append("\n");
                else if (check)
                    // System.out.println(line);
                    end.append(line).append("\n");

                check = false;
                line = reader.readLine();
            }
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Grep.jar [-v] [-i] [-r] word inputname.txt");
            parser.printUsage(System.err);
            e.printStackTrace();
        }
        return end.toString();
    }
}
