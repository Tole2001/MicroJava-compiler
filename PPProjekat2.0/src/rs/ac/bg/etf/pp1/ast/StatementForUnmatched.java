// generated with ast extension for cup
// version 0.8
// 29/11/2023 9:48:59


package rs.ac.bg.etf.pp1.ast;

public class StatementForUnmatched extends Unmatched {

    private ForL ForL;
    private For_loop_one for_loop_one;
    private For_loop_two for_loop_two;
    private For_loop_one for_loop_one1;
    private Unmatched Unmatched;

    public StatementForUnmatched (ForL ForL, For_loop_one for_loop_one, For_loop_two for_loop_two, For_loop_one for_loop_one1, Unmatched Unmatched) {
        this.ForL=ForL;
        if(ForL!=null) ForL.setParent(this);
        this.for_loop_one=for_loop_one;
        if(for_loop_one!=null) for_loop_one.setParent(this);
        this.for_loop_two=for_loop_two;
        if(for_loop_two!=null) for_loop_two.setParent(this);
        this.for_loop_one1=for_loop_one1;
        if(for_loop_one1!=null) for_loop_one1.setParent(this);
        this.Unmatched=Unmatched;
        if(Unmatched!=null) Unmatched.setParent(this);
    }

    public ForL getForL() {
        return ForL;
    }

    public void setForL(ForL ForL) {
        this.ForL=ForL;
    }

    public For_loop_one getFor_loop_one() {
        return for_loop_one;
    }

    public void setFor_loop_one(For_loop_one for_loop_one) {
        this.for_loop_one=for_loop_one;
    }

    public For_loop_two getFor_loop_two() {
        return for_loop_two;
    }

    public void setFor_loop_two(For_loop_two for_loop_two) {
        this.for_loop_two=for_loop_two;
    }

    public For_loop_one getFor_loop_one1() {
        return for_loop_one1;
    }

    public void setFor_loop_one1(For_loop_one for_loop_one1) {
        this.for_loop_one1=for_loop_one1;
    }

    public Unmatched getUnmatched() {
        return Unmatched;
    }

    public void setUnmatched(Unmatched Unmatched) {
        this.Unmatched=Unmatched;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForL!=null) ForL.accept(visitor);
        if(for_loop_one!=null) for_loop_one.accept(visitor);
        if(for_loop_two!=null) for_loop_two.accept(visitor);
        if(for_loop_one1!=null) for_loop_one1.accept(visitor);
        if(Unmatched!=null) Unmatched.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForL!=null) ForL.traverseTopDown(visitor);
        if(for_loop_one!=null) for_loop_one.traverseTopDown(visitor);
        if(for_loop_two!=null) for_loop_two.traverseTopDown(visitor);
        if(for_loop_one1!=null) for_loop_one1.traverseTopDown(visitor);
        if(Unmatched!=null) Unmatched.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForL!=null) ForL.traverseBottomUp(visitor);
        if(for_loop_one!=null) for_loop_one.traverseBottomUp(visitor);
        if(for_loop_two!=null) for_loop_two.traverseBottomUp(visitor);
        if(for_loop_one1!=null) for_loop_one1.traverseBottomUp(visitor);
        if(Unmatched!=null) Unmatched.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementForUnmatched(\n");

        if(ForL!=null)
            buffer.append(ForL.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(for_loop_one!=null)
            buffer.append(for_loop_one.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(for_loop_two!=null)
            buffer.append(for_loop_two.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(for_loop_one1!=null)
            buffer.append(for_loop_one1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Unmatched!=null)
            buffer.append(Unmatched.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementForUnmatched]");
        return buffer.toString();
    }
}