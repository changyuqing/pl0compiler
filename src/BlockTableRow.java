public class BlockTableRow {
    private int last;              //当前分程序的最后一个定义的标识符在table中的索引
    private int lastpar;           //当前分程序的最后一个参数在table中的索引
    private int psize;             //参数及内务信息所占运行栈的规模
    private int vsize;             //局部变量、参数及内务信息在运行栈中规模

    public BlockTableRow() {

    }

    public BlockTableRow(int last,int lastpar,int psize,int vsize){
        setLast(last);
        setLastpar(lastpar);
        setPsize(psize);
        setVsize(vsize);
    }
    public int getLast() {
        return last;
    }

    public void setLast(int last) {
        this.last = last;
    }

    public int getLastpar() {
        return lastpar;
    }

    public void setLastpar(int lastpar) {
        this.lastpar = lastpar;
    }



    public int getPsize() {
        return psize;
    }

    public void setPsize(int psize) {
        this.psize = psize;
    }

    public int getVsize() {
        return vsize;
    }

    public void setVsize(int vsize) {
        this.vsize = vsize;
    }
}
