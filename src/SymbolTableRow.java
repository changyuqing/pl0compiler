public class SymbolTableRow {

    private String name ;
    private int link ;              //同一个分程序中的上一个标识符

    private int type ;              //标志符基本类型
    private int refer ;             //过程标识符指向的btab
    private int level ;             //标识符所属分程序层次

    public SymbolTableRow(){

    }
    public SymbolTableRow(String name, int link, int type, int refer, int level){
        setName(name);
        setLink(link);
        setType(type);
        setRefer(refer);
        setLevel(level);
    }
    public void print(){
        System.out.printf("%8s%8s%8s%8s%8s\n",name,link,type,refer,level);
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

}
