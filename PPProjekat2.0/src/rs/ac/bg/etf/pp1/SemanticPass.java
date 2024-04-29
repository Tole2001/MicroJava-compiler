package rs.ac.bg.etf.pp1;

//SREDI ONO MATCHED ZAZ IF URADI NAMESPACEI  FOR AIGHT 
//OVO SE PROVERITI JER SE :: javljaju samo kod namespacea PROVERI KASNIJE I ONE FIELD LIEST ZA NAMESPACE DesignatorN uradi i DesignatorY
//MORAS DA DODAS I ZA FACTORNEWCLASS SVOJ I LINIJA 491 dde je zakomentarisana greska Sve za Designator mora i za DesignatorNamespace
//ZA STATEMENTS MORAS FOR PETLJU KAO I SVE SA DESIGNATORNAMESPACE KAO NAMESPACE I TJT I MORAS ZA Globalne Promenljive METHODE
// LINIJE 503 i jos jedna regulisi komentar za gresku zbog novog designatora
//IMA GRESKA SA BOOL U SEMANTICKOJ IZ NEKOG RAZLOGA


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {
	Obj currentMethod = null;
	//Obj currentClass = null;
	//Obj currentExtendedClass = null;
	String curNameSpace = "";
	Obj currentNamespace = null;
	private boolean fieldDecl = false;
	Struct currentType = null;
	boolean errorDetected = false;
	int formalParamCnt = 0;
	int nVars;
	Collection<Obj> actPartsRequired; 
	ArrayList<Struct> actPartsPassed;
	
	Logger log = Logger.getLogger(getClass());
	private boolean isArray = false;
	private boolean inFor = false;
	private boolean inNameSpace = false;
	//Uradi samo da nmze dvai sta namespace i da nmze namespacu da se trazi nes sto neam
	Map.Entry<String, String[]> myPair; 

	
	private int breakCnt = 0;
	private int continueCnt = 0;
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}
	
	
	public void report_info(String message, SyntaxNode info) {
		//SyntaxNode iinfo = (SyntaxNode.)
		
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
	public boolean passed(){
    	return !errorDetected;
    }
	
	
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
		MyTab.tempHelp = Tab.insert(Obj.Var, "#", Tab.intType);
		//System.out.println("progName");
	}
	
	public void visit(TVoid type) {
		type.struct = Tab.noType;
		currentType = Tab.noType;
	}
	
	
	public void visit(Program prog) {
		nVars = Tab.currentScope.getnVars();
		
		Obj mainMeth = Tab.find("main");
		if(mainMeth != Tab.noObj  
				&&	mainMeth.getKind() == Obj.Meth 
				&&	mainMeth.getType() == Tab.noType
				&&	mainMeth.getLevel() == 0) 
				report_info("postoji ispravan main", prog);
			else
				report_error("ne postoji void main() globalna funkcija", prog);
		Tab.chainLocalSymbols(prog.getProgName().obj);
		Tab.closeScope();
	}
	
	public void visit(OnceIdent type) {
		Obj typeNode = Tab.find(type.getId());
		if(typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getId() + " u tabeli simbola!", null);
			type.struct = Tab.noType;
		} else {
			if(Obj.Type == typeNode.getKind()) {
				currentType = typeNode.getType();
				type.struct = currentType;
				//report_info("tip = " + type.getTypeName(), type);
			}
			else {
				report_error("Greska: Ime " + type.getId() + " ne predstavlja tip!", null);
				type.struct = Tab.noType;
			}
		}
	}
	
	public void visit(TwiceIdent type) {
		String firstIdentifier = type.getA1();
	    String secondIdentifier = type.getA2();

	    Obj firstTypeNode = Tab.find(firstIdentifier);
	    Obj secondTypeNode = Tab.find(secondIdentifier);
	    
	    if (firstTypeNode == Tab.noObj) {
	        report_error("Nije pronadjen tip " + firstIdentifier + " u tabeli simbola!", null);
	        type.struct = Tab.noType;
	    } else if (secondTypeNode == Tab.noObj) {
	        report_error("Nije pronadjen tip " + secondIdentifier + " u tabeli simbola!", null);
	        type.struct = Tab.noType;
	    } else {
	        if (Obj.Type == firstTypeNode.getKind() && Obj.Type == secondTypeNode.getKind()) {
	            report_error("Greska: Ime " + firstIdentifier + " ili " + secondIdentifier + " ne predstavlja tip!", null);
	            type.struct = Tab.noType;
	        } else {
	            type.struct = (Obj.Type == firstTypeNode.getKind()) ? firstTypeNode.getType() : secondTypeNode.getType();
	        }
	    }
		
	}//Proveri kasnije klimav sam sa ovim nesto
	

	public void visit(MethodTypeName methodTypeName) {
		String name;
		if(inNameSpace) {
			name = curNameSpace + "_" + methodTypeName.getMethName();
		}else {
			name = methodTypeName.getMethName();
		}
		currentMethod = Tab.insert(Obj.Meth, name, currentType);
		methodTypeName.obj = currentMethod; 
		Tab.openScope();
		//if(inNameSpace) {
			//Tab.insert(Obj.Var, "this", currentNamespace.getType());
			//formalParamCnt++;
		//}
		//report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
	}
	
	public void visit(MethodDec methodDec) {
		currentMethod.setLevel(formalParamCnt);
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		methodDec.obj = currentMethod;
		currentMethod = null;
		formalParamCnt = 0;
	}
	
	public void visit(FormalPar formalPar) {
		Struct type = formalPar.getType().struct;
		if(isArray) 
			type = new Struct(Struct.Array, type);
		Obj elem = Tab.insert(Obj.Var, formalPar.getFormalParamName(), type);
		formalParamCnt++;
		isArray = false;
	}//sredi nonterminale kasnije
	
	public void visit(NoFieldDecl noFieldDecl) {
		fieldDecl = false;
	}
	
	public void visit(ConstType constType) {
		currentType = constType.getType().struct;
		if(currentType != Tab.intType && currentType != Tab.charType && currentType != MyTab.boolType)
			report_error("Greska: const mora biti  tipa int|char|bool", constType);
	}
	
	public void visit(ConstPart constDecls) { // zavrsen red
		currentType = null;
	}//provera neterminala
	
	public void visit(NumConst numConst) {
		numConst.struct = Tab.intType;
	}
	
	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}
	
	public void visit(BoolConst boolConst) {
		boolConst.struct = MyTab.boolType; 
	}
	
	public void visit(ConstDeclaration constDeclar ) {
		if(dozvoljenoDefinisanje(constDeclar.getConstName(), constDeclar))
			if(constDeclar.getConstVal().struct == currentType) {
				String name;
				if(inNameSpace) {
					name = curNameSpace + "_" +  constDeclar.getConstName();
				}else {
					name = constDeclar.getConstName(); 
				}
				constDeclar.obj = Tab.insert(Obj.Con, name, currentType);
				
			}
			
			else 
				report_error("Greska: losi tipovi definisanja konstante", constDeclar);
	}
	
	private boolean dozvoljenoDefinisanje(String name,  SyntaxNode info) {
		if(Tab.currentScope.findSymbol(name) == null) 
			return true;
		
		report_error("GRESKA u opsegu vec postoji simbol sa imenom " + name, info);
		return false;
	
	}
	
	public void visit(VarType varType) {
		currentType = varType.getType().struct;
	}
	
	public void visit(VarDeclaration varDeclar) {
		if(dozvoljenoDefinisanje(varDeclar.getVarName(), varDeclar)) {
			//ubaci promenljivu u tabelu
			int kind;
			String name;
			if(inNameSpace && currentMethod == null) {
				
				name = curNameSpace +"_" + varDeclar.getVarName();
			}
				
				//kind = Obj.Var;
			else
				name =  varDeclar.getVarName();
				
			kind = Obj.Var;
			if(isArray) {
				varDeclar.obj = Tab.insert(kind, name, new Struct(Struct.Array, currentType));
				isArray = false;
			}
			else
				varDeclar.obj = Tab.insert(kind, name, currentType);
			
		}
	}
	
	public void visit(Array array) {
		isArray  = true;
	}
	
	
	private boolean checkDesigType(Designator designator) {
		int localKind = designator.obj.getKind();
		if(localKind == Obj.Var || localKind == Obj.Elem || localKind == Obj.Fld) 
			return true;
		return false;
	}
	
	
	private boolean kompatibilniTipovi(Struct tempL, Struct tempR) {
		if(tempL == Tab.noType && (tempR.getKind() == Struct.Class || tempR.getKind() == Struct.Array))
			return true;
		
		if(tempL.getKind() == Struct.Array && tempR.getKind() == Struct.Array) {
			tempL = tempL.getElemType();
			tempR = tempR.getElemType();
		}
		
		
		if(tempL != tempR) {
			while(tempR.getKind() == Struct.Class) {
				tempR = tempR.getElemType();
				if(tempR == tempL)
					return true;
			}
			
			return false;
		}
		return true;
	}
	
	
	public void visit(DesigAssign desigAssign) {
		checkDesigType(desigAssign.getDesignator()); // dodaj if
		
		Struct tempL = desigAssign.getDesignator().obj.getType();
		Struct tempR = desigAssign.getExpr().struct;
		if(!desigAssign.getExpr().struct.assignableTo(desigAssign.getDesignator().obj.getType()))
			report_error("Greska: losi tipovi dodele", desigAssign);
		//if(!kompatibilniTipovi(tempL, tempR))
			//report_error("Greska: losi tipovi dodele", desigAssign);
		
				
	}
	
	public void visit(DesigNameO desigName) {
		//if(inNameSpace) {
		//	String name = curNameSpace + "_" + desigName.getDesigName();
		//	desigName.obj = Tab.find(name);
		//}else {
		//	desigName.obj = Tab.find(desigName.getDesigName());
		//}
		String fnd = curNameSpace + "_" + desigName.getDesigName();
		Obj a = Tab.find(fnd);
		if(inNameSpace && a != Tab.noObj) {
			String name = curNameSpace + "_" + desigName.getDesigName();
			desigName.obj = Tab.find(name);
		}else {
			desigName.obj = Tab.find(desigName.getDesigName());
		}
		
		

		
	}
	
	public void visit(DesigNameD desigName) {
		String name =desigName.getNameSpaceName().getNamespaceName()+ "_" +desigName.getDesigName();
		desigName.obj = Tab.find(name);

		
	}
	
	
	private Obj getFirstLeft(DesigAddit desigAddit) {
		if(desigAddit.getDesigAdditional() instanceof NoDesigAddit) {
			SyntaxNode parent = desigAddit.getParent();
			while(parent instanceof DesigAddit)
				parent = parent.getParent();
			return ((Designator)parent).getDesigName().obj;
		}
		else
			return desigAddit.getDesigAdditional().obj;

	}
	
	
	/*public void visit(DotIdent desigId) {
		Obj firstLeft = getFirstLeft((DesigAddit)desigId.getParent());
		
		if(firstLeft == Tab.noObj)	// vec je bilo greske
			desigId.obj = Tab.noObj;
		else {
			if(firstLeft.getType().getKind() != Struct.Class) {
				report_error("Greska: designator " + firstLeft.getName() + " nije objekat ", desigId);
			}
			else {	// jeste objekat		
				if(currentClass != null && firstLeft.getType() == currentClass.getType()) { // 
					Obj temp = Tab.currentScope().getOuter().getLocals().searchKey(desigId.getPartName());
					if(temp == null ) {
						desigId.obj = dodajExtMethodAkoPostoji(desigId.getPartName());
						if(desigId.obj == Tab.noObj)
							report_error("Greska: ne postoji polje/metod " + desigId.getPartName(), desigId);
					}
					else 
						desigId.obj = temp;
				}
				else {				
					desigId.obj = Tab.noObj;
					firstLeft.getType().getMembers().forEach(e->{
						if(e.getName().equals(desigId.getPartName())) {
							desigId.obj = e;
							//((DesigAddit)desigId.getParent()).obj = e;
							report_info("pristup polju/metodi " + desigId.getPartName() + " ", desigId);
							return;
						}
					});
					if(desigId.obj == Tab.noObj)
						report_error("Greska: Ne postoji metod/polje ", desigId);
				}
			}
		}
	} OVO PROVERI KASNIJE ALI OVO JE KAD OCES POLJU KLASE DA PRISTUPIS*/
	//PODSETNIK GORE ZA DESIGNATORY
	public void visit(Designator desig) {
		Obj temp = desig.getDesigName().obj;

		if(desig.getDesigAdditional() instanceof NoDesigAddit) {
			desig.obj = temp;
			if(desig.obj.getKind() == Obj.Con)
				report_info("Pristup konstanti " + desig.obj.getName(), desig);
			else if(desig.obj.getKind() == Obj.Var)
				report_info("Pristup promenljivoj " + desig.obj.getName(), desig);
			return;
		}
		
		
		if(desig.getDesigAdditional() instanceof NoDesigAddit)
			desig.obj = desig.getDesigName().obj;
		else 
			desig.obj = desig.getDesigAdditional().obj;
	}
	
	public void visit(DesigAddit desigAddit) {
		desigAddit.obj = desigAddit.getDesigParts().obj;
	}
	
	
	public void visit(DesigArr desigArr) {
		Obj firstLeft = getFirstLeft((DesigAddit)desigArr.getParent());
		
		if(firstLeft == Tab.noObj) 
			desigArr.obj = Tab.noObj;
		else {
			if(desigArr.getExpr().struct != Tab.intType)
				report_error("Greska: u [] mora biti int", desigArr);
			Struct struk;
			
			if(firstLeft.getType().getKind() == Struct.Array) 
				desigArr.obj = new Obj(Obj.Elem, "elem", firstLeft.getType().getElemType());
			else {
				report_error("Greska: " + firstLeft.getName() + " nije niz ", desigArr);
				desigArr.obj = Tab.noObj;
			}
		}
					
	}
	
	
	public void visit(DesigPlusPlus desigPlusPlus) {
		if(!checkDesigType(desigPlusPlus.getDesignator()) || desigPlusPlus.getDesignator().obj.getType() != Tab.intType) 
			report_error("Greska: plus plus nije var int", desigPlusPlus);
	}
	
	public void visit(DesigMinusMinus desigMinusMinus) {
		if(!checkDesigType(desigMinusMinus.getDesignator()) || desigMinusMinus.getDesignator().obj.getType() != Tab.intType) 
			report_error("Greska: minus minus nije var int", desigMinusMinus);
	}
	
	public void visit(StatementRead statementRead) {
		Designator d = statementRead.getDesignator();
		if(checkDesigType(d)) 
			if(d.obj.getType() == MyTab.intType || d.obj.getType() == MyTab.charType || d.obj.getType() == MyTab.boolType) {
				report_info("read()", statementRead);
				return;
			} 
		report_error("GRESKA read nema dobre parametre", statementRead);
	}	
	
	public void visit(StatementPrint statementPrint) {
		Struct kind = statementPrint.getExpr().struct;
		if(kind != Tab.intType && kind != Tab.charType && kind != MyTab.boolType)
			report_error("Greska: print mora imati int/char/bool", statementPrint);
	}
	
	

	
