package com.xm.coder.tryWithResources;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @auther xgs
 * @date 2020/4/12 10:17 下午
 */

public class TryWithResourcesExample {
    @Test
    public void example() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("xiaoming.txt"))) {
            // 心思在这里！
            writer.println("今晚可以不加班！");
        }
    }

    @Test
    public void useTryCatchFinally() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("xiaoming.txt"));
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    @Test
    public void useTryWithResources() {
        try (Scanner scanner = new Scanner(new File("xiaoming.txt"))) {
            while (scanner.hasNext()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }
    @Test
    public void useTryWithResourcesMulti() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File("xiaoming.txt"));
             // 小明对小红说：
             PrintWriter writer = new PrintWriter(new File("xiaohong.txt"))) {
            while (scanner.hasNext()) {
                writer.print(scanner.nextLine());
            }
        }
    }
    @Test
    public void orderOfClosingResources() throws Exception {
        try (XiaoMingResource ming = new XiaoMingResource();
             XiaoHongResource hong = new XiaoHongResource()) {
            ming.playHonorOfKings();
            hong.playHonorOfKings();
            System.out.println("各种秀操作……");
            System.out.println("玩了很久，该睡觉了");
        }
    }
}
