package ex3;

import common.CategoryCountObject;
import common.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class CategoryObjectAndFilePath implements Comparable<CategoryObjectAndFilePath>{
    private CategoryCountObject categoryCountObject;
    private String path;

    public CategoryObjectAndFilePath(CategoryCountObject categoryCountObject, String path) {
        this.categoryCountObject = categoryCountObject;
        this.path = path;
    }

    public CategoryCountObject getCategoryCountObject() {
        return categoryCountObject;
    }

    public void setCategoryCountObject(CategoryCountObject categoryCountObject) {
        this.categoryCountObject = categoryCountObject;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int compareTo(CategoryObjectAndFilePath compareObject) {
        return this.getCategoryCountObject().compareTo(compareObject.getCategoryCountObject());
    }
}

public class SolutionEx3 {
    public File sortLargeFile(String fileLocation) {
        int maxNumsLine = 1000;
        String smallFilesDir = "smallFiles";
        List<File> files = Utils.separateFile(fileLocation, smallFilesDir, maxNumsLine);
        List<File> smallSortedFiles = files.stream().map(file -> sortSmallFileByObjectId(file)).collect(Collectors.toList());

        HashMap<String, Integer> filePathToNextLineToRead = new HashMap<>();

        PriorityQueue<CategoryObjectAndFilePath> categoryCountObjectPriorityQueue = new PriorityQueue<>();

        smallSortedFiles.forEach(file -> {
            String lineData = getLineData(file.getPath(), 0);
            if (lineData == null) return;
            CategoryCountObject categoryCountObject = Utils.convertCsvLineDataToObject(lineData);
            categoryCountObjectPriorityQueue.add(new CategoryObjectAndFilePath(categoryCountObject, file.getPath()));
            filePathToNextLineToRead.put(file.getPath(), 1);
        });

        File file = Utils.createFile("result/result.csv");
        System.out.println("print sort result");
        while (!categoryCountObjectPriorityQueue.isEmpty()) {
            CategoryObjectAndFilePath item = categoryCountObjectPriorityQueue.poll();
            CategoryCountObject categoryCountObject = item.getCategoryCountObject();
//            System.out.println(categoryCountObject);
            Utils.writeDataToFile(categoryCountObject.getCsvString() + "\n", file);
            String path = item.getPath();
            Integer lineToRead = filePathToNextLineToRead.get(path);
            try {
                String nextObjectInQueueData = getLineData(path, lineToRead);
                if (nextObjectInQueueData != null) {
                    categoryCountObjectPriorityQueue.add(new CategoryObjectAndFilePath(Utils.convertCsvLineDataToObject(nextObjectInQueueData), path));
                    filePathToNextLineToRead.put(path, lineToRead + 1);
                }
            } catch (Exception e) {
                continue;
            }
        }
        return file;
    }

    private File sortSmallFileByObjectId(File file) {
        List<CategoryCountObject> categoryCountObjects = Utils.readDataFromCsvFile(file.getPath());
        Collections.sort(categoryCountObjects);
        String filePath = "small_file_sorted/" + file.getName() + "_sorted.csv";
        File resultFile = Utils.createFile(filePath);
        Utils.writeToCsvFile(categoryCountObjects, filePath);
        return resultFile;
    }

    public String getLineData(String fileLocation, Integer lineNo) {
        try (Stream lines = Files.lines(Paths.get(fileLocation))) {
            String extractedLine = (String) lines.skip(lineNo).findFirst().get();
            return extractedLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SolutionEx3 mainEx3 = new SolutionEx3();
        String sourceFilePath = "src/hash_catid_count.csv";
//        String sourceFilePath = "src/test.csv";
        String targetFilePath = "testFile/randomOrderFile.csv";
        Utils.getRandomOrderFile(sourceFilePath, targetFilePath);
        mainEx3.sortLargeFile(targetFilePath);
    }
}
