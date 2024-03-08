import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        String inputFilePath = inputFilePath(args);
        String outputFilePath = outputFilePath(args);

        Map<Character, Long> charsCount = countEnglishLettersInFile(inputFilePath);
        writeCharsCountToFile(outputFilePath, charsCount);
    }

    private static String inputFilePath(String[] args) {
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Input file: ");
            return scanner.nextLine();
        } else {
            return args[0];
        }
    }

    private static String outputFilePath(String[] args) {
        if (args.length < 2) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Output file: ");
            return scanner.nextLine();
        } else {
            return args[1];
        }
    }

    private static Map<Character, Long> countEnglishLettersInFile(String inputFilePath) {
        Map<Character, Long> charsCount = initializeCharsCountMap();
        try (FileInputStream inputFileStream = new FileInputStream(inputFilePath)) {
            try {
                countEnglishLetters(inputFileStream, charsCount);
            } catch (IOException e) {
                System.out.println("Error occurred while reading from " + inputFilePath);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + inputFilePath + " doesn't exist");
            exit(0);
        } catch (IOException e) {
            System.out.println("Cannot open a file " + inputFilePath);
            exit(0);
        }
        return charsCount;
    }

    private static void writeCharsCountToFile(String outputFilePath, Map<Character, Long> charsCount) {
        try (FileWriter fileOutputWriter = new FileWriter(outputFilePath)) {
            try {
                for (Map.Entry<Character, Long> entry : charsCount.entrySet()) {
                    char ch = entry.getKey();
                    long count = entry.getValue();
                    fileOutputWriter.append(ch).append(" - ").append(String.valueOf(count)).append("\n");
                }
                fileOutputWriter.flush();
            } catch (IOException e) {
                System.out.println("Error occurred while writing to " + outputFilePath);
                exit(0);
            }
        } catch (IOException e) {
            System.out.println("File " + outputFilePath + " is directory or cannot be opened");
            exit(0);
        }
    }

    private static Map<Character, Long> initializeCharsCountMap() {
        Map<Character, Long> map = new LinkedHashMap<>();
        for (char i = 'a'; i < 'z'; ++i) {
            map.put(i, 0L);
        }
        for (char i = 'A'; i < 'Z'; ++i) {
            map.put(i, 0L);
        }
        return map;
    }

    private static void countEnglishLetters(FileInputStream inputStream, Map<Character, Long> charsCount) throws IOException {
        int ch = inputStream.read();
        while (ch != -1) {
            char realChar = (char) ch;
            if (isEnglishLetter(realChar)) {
                charsCount.put(realChar, charsCount.get(realChar) + 1);
            }
            ch = inputStream.read();
        }
    }

    private static boolean isEnglishLetter(char ch) {
        return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z');
    }
}