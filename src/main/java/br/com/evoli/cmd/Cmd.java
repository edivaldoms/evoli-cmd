package br.com.evoli.cmd;

/*
 * 15/10/2016
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author edivaldo
 */
public class Cmd {

  //private static final String CMD = "REM netsh int ip set address name = \"Local Area Connection\" source = static addr = 192.168.222.3 mask = 255.255.255.0";
  ////String command = "git clone https://github.com/volojs/create-template.git";
  ///
  public static void run(String argument) throws IOException {
    List<String> command = new ArrayList<>();
    OsType.OS_TYPE osType = OsType.getOperatingSystemType();
    //System.out.println("OS: " + osType);
    String shell;
    if (osType.toString().equals("Windows")) {
      command.add("cmd.exe");
      command.add("/c");
    } else {
      shell = "/bin/bash";
      command.add(shell);
    }

    {
      String[] arg = argument.split(" ");
      command.addAll(Arrays.asList(arg));
      //command.addAll(Arrays.asList(argument));
    }
    //command.add(argument);
    //command.addAll(Arrays.asList(argument));

    //System.out.println("####$$: " + command.toString());
    InputStream inputStream = null;
    InputStream errorStream = null;
    try {
      ProcessBuilder processBuilder = new ProcessBuilder(command);
      Process process = processBuilder.start();

      inputStream = process.getInputStream();
      errorStream = process.getErrorStream();

      // Get input streams
      BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
      BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      // Read command standard output
      String s;
      if (stdInput.readLine() != null && (stdInput.readLine() + "").length() > 0) {
        //System.out.println(":) ");
        while ((s = stdInput.readLine()) != null) {
          System.out.println(s);
        }
      }

      // Read command errors
      if (stdError.readLine() != null && (stdError.readLine() + "").length() > 0) {
        System.out.println(":| ");
        while ((s = stdError.readLine()) != null) {
          System.out.println(s);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        inputStream.close();
      }
      if (errorStream != null) {
        errorStream.close();
      }
    }
  }

  public static void main0(String args[]) throws IOException {
    String line;
    Scanner scan = new Scanner(System.in);

    Process process = Runtime.getRuntime().exec("cmd.exe /c");
    OutputStream stdin = process.getOutputStream();
    InputStream stderr = process.getErrorStream();
    InputStream stdout = process.getInputStream();

    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));

    String input = scan.nextLine();
    input += "\n";
    writer.write(input);
    writer.flush();

    input = scan.nextLine();
    input += "\n";
    writer.write(input);
    writer.flush();

    while ((line = reader.readLine()) != null) {
      System.out.println("Stdout: " + line);
    }

    input = scan.nextLine();
    input += "\n";
    writer.write(input);
    writer.close();

    while ((line = reader.readLine()) != null) {
      System.out.println("Stdout: " + line);
    }
  }

  public static void main00(String args[]) throws IOException {
    String com = "java -version";

    //String[] fff = com.split(" ");
    //RunPromptCommand.run(fff);
    Cmd.run(com);

    System.exit(0);
  }

  public static void main(String args[]) throws IOException {
    //RunPromptCommand.executa();

    // Exemplo de comando
    //ping google.com
    //git clone https://github.com/volojs/create-template.git
    //dir
    //rmdir create-template /q /s
    //exit
    //
    Scanner reader = new Scanner(System.in);  // Reading from System.in
    System.out.println("Digite o comando: ");
    while (reader.hasNext()) {
      String comando = reader.nextLine();
      if (comando != null && comando.length() > 0 && !comando.equalsIgnoreCase("exit")) {
        Cmd.run(comando);
      } else {
        Cmd.run("exit");
        System.out.println("Até logo...");
        System.exit(0);
        break;
      }
    }

    Cmd.run("exit");
    System.out.println("Até logo.");
    System.exit(0);
  }
}
