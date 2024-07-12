package javiergs;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.Type;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class ExampleJavaParser {
	
	public static void main(String[] args) throws IOException {
		
		// java code
		// String filePath = "your/local/path/here/ExampleJSoup.java";
		// InputStream in = new FileInputStream(filePath);
		URL url = new URL(
			"https://raw.githubusercontent.com/CSC3100/Paint-App/main/src/main/java/javiergs/paint/gamma/Officer.java"
		);
		InputStream in = url.openConnection().getInputStream();
		
		// Parse the file
		JavaParser parser = new JavaParser();
		Optional <CompilationUnit> result = parser.parse(in).getResult();
		if (result.isPresent()) {
			CompilationUnit cu = result.get();
			// Extract class declarations
			List<ClassOrInterfaceDeclaration> classDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
			for (ClassOrInterfaceDeclaration classDeclaration : classDeclarations) {
				System.out.println("Class: " + classDeclaration.getName());
				// Extract field declarations within the class
				for (FieldDeclaration field : classDeclaration.getFields()) {
					for (VariableDeclarator variable : field.getVariables()) {
						Type fieldType = variable.getType();
						String fieldName = variable.getNameAsString();
						System.out.println("Field: " + fieldName + " Type: " + fieldType);
					}
				}
				// Extract method declarations within the class
				for (MethodDeclaration method : classDeclaration.getMethods()) {
					System.out.println("Method: " + method.getName());
					// Extract parameters of the method
					for (Parameter parameter : method.getParameters()) {
						Type paramType = parameter.getType();
						String paramName = parameter.getNameAsString();
						System.out.println("Parameter: " + paramName + " Type: " + paramType);
					}
				}
			}
		}
	}
}