public class AllFourCode {
    private int MAX_FOURCODE = 10000;
    private int codePtr = 0;          //指向下一条将要产生的代码
    private static int tno = 0;
    private static int beginno = 0;
    private static int ifno = 0;
    private static int elseno = 0;
    private static int whileloopno = 0;
    private static int whileendno = 0;
    private static int vartno = 0;


    private FourCode[] fourCodeArray = new FourCode[MAX_FOURCODE];

    public AllFourCode() {
        for (int i = 0; i < MAX_FOURCODE; i++) {
            fourCodeArray[i] = new FourCode();
        }
    }

    public String gen(String op, String t1, String t2, String t3) {
        fourCodeArray[codePtr].setOP(op);
        fourCodeArray[codePtr].setT1(t1);
        fourCodeArray[codePtr].setT2(t2);
        switch (op) {
            case "ADD":
            case "SUB":
            case "MUL":
            case "DIV":
            case "MOV":     //运算符及数组操作生成
                if (t3.equals("#")) {
                    t3 = t3 + tno;
                    tno += 1;
                }
                fourCodeArray[codePtr].setT3(t3);
                break;
            case "BNE":
            case "BEQ":
            case "BGEZ":
            case "BGTZ":
            case "BLEZ":
            case "BLTZ":     //条件跳转生成
                switch (t3) {
                    case "if":
                        t3 += ifno;
                        ifno += 1;
                        break;
                    case "while":
                        t3 = "whileend" + whileendno;
                        whileendno += 1;
                        break;
                }
                fourCodeArray[codePtr].setT3(t3);
                break;
            case "JMP":                                            //无条件跳转生成
                if (t3.equals("else")) {
                    t3 += elseno;
                    elseno += 1;
                } else if (t3.equals("begin")) {
                    t3 += beginno;
                    beginno += 1;
                }
                fourCodeArray[codePtr].setT3(t3);
                break;
            case "PRO":
            case "VAR":
            case "CON":
            case "CALL":
            case "READ":
            case "WRITE"://声明语句和调用语句生成
                fourCodeArray[codePtr].setT3(t3);
                break;
            case "VART":    //临时变量空间声明处理
                if (t3.equals("0")) {
                    fourCodeArray[codePtr].setT3("0");
                    t3 = codePtr + "";
                } else {
                    vartno = Integer.valueOf(t3);
                    fourCodeArray[vartno].setT3(tno + "");
                    codePtr--;
                }
                break;
            case "LABEL":                //标记生成
                if (t3.equals("while")) {
                    t3 += whileloopno;
                    whileloopno++;
                }
                fourCodeArray[codePtr].setT3(t3);
                break;
        }
        codePtr++;
        return t3;
    }

    public int getCodePtr() {
        return codePtr;
    }


    public FourCode[] getFourCodeArray() {
        return fourCodeArray;
    }

    public void printAll() {
        for (int i = 0; i < codePtr; i++) {
            fourCodeArray[i].print();
        }
    }

}
