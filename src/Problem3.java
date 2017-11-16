import java.util.Scanner;

public class Problem3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Enter start: ");
            int start = in.nextInt();
            System.out.print("Enter finish: ");
            int finish = in.nextInt();
            System.out.print("Enter num: ");
            int num = in.nextInt();
            System.out.println(findFactors(start,finish,num));
            in.nextLine();
            System.out.print("Continue? ");
            boolean cont = true;
            switch(in.nextLine().toLowerCase()) {
                case "y":
                case "yes":
                case "yep":
                case "of course":
                case "obviously":
                    break;
               default:
                    cont = false;
            }

            /*
             String Case = in.nextLine().toLowerCase();
             if ((Case.equals("y") || Case.equals("yes") || Case.equals("yep") ||
                Case.equals("of course") || Case.equals("obviously"))) {
                    // do nothing
             } else {
                cont = false;
             }
            */
            if (!cont) break;
        } while (true);
    }
    public static int findFactors (int start, int finish, int num) {
        int total = 0;
        while (start <= finish) {
            if (start % num == 0) total++;
            start++;
        }
        System.out.println(new Problem3().toString());
        return total;
    }
}
