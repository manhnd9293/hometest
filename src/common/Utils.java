package common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Utils {

    public static List<CategoryCountObject> readDataFromCsvFile(String fileLocation) {
        List<CategoryCountObject> records = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                String objectId = values[0];
                List<Long> catIdList = getLongList(values[1]);
                List<Long> catCountList = getLongList(values[2]);
                records.add(new CategoryCountObject(objectId, catIdList, catCountList));
            }
            return records;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            ;
            e.printStackTrace();
        }
        return null;
    }

    public static CategoryCountObject convertCsvLineDataToObject(String data) {
        String[] values = data.split("\t");
        String objectId = values[0];
        List<Long> catIdList = getLongList(values[1]);
        List<Long> catCountList = getLongList(values[2]);
        return new CategoryCountObject(objectId, catIdList, catCountList);
    }

    public static List<Long> getLongList(String idStringList) {
        LinkedList<Long> res = new LinkedList<>();
        String substring = idStringList.substring(1, idStringList.length() - 1);
        if (substring.length() == 0) {
            return res;
        }
        String[] split = substring.split(",");
        for (String id : split) {
            res.add(Long.parseLong(id));
        }
        return res;
    }

    public static List<File> separateFile(String fileLocation, Integer numsLineLimit) {
        createDirectory("testFiles");

        List<File> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            Integer numsLine = 0;
            Integer fileIndex = 0;
            while ((line = br.readLine()) != null) {
                numsLine++;
                stringBuilder.append(line + "\n");
                if ((int)numsLine == numsLineLimit) {
                    fileIndex++;
                    File splitFile = createFile("split" + fileIndex+ ".csv" ,  "testFiles");
                    String data = stringBuilder.toString();
                    writeDataToFile(data, splitFile);
                    numsLine = 0;
                    stringBuilder = new StringBuilder();
                    result.add(splitFile);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void createDirectory(String dirName) {
        try {
            Files.createDirectories(Path.of(dirName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataToFile(String data, File file) {
        try (PrintStream out = new PrintStream(new FileOutputStream(file.getAbsolutePath()))) {
            out.print(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static File createFile(String fileName, String dir) {
        createDirectory(dir);
        Path path = Paths.get(dir + "/" + fileName);
        try {
            File file = new File(path.toUri());
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
            return file;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    private static File writeToCsvFile(List<CategoryCountObject> list) {
        File file = createFile("randomOrderData.csv", "randomOrder");
        StringBuilder sb = new StringBuilder();
        for (CategoryCountObject item : list) {
            sb.append(item.getCsvString() + "\n");
        }
        writeDataToFile(sb.toString(), file);

        return file;
    }

    private static File randomShuffleFile(String fileLocation) {
        List<CategoryCountObject> categoryCountObjects = readDataFromCsvFile(fileLocation);
        List<CategoryCountObject> randomList = List.copyOf(Set.copyOf(categoryCountObjects));
        return writeToCsvFile(randomList);
    }

    public static void main(String[] args) {
        randomShuffleFile("src/hash_catid_count.csv");

    }
}