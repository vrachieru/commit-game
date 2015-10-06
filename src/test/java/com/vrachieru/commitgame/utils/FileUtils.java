package com.vrachieru.commitgame.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {
  public static boolean deleteDirectory(File directory) {
    if (directory.isDirectory()) {
      boolean success = true;
      String[] children = directory.list();
      for (int i = 0; i < children.length; i++) {
        success = deleteDirectory(new File(directory, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    return directory.delete();
  }

  public static boolean createFile(File file) {
    try {
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      if (!file.exists()) {
        file.createNewFile();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
