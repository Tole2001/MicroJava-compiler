// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class VarPart extends InitPart {

    private VarType VarType;
    private VarL VarL;

    public VarPart (VarType VarType, VarL VarL) {
        this.VarType=VarType;
        if(VarType!=null) VarType.setParent(this);
        this.VarL=VarL;
        if(VarL!=null) VarL.setParent(this);
    }

    public VarType getVarType() {
        return VarType;
    }

    public void setVarType(VarType VarType) {
        this.VarType=VarType;
    }

    public VarL getVarL() {
        return VarL;
    }

    public void setVarL(VarL VarL) {
        this.VarL=VarL;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarType!=null) VarType.accept(visitor);
        if(VarL!=null) VarL.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarType!=null) VarType.traverseTopDown(visitor);
        if(VarL!=null) VarL.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarType!=null) VarType.traverseBottomUp(visitor);
        if(VarL!=null) VarL.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarPart(\n");

        if(VarType!=null)
            buffer.append(VarType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarL!=null)
            buffer.append(VarL.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarPart]");
        return buffer.toString();
    }
}
