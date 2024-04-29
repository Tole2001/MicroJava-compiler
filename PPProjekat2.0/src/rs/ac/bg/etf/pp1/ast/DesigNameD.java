// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class DesigNameD extends DesigName {

    private NameSpaceName NameSpaceName;
    private String desigName;

    public DesigNameD (NameSpaceName NameSpaceName, String desigName) {
        this.NameSpaceName=NameSpaceName;
        if(NameSpaceName!=null) NameSpaceName.setParent(this);
        this.desigName=desigName;
    }

    public NameSpaceName getNameSpaceName() {
        return NameSpaceName;
    }

    public void setNameSpaceName(NameSpaceName NameSpaceName) {
        this.NameSpaceName=NameSpaceName;
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
        if(NameSpaceName!=null) NameSpaceName.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NameSpaceName!=null) NameSpaceName.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NameSpaceName!=null) NameSpaceName.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesigNameD(\n");

        if(NameSpaceName!=null)
            buffer.append(NameSpaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+desigName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesigNameD]");
        return buffer.toString();
    }
}
