
import java.util.HashMap;
import java.util.Map;

public class CharTable {
    private HashMap keyWord = new HashMap<String,Integer>();
    private HashMap punctuationMark = new HashMap<String,Integer>();
//    private String[] keyWord = {"begin", "end", "if", "then", "else", "const", "procedure",
//            "var", "do", "while", "read", "write", "to"};
    private int symLength=100;
    private String[] symTable=new String[symLength];

    private int conLength=100;
    private String[] constTable=new String[conLength];
    public  CharTable(){
        keyWord.put("begin",23);
        keyWord.put("end",30);
        keyWord.put("if",24);
        keyWord.put("then",33);
        keyWord.put("else",31);
        keyWord.put("const",19);
        keyWord.put("procedure",21);
        keyWord.put("var",20);
        keyWord.put("do",32);
        keyWord.put("while",25);
        keyWord.put("read",27);
        keyWord.put("write",28);
        keyWord.put("odd",29);
        keyWord.put("call",26);
        punctuationMark.put("+",3);
        punctuationMark.put("-",2);
        punctuationMark.put("*",4);
        punctuationMark.put("/",5);
        punctuationMark.put("(",12);
        punctuationMark.put(")",13);
        punctuationMark.put("=",6);
        punctuationMark.put(",",14);
        punctuationMark.put(":",17);
        punctuationMark.put(";",15);


    }
    public int isKeyWord(String key){
        if(keyWord.containsKey(key)){
            return (Integer) keyWord.get(key);
        }else {
            return 22;//RETURN ident
        }
    }
    public int isPunctuationMark(String mark){
        if(punctuationMark.containsKey(mark)){
            return (Integer) punctuationMark.get(mark);
        }else {
            return 0;// return nul
        }
    }
    public String[] getSymTable(){
        return symTable;
    }
    public String[] getConstTable(){
        return constTable;
    }
}
