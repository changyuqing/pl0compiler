import java.io.*;

public class LexAnalysis {
    private static int nul = 0;
    //变量申明符
    private static int intcon = 1;
    //运算符
    private static int minussy = 2;
    private static int plussy = 3;
    private static int multi = 4;
    private static int divsy = 5;
    //关系运算符
    private static int eql = 6;
    private static int neq = 7;
    private static int gtr = 8;
    private static int geq = 9;
    private static int lss = 10;
    private static int leq = 11;
    //括号
    private static int lparent = 12;
    private static int rparent = 13;
    //标点符号
    private static int comma = 14;
    private static int semicolon = 15;
    private static int period = 16;
    private static int colon = 17;
    private static int becomes = 18;
    //各类声明符号和标识符
    private static int constsy = 19;
    private static int varsy = 20;
    private static int procsy = 21;
    private static int ident = 22;
    //语句起始符号
    private static int beginsy = 23;
    private static int ifsy = 24;
    private static int whilesy = 25;
    private static int callsy = 26;
    private static int readsy = 27;
    private static int writesy = 28;
    private static int oddsy = 29;
    //其他
    private static int endsy = 30;
    private static int elsesy = 31;
    private static int dosy = 32;
    private static int thensy = 33;


    private static int consttype = 34;
    private static int vartype = 35;
    private static int proctype = 36;

    private CharTable charTable = new CharTable();
    private String[] symTable = charTable.getSymTable();
    private int symLength = symTable.length;
    private String[] constTable = charTable.getConstTable();
    private int conLength = constTable.length;

    private char ch = ' ';
    private int sy = 0;
    private String strToken;
    private String filename;
    private char[] buffer;
    private int searchPtr = 0;
    private static int line = 1;
    private static int isNewLine = 0;

    public LexAnalysis(String _filename) {
        for (int i = 0; i < symLength; i++) {
            symTable[i] = null;
        }
        for (int j = 0; j < conLength; j++) {
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
            //   System.out.println("read file test.txt...");
            bf = new BufferedReader(new FileReader(file));
            String temp1 = "", temp2 = "";
            while ((temp1 = bf.readLine()) != null) {
                temp2 = temp2 + temp1 + String.valueOf('\n');

            }
            buffer = temp2.toCharArray();
            bf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String nextSym() {
        isNewLine = 0;
        strToken = "";
        getBC();//去除空格符
        if (isLetter()) { //读取一个标识符或关键字
            do {
                concat();
                getChar();
            } while (isLetter() || isDigit());
            if (strToken.length() < 14) {     // 14 为标志符长度
                sy = charTable.isKeyWord(strToken);
            } else {
                sy = nul;
                Errors.error(1);  //标识符过长
            }
        } else if (isDigit()) {  //读取一个整数
            sy = intcon;
            do {
                concat();
                getChar();
            } while (isDigit());
            if (strToken.length() > 9) {
                sy = nul;
                Errors.error(2);  //整数过长
            }
        } else if (ch == ':') { //读取一个赋值符或冒号
            concat();
            getChar();
            if (ch == '=') {
                concat();
                sy = becomes;
                getChar();
            } else {
                Errors.error(60); // todo 缺少=
            }
        } else if (ch == '<') { //小于，小于等于，不等于
            // Todo 没有判断<的情况
            concat();
            getChar();
            if (ch == '=') {
                concat();
                sy = leq;
                getChar();
            } else sy = lss;
        } else if (ch == '#') { //不等于
            concat();
            sy = neq;
            getChar();
        } else if (ch == '>') { //大于，大于等于
            getChar();
            if (ch == '=') {
                concat();
                sy = geq;
                getChar();
            } else sy = gtr;
        } else if (ch == '.') { //句点，结束符
            concat();
            sy = period;
        } else {                   //读取其他合法字符：'+','-','*','/','(',')','[',']','=',',',':',';'
            concat();
            sy = charTable.isPunctuationMark(ch + "");
            if (sy == nul) {
                Errors.error(7);      //一个非法字符
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
//	System.out.print(ch);
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

    public String concat() {
        strToken = strToken + String.valueOf(ch);
        return strToken;
    }

    // todo 把isletter还原成原始函数
    public boolean isLetter() {
        if (Character.isLetter(ch)) {
            return true;
        }
        return false;
    }

    public boolean isDigit() {
        if (Character.isDigit(ch)) {
            return true;
        }
        return false;
    }

    public String getStrToken() {
        return strToken;
    }



    public void showError() {
        System.out.println();
        System.out.print("ERROR: cannot recognize the word in line " + line);
        System.out.println();


    }
    public static int getLine(){
        return line-isNewLine;
    }


    public int getSy() {
        return sy;
    }
}
