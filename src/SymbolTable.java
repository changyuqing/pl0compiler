
public class SymbolTable {

    private int rowMax = 20;           //最大表长

    //TableRow是符号表中的每一行
    //tablePtr指向符号表中已经填入值最后一项的下一项
    //length表示符号表中填入；了多少行数据，实际上可以用tablePtr来表示
    private SymbolTableRow[] table = new SymbolTableRow[rowMax];          //rowMax行



    private int tablePtr = 0;




    //初始化，全部为0
    public SymbolTable() {
        for (int i = 0; i < rowMax; i++) {
            table[i] = new SymbolTableRow("", 0, 0, 0, 0, 0, 0);
        }
    }
    public void printTable(){
        for (int i = 1; i <= tablePtr; i++) {
            table[i].print();
        }
    }



    public boolean isFull() {
        return tablePtr == rowMax;
    }

    //获取符号表中第i行
    public SymbolTableRow getRow(int i) {
        return table[i];
    }
    public void enterTable(String name,int type,int leave,int link){
        tablePtr++;
        table[tablePtr].setName(name);
        table[tablePtr].setType(type);
        table[tablePtr].setLevel(leave);
        table[tablePtr].setLink(link);
        table[tablePtr].setRefer(0);
        table[tablePtr].setAdr(0);
        table[tablePtr].setIfvar(0);


    }


    /*
     *登录常量进符号表
     * 参数：
     * name：常量名
     * level：所在层次
     * value：值
     * address：相对于所在层次基地址的地址
     */


    /*
     *    登录变量进符号表
     *  参数同上
     *  说明：由于登录符号表操作都是在变量声明或常量声明或过程声明中调用，而PL/0不支持变量声明时赋值，所以不传入参数value
     *
     */



    public int getTablePtr() {
        return tablePtr;
    }



}