//REALIZUJ I DESIGNATOR Y ZBOG NAMESPACEA	
private boolean checkParams(Designator desig) {
		
		if(actPartsPassed.size() == actPartsRequired.size()) {
			int i = 0;
			for(Obj req: actPartsRequired) {
				if(req.getType() != actPartsPassed.get(i))
					if( !(req.getType().getKind() == Struct.Array && actPartsPassed.get(i).getKind() == Struct.Array)) {
						i = -1; 
						break;
					}
				i++;					
			}
			if(i != -1)
				return true;
		}
		if(actPartsPassed.size() + 1 == actPartsRequired.size()) {
			int i = 0; boolean prvi = true;
			for(Obj req: actPartsRequired) {
				if(prvi) {
					prvi = false;
					continue;
				}
				if(req.getType() != actPartsPassed.get(i))
					if( !(req.getType().getKind() == Struct.Array && actPartsPassed.get(i).getKind() == Struct.Array)) {
						i = -1; 
						break;
					}
				i++;					
			}
			if(i != -1)
				return true;
		}
		
		return false;

	}
//MorasUraditi i za DeigantorY red 440
public void visit(DesigMethod desigMethod) {
	//Obj localObj = Tab.find(desigMethod.getDesignator().obj.getName());
	
	if(desigMethod.getDesignator().obj.getKind() != Obj.Meth) {
		report_error("ne postoji metoda " + desigMethod.getDesignator().obj.getName(), desigMethod);			
	}
	else {
		actPartsRequired = desigMethod.getDesignator().obj.getLocalSymbols();
		if(!checkParams(desigMethod.getDesignator()))
			report_error("Greska: losi parametri u pozivu metode " + desigMethod.getDesignator().obj.getName(), desigMethod);
		else {
			if(desigMethod.getDesignator().obj.getType().getKind() == Struct.Class || 
					desigMethod.getDesignator().obj.getType().getKind() == Struct.Array)
				report_info("Poziv klasne metode " + desigMethod.getDesignator().obj.getName(), desigMethod);
			else
				report_info("Poziv funkcije " + desigMethod.getDesignator().obj.getName(), desigMethod);
		}	
	}
	actPartsPassed = null;
	actPartsRequired = null;
}


public void visit(FactorConst factorConst) {
	factorConst.struct = factorConst.getConstVal().struct;
}


/*public void visit(FactorNewClass factorNewClass) {
	if (factorNewClass.getType().struct.getKind() != Struct.Class) {
		report_error("nije klasa", factorNewClass);
	}
	else
		report_info("Nov objekat tipa " + factorNewClass.getType().getTypeName() , factorNewClass);
	factorNewClass.struct = factorNewClass.getType().struct;
} KLASA*/

//MORAS DA DODAS I ZA FACTORNEWCLASS SVOJ
public void visit(FactorNewArray factorNewArray) {
	Struct s = new Struct(Struct.Array, factorNewArray.getType().struct);
	factorNewArray.struct = s;
	
	if(factorNewArray.getExpr().struct != Tab.intType)
		report_error("Greska: u [] mora stajati int ", factorNewArray);
	
	//report_info("niz tipa ", factorNewArray);
}


public void visit(FactorExpr factorExpr) {
	factorExpr.struct = factorExpr.getExpr().struct;
}

public void visit(FactorDes factorDes) {
	factorDes.struct = factorDes.getDesignator().obj.getType();
	//report_info("Tip je " + factorDes.getDesignator().obj.getKind(),factorDes);
	if(factorDes.getOptActPartsOpt() instanceof NoOptActParts)
		return;
	if(factorDes.getDesignator().obj.getKind() != Obj.Meth) {
		if(factorDes.getDesignator().getDesigName() instanceof DesigNameO) {
			DesigNameO d =(DesigNameO) factorDes.getDesignator().getDesigName();
			report_error("Greska: " + d.getDesigName() + " nije funkcija/metoda", factorDes);
		}else {
			DesigNameD d =(DesigNameD) factorDes.getDesignator().getDesigName();
			String name = d.getNameSpaceName().getNamespaceName() + "_" + d.getDesigName();
			report_error("Greska: " + name + " nije funkcija/metoda", factorDes);
		}
			
		return;
	}

	
	actPartsRequired = factorDes.getDesignator().obj.getLocalSymbols();
	
	if(!checkParams(factorDes.getDesignator()))
		//report_error("Greska: losi parametri pri pozivu methode " + factorDes.getDesignator().getDesigName().getDesigName(), factorDes);
	
	factorDes.struct = factorDes.getDesignator().obj.getType();
	
	actPartsRequired = null;
	actPartsPassed = null;
}

