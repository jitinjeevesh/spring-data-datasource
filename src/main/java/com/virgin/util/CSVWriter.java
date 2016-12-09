package com.virgin.util;

import com.virgin.domain.TestKind;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String COMMA_DELIMITER = ",";

    public void writeToCSV(List<TestKind> testKinds) {
        String FILE_HEADER = "id,name,active,date,type";

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("TestKind.csv");
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (TestKind testKind : testKinds) {
                fileWriter.append(String.valueOf(testKind.getId()))
                        .append(COMMA_DELIMITER)
                        .append(testKind.getName())
                        .append(COMMA_DELIMITER)
                        .append(String.valueOf(testKind.getIsActive()))
                        .append(COMMA_DELIMITER)
                        .append(testKind.getCurrentDate().toString())
                        .append(COMMA_DELIMITER)
                        .append(String.valueOf(testKind.getKindType()))
                        .append(NEW_LINE_SEPARATOR);
            }
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

}
