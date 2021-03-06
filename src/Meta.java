import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class Meta {
    private static final int NAME_LENGTH = 10;
    public static void main(String[] args) {
        if (args.length == 0) args = new String[] {"Hello!"};
        for (int j = 0; j < args.length; j++) {
            StringBuilder randomName = new StringBuilder();
            for (int i = 0; i < NAME_LENGTH; i++) {
                randomName.append((char) ((int) (Math.random() * 25) + 'a'));
            }
            randomName.append(".java");
            File file = new File(randomName.toString());
            writeProgram(file,args[j]);
            System.out.println();
            compileProgram(file);
            System.out.println();
            runProgram(file);
        }
    }
    public static void writeProgram(File file, String msg) {
        System.out.println("Writing " + file);
        try {
            FileWriter out = new FileWriter(file);
            String className = className(file);
            out.write(String.format("public class %s { public static void main(String[] args) { System.out.println(\"" + msg + "\"); } }",className));
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
            String[] cmd = getCmd("javac " + file.getName());
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
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
            String[] cmd = getCmd("java " + className);
            Process pro = Runtime.getRuntime().exec(cmd);
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
    public static String[] getCmd(String args) {
        String osName = System.getProperty("os.name" );
        String[] cmd = new String[3];
        if( osName.equals( "Windows NT" ) || osName.equals("Windows 10") )
        {
            cmd[0] = "cmd.exe" ;
            cmd[1] = "/C start cmd" ;
            cmd[2] = args;
        }
        else if ( osName.equals("Windows 8.1")) {
            cmd[0] = "cmd";
            cmd[1] = "/c";
            cmd[2] = args;
        }
        else if( osName.equals( "Windows 95" ) )
        {
            cmd[0] = "command.com" ;
            cmd[1] = "/C" ;
            cmd[2] = args;
        } else {
            System.out.println(osName);
            cmd[0] = "/bin/sh";
            cmd[1] = "-c";
            cmd[2] = args;
        }
        return cmd;
    }
    public static String separator() {
        return System.getProperty("file.separator");
    }
}
