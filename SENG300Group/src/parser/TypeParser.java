package parser;

import java.util.Scanner;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.compiler.ASTVisitor; 

public class TypeParser {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Path Name: ");
		String directory = input.next();
		
		System.out.print("Java Type: ");
		String type = input.next(); 
		
		int count = 0; 
		
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(directory.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		
		CompilationUnit cu = (CompilationUnit)parser.createAST(null);
		
		
		//Print Output 
		System.out.println("");
		System.out.println(type + "." + " Declarations found: " + count + ";");
		System.out.println("references found: " + count);
	}

}
