// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class VarListCom extends VarListComma {

    private VarL VarL;

    public VarListCom (VarL VarL) {
        this.VarL=VarL;
        if(VarL!=null) VarL.setParent(this);
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
        if(VarL!=null) VarL.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarL!=null) VarL.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarL!=null) VarL.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarListCom(\n");

        if(VarL!=null)
            buffer.append(VarL.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarListCom]");
        return buffer.toString();
    }
}
