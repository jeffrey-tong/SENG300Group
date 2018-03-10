package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.ASTVisitor; 

public class TypeParserV2 {

	public static int defCount = 0; 
	public static int refCount = 0; 
	public static String type; 
	
	public static String fileContents(String fileName, String classPath) throws IOException {
		StringBuilder content = new StringBuilder(); 
		BufferedReader reader = new BufferedReader(new FileReader(classPath + "\\" + fileName));
		String line = null; 
		
		while((line = reader.readLine()) != null) {
			content.append(line); 
		}
		
		reader.close(); 
		return content.toString(); 
	}
		
	public static void main(String[] args) throws IOException {
	//	String sourcePath = args[0]; 
		String[] classPath = {"C:\\Users\\jr281\\Desktop\\SENG300Group\\SENG300Group\\src\\testFiles"};
		File folder = new File(classPath[0]);
		File[] files = folder.listFiles();
		
		for (File file : files) {
			if (file.isFile()) {
				ASTParser parser = ASTParser.newParser(AST.JLS8);
				char[] fileData = fileContents(file.getName(),classPath[0]).toCharArray(); 
				
				Scanner input = new Scanner(System.in);
				System.out.print("Java Type: ");
				type = input.next(); 
				input.close(); 
				
				parser.setResolveBindings(true);
				parser.setBindingsRecovery(true);
				parser.setUnitName(file.getName());
				parser.setKind(ASTParser.K_COMPILATION_UNIT);
				parser.setEnvironment(classPath, null, null, false);
				parser.setSource(fileData);
				
				CompilationUnit cu = (CompilationUnit)parser.createAST(null);
				
				cu.accept(new ASTVisitor() {
					ArrayList<Object> names = new ArrayList<Object>();
					
					//Records number of declarations of given type 
					public boolean visit(VariableDeclarationFragment node) {
						SimpleName name = node.getName(); 
						System.out.println(node.resolveBinding());
						this.names.add(name.getIdentifier());
						System.out.println("Name: " + name.toString());
						defCount++; 
						return false; 
					}
					
					//Records number of references to the given type 
					public boolean visit(SimpleName node) {
						if (this.names.contains(node.getIdentifier())) {
							refCount++; 
						}
						return true;
					}
				});
			}
		}
		
		//Print Output 
		System.out.println("");
		System.out.println(type + "." + " Declarations found: " + defCount + ";");
		System.out.println("references found: " + refCount);
	}

}
