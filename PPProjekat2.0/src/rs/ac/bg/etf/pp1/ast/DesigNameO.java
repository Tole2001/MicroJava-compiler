// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class DesigNameO extends DesigName {

    private String desigName;

    public DesigNameO (String desigName) {
        this.desigName=desigName;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName=desigName;
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
        buffer.append("DesigNameO(\n");

        buffer.append(" "+tab+desigName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigNameO]");
        return buffer.toString();
    }
}
