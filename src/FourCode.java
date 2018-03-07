public class FourCode {
    private String OP;
    private String T1;
    private String T2;
    private String T3;

    public FourCode() {

    }

    public FourCode(String op, String t1, String t2, String t3) {
        OP = op;
        T1 = t1;
        T2 = t2;
        T3 = t3;
    }

    public void print() {
        System.out.printf("%12s%12s%12s%12s\n", OP, T1, T2, T3);
    }

    public String getOP() {
        return OP;
    }

    public void setOP(String OP) {
        this.OP = OP;
    }

    public String getT1() {
        return T1;
    }

    public void setT1(String t1) {
        T1 = t1;
    }

    public String getT2() {
        return T2;
    }

    public void setT2(String t2) {
        T2 = t2;
    }

    public String getT3() {
        return T3;
    }

    public void setT3(String t3) {
        T3 = t3;
    }
}
