public class SymbolTableRow {

    private String name ;
    private int link ;              //同一个分程序中的上一个标识符

    private int type ;              //标志符基本类型
    private int refer ;             //数组、过程函数标识符指向的atab、btab
    private int level ;             //标识符所属分程序层次
    private int ifvar ;             //如果是参数，是否是变量形参
    private int adr ;               //标识符在分程序运行栈的偏移量

    public SymbolTableRow(){

    }
    public SymbolTableRow(String name, int link, int type, int refer, int level, int ifvar, int adr){
        setName(name);
        setLink(link);
        setType(type);
        setRefer(refer);
        setLevel(level);
        setIfvar(ifvar);
        setAdr(adr);

    }
    public void print(){
        System.out.println(name+"   "+link+"   "+type+"   "+refer+"   "+level+"   "+ifvar+"   "+adr);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRefer() {
        return refer;
    }

    public void setRefer(int refer) {
        this.refer = refer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIfvar() {
        return ifvar;
    }

    public void setIfvar(int ifvar) {
        this.ifvar = ifvar;
    }

    public int getAdr() {
        return adr;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }
}
