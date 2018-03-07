import java.io.*;

public class LexAnalysis {


    private CharTable charTable = new CharTable();
    private String[] symTable = charTable.getSymTable();

    private String[] constTable = charTable.getConstTable();

    private char ch = ' ';
    private int sy = 0;
    private String strToken;
    private String filename;
    private char[] buffer;
    private int searchPtr = 0;
    private static int line = 1;
    static int isNewLine = 0;

    public LexAnalysis(String _filename) {
        for (int i = 0; i < symTable.length; i++) {
            symTable[i] = null;
        }
        for (int j = 0; j < constTable.length; j++) {
            constTable[j] = null;
        }
        filename = _filename;
    }

    /**
     * 预处理函数：
     * 功能：读取源文件内容到字符数组buffer中去，包括换行符
     */
    public void preManage() {
        File file = new File(filename);
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String temp1 = "", temp2 = "";
            while ((temp1 = bf.readLine()) != null) {
                temp2 = temp2 + temp1 + String.valueOf('\n');

            }
            buffer = temp2.toCharArray();
            bf.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件不存在");
        } catch (IOException e) {
            System.out.println("文件无法打开");
        }
    }

    public String nextSym() {
        isNewLine = 0;
        strToken = "";
        getBC();//去除空格符
        int nul = 0;
        if (isLetter()) { //读取一个标识符或关键字
            do {
                concat();
                getChar();
            } while (isLetter() || isDigit());
            if (strToken.length() < 14) {     // 14 为标志符长度
                sy = charTable.isKeyWord(strToken);
            } else {
                sy = nul;
                Errors.error(0);  //标识符过长
            }
        } else if (isDigit()) {  //读取一个整数
            int intcon = 1;
            sy = intcon;
            do {
                concat();
                getChar();
            } while (isDigit());
            if (strToken.length() > 9) {
                sy = nul;
                Errors.error(1);  //整数过长
            }
        } else if (ch == ':') { //读取一个赋值符或冒号
            concat();
            getChar();
            int becomes = 18;
            if (ch == '=') {
                concat();
                sy = becomes;
                getChar();
            } else {
                sy = becomes;
                getChar();
                Errors.error(7); // 赋值符应该有等号
            }
        } else if (ch == '<') { //小于，小于等于
            concat();
            getChar();
            int lss = 10;
            if (ch == '=') {
                concat();
                int leq = 11;
                sy = leq;
                getChar();
            } else sy = lss;
        } else if (ch == '#') { //不等于
            concat();
            int neq = 7;
            sy = neq;
            getChar();
        } else if (ch == '>') { //大于，大于等于
            getChar();
            int gtr = 8;
            if (ch == '=') {
                concat();
                int geq = 9;
                sy = geq;
                getChar();
            } else sy = gtr;
        } else if (ch == '.') { //句点，结束符
            concat();
            int period = 16;
            sy = period;
        } else {                   //读取其他合法字符：'+','-','*','/','(',')','[',']','=',',',':',';'
            concat();
            sy = charTable.isPunctuationMark(ch + "");
            if (sy == nul) {
                Errors.error(2);      //一个非法字符
            }
            getChar();
        }
        return strToken;
    }

    public char getChar() {
        if (searchPtr < buffer.length) {
            ch = buffer[searchPtr];
            searchPtr++;
        }
        return ch;
    }

    public void getBC() {
        while ((ch == ' ' || ch == '	' || ch == '\n') && (searchPtr < buffer.length)) {
            if (ch == '\n') {
                line++;
                isNewLine = 1;
            }
            getChar();
        }
    }

    public void concat() {
        strToken = strToken + String.valueOf(ch);
    }

    public boolean isLetter() {
        return Character.isLetter(ch);
    }

    public boolean isDigit() {
        return Character.isDigit(ch);
    }

    public String getStrToken() {
        return strToken;
    }

    public void showError() {
        System.out.println();
        System.out.print("ERROR: cannot recognize the word in line " + line);
        System.out.println();
    }

    public static int getLine() {
        return line - isNewLine;
    }

    public int getSy() {
        return sy;
    }
}
