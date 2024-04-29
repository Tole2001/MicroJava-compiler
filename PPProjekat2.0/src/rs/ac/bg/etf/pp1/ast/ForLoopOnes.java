// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class ForLoopOnes extends For_loop_one {

    private DesignatorStatement DesignatorStatement;
    private DesignatorStatementLocalList DesignatorStatementLocalList;

    public ForLoopOnes (DesignatorStatement DesignatorStatement, DesignatorStatementLocalList DesignatorStatementLocalList) {
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
        this.DesignatorStatementLocalList=DesignatorStatementLocalList;
        if(DesignatorStatementLocalList!=null) DesignatorStatementLocalList.setParent(this);
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public DesignatorStatementLocalList getDesignatorStatementLocalList() {
        return DesignatorStatementLocalList;
    }

    public void setDesignatorStatementLocalList(DesignatorStatementLocalList DesignatorStatementLocalList) {
        this.DesignatorStatementLocalList=DesignatorStatementLocalList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
        if(DesignatorStatementLocalList!=null) DesignatorStatementLocalList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
        if(DesignatorStatementLocalList!=null) DesignatorStatementLocalList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        if(DesignatorStatementLocalList!=null) DesignatorStatementLocalList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForLoopOnes(\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatementLocalList!=null)
            buffer.append(DesignatorStatementLocalList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForLoopOnes]");
        return buffer.toString();
    }
}
