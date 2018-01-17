public class BlockTable {
    private int rowMax = 20;           //最大表长
    private BlockTableRow[] table = new BlockTableRow[rowMax];
    private int tablePtr = 0;

    public BlockTable() {
        for (int i = 0; i < rowMax; i++) {
            table[i] = new BlockTableRow(0, 0, 0, 0);
        }
    }

    public void enterTable(int last, int lastpar, int psize, int vsize) {
        tablePtr++;
        table[tablePtr].setLast(last);
        table[tablePtr].setLastpar(lastpar);
        table[tablePtr].setPsize(psize);
        table[tablePtr].setVsize(vsize);
    }

    public BlockTableRow getRow(int i) {
        return table[i];
    }

    public int getTablePtr() {
        return tablePtr;
    }

    public boolean isFull() {
        return tablePtr == rowMax;
    }


}
