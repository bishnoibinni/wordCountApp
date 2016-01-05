import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Received the argument of the file successfully");
            File file = new File(args[0]);
            if (file.exists()) {
                System.out.println("Provided file path exists : " + args[0]);
                if (file.isFile()) {
                    System.out.println("Processing further on the valid input file : " + args[0]);
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String line;
                        Map<String, Integer> wordCountMap = new HashMap<>();

                        while (null != (line = bufferedReader.readLine())) {
                            processWordCounts(line, wordCountMap);
                        }

                        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>( wordCountMap.entrySet() );
                        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
                                return (o2.getValue()).compareTo( o1.getValue() );
                            }
                        });

                        for (int counter = 0; counter < 10; counter++) {
                            System.out.println(list.get(counter).getKey() + "(" + list.get(counter).getValue() + ")");
                        }

                    } catch (IOException ioe) {
                        System.out.println("IOException at the File reader level");
                    } finally {
                        if (null != fileReader) {
                            try {
                                fileReader.close();
                            } catch (IOException ioe) {
                                System.out.println("IOException in closing the fileReader");
                            }
                        }
                    }
                } else {
                    System.out.println("Please provide a valid file, provided file is not a valid File");
                }
            } else {
                System.out.println("Please verify the path of the file provided, invalid path of the file in the argument " + args[0]);
            }
        } else {
            System.out.println("Please provide the test sample.txt file as a command line argument");
        }
    }

    private static void processWordCounts(String line, Map<String, Integer> wordCountMap) {
        if (!ProjectConstantsX.NEW_LINE.equals(line) && !ProjectConstantsX.EMPTY.equals(line) && !ProjectConstantsX.SPACE.equals(line)) {
            String[] listOfWordsInALine = line.replaceAll("[^\\w'-]", " ").split(ProjectConstantsX.SPACE);
            for (String word : listOfWordsInALine) {
                word = word.trim().toLowerCase();
                if (!ProjectConstantsX.EMPTY.equals(word)) {
                    addToWordCountMap(word, wordCountMap);
                }
            }
        }
    }

    private static void addToWordCountMap(String word, Map<String, Integer> wordCountMap) {
        wordCountMap.put(word, (wordCountMap.containsKey(word) ? wordCountMap.get(word) : 0) + 1);
    }
}
