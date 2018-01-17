public class MpgAnalysis {
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


    LexAnalysis lex;

    private int MaxLevel = 10;

    private SymbolTable STable = new SymbolTable();       //符号表
    private AllFourCode fourCode = new AllFourCode();                 //存放目标代码
    private BlockTable BTable = new BlockTable();
    private int[] display = new int[MaxLevel];

    private int level = 0;                //主程序为第0层

    public MpgAnalysis(String filename) {
        lex = new LexAnalysis(filename);
        lex.preManage();
    }

    public boolean mgpAnalysis() {
        lex.nextSym();
        block();
        if(Errors.getNum() == 0){
        fourCode.printAll();
        System.out.println("符号表：");
        STable.printTable();
        }
        return Errors.getNum() != 0;
    }

    public void block() {
        String vartstring,  //记录临时变量声明的四元式地址
                beginlabel; //过程和函数跳到函数体的标志
        if (lex.getSy() == constsy) {
            constDeclare();
        }
        if (lex.getSy() == varsy) {
            varDeclare();
        }
        vartstring = fourCode.gen("VART", "0", "", "");   //生成临时变量声明的四元式
        beginlabel = fourCode.gen("JMP", "", "", "begin");  //生成跳转至函数体的四元式

        while (lex.getSy() == procsy) {
            procDeclare();
        }

        fourCode.gen("LABEL", "", "", beginlabel);    //
        if (lex.getSy() == beginsy) {
            compoundStatement();
        } else {
            Errors.error(50);  //分程序必须有一个复合语句部分
        }
        fourCode.gen("VART", "1", vartstring, "");

        if (level == 0) {
            if (lex.getSy() == period) {
                return;
            } else {
                Errors.error(58);  //程序结尾缺少句号
            }
        }

        level -= 1;
    }

    private void constDeclare() {
        String identname;
        String identvalue;
        identname = lex.nextSym();
        while (lex.getSy() == ident) {
            enterTable(identname, consttype);
            lex.nextSym();
            if (lex.getSy() == eql) {
                identvalue = lex.nextSym();
                if (lex.getSy() == intcon) {
                    fourCode.gen("CON", identvalue, "-", identname);
                } else if (lex.getSy() == minussy) {
                    identvalue = lex.nextSym();
                    if (lex.getSy() == intcon) {
                        fourCode.gen("CON", "-" + identvalue, "", identname);
                    } else {
                        Errors.error(11);// 非法的常量值
                    }
                } else if (lex.getSy() == plussy) {
                    identvalue = lex.nextSym();
                    if (lex.getSy() == intcon) {
                        fourCode.gen("CON", identvalue, "", identname);
                    } else {
                        Errors.error(11);// 非法的常量值
                    }
                } else {
                    Errors.error(11);// 非法的常量值
                }
            } else if (lex.getSy() == becomes) {
                Errors.error(17);   //常量赋值符应该为等号
            } else {
                Errors.error(18);   //应该有等号为常量赋值
            }
            lex.nextSym();
            if (lex.getSy() == semicolon) {
                break;
            } else if (lex.getSy() == comma) {
                identname = lex.nextSym();
            } else {
                Errors.error(19);  //每个常量声明应该有逗号分隔
            }
        }
        if (lex.getSy() == semicolon) {
            lex.nextSym();
        } else {
            Errors.error(20);  //常量声明结尾应该有分号
        }
    }

    private void varDeclare()                       //变量声明处理
    {
        String identname;

        identname = lex.nextSym();
        while (lex.getSy() == ident) {

            enterTable(identname, vartype);
            fourCode.gen("VAR", "", "", identname);
            lex.nextSym();
            if (lex.getSy() == semicolon) {
                break;
            } else if (lex.getSy() == comma) {
                identname = lex.nextSym();
            } else {
                Errors.error(19);  //每个常量声明应该有逗号分隔
            }
        }
        if (lex.getSy() == semicolon) {
            lex.nextSym();
        } else {
            Errors.error(20);  //常量声明结尾应该有分号
        }
    }

    private void procDeclare() {
        int
                paranumber = 0,
                prt = 0,
                prb = 0;
        String procname;

        procname = lex.nextSym();
        if (lex.getSy() != ident) {
            Errors.error(30);  //没有过程名
        }
        fourCode.gen("PRO", "", "", procname);
        enterTable(procname, proctype);

        prt = STable.getTablePtr();
        lex.nextSym();

        level += 1;             //level+1,进入下一层分程序，更改display区。
        if (level >= MaxLevel) {
            Errors.error(34);  //嵌套层次过多
        }
        if (BTable.isFull()) {
            Errors.error(35);  //分程序表溢出
        }
        BTable.enterTable(0, 0, 0, 0);
        display[level] = BTable.getTablePtr();
        STable.getRow(prt).setRefer(BTable.getTablePtr());

        if (lex.getSy() == semicolon) {
            lex.nextSym();
        } else {
            Errors.error(33);  //过程和函数声明结尾应有分号
        }

        block();                //进入下一层分程序

        if (lex.getSy() == semicolon) {
            lex.nextSym();
        } else
            Errors.error(36);  //每个分程序结束应有分号
    }//procdeclare end

    public void enterTable(String name, int type) {
        int i, link;
        if (STable.isFull()) {
            Errors.error(8);    //符号表溢出

        } else {
            STable.getRow(0).setName(name);
            i = link = BTable.getRow(display[level]).getLast();

            while (!STable.getRow(i).getName().equals(name)) {
                i = STable.getRow(i).getLink();
            }
            if (i != 0) {
                Errors.error(9);   //标识符重定义
            } else {
                STable.enterTable(name, type, level, link);
            }
            BTable.getRow(display[level]).setLast(STable.getTablePtr());
        }
    }

    int locate(String name)     //在符号表查标识符
    {
        int i, j;
        i = level;
        STable.getRow(0).setName(name);
        do {
            j = BTable.getRow(display[i]).getLast();
            while (!STable.getRow(j).getName().equals(name) && j != 0) {
                j = STable.getRow(j).getLink();
            }
            i--;
        } while (i >= 0 && j == 0);
        if (j == 0) {
            Errors.error(10); //未定义的标识符
        }
        return j;
    }//locate end

    void statement()        //语句处理
    {
        int i;
        String first;

        if (lex.getSy() == ident) {
            i = locate(lex.getStrToken());
            first = lex.getStrToken();
//            if(tab[i].obj == proctype){
//                nextsym();
//                call(tab[i].refer, first);
//            }
            if (STable.getRow(i).getType() == vartype) {
                lex.nextSym();
                assignmentStatement(first);
            } else if (STable.getRow(i).getType() == consttype) {
                Errors.error(51);    //常量不能被赋值
            }
        } else if (lex.getSy() == beginsy)
            compoundStatement();
        else if (lex.getSy() == ifsy)
            ifStatement();
        else if (lex.getSy() == whilesy)
            whileStatement();
        else if (lex.getSy() == readsy)
            readProc();
        else if (lex.getSy() == writesy)
            writeproc();
        else if (lex.getSy() == callsy)
            callProc();

    }

    private void ifStatement()          //判断语句处理
    {
        String label1, label2;
        lex.nextSym();
        label1 = conditionExpression("if");
        if (lex.getSy() == thensy) {
            lex.nextSym();
        } else
            Errors.error(42);   //缺少then
        statement();

        if (lex.getSy() == elsesy) {
            label2 = fourCode.gen("JMP", "", "", "else");
            fourCode.gen("LABEL", "", "", label1);
            lex.nextSym();
            statement();
            fourCode.gen("LABEL", "", "", label2);
        } else {
            fourCode.gen("LABEL", "", "", label1);
        }
    }

    private void compoundStatement()        //复合语句处理
    {
        int sy;
        lex.nextSym();
        statement();
        sy = lex.getSy();
        while (sy == semicolon || sy == beginsy || sy == ifsy || sy == whilesy) {
            if (sy != semicolon) {
                Errors.error(40);  //复合语句中每条语句结尾应有分号
            } else
                lex.nextSym();
            statement();
            sy = lex.getSy();
        }
        if (sy == endsy) {
            lex.nextSym();
        } else
            Errors.error(41);  //缺少end
    }

    private void assignmentStatement(String first)       //赋值语句处理
    {
        String second;


        if (lex.getSy() == becomes) {
            lex.nextSym();
            second = simpleExpression();
            fourCode.gen("MOV", second, "", first);
        } else
            Errors.error(39);  //缺少赋值符号

    }

    private String conditionExpression(String label)        //条件表达式处理//增加odd
    {
        String first, second;
        String op = "";
        int sy = lex.getSy();
        if (sy == oddsy) {
            lex.nextSym();
            first = simpleExpression();
            first = fourCode.gen("ODD", first, "", "if");
        } else {
            first = simpleExpression();
            sy = lex.getSy();
            if (sy == eql || sy == neq || sy == gtr || sy == geq || sy == leq || sy == lss) {
                if (sy == eql) {
                    op = "BNE";
                } else if (sy == neq) {
                    op = "BEQ";
                } else if (sy == gtr) {
                    op = "BLEZ";
                } else if (sy == geq) {
                    op = "BLTZ";
                } else if (sy == leq) {
                    op = "BGTZ";
                } else {
                    op = "BGEZ";
                }
                lex.nextSym();
            } else {
                Errors.error(39);  //非法的关系运算符
            }
            second = simpleExpression();
            first = fourCode.gen(op, first, second, label);
        }
        return first;
    }

    private String simpleExpression()         //简单表达式处理
    {

        int refer = 0;
        int op;
        String first, second;

        if (lex.getSy() == plussy || lex.getSy() == minussy) {
            op = lex.getSy();
            lex.nextSym();
            first = term();
            if (op == minussy) {
                first = fourCode.gen("MUL", "0", first, "#");
            }
        } else {
            first = term();
        }
        while (lex.getSy() == plussy || lex.getSy() == minussy) {
            op = lex.getSy();
            lex.nextSym();
            second = term();
            if (op == plussy) {
                first = fourCode.gen("ADD", first, second, "#");
            } else {
                first = fourCode.gen("SUB", first, second, "#");
            }
        }
        return first;
    }

    private String term()                 //项处理
    {
        int sy, op;
        String first, second;
        sy = lex.getSy();
        first = factor();
        while (sy == multi || sy == divsy) {
            op = sy;
            lex.nextSym();
            second = factor();
            if (op == multi) {
                first = fourCode.gen("MUL", first, second, "#");
            } else if (op == divsy) {
                first = fourCode.gen("DIV", first, second, "#");
            }
        }
        return first;
    }

    private String factor()           //因子处理
    {
        int i, sy;
        String first = "";
        sy = lex.getSy();
        if (sy == intcon || sy == ident || sy == lparent) {
            if (sy == intcon) {

                first = lex.getStrToken();
                lex.nextSym();
            } else if (sy == ident) {
                i = locate(lex.getStrToken());
                first = lex.getStrToken();
                lex.nextSym();
                if (STable.getRow(i).getType() == proctype) {
                    Errors.error(37); //过程不能参与运算
                }
            } else {
                lex.nextSym();
                first = simpleExpression();
                sy = lex.getSy();
                if (sy == rparent) {
                    lex.nextSym();
                } else
                    Errors.error(16);  //缺右括号
            }
        } else {
            Errors.error(38);  //非法的因子
            lex.nextSym();
        }
        return first;
    }//factor end

    private void whileStatement()         //循环语句处理
    {
        int i;
        String first, second;
        String label1, label2;
        String op, calop;

        label1 = fourCode.gen("LABEL", "", "", "while");
        lex.nextSym();
        label2 = conditionExpression("while");
        if (lex.getSy() == dosy) {
            lex.nextSym();
            statement();
        } else {
            Errors.error(46);//缺少do
        }

        fourCode.gen("JMP", "", "", label1);
        fourCode.gen("LABEL", "", "", label2);

    }

    private void callProc()        //call处理
    {
        int i;

        lex.nextSym();

        if (lex.getSy() == ident) {
            i = locate(lex.getStrToken());
            if (STable.getRow(i).getType() != proctype) {
                Errors.error(56);  //Call中实参必须是分程序
            }
            fourCode.gen("CALL", "", "", lex.getStrToken());
            lex.nextSym();
        } else
            Errors.error(48);  //应该是一个标识符
    }

    private void readProc()        //read处理
    {
        int i;

        lex.nextSym();
        if (lex.getSy() == lparent) {
            lex.nextSym();
        } else
            Errors.error(47);  //缺少左括号
        if (lex.getSy() == ident) {
            i = locate(lex.getStrToken());
            if (STable.getRow(i).getType() != vartype) {
                Errors.error(55);  //read语句中实参必须是变量
            }
            fourCode.gen("READ", "", "", lex.getStrToken());
            lex.nextSym();
        } else
            Errors.error(48);  //应该是一个标识符
        while (lex.getSy() == comma) {
            String temp = lex.nextSym();
            i = locate(temp);
            if (lex.getSy() == ident) {
                if (STable.getRow(i).getType() != vartype) {
                    Errors.error(55);
                }
                fourCode.gen("READ", "", "", temp);
                lex.nextSym();
            } else
                Errors.error(48);  //应该是一个标识符
        }
        if (lex.getSy() == rparent) {
            lex.nextSym();
        } else
            Errors.error(16);  //缺少右括号
    }

    private void writeproc()        //write处理
    {
        String first;
        lex.nextSym();
        if (lex.getSy() == lparent) {
            lex.nextSym();
        } else
            Errors.error(47);  //缺少左括号

        first = simpleExpression();
        fourCode.gen("WRITE", "", "", first);
        while (lex.getSy() == comma) {
            lex.nextSym();
            first = simpleExpression();
            fourCode.gen("WRITE", "", "", first);
            lex.nextSym();
        }
        if (lex.getSy() == rparent) {
            lex.nextSym();
        } else
            Errors.error(16);    //缺少右括号
    }
}
