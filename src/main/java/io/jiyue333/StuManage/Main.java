package io.jiyue333.StuManage;

import io.jiyue333.StuManage.util.PrintFormatter;
import io.jiyue333.StuManage.util.SimpleContainer;

public class Main {

    public static void main(String[] args) {
//        DataGenerationService dataGenerationService = SimpleContainer.getInstance(DataGenerationServiceImpl.class);
//        dataGenerationService.generateRandomData();
        PrintFormatter printFormatter = SimpleContainer.getInstance(PrintFormatter.class);
        printFormatter.printMenu();
    }
}