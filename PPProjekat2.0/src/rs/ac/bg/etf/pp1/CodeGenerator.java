package rs.ac.bg.etf.pp1;
//REGULISE SE FOR PROVERI NAMESPACE I TAKO TE PRICE AND JUST GO WILD CUH
//AKO PRORADI MYTAB JE ZA BOOL TO URADI NAKON TESTA 301
/*ExprOpt(
        ExprNonT(
          OptMin(
          ) [OptMin]
          TermF(
            FactorDes(
              Designator(
                DesigNameO(
                 bodovi
                ) [DesigNameO]
                NoDesigAddit(
                ) [NoDesigAddit]
              ) [Designator]
              NoOptActParts(
              ) [NoOptActParts]
            ) [FactorDes]
          ) [TermF]
          AddTermA(
            NoAddTerm(
            ) [NoAddTerm]
            PlusOp(
            ) [PlusOp]
            TermF(
              FactorConst(
                NumConst(
                 7
                ) [NumConst]
              ) [FactorConst]
            ) [TermF]
          ) [AddTermA]
        ) [ExprNonT]
      ) [ExprOpt]
    ) [DesigAssign]
  ) [StatementD]
) [MatchedStm]
) [StatementsNew]*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	
	private Stack<Integer> ifStack = new Stack<Integer>();
	private Stack<Integer> elseStack = new Stack<Integer>();
	private Stack<Integer> forStack = new Stack<Integer>();
	private Stack<Integer> updateForStack = new Stack<Integer>();
	private Stack<Integer> ternaryStack = new Stack<Integer>();
	private Stack<Integer> conditionFor = new Stack<Integer>();
	private Stack<Integer> updateFor = new Stack<Integer>();
	
	private Stack<List<Integer>> breakStack = new Stack<List<Integer>>();
	private Stack<List<Integer>> continueStack = new Stack<List<Integer>>();
	
	private List<Integer> andList = new ArrayList<Integer>();
	private List<Integer> orList = new ArrayList<Integer>();
	private int inForC = 0;
	private int updateForA = 0;
	private int FirstFor = 0;
	
	private static final int inNowhere = 0;
	private static final int inIfCond = 1;
	private static final int inForBlock = 2;
	private static final int inNameSpace = 3;
	
	
	private Stack<Integer> where = new Stack<Integer>();
	{ where.push(inNowhere);}
	
	private int initCnt = 0;
	private HashMap<Struct, Integer> TVF = new HashMap<Struct, Integer>();
	private HashMap<Struct, ArrayList<Integer>> classesAdrToBeFixed = new HashMap<Struct, ArrayList<Integer>>();
	
	private boolean globalMethDeclar = false;
	
	
	public int getMainPc(){
		return mainPc;
	}
	
	
	public void visit(ForL fr) {
		inForC = 99;
		where.push(inForBlock);
		breakStack.push(new ArrayList<Integer>());
		continueStack.push(new ArrayList<Integer>());
	}
	
	public void visit(ForLoopTwos FRT) {
		inForC = 45;
	    updateForA = 1;
		
		
		conditionFor.push(Code.pc);
	}
	
	public void visit(ForLoopOnes FRO) {
		if(inForC == 45) {
			updateForStack.push(Code.pc);
		}
		if(updateForA == 0) {
			Code.putJump(conditionFor.pop());
		}
	}
	
	public void visit(StatementForMatched stmtF) {
		where.pop();
	}
	
public void visit(StatementForUnmatched stmtF) {
		where.pop();
	}

	
	public void visit(VarDeclaration var) {
		SyntaxNode synt = var.getParent();
		//ovde ces morati da proveris ovo sve sta jos moze da bude roditelj VarDecl synt instanceof VarList || 
		while(synt instanceof VarListComma || synt instanceof VarL) {
			synt = synt.getParent();
		}
		if(synt instanceof VarPart) {
			initCnt++;
		}
	}
	//ova dva vrv ne moras to su klase
	private void fixTVF(ArrayList<Integer> ar) {
		if(ar != null)
			ar.forEach(e->{
				Code.put2(e, Code.dataSize);
			});
	}
	
	private void generateTVF() {
		
	}
	
	public void visit(MethodTypeName methName) {
		if("main".equalsIgnoreCase(methName.getMethName())) {
			mainPc = Code.pc;
			generateTVF();
		}
		
		methName.obj.setAdr(Code.pc);
		
		int formparCount = methName.obj.getLevel();
		int lclCnt = methName.obj.getLocalSymbols().size();
		
		Code.put(Code.enter);
		Code.put(formparCount);
		Code.put(lclCnt);	
	}
	
	public void visit(MethodDec method) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	
	private int ConstVL(SyntaxNode type) {
		if( type instanceof NumConst)
			return ((NumConst)type).getN1();
		if(type instanceof CharConst)
			return ((CharConst)type).getC1().charAt(1);
		if(type instanceof BoolConst)
			if(((BoolConst)type).getB1().equals("true"))
				return 1;
			else
				return 0;
		return -1;
	}
	
	public void visit(FactorConst cnst) { 
		Obj con = Tab.insert(Obj.Con, "$", cnst.struct);
		con.setAdr(ConstVL(cnst.getConstVal()));
		Code.load(con);
	}
	
	public void visit(ConstDeclaration cnst) {
		cnst.obj.setAdr(ConstVL(cnst.getConstVal()));
		Code.load(cnst.obj);
	}
	
	
	public void visit(DesigAssign desig) {	
		if(desig.getDesignator().obj.getKind() == Obj.Elem)
			if(desig.getDesignator().obj.getType() == Tab.charType)
				Code.put(Code.bastore);
			else
				Code.put(Code.astore);
		else
			Code.store(desig.getDesignator().obj);
	}//AKumulator se smesta na potrebnu lokaciju
	
	
	
	/*private void checkDesigParent(Designator designator) {
		SyntaxNode parent = designator.getParent();
		//if(designator.getDesigAdditional() instanceof DesigAddit)
			//Code.load(designator.getDesigName().obj);
		//else
		// ako je poziv metode ili dodela vrednosti ne treba mi vrednost designatora
		// u ostalim slucajevima mi treba
		if(!(parent instanceof DesigAssign && designator.getDesigAdditional() instanceof NoDesigAddit) // ako je dodela vrednosti nije mi potrebno da dohvatam vrednost, ali ako ima delove, onda jeste
		&& !(parent instanceof DesigMethod) 
		&& !(parent instanceof FactorDes && ((FactorDes)parent).getOptActPartsOpt() instanceof OActPO) // poziv funkcije u izrazu
		&& !(parent instanceof StatementRead))
			Code.load(designator.obj);
	}*/
	
	
	public void visit(GlobalStart gs) {
		globalMethDeclar = true;
	}
	
	public void visit(DesigNameO desigNM) {
		if(!globalMethDeclar) {
			if(desigNM.obj.getKind() == Obj.Fld || desigNM.obj.getKind() == Obj.Meth) {
				Code.put(Code.load_n);
				}
		}
		
		//OVO URADI KASNIJE JER TI JE DESIG NAME DRUGACIJI
		
		
		if(((Designator)desigNM.getParent()).getDesigAdditional() instanceof NoDesigAddit) {
			SyntaxNode parent = desigNM.getParent().getParent();
			if(!(parent instanceof DesigAssign) // ako je dodela vrednosti nije mi potrebno da dohvatam vrednost, ali ako ima delove, onda jeste
				&& !(parent instanceof DesigMethod) 
				&& !(parent instanceof FactorDes && ((FactorDes)parent).getOptActPartsOpt() instanceof OActPO) // poziv funkcije u izrazu
				&& !(parent instanceof StatementRead))
					Code.load(desigNM.obj);
		} else
			Code.load(desigNM.obj);
				
		
				
	}
	
	public void visit(DesigNameD deisgNMD) {
		if(!globalMethDeclar) {
			if(deisgNMD.obj.getKind() == Obj.Fld || deisgNMD.obj.getKind() == Obj.Meth) {
				Code.put(Code.load_n);
				}
		}
		
		if(((Designator)deisgNMD.getParent()).getDesigAdditional() instanceof NoDesigAddit) {
			SyntaxNode parent = deisgNMD.getParent().getParent();
			if(!(parent instanceof DesigAssign) // ako je dodela vrednosti nije mi potrebno da dohvatam vrednost, ali ako ima delove, onda jeste
				&& !(parent instanceof DesigMethod) 
				&& !(parent instanceof FactorDes && ((FactorDes)parent).getOptActPartsOpt() instanceof OActPO) // poziv funkcije u izrazu
				&& !(parent instanceof StatementRead))
					Code.load(deisgNMD.obj);
		} else
			Code.load(deisgNMD.obj);
		
	}
	
	public void visit(DesigId ID) {
		// da li je poslednji?
				if(ID.getParent().getParent() instanceof Designator) {
					SyntaxNode desig = ID.getParent().getParent();
					SyntaxNode parent = desig.getParent();
					if(!(parent instanceof DesigAssign ) // ako je dodela vrednosti nije mi potrebno da dohvatam vrednost, ali ako ima delove, onda jeste
							&& !(parent instanceof DesigMethod) 
							&& !(parent instanceof FactorDes && ((FactorDes)parent).getOptActPartsOpt() instanceof OActPO) // poziv funkcije u izrazu
							&& !(parent instanceof StatementRead))
								Code.load(ID.obj);
				} else
					Code.load(ID.obj);
	}
	
	public void visit(AddTermA addTerm) {
		SyntaxNode op = addTerm.getAddop();
		if(op instanceof PlusOp)
			Code.put(Code.add);
		if(op instanceof MinusOp)
			Code.put(Code.sub);
	}
	
	
	public void visit(StatementPrint stmPrint) {
		if(stmPrint.getExpr().struct == Tab.intType || stmPrint.getExpr().struct == MyTab.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}
		else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	
	public void visit(StatementRead statementRead) {

		Code.put(Code.read);
		Code.store(statementRead.getDesignator().obj);
	}
	
	public void visit(OptMin neg) {
		//Code.put(Code.neg);
	}
	
	
	public void visit(TermM term) {
		SyntaxNode type = term.getMulop();
		
		if(type instanceof TimesOp)
			Code.put(Code.mul);
		if(type instanceof DivOp)
			Code.put(Code.div);
		if(type instanceof ModOP)
			Code.put(Code.rem);		
	}
	
	
	public void visit(ExprNonT expr) {
		if(expr.getOptMinus() instanceof OptMin)
			Code.put(Code.neg);
	}
	
	
	public void visit(CondFactR cond) {
		SyntaxNode type = cond.getRelop();
		
		if(type instanceof EqualsTo) 
			Code.putFalseJump(Code.eq, 0);
		if(type instanceof Differnt)
			Code.putFalseJump(Code.ne, 0);
		if(type instanceof LessThen)
			Code.putFalseJump(Code.lt, 0);
		if(type instanceof LessThenEqual)
			Code.putFalseJump(Code.le, 0);
		if(type instanceof GreaterThen)
			Code.putFalseJump(Code.gt, 0);
		if(type instanceof GreaterThenEqual)
			Code.putFalseJump(Code.ge, 0);
	}
	
	public void visit(CondFactE cond) {
		Code.put(Code.const_1);
		Code.putFalseJump(Code.eq, 0);
	}
	
	public void visit(If i) {
		where.push(inIfCond);
	}
	
	public void visit(Else els) {
		
		Code.put(Code.jmp);
		Code.put2(0);
		elseStack.push(Code.pc - 2);
		
		Code.fixup(ifStack.pop());		
	}
	
	public void visit(UnmatchedIf unmatchedIf) {
		Code.fixup(ifStack.pop());
		if(where.peek() == inForBlock) {
			Code.putJump(updateFor.pop());
			updateForA = 0;
		}
	}
	
	public void visit(UnmatchedElse unmatchedElse) {
		Code.fixup(elseStack.pop());
	}
	
	public void visit(StatementIf stmIf) {
		Code.fixup(elseStack.pop());
	}
	
	private void condition(int adr) {
		if(where.peek() == inIfCond) {
			Code.put(Code.jmp);
			Code.put2(0);
			
			ifStack.push(Code.pc - 2);
			
			orList.forEach(o->{
				Code.fixup(o); // postavlja then 
			});
		}
		
		orList.clear();
	}
	
	public void visit(CondCond Cond) {
		//if(condTernary.getParent() instanceof StatementDo)
		//	condition(doWhileStack.pop()); OVDE MORA PROVERA DAL JE FOR ITD ITD aka mozda i mora da se napravi samo novi
		condition(0);		
		if(where.peek() == inIfCond)
			where.pop();
	}
	
	
	private void or() {
		Code.put(Code.jmp);
		//if(whereAmI.peek() == inDoWhileBlock)
		//	Code.put2(doWhileStack.peek() -  Code.pc + 1);
		//else {
		//	Code.put2(0); // skakanje na then isto moras proveriti za for kasnije sve
		//	orList.add(Code.pc - 2);
		//}
		
		Code.put2(0); // skakanje na then
		orList.add(Code.pc - 2);
		
		
		andList.forEach(adr->{
			Code.fixup(adr);
		});
		andList.clear();		
	}
	
	public void visit(ConditionT cond) {
		or();
	}
	
	public void visit(ConditionC cond) {
		or();
	}
	
	
	private void and() {
		andList.add(Code.pc - 2);	
	}
	
	public void visit(CondTermC cond) {
		and();
	}
	
	public void visit(CondTermT cond) {
		and();
	}
	
	
	public void visit(StatementBreak stmBreak) {
		Code.putJump(0);
		breakStack.peek().add(Code.pc - 2);
	}
	
	public void visit(StatementContinue stmContinue) {
		Code.putJump(0);
		continueStack.peek().add(Code.pc-2);
	}
	
	
	public void visit(DesigAddit desigAdditional) {
		Obj o;
		if(desigAdditional.getParent() instanceof Designator)
			o = ((Designator)desigAdditional.getParent()).obj;
		else
			o = ((DesigAddit)desigAdditional.getParent()).obj;
		
		o.getLocalSymbols().forEach(e->{
			if(desigAdditional.getDesigParts() instanceof DesigId)
				if(e.getName().equals(((DesigId)desigAdditional.getDesigParts()).getPartName())) {
					Code.load(e);
				}
					
		});
		
	}
	
	
	public void visit(FactorNewArray factorNewArray) {
		Code.put(Code.newarray);
		if(factorNewArray.getType().struct == Tab.charType)
			Code.put(0);
		else
			Code.put(1);
	}
	
	
	public void visit(DesigArr desigArr) {
		// da li je poslednji
		if(desigArr.getParent().getParent() instanceof Designator) {
			SyntaxNode desig = desigArr.getParent().getParent();
			SyntaxNode parent = desig.getParent();
			if(!(parent instanceof DesigAssign ) // ako je dodela vrednosti nije mi potrebno da dohvatam vrednost, ali ako ima delove, onda jeste
					&& !(parent instanceof DesigMethod) 
					&& !(parent instanceof FactorDes && ((FactorDes)parent).getOptActPartsOpt() instanceof OActPO) // poziv funkcije u izrazu
					&& !(parent instanceof StatementRead))
				if(desigArr.obj.getType() == Tab.charType)
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
		} else {
			if(desigArr.obj.getType() == Tab.charType)
				Code.put(Code.baload);
			else
				Code.put(Code.aload);
			if(((DesigAddit)desigArr.getParent()).getParent() instanceof DesigAddit
			&& ((DesigAddit)((DesigAddit)desigArr.getParent()).getParent()).getDesigParts() instanceof DesigId) {
				Code.put(Code.dup);
				Code.store(MyTab.tempHelp);
				
			}
				
		}
		
	}
	
	
	private Obj secondToLast(Designator desig) {
		if(((DesigAddit)desig.getDesigAdditional()).getDesigAdditional() instanceof NoDesigAddit)
			return desig.getDesigName().obj;
		else
			return ((DesigAddit)desig.getDesigAdditional()).getDesigAdditional().obj;
	}
	
	
	private void MethCall(Designator desig) {
		if(desig.getDesigAdditional() instanceof NoDesigAddit) {
			Code.put(Code.call);
			Code.put2(desig.obj.getAdr() - Code.pc + 1);
		} else {
			Obj referenca;
			if(((DesigAddit)desig.getDesigAdditional()).getDesigAdditional() instanceof NoDesigAddit)
				referenca = desig.getDesigName().obj;
			else
				referenca = ((DesigAddit)desig.getDesigAdditional()).getDesigAdditional().obj;
			
			//Code.put(Code.dup);
			Obj prz = secondToLast(desig);
			if(prz.getKind() == Obj.Elem) {
				Code.load(MyTab.tempHelp);
			}
			else
				Code.load(prz);
			
			Code.put(Code.getfield);
			Code.put2(0);
			
			Code.put(Code.invokevirtual);
			String name = desig.obj.getName();
			for(int i = 0; i < name.length(); i++)
				Code.put4(name.charAt(i));
			Code.put4(-1);
		}
	}
	
	public void visit(DesigMethod desigMethod) {
		MethCall(desigMethod.getDesignator());
		
		if(desigMethod.getDesignator().obj.getType() != Tab.noType)
			Code.put(Code.pop);
		
	}
	
	
	public void visit(FactorDes factorDes) {
		if(factorDes.getOptActPartsOpt() instanceof OActPO) { // poziv funkcije
			MethCall(factorDes.getDesignator());
		}
	}
	
	public void visit(DesigPlusPlus plusPlus) {
		if(where.peek() != inForBlock || updateForA != 1) {
			Code.put(Code.const_1);
			Code.put(Code.add);
			Code.store(plusPlus.getDesignator().obj);
		}else {
			updateFor.push(Code.pc);
		}

	}
	
	public void visit(DesigMinusMinus minusMinus) {
		if(where.peek() != inForBlock && updateForA != 1) {
			Code.put(Code.const_1);
			Code.put(Code.sub);
			Code.store(minusMinus.getDesignator().obj);
		}else {
			updateFor.push(Code.pc);
		}
		
	}
	
	public void visit(ExprMinus exprMinus) {
		Code.put(Code.neg); // this will negate value from the top of the exprStack
	}
	
	
	public void visit(ExprAdd exprAdd) {
		// this will use 2 values from the exprStack and 
		if(exprAdd.getAddop() instanceof PlusOp) {
			Code.put(Code.add); // add them
		} else {
			Code.put(Code.sub); // subtract them
		}
		// and put the result back to the exprStack		
	}
}

