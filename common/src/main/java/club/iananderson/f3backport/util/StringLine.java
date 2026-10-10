package club.iananderson.f3backport.util;

import java.util.ArrayList;

public class StringLine {
  protected ArrayList<String> stringList = new ArrayList<>();

  public StringLine() {
  }

  public static StringLine builder() {
    return new StringLine();
  }

  /**
   * Adds the string with a newline character at the end.
   **/
  public StringLine addLine(String string) {
    this.stringList.add(string + "\n");

    return this;
  }

  /**
   * Adds the string without a newline character at the end.
   *
   * @return The final combined string
   **/
  public String lastLine(String lastString) {
    this.stringList.add(lastString);

    StringBuilder lineBuilder = new StringBuilder();

    for (String stringLine : stringList) {
      lineBuilder.append(stringLine);
    }

    return lineBuilder.toString();
  }

  public String boolDesc(String description, Boolean configDefault) {
    return this.addLine(description).addLine("(true/false)").lastLine("Default is " + configDefault + ".");
  }
}
