// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class StatementPrint extends Matched {

    private Expr Expr;
    private OptNumPrint OptNumPrint;

    public StatementPrint (Expr Expr, OptNumPrint OptNumPrint) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.OptNumPrint=OptNumPrint;
        if(OptNumPrint!=null) OptNumPrint.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public OptNumPrint getOptNumPrint() {
        return OptNumPrint;
    }

    public void setOptNumPrint(OptNumPrint OptNumPrint) {
        this.OptNumPrint=OptNumPrint;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(OptNumPrint!=null) OptNumPrint.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(OptNumPrint!=null) OptNumPrint.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(OptNumPrint!=null) OptNumPrint.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrint(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptNumPrint!=null)
            buffer.append(OptNumPrint.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrint]");
        return buffer.toString();
    }
}