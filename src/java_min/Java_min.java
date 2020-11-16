package java_min;

import java.io.*;
import java.util.Hashtable;
import java.util.Scanner;


/**
 * 이름-학번
 */
public class Java_min implements AutoCloseable {
    private static final String FILE_PATH = "./FileSystem.txt";
    private static Hashtable<String, String> hashtable = new Hashtable<>();

    public static  void  EnhancedFileSystem() {
        // file insert
        try {
            Scanner fileScanner = new Scanner(new File(FILE_PATH));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("-");
                hashtable.put(parts[0], parts[1]); // parts[0] : 1(name), parts[1] :2(num)
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {

        }
        //파일 불러와서 메모리 적재하기 완료
    }

    public String search(String stdID) {
        return hashtable.get(stdID);
    }

    public void insert(String stdID, String name) {
        hashtable.put(stdID, name);
    }

    public void update(String stdID, String name) {
        if (hashtable.containsKey(stdID)) {
            hashtable.replace(stdID, name);
        } else {
            hashtable.put(stdID, name);
        }
    }

    public void delete(String stdID) {
        hashtable.remove(stdID);
    }

    @Override
    public void close() throws Exception {
        FileWriter fileWriter = new FileWriter(FILE_PATH);
        hashtable.forEach((k, v) -> { //k :key ,v: value
            try {
                fileWriter.write(k);
                fileWriter.write("-");
                fileWriter.write(v);
                fileWriter.write(System.getProperty("line.separator"));
            } catch (IOException e) {
                //ignore
            }
        });
        fileWriter.close();
    }

    public static void main(String[] args) {
        try (Java_min enhancedFileSystem = new Java_min()) {

            while (true) {
                System.out.println("--School Informaton--");
                System.out.println("1 : ID Insert");
                System.out.println("2 : ID Searching");
                System.out.println("3 : ID delete");
                System.out.println("4 : EXIT");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String result = bufferedReader.readLine();
                if (result.equals("1")) {
                    System.out.println("please, Insert ID NUMBER-NAME!");
                    String newRecord = bufferedReader.readLine();
                    String[] parts = newRecord.split("-");
                    enhancedFileSystem.insert(parts[0], parts[1]);
                } else if (result.equals("2")) {
                    System.out.println("Please enter the ID number you want to search for. ");
                    String name = bufferedReader.readLine();
                    String searchResult = enhancedFileSystem.search(name);
                    System.out.println(searchResult);
                } else if (result.equals("3")) {
                    System.out.println("Please enter the ID number you want to delete for. ");
                    String name = bufferedReader.readLine();
                    enhancedFileSystem.delete(name);
                } else if (result.equals("4")){
                    System.out.println("Bye Bye~ ");
                    break;
                }
                else {
                    System.out.println("You are wrong Insert");
                }
            }
        } catch (FileNotFoundException e) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}