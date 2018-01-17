import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filename;
        System.out.println("请输入文件名");
        System.out.print(">>");
        Scanner scanner = new Scanner(System.in);
        filename = scanner.next();
        MpgAnalysis mp=new MpgAnalysis(filename);
        if(!mp.mgpAnalysis()){
            System.out.println(">compile succeed!");
        }

    }
}