public void visit(OActPO oa) {
	
}

public void visit(NoActParts na) {
	actPartsPassed = new ArrayList<Struct>();
}

public void visit(ActPartsC actPart) {
	if(actPartsPassed == null)
		actPartsPassed = new ArrayList<Struct>();
	actPartsPassed.add(actPart.getExpr().struct);
}

public void visit(ActPartsE actPart) {
	if(actPartsPassed == null)
		actPartsPassed = new ArrayList<Struct>();
	actPartsPassed.add(actPart.getExpr().struct);
}


public void visit(CondCond condCond) {
	condCond.struct = condCond.getCondition().struct;
//
	if (condCond.struct == MyTab.boolType) {
		report_info("Dobar uslov", condCond);
	}
	else
		report_error("GRESKA los uslov", condCond);
}


public void visit(ConditionC cond) {
	if(cond.getCondition().struct == MyTab.boolType && cond.getCondTerm().struct == MyTab.boolType)
		cond.struct = MyTab.boolType;
	else
		report_error("LOS TIP USLOVA", cond);
}


public void visit(ConditionT cond) {
	cond.struct = cond.getCondTerm().struct;
}


public void visit(CondTermC cond) {
	if(cond.getCondFact().struct == MyTab.boolType && cond.getCondTerm().struct == MyTab.boolType)
		cond.struct = MyTab.boolType;
}

