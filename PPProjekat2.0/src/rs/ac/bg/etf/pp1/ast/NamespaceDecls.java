// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class NamespaceDecls extends NamespaceList {

    private NamespaceList NamespaceList;
    private NamespaceDecl NamespaceDecl;

    public NamespaceDecls (NamespaceList NamespaceList, NamespaceDecl NamespaceDecl) {
        this.NamespaceList=NamespaceList;
        if(NamespaceList!=null) NamespaceList.setParent(this);
        this.NamespaceDecl=NamespaceDecl;
        if(NamespaceDecl!=null) NamespaceDecl.setParent(this);
    }

    public NamespaceList getNamespaceList() {
        return NamespaceList;
    }

    public void setNamespaceList(NamespaceList NamespaceList) {
        this.NamespaceList=NamespaceList;
    }

    public NamespaceDecl getNamespaceDecl() {
        return NamespaceDecl;
    }

    public void setNamespaceDecl(NamespaceDecl NamespaceDecl) {
        this.NamespaceDecl=NamespaceDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NamespaceList!=null) NamespaceList.accept(visitor);
        if(NamespaceDecl!=null) NamespaceDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NamespaceList!=null) NamespaceList.traverseTopDown(visitor);
        if(NamespaceDecl!=null) NamespaceDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NamespaceList!=null) NamespaceList.traverseBottomUp(visitor);
        if(NamespaceDecl!=null) NamespaceDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NamespaceDecls(\n");

        if(NamespaceList!=null)
            buffer.append(NamespaceList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NamespaceDecl!=null)
            buffer.append(NamespaceDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NamespaceDecls]");
        return buffer.toString();
    }
}
