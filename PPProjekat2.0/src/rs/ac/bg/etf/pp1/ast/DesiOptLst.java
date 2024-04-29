// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class DesiOptLst extends DesiOptList {

    private DesiOpt DesiOpt;
    private DesiOptList DesiOptList;

    public DesiOptLst (DesiOpt DesiOpt, DesiOptList DesiOptList) {
        this.DesiOpt=DesiOpt;
        if(DesiOpt!=null) DesiOpt.setParent(this);
        this.DesiOptList=DesiOptList;
        if(DesiOptList!=null) DesiOptList.setParent(this);
    }

    public DesiOpt getDesiOpt() {
        return DesiOpt;
    }

    public void setDesiOpt(DesiOpt DesiOpt) {
        this.DesiOpt=DesiOpt;
    }

    public DesiOptList getDesiOptList() {
        return DesiOptList;
    }

    public void setDesiOptList(DesiOptList DesiOptList) {
        this.DesiOptList=DesiOptList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesiOpt!=null) DesiOpt.accept(visitor);
        if(DesiOptList!=null) DesiOptList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesiOpt!=null) DesiOpt.traverseTopDown(visitor);
        if(DesiOptList!=null) DesiOptList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesiOpt!=null) DesiOpt.traverseBottomUp(visitor);
        if(DesiOptList!=null) DesiOptList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesiOptLst(\n");

        if(DesiOpt!=null)
            buffer.append(DesiOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesiOptList!=null)
            buffer.append(DesiOptList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesiOptLst]");
        return buffer.toString();
    }
}
