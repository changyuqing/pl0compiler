
public class SymbolTable {

    //TableRow是符号表中的每一行
    //tablePtr指向符号表中已经填入值最后一项
    private int rowMax = 20;           //最大表长
    private SymbolTableRow[] table = new SymbolTableRow[rowMax];          //rowMax行
    private int tablePtr = 0;

    //初始化，全部为0
    public SymbolTable() {
        for (int i = 0; i < rowMax; i++) {
            table[i] = new SymbolTableRow("", 0, 0, 0, 0);
        }
    }

    public void printTable() {
        System.out.println("    name    link    type   refer   level");
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

    /*
     *登录标识符进符号表
     * 参数：
     * name：标识符名
     * type：种类
     * link：同一个分程序中的上一个标识符
     * leave：标识符所属分程序层次
     */
    public void enterTable(String name, int type, int leave, int link) {
        tablePtr++;
        table[tablePtr].setName(name);
        table[tablePtr].setType(type);
        table[tablePtr].setLevel(leave);
        table[tablePtr].setLink(link);
        table[tablePtr].setRefer(0);
    }

    public int getTablePtr() {
        return tablePtr;
    }


}