public void visit(CondTermT cond) {
	cond.struct = cond.getCondFact().struct;
}


public void visit(CondFactE cond) {
	 cond.struct = cond.getExpr().struct;
	 if(cond.struct != MyTab.boolType)
		 report_error("Greska: nije bool", cond);
}

public void visit(CondFactR cond) {
	 if(kompatibilniTipovi(cond.getExpr().struct, cond.getExpr().struct)) //  DA LI SU KOMPATIBILNI I DA LI JE NIZ ILI CLASSS
		if((cond.getExpr().struct.getKind() == Struct.Array || cond.getExpr().struct.getKind() == Struct.Class)
		&& !(cond.getRelop() instanceof EqualsTo || cond.getRelop() instanceof Differnt)) {
			report_error("Greska: klasu i niz mogu samo da poredim po jednakosti ",cond);
			cond.struct = Tab.noType;

		}
		else
		 cond.struct = MyTab.boolType;
	 else {
		 report_error("Greska: nisu kompatibilni tipovi", cond);
		 cond.struct = Tab.noType;
	 }
}

//public void visit(ExprOpt expr) {
//	expr.struct = expr.getExprBody().struct;
//}

public void visit(ExprNonT expr) {
	expr.struct = expr.getTerm().struct;
	if(expr.getOptMinus() instanceof OptMin)
		if(expr.struct != Tab.intType) {
			report_error("Greska: expr mora biti tipa int", expr);
			expr.struct = Tab.noType;
		}
	if(!(expr.getAddTerm() instanceof NoAddTerm) && expr.getTerm().struct != Tab.intType) {
		report_error("Greska: sabiranje nije tipa int", expr.getParent());
	}
}


