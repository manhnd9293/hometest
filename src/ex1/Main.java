package ex1;

import common.CatCountObject;
import common.Utils;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, world");
        List<CatCountObject> catCountObjects = Utils.readDataFromCsvFile();
        catCountObjects.forEach(item -> System.out.println(item));

    }
}
