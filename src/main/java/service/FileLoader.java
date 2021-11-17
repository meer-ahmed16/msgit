package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileLoader<T> {
    public T loadData(String fileName, Class<T> clazz) throws IOException {
        File file = new File(CoffeeMachine.class.getClassLoader().getResource(fileName).getFile());
        Scanner scanner = new Scanner(file);
        StringBuilder jsonInput = new StringBuilder();
        while(scanner.hasNext()) jsonInput.append(scanner.nextLine()+'\n');
        scanner.close();
        T object = new ObjectMapper().readValue(jsonInput.toString(), clazz);
        return object;
    }

}
