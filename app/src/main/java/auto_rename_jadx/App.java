/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package auto_rename_jadx;
import java.io.File;
import jadx.api.JadxArgs;
import jadx.api.JadxDecompiler;
import jadx.api.JavaClass;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
  public static void main(String[] args) {
    JadxArgs jadxArgs = new JadxArgs();
    String path = (args.length != 0) ? args[0] : "youtube.apk";
    jadxArgs.setInputFile(new File(path));
    Pattern className = Pattern.compile("return \"(\\w+)\\{");
    // jadxArgs.setOutDir(new File("output"));
    try (JadxDecompiler jadx = new JadxDecompiler(jadxArgs)) {
      jadx.load();
      Path mappingsFolder=Files.createDirectories(Path.of("mappings/")); 
      for (JavaClass cls : jadx.getClasses()) {
        if (Character.isLowerCase(cls.getName().charAt(0))) {
          if (cls.searchMethodByShortId("toString()Ljava/lang/String;") != null) {
            Matcher classNameMatcher = className.matcher(cls.getCode());
            if (classNameMatcher.find()) {
              if (!cls.getName().equals("jgo")) {
                String fileContent = "CLASS " + cls.getName() + " " + matcher.group(1);
                System.out.println(fileContent);
                Files.writeString(mappingsFolder.resolve(classNameMatcher.group(1)+".mapping"),fileContent,StandardOpenOption.CREATE);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
