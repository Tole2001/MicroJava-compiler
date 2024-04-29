// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class TwiceIdent extends Type {

    private String a1;
    private String a2;

    public TwiceIdent (String a1, String a2) {
        this.a1=a1;
        this.a2=a2;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1=a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2=a2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TwiceIdent(\n");

        buffer.append(" "+tab+a1);
        buffer.append("\n");

        buffer.append(" "+tab+a2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TwiceIdent]");
        return buffer.toString();
    }
}
