import com.sun.org.apache.xalan.internal.xsltc.compiler.CompilerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Meta {
    private static final int NAME_LENGTH = 10;
    public static void main(String[] args) {
        StringBuilder randomName = new StringBuilder();
        for (int i = 0; i < NAME_LENGTH; i++) {
            randomName.append((char)((int)(Math.random() * 25) + 'a'));
        }
        randomName.append(".java");
        File file = new File(randomName.toString());
        writeProgram(file);
        compileProgram(file);
        runProgram(file);
    }
    public static void writeProgram(File file) {
        System.out.println("Writing " + file);
        try {
            FileWriter out = new FileWriter(file);
            String className = className(file);
            out.write(String.format("public class %s { public static void main(String[] args) { System.out.println(\"Hello!\"); } }",className));
            out.close();
        } catch (Exception e) {
            System.err.println("Error writing to '" + file + "'.");
            e.printStackTrace();
        }
    }
    public static void compileProgram(File file) {
        System.out.println("Compiling " + file);
        try {
            System.out.println(file.getAbsoluteFile());
            Process pro = Runtime.getRuntime().exec("javac " + file.getAbsoluteFile());
            pro.waitFor();
            if (pro.exitValue() != 0) {
                throw new CompilerException("Error compiling program " + file);
            }
        }
        catch (Exception e) {
            System.err.println("Error compiling " + file + ".");
            e.printStackTrace();
        }
    }
    public static String className(File file) {
        return file.getName().split(".java")[0];
    }
    public static String getPackage() {
        try {
            return Class.forName("Meta").getClass().getPackage().toString();
        } catch (Exception e) {
            System.err.println("Unable to get package name");
            e.printStackTrace();
        }
        return "unknown";
    }
    public static void runProgram(File file) {
        System.out.println("Running " + file);
        String className = className(file);
        try {
            Process pro = Runtime.getRuntime().exec("java " + className);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("Program standard out: " + line);
            }
            pro.waitFor();
        } catch (Exception e) {
            System.err.println("Error occured when attempting to run " + file);
            e.printStackTrace();
        }
    }
    public static String separator() {
        return System.getProperty("file.separator");
    }
}
