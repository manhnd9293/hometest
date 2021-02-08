package common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Utils {

    /**
     * read data from csv file
     * @param fileLocation
     * @return
     */
    public static List<CategoryCountObject> readDataFromCsvFile(String fileLocation) {
        List<CategoryCountObject> records = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] values = line.split("\t");
                String objectId = values[0];
                System.out.println("objId = " + objectId);
                System.out.println("cat id = " + values[1]);
                System.out.println("cat id cout = " + values[2]);
                List<Long> catIdList = getLongList(values[1]);
                List<Long> catCountList = getLongList(values[2]);
                records.add(new CategoryCountObject(objectId, catIdList, catCountList));
            }
            return records;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get list of long type data from string data in form: "num1,num2,num3..."
     * @param idStringList
     * @return
     */
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

    /**
     * convert string data at a line in csv file to object
     * @param data string information in csv file
     * @return category count object
     */
    public static CategoryCountObject convertCsvLineDataToObject(String data) {
        String[] values = data.split("\t");
        String objectId = values[0];
        List<Long> catIdList = getLongList(values[1]);
        List<Long> catCountList = getLongList(values[2]);
        return new CategoryCountObject(objectId, catIdList, catCountList);
    }

    /**
     * separate a large csv file into small files which contain a specific maximum number of line
     * @param sourceFileLocation location of the large file
     * @param targetDir path of directory contain small files
     * @param maxNumOfLines maximum number of line in each small files
     * @return list of small files object
     */
    public static List<File> separateLargeFile(String sourceFileLocation, String targetDir, Integer maxNumOfLines) {
        createDirectory(Path.of(targetDir));

        List<File> result = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileLocation))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            Integer numsLine = 0;
            Integer fileIndex = 0;
            while (true) {
                line = br.readLine();
                if (line == null) {
                    if (stringBuilder.toString().length() != 0) {
                        File splitFile = createSmallFile(targetDir, stringBuilder, fileIndex);
                        result.add(splitFile);
                    }
                    break;
                }
                numsLine++;
                stringBuilder.append(line + "\n");
                if ((int) numsLine == maxNumOfLines) {
                    File splitFile = createSmallFile(targetDir, stringBuilder, fileIndex);
                    fileIndex++;
                    numsLine = 0;
                    stringBuilder = new StringBuilder();
                    result.add(splitFile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * utility function to create a small files when separate large file
     * @param targetDir
     * @param stringBuilder
     * @param fileIndex
     * @return
     */
    private static File createSmallFile(String targetDir, StringBuilder stringBuilder, Integer fileIndex) {
        String data = stringBuilder.toString();
        File splitFile = createFile(targetDir + "/split" + fileIndex + ".csv");
        appendDataToFile(data, splitFile);
        return splitFile;
    }

    /**
     * append string data of category count objects to csv file
     * @param data
     * @param file
     */
    public static void appendDataToFile(String data, File file) {
        try {
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath(), true);
            out.write(data.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create a directory
     * @param dirPath
     */
    public static void createDirectory(Path dirPath) {
        try {
            Files.createDirectories(dirPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * create a file
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        Path path = Path.of(filePath);
        createDirectory(path.getParent());
        try {
            File file = new File(path.toUri());
            if (file.createNewFile()) {
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

    /**
     * write a list of category count object to a csv file
     * @param list
     * @param filePath
     * @return
     */
    public static File writeToCsvFile(List<CategoryCountObject> list, String filePath) {
        File file = createFile(filePath);
        StringBuilder sb = new StringBuilder();
        for (CategoryCountObject item : list) {
            sb.append(item.getCsvString() + "\n");
        }
        appendDataToFile(sb.toString(), file);
        return file;
    }

    /**
     * create file which object id is random order from original file
     * @param sourceFilePath source file
     * @param targetFilePath target file
     * @return
     */
    public static File getRandomOrderFile(String sourceFilePath, String targetFilePath) {
        List<CategoryCountObject> categoryCountObjects = readDataFromCsvFile(sourceFilePath);
        List<CategoryCountObject> randomList = List.copyOf(Set.copyOf(categoryCountObjects));
        return writeToCsvFile(randomList, targetFilePath);
    }

}