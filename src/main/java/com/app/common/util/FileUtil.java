package com.app.common.util;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static final String PATH = "C:\\Users\\sarpan\\IdeaProjects\\Food_Delivery_System_JavaEE\\src\\main\\resources";

    public static String toFilePath(String folder, Part part) throws IOException {
        String filePath = "";
        String directoryPath = PATH + File.separator +  folder;
        File fileDirectory = new File(directoryPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }
        filePath = directoryPath + File.separator + part.getSubmittedFileName();
        part.write(filePath);
        return filePath;
    }
}
