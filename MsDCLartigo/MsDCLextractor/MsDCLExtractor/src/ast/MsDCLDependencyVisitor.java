package ast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import msdcl.dependencies.ClassAnnotationDependency;
import msdcl.dependencies.ClassNormalAnnotationDependency;
import msdcl.dependencies.ClassSingleAnnotationDependency;
import msdcl.dependencies.Dependency;
import msdcl.dependencies.FieldAnnotationDependency;
import msdcl.dependencies.FieldNormalAnnotationDependency;
import msdcl.dependencies.FieldSingleAnnotationDependency;
import msdcl.dependencies.MemberPair;
import msdcl.dependencies.MethodAnnotationDependency;
import msdcl.dependencies.MethodNormalAnnotationDependency;
import msdcl.exception.MsDCLException;

public class MsDCLDependencyVisitor extends ASTVisitor {

	private ArrayList<Dependency> dependencies;
	private Set<TypeDeclaration> declarations;

	private ICompilationUnit unit;
	private CompilationUnit fullClass;
	private String className;
	private ArrayList allDependenciesOfFile;
	public MsDCLDependencyVisitor() {
	}

	public MsDCLDependencyVisitor(String className2, String str) throws MsDCLException {
		try {
			this.dependencies = new ArrayList<Dependency>();
			ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			this.fullClass = (CompilationUnit) parser.createAST(null);
			List types = fullClass.types();
			this.className = className2.substring(0, className2.length() - 5);
			this.declarations = new LinkedHashSet<TypeDeclaration>();
			this.allDependenciesOfFile = new ArrayList<>();
			this.fullClass.accept(this);
			
		} catch (Exception e) {
			e.printStackTrace();  
			throw new MsDCLException(e, unit);
		}
	}

	public boolean visit(MarkerAnnotation node) { // Visita à uma anotação definida em uma classe. No contexto do
													// MsDCL será considerada as anotações @Autowired (Spring)

		if (node.getParent().getNodeType() == ASTNode.FIELD_DECLARATION) {
			FieldDeclaration field = (FieldDeclaration) node.getParent();
			Type type = field.getType();
			String typeDependency = addNameOfTypes(type);

			this.dependencies
					.add(new FieldAnnotationDependency(this.className, node.getTypeName().getFullyQualifiedName(),
							fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength(),
							((VariableDeclarationFragment) field.fragments().get(0)).getName().getIdentifier(),
							typeDependency));

		} else if (node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			MethodDeclaration method = (MethodDeclaration) node.getParent();
			this.dependencies.add(new MethodAnnotationDependency(this.className,
					node.getTypeName().getFullyQualifiedName(), fullClass.getLineNumber(node.getStartPosition()),
					node.getStartPosition(), node.getLength(), method.getName().getIdentifier()));

		} else if (node.getParent().getNodeType() == ASTNode.TYPE_DECLARATION) {

			this.dependencies.add(new ClassAnnotationDependency(this.className,
					node.getTypeName().getFullyQualifiedName(), fullClass.getLineNumber(node.getStartPosition()),
					node.getStartPosition(), node.getLength()));

		}

		this.allDependenciesOfFile.addAll(this.dependencies);
		return true;
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		String value = "";

		List<MemberValuePair> members = node.values();
		// MemberPair memberPairs = null;
		Set<MemberPair> memberPairs = new HashSet<>();
		for (MemberValuePair m : members) {
			if (m.getName().getIdentifier().equals("value")) {
				value = m.getValue().toString();
				value = value.replaceAll("\"", "");
				
			}

		}
		if (node.getParent().getNodeType() == ASTNode.TYPE_DECLARATION) {

			this.dependencies.add(new ClassNormalAnnotationDependency(this.className,
					node.getTypeName().getFullyQualifiedName(), fullClass.getLineNumber(node.getStartPosition()),
					node.getStartPosition(), node.getLength(), value));
		}

		else if (node.getParent().getNodeType() == ASTNode.FIELD_DECLARATION) {
			FieldDeclaration field = (FieldDeclaration) node.getParent();
			Type type = field.getType();
			String typeDependency = addNameOfTypes(type);
			this.dependencies
					.add(new FieldNormalAnnotationDependency(this.className, node.getTypeName().getFullyQualifiedName(),
							fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength(),
							((VariableDeclarationFragment) field.fragments().get(0)).getName().getIdentifier(),
							typeDependency, value));
		} else if (node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			MethodDeclaration method = (MethodDeclaration) node.getParent();
			this.dependencies.add(new MethodNormalAnnotationDependency(this.className,
					node.getTypeName().getFullyQualifiedName(), fullClass.getLineNumber(node.getStartPosition()),
					node.getStartPosition(), node.getLength(), method.getName().getIdentifier(), value));
		}
		this.allDependenciesOfFile.addAll(this.dependencies);

		return true;
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {

		String expression = node.getValue().toString();
		if (node.getParent().getNodeType() == ASTNode.TYPE_DECLARATION) {
			this.dependencies.add(new ClassSingleAnnotationDependency(this.className,
					node.getTypeName().getFullyQualifiedName(), fullClass.getLineNumber(node.getStartPosition()),
					node.getStartPosition(), node.getLength(), expression));
		} 
		
		else if (node.getParent().getNodeType() == ASTNode.FIELD_DECLARATION) {
			FieldDeclaration field = (FieldDeclaration) node.getParent();
			Type type = field.getType();
			String typeDependency = addNameOfTypes(type);
			this.dependencies
					.add(new FieldSingleAnnotationDependency(this.className, node.getTypeName().getFullyQualifiedName(),
							fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength(),
							((VariableDeclarationFragment) field.fragments().get(0)).getName().getIdentifier(),
							typeDependency, expression));
		}

		this.allDependenciesOfFile.addAll(this.dependencies);

		return true;

	}

	public String addNameOfTypes(Type type) {

		if (type instanceof SimpleType) {
			return ((SimpleType) type).getName().getFullyQualifiedName();
		} else if (type instanceof QualifiedType) {
			return ((QualifiedType) type).getName().getFullyQualifiedName();
		} else if (type instanceof PrimitiveType) {
			return ((PrimitiveType) type).getPrimitiveTypeCode().toString();
		}
		return "";
	}

	public ArrayList<Dependency> getDependencies() {
		return dependencies;
	}

	public Set<TypeDeclaration> getDeclarations() {
		return declarations;
	}

	public ICompilationUnit getUnit() {
		return unit;
	}

	public CompilationUnit getFullClass() {
		return this.fullClass;
	}

	public ArrayList getAllDependenciesOfFile() {
		return allDependenciesOfFile;
	}

}
