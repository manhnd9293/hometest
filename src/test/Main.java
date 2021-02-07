package test;

import common.CategoryCountObject;
import common.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;


class CategoryObjectAndFilePath{
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
}

public class Main {
    public static void sortLargeFile(String fileLocation) {
        int maxNumsLine = 100;
        List<File> files = Utils.separateFile(fileLocation, maxNumsLine);

        //todo sort each file in list files by order id


        HashMap<String, Integer> filePathToNextLineToRead = new HashMap<>();

        PriorityQueue<CategoryObjectAndFilePath> categoryCountObjectPriorityQueue = new PriorityQueue<>(new Comparator<CategoryObjectAndFilePath>() {
            @Override
            public int compare(CategoryObjectAndFilePath first, CategoryObjectAndFilePath second) {
                return first.getCategoryCountObject().getObjectId().compareTo(second.getCategoryCountObject().getObjectId());
            }
        });

        files.forEach(file -> {
            String lineData = getLineData(file.getPath(), 0);
            if (lineData == null) return;
            CategoryCountObject categoryCountObject = Utils.convertCsvLineDataToObject(lineData);
            categoryCountObjectPriorityQueue.add(new CategoryObjectAndFilePath(categoryCountObject, file.getPath()));
            filePathToNextLineToRead.put(file.getPath(), 1);
        });

        File file = Utils.createFile("sortFile", "result");
        while (categoryCountObjectPriorityQueue.isEmpty()) {
            CategoryObjectAndFilePath item = categoryCountObjectPriorityQueue.poll();
            CategoryCountObject categoryCountObject = item.getCategoryCountObject();
            Utils.writeDataToFile(categoryCountObject.getCsvString() + "\n", file);
            String path = item.getPath();
            Integer lineToRead = filePathToNextLineToRead.get(path);
            String nextQueueObject = getLineData(path, lineToRead);
            if (nextQueueObject != null) {
                categoryCountObjectPriorityQueue.add(new CategoryObjectAndFilePath(Utils.convertCsvLineDataToObject(nextQueueObject), path));
                filePathToNextLineToRead.put(path, lineToRead + 1);
            }
        }
    }

    public static String getLineData(String fileLocation, Integer lineNo) {
        try (Stream lines = Files.lines(Paths.get(fileLocation))) {
            String extractedLine = (String) lines.skip(lineNo).findFirst().get();
            System.out.println(extractedLine);
            return extractedLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String lineData = getLineData("src/hash_catid_count.csv",99999);
    }
}
