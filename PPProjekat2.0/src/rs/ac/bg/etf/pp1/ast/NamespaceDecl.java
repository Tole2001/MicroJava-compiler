// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class NamespaceDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private NameSpaceName NameSpaceName;
    private NameSpaceBegin NameSpaceBegin;
    private VarList VarList;
    private MethodDeclList MethodDeclList;

    public NamespaceDecl (NameSpaceName NameSpaceName, NameSpaceBegin NameSpaceBegin, VarList VarList, MethodDeclList MethodDeclList) {
        this.NameSpaceName=NameSpaceName;
        if(NameSpaceName!=null) NameSpaceName.setParent(this);
        this.NameSpaceBegin=NameSpaceBegin;
        if(NameSpaceBegin!=null) NameSpaceBegin.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
        this.MethodDeclList=MethodDeclList;
        if(MethodDeclList!=null) MethodDeclList.setParent(this);
    }

    public NameSpaceName getNameSpaceName() {
        return NameSpaceName;
    }

    public void setNameSpaceName(NameSpaceName NameSpaceName) {
        this.NameSpaceName=NameSpaceName;
    }

    public NameSpaceBegin getNameSpaceBegin() {
        return NameSpaceBegin;
    }

    public void setNameSpaceBegin(NameSpaceBegin NameSpaceBegin) {
        this.NameSpaceBegin=NameSpaceBegin;
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public MethodDeclList getMethodDeclList() {
        return MethodDeclList;
    }

    public void setMethodDeclList(MethodDeclList MethodDeclList) {
        this.MethodDeclList=MethodDeclList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NameSpaceName!=null) NameSpaceName.accept(visitor);
        if(NameSpaceBegin!=null) NameSpaceBegin.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
        if(MethodDeclList!=null) MethodDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NameSpaceName!=null) NameSpaceName.traverseTopDown(visitor);
        if(NameSpaceBegin!=null) NameSpaceBegin.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NameSpaceName!=null) NameSpaceName.traverseBottomUp(visitor);
        if(NameSpaceBegin!=null) NameSpaceBegin.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        if(MethodDeclList!=null) MethodDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NamespaceDecl(\n");

        if(NameSpaceName!=null)
            buffer.append(NameSpaceName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(NameSpaceBegin!=null)
            buffer.append(NameSpaceBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclList!=null)
            buffer.append(MethodDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NamespaceDecl]");
        return buffer.toString();
    }
}
