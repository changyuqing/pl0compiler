public class BlockTable {
    private int rowMax = 20;           //最大表长
    private int[] table = new int[rowMax];
    private int tablePtr = 0;

    public BlockTable() {
        for (int i = 0; i < rowMax; i++) {
            table[i] = 0;
        }
    }

    public void enterTable(int last) {
        tablePtr++;
        table[tablePtr] = last;
    }

    public int getRow(int i) {
        return table[i];
    }

    public void setRow(int i, int last) {
        table[i] = last;
    }

    public int getTablePtr() {
        return tablePtr;
    }

    public boolean isFull() {
        return tablePtr == rowMax;
    }
}