public void visit(TermM term) {
	if(term.getTerm().struct != Tab.intType || term.getFactor().struct != Tab.intType)
		report_error("Greska: mnozenje nije tipa int", term);
	term.struct = term.getTerm().struct;
}

public void visit(TermF term) {
	term.struct = term.getFactor().struct;
}

public void visit(AddTermA addTerm) {
	addTerm.struct = addTerm.getTerm().struct;
	if((addTerm.getAddTerm() instanceof AddTermA && addTerm.getAddTerm().struct != Tab.intType) || addTerm.getTerm().struct != Tab.intType) {
		report_error("Greska: sabiranje nije tipa int", addTerm.getParent());
		addTerm.struct = Tab.noType;
	}
}


public void visit(StatementBreak stBreak) {
	if (breakCnt > 0) 
		report_info("ispravan break", stBreak);
	else 
		report_error("los break", stBreak);
}


public void visit(StatementContinue stContinue) {
	if(continueCnt > 0)
		report_info("ispravan continue", stContinue);
	else 
		report_error("los continnue", stContinue.getParent());
}

public void visit(StatementReturn stmReturn) {
	if(stmReturn.getOptExpr() instanceof NoExpr && currentMethod.getType() != Tab.noType)
		report_error("Greska: metoda nije void potreba je povratna vrednost ", stmReturn);
	if(stmReturn.getOptExpr() instanceof ExprO && !kompatibilniTipovi(((ExprO)stmReturn.getOptExpr()).getExpr().struct, currentMethod.getType()))
		report_error("Greska: povratna vrednost metode i return-a nisu iste ", stmReturn);
}


