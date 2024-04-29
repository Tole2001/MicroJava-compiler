// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class InitLists extends InitList {

    private InitList InitList;
    private InitPart InitPart;

    public InitLists (InitList InitList, InitPart InitPart) {
        this.InitList=InitList;
        if(InitList!=null) InitList.setParent(this);
        this.InitPart=InitPart;
        if(InitPart!=null) InitPart.setParent(this);
    }

    public InitList getInitList() {
        return InitList;
    }

    public void setInitList(InitList InitList) {
        this.InitList=InitList;
    }

    public InitPart getInitPart() {
        return InitPart;
    }

    public void setInitPart(InitPart InitPart) {
        this.InitPart=InitPart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InitList!=null) InitList.accept(visitor);
        if(InitPart!=null) InitPart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InitList!=null) InitList.traverseTopDown(visitor);
        if(InitPart!=null) InitPart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InitList!=null) InitList.traverseBottomUp(visitor);
        if(InitPart!=null) InitPart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InitLists(\n");

        if(InitList!=null)
            buffer.append(InitList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(InitPart!=null)
            buffer.append(InitPart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InitLists]");
        return buffer.toString();
    }
}
