package javiergs.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a JPanel that allows the user to choose a Java file and parse it using JavaParser.
 * The parsed information is displayed in a JTextArea.
 *
 * @author javiergs
 * @version 1.0
 */
public class JavaParserPanel extends JPanel implements ActionListener {
	
	private JTextArea textArea;
	
	public JavaParserPanel() {
		// widgets
		JButton buttonLocal = new JButton("Choose a Java File...");
		textArea = new JTextArea();
		textArea.setBackground(new Color(172, 248, 199));
		JScrollPane scrollPane = new JScrollPane(textArea);
		// layout
		setLayout(new BorderLayout());
		add(buttonLocal, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		// behavior
		buttonLocal.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		textArea.setText("");
		// let me introduce the JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Java Files", "java"));
		fileChooser.setDialogTitle("Choose a Java File");
		int result = fileChooser.showOpenDialog(this);
		try {
			if (result == JFileChooser.APPROVE_OPTION) {
				parseJavaFile(fileChooser.getSelectedFile().getAbsolutePath());
			}
		} catch (Exception ex) {
			textArea.setText("Error: " + ex.getMessage());
		}
	}
	
	private void parseJavaFile(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		JavaParser parser = new JavaParser();
		Optional<CompilationUnit> result = parser.parse(in).getResult();
		if (result.isPresent()) {
			CompilationUnit cu = result.get();
			// Extract class declarations
			List<ClassOrInterfaceDeclaration> classDeclarations = cu.findAll(ClassOrInterfaceDeclaration.class);
			for (ClassOrInterfaceDeclaration classDeclaration : classDeclarations) {
				textArea.append("Class: " + classDeclaration.getName() + "\n");
				textArea.append("Lines of code: " + classDeclaration.getEnd().get().line + "\n");
				// Extract field declarations within the class
				textArea.append("\nFields:\n");
				for (FieldDeclaration field : classDeclaration.getFields()) {
					for (VariableDeclarator variable : field.getVariables()) {
						Type fieldType = variable.getType();
						String fieldName = variable.getNameAsString();
						textArea.append("Field: " + fieldName + " Type: " + fieldType + "\n");
					}
				}
				// Extract method declarations within the class
				textArea.append("\nMethods:\n");
				for (MethodDeclaration method : classDeclaration.getMethods()) {
					textArea.append("Method: " + method.getName() + "\n");
					textArea.append("Lines of code: " + (method.getEnd().get().line - method.getBegin().get().line) + "\n");
					// Extract parameters of the method
					for (Parameter parameter : method.getParameters()) {
						Type paramType = parameter.getType();
						String paramName = parameter.getNameAsString();
						textArea.append("Parameter: " + paramName + " Type: " + paramType + "\n");
					}
				}
			}
		}
	}
	
}