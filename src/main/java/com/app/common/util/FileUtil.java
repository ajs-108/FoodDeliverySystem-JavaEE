package com.app.common.util;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    private static final String DIR_PATH = "D:\\user\\IdeaProjects\\Food_Delivery_System_JavaEE\\src\\main\\webapp";

    public static String getFilePath(String folder, Part part) throws IOException {
        String directoryPath = DIR_PATH + File.separator + folder;
        File fileDirectory = new File(directoryPath);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdir();
        }
        String filePath = directoryPath + File.separator + part.getSubmittedFileName();
        part.write(filePath);
        return folder + File.separator + part.getSubmittedFileName();
    }
}
