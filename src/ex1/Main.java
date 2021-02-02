package ex1;

import common.CatCountObject;
import common.Utils;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static Long getMostPopularCategoryId(List<CatCountObject> list) {
        HashMap<Long, Long> categoryIdToFrequency = new HashMap<>();
        long maxTotalFrequency = 0;
        long maxId = 0;
        for (CatCountObject catCountObject : list) {
            for (int i = 0; i < catCountObject.getCategoryId().size(); i++) {
                Long catId = catCountObject.getCategoryId().get(i);
                if (!categoryIdToFrequency.containsKey(catId)) {
                    categoryIdToFrequency.put(catId, 0L);
                }
                Long updateFrequency = categoryIdToFrequency.get(catId) + catCountObject.getCategoryCont().get(i);
                categoryIdToFrequency.put(catId, updateFrequency);
                if (updateFrequency > maxTotalFrequency) {
                    maxTotalFrequency = updateFrequency;
                    maxId = catId;
                }
            }
        }

        System.out.println("Most popular category id = " + maxId + " with frequency = " + maxTotalFrequency);
        return maxId;
    }

    public static Long getLargestAppearCategoryId(List<CatCountObject> list) {
        long maxAppearTime = 0;
        long maxId = 0;
        for (CatCountObject catCountObject : list) {
            for (int i = 0; i < catCountObject.getCategoryId().size(); i++) {
                Long appearTime = catCountObject.getCategoryCont().get(i);
                if (appearTime > maxAppearTime) {
                    maxAppearTime = appearTime;
                    maxId = catCountObject.getCategoryId().get(i);
                }
            }
        }

        System.out.println("Largest appear time category id = " + maxId + " with frequency = " + maxAppearTime);
        return maxId;
    }

    public static void main(String[] args) {
        List<CatCountObject> catCountObjects = Utils.readDataFromCsvFile("src/hash_catid_count.csv");
        getMostPopularCategoryId(catCountObjects);
        getLargestAppearCategoryId(catCountObjects);
    }
}