public void visit(NameSpaceName namespaceName) {
	/*if(namespaceName.getParent() instanceof NamespaceDecl) {
		report_info("Roditelj je namespace Decl", namespaceName.getParent());
		currentNamespace = Tab.insert(Obj.Type, namespaceName.getNamespaceName(), new Struct(Struct.Class));
		//currentNamespace = Tab.insert(Obj.Type, namespaceName.getNamespaceName(), Tab.noType);
		namespaceName.obj = currentNamespace;
		Tab.openScope();
		//Tab.insert(Obj.Fld, "TVF", namespaceName.obj.getType());
		inNameSpace = true;
	}*/
	//currentNamespace = Tab.insert(Obj.Type, namespaceName.getNamespaceName(), new Struct(Struct.Class));
	//namespaceName.obj = currentNamespace;
	//Tab.openScope();

	//inNameSpace = true;
	if(namespaceName.getParent() instanceof NamespaceDecl) {
		inNameSpace = true;
		curNameSpace = namespaceName.getNamespaceName();
	}
	
	
}

public void visit(NamespaceDecl namespaceDecl) {
	//Struct parent =(namespaceDecl.getNameSpaceBegin() instanceof NameSpaceBegin)? Tab.noType: Tab.noType;
	//currentNamespace.getType().setElementType(parent);
	/*currentNamespace.getType().setMembers(Tab.currentScope().getLocals());
	Tab.chainLocalSymbols(currentNamespace.getType());
	Tab.closeScope();
	currentNamespace = null;
	inNameSpace = false;*/
	inNameSpace = false;
	curNameSpace = "";
}

public void visit(ForL doWhile) {
	inFor = true;
	breakCnt++;
	continueCnt++;
}

public void visit(StatementForUnmatched unmatchedFor) {
	breakCnt--;
	continueCnt--;
	inFor = false;
}

public void visit(StatementForMatched matchedFor) {
	breakCnt--;
	continueCnt--;
	inFor = false;
}

public void visit(ExprMinus exprMinus) {
	if(exprMinus.getTerm().struct != Tab.intType) {
	    // ! Specification constraint: Expr has to be the int type
		report_error("Tip negiranog izraza treba da bude int", exprMinus);        	
		exprMinus.struct = Tab.noType;
		return;
	}
	// send cumulative type (intType) to the Expr
	exprMinus.struct = Tab.intType;    	
}

public void visit(ExprNoMinus exprAdd) {
	// send type (TermType) to the upper nodes in tree
	exprAdd.struct = exprAdd.getTerm().struct;
}


public void visit(ExprAdd exprAdd) {
	if(!exprAdd.getExpr().struct.compatibleWith(exprAdd.getTerm().struct)) {
	    // ! Specification constraint: Expr and Term types have to be compatible
		report_error("Tip svih sabiraka moraju biti kompatibilni (" + exprAdd.getExpr().struct + "," + exprAdd.getTerm().struct + ")", exprAdd);        	
		exprAdd.struct = Tab.noType;
		return;
	}
	if(exprAdd.getExpr().struct != Tab.intType || exprAdd.getTerm().struct != Tab.intType) {
	    // ! Specification constraint: Expr and Term have to be the int type
		report_error("Tip svih sabiraka treba da bude int", exprAdd);        	
		exprAdd.struct = Tab.noType;
		return;
	}
	// send cumulative type (intType) to the Expr
	exprAdd.struct = Tab.intType;
}

	
	
}
