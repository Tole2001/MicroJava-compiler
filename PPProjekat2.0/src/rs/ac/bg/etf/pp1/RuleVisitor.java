package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor {
	
	int varDeclCount = 0;
	
	
	public void visit(NamespaceDecl vardecl){
		varDeclCount++;
	}

}
