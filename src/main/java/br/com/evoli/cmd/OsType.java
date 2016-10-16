package br.com.evoli.cmd;

public final class OsType {

  private static OS_TYPE theOS;

  public static OS_TYPE getOperatingSystemType() {
    if (theOS == null) {
      String OS = System.getProperty("os.name", "generic").toLowerCase();
      if (OS.contains("win")) {
        theOS = OS_TYPE.Windows;
      } else if ((OS.contains("mac")) || (OS.contains("darwin"))) {
        theOS = OS_TYPE.MacOS;
      } else {
        theOS = OS_TYPE.Linux;
      }
    }
    return theOS;
  }

  public enum OS_TYPE {
    Windows, MacOS, Linux
  }
}
