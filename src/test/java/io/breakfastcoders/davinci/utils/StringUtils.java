package io.breakfastcoders.davinci.utils;

public class StringUtils {
  public static String flattenString(String toFlatten) {
    String ret = toFlatten.replaceAll("\n", "");
    ret = ret.replaceAll("\t", "");
    return ret.replaceAll(" ", "");
  }
}
