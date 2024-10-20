package io.jiyue333.StuManage;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import io.jiyue333.StuManage.service.DataGenerationService;
import io.jiyue333.StuManage.util.PrintFormatter;
import io.jiyue333.StuManage.service.impl.DataGenerationServiceImpl;

public class Main {

    public static void main(String[] args) {
        DataGenerationService dataGenerationService = new DataGenerationServiceImpl();
        dataGenerationService.generateRandomData();
        PrintFormatter printFormatter = new PrintFormatter();
        printFormatter.printMenu();
    }
}