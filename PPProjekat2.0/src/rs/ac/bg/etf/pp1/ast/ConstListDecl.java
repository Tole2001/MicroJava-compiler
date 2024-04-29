// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class ConstListDecl extends ConstL {

    private ConstL ConstL;
    private ConstDeclaration ConstDeclaration;

    public ConstListDecl (ConstL ConstL, ConstDeclaration ConstDeclaration) {
        this.ConstL=ConstL;
        if(ConstL!=null) ConstL.setParent(this);
        this.ConstDeclaration=ConstDeclaration;
        if(ConstDeclaration!=null) ConstDeclaration.setParent(this);
    }

    public ConstL getConstL() {
        return ConstL;
    }

    public void setConstL(ConstL ConstL) {
        this.ConstL=ConstL;
    }

    public ConstDeclaration getConstDeclaration() {
        return ConstDeclaration;
    }

    public void setConstDeclaration(ConstDeclaration ConstDeclaration) {
        this.ConstDeclaration=ConstDeclaration;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstL!=null) ConstL.accept(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstL!=null) ConstL.traverseTopDown(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstL!=null) ConstL.traverseBottomUp(visitor);
        if(ConstDeclaration!=null) ConstDeclaration.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstListDecl(\n");

        if(ConstL!=null)
            buffer.append(ConstL.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclaration!=null)
            buffer.append(ConstDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstListDecl]");
        return buffer.toString();
    }
}
