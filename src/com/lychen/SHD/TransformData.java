package com.lychen.SHD;

import java.io.*;
import java.util.Scanner;

public class TransformData {
    public static void Msv2Mhs(String fileName) throws IOException {
        if (fileName.contains("scp") || fileName.contains("example")) {
            Scanner scanner = new Scanner(new File(fileName));
            int universeSize = scanner.nextInt();
            int numOfSets = scanner.nextInt();

            for (int i = 0; i < numOfSets; i++) {
                scanner.nextInt();
            }
            String outFileName = "";
            if (fileName.contains("scp"))
                outFileName = "./src/com/lychen/SHD/data/syn/" + fileName.substring(fileName.indexOf("scp"));
            else
                outFileName = "./src/com/lychen/SHD/data/paper/" + fileName.substring(fileName.indexOf("example"));
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(outFileName));
            for (int i = 0; i < universeSize; i++) {
                int numOfSetsForElem = scanner.nextInt();
                for (int j = 0; j < numOfSetsForElem; j++) {
                    if (j != 0) osw.write(" ");
                    String set = String.valueOf(scanner.nextInt());
                    osw.write(set);
                }
                osw.write("\n");
            }
            osw.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "D:\\enumerateWeightedSetCovers\\tests\\paper\\example1.txt";
        Msv2Mhs(fileName);
    }
}
