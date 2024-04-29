// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class VarLists extends VarList {

    private VarList VarList;
    private VarPrt VarPrt;

    public VarLists (VarList VarList, VarPrt VarPrt) {
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
        this.VarPrt=VarPrt;
        if(VarPrt!=null) VarPrt.setParent(this);
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public VarPrt getVarPrt() {
        return VarPrt;
    }

    public void setVarPrt(VarPrt VarPrt) {
        this.VarPrt=VarPrt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarList!=null) VarList.accept(visitor);
        if(VarPrt!=null) VarPrt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
        if(VarPrt!=null) VarPrt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        if(VarPrt!=null) VarPrt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarLists(\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarPrt!=null)
            buffer.append(VarPrt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarLists]");
        return buffer.toString();
    }
}
