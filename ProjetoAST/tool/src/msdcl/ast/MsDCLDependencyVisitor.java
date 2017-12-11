package msdcl.ast;

import java.util.ArrayList;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.ITypeHierarchy;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import msdcl.dependencies.ClassAnnotationDependency;
import msdcl.dependencies.DeclareParameterDependency;
import msdcl.dependencies.DeclareVariableDependency;
import msdcl.dependencies.Dependency;
import msdcl.dependencies.ExtendsDirectDependency;
import msdcl.dependencies.FieldAnnotationDependency;
import msdcl.dependencies.ImplementsDependency;
import msdcl.dependencies.MethodAnnotationDependency;
import msdcl.exception.MsDCLException;

public class MsDCLDependencyVisitor extends ASTVisitor {

	private ArrayList<Dependency> dependencies;
	private ICompilationUnit unit;
	private CompilationUnit compUnit;
	private CompilationUnit fullClass;
	private String className;
	


	public MsDCLDependencyVisitor() {
		this.unit = unit;
	}

	public MsDCLDependencyVisitor(ICompilationUnit unit) throws MsDCLException {
		try {
			this.dependencies = new ArrayList<Dependency>();
			this.unit = unit;

			this.className = unit.getParent().getElementName() + "."
					+ unit.getElementName().substring(0, unit.getElementName().length() - 5);

			ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(unit);
			parser.setResolveBindings(true);
			
			
			this.fullClass = (CompilationUnit) parser.createAST(null); // parse

			this.fullClass.accept(this);
		} catch (Exception e) {
			throw new MsDCLException(e, unit);
		}
	}

	public MsDCLDependencyVisitor(String str) throws MsDCLException {
		try {
			System.out.println("O que chegou aqui senhor: "+ str);
			this.dependencies = new ArrayList<Dependency>();
			
			ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setResolveBindings(true);
			parser.setStatementsRecovery(true);
			parser.setBindingsRecovery(true);
			parser.setSource(str.toCharArray());
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			
			fullClass = (CompilationUnit) parser.createAST(null);
			parser.setSource(str.toCharArray());
			 
			System.out.println(fullClass.getLineNumber(34));
		//	this.unit = (ICompilationUnit) parser.createAST(null);
		//	 CompilationUnit ast3 = parseCompilationUnitAst3(fullClass);

			System.out.println(unit.getElementName());
			this.className = unit.getParent().getElementName() + "."
					+ unit.getElementName().substring(0, unit.getElementName().length() - 5);
			System.out.println("className: " + className);
			
			this.fullClass.accept(this);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new MsDCLException(e, unit);
		}
	}

	public ArrayList<Dependency> getDependencies() {
		return dependencies;
	}

	public ICompilationUnit getUnit() {
		return unit;
	}

	@Override
	public boolean visit(TypeDeclaration node) {// visita todas as declarações em uma classe
		try {

			IType type = (IType) unit.getTypes()[0];
			ITypeHierarchy typeHierarchy = type.newSupertypeHierarchy(null);
			IType[] typeSuperinter = typeHierarchy.getAllInterfaces();

			for (IType t : typeSuperinter) {
				for (Object it : node.superInterfaceTypes()) {
					switch (((Type) it).getNodeType()) {
					case ASTNode.SIMPLE_TYPE:
						SimpleType st = (SimpleType) it;
						if (t.getFullyQualifiedName().equals(st.getName().resolveTypeBinding().getQualifiedName())) {
							if (!type.isInterface()) {
								this.dependencies.add(new ImplementsDependency(this.className,
										t.getFullyQualifiedName(), fullClass.getLineNumber(st.getStartPosition()),
										st.getStartPosition(), st.getLength()));
							} else {
								this.dependencies.add(new ExtendsDirectDependency(this.className,
										t.getFullyQualifiedName(), fullClass.getLineNumber(st.getStartPosition()),
										st.getStartPosition(), st.getLength()));
							}
							continue;
						}
						break;
					case ASTNode.PARAMETERIZED_TYPE:
						ParameterizedType pt = (ParameterizedType) it;
						if (t != null && t.getFullyQualifiedName() != null && pt != null && pt.getType() != null
								&& pt.getType().resolveBinding() != null
								&& t.getFullyQualifiedName().equals(pt.getType().resolveBinding().getBinaryName())) {
							if (!type.isInterface()) {
								this.dependencies.add(new ImplementsDependency(this.className,
										t.getFullyQualifiedName(), fullClass.getLineNumber(pt.getStartPosition()),
										pt.getStartPosition(), pt.getLength()));
							} else {
								this.dependencies.add(new ExtendsDirectDependency(this.className,
										t.getFullyQualifiedName(), fullClass.getLineNumber(pt.getStartPosition()),
										pt.getStartPosition(), pt.getLength()));
							}
							continue;
						}
						break;
					}
				}
				// this.dependencies.add(new ImplementIndirectDependency(this.className,
				// t.getFullyQualifiedName(), null, null, null));
			}

		} catch (JavaModelException e) {
			throw new RuntimeException("AST Parser error.", e);
		}

		return false;

	}

	@Override
	public boolean visit(SingleMemberAnnotation node) { // Visita à uma anotação definida em uma classe. No contexto do
														// MsDCL será considerada as anotações @Autowired (Spring)
		if (node.getParent().getNodeType() == ASTNode.FIELD_DECLARATION) {
			FieldDeclaration field = (FieldDeclaration) node.getParent();
			this.dependencies.add(new FieldAnnotationDependency(this.className,
					node.getTypeName().resolveTypeBinding().getQualifiedName(),
					fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength(),
					((VariableDeclarationFragment) field.fragments().get(0)).getName().getIdentifier()));
		} else if (node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			MethodDeclaration method = (MethodDeclaration) node.getParent();
			this.dependencies.add(new MethodAnnotationDependency(this.className,
					node.getTypeName().resolveTypeBinding().getQualifiedName(),
					fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength(),
					method.getName().getIdentifier()));
		} else if (node.getParent().getNodeType() == ASTNode.TYPE_DECLARATION) {
			this.dependencies.add(new ClassAnnotationDependency(this.className,
					node.getTypeName().resolveTypeBinding().getQualifiedName(),
					fullClass.getLineNumber(node.getStartPosition()), node.getStartPosition(), node.getLength()));
		}
		return true;
	}

	@Override
	public boolean visit(MethodDeclaration node) {
		for (Object o : node.parameters()) {
			if (o instanceof SingleVariableDeclaration) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
				this.dependencies.add(new DeclareParameterDependency(this.className,
						this.getTargetClassName(svd.getType().resolveBinding()),
						fullClass.getLineNumber(svd.getStartPosition()), svd.getStartPosition(), svd.getLength(),
						node.getName().getIdentifier(), svd.getName().getIdentifier()));
				if (svd.getType().getNodeType() == Type.PARAMETERIZED_TYPE) {
					for (Object t : ((ParameterizedType) svd.getType()).typeArguments()) {
						if (t instanceof SimpleType) {
							SimpleType st = (SimpleType) t;
							this.dependencies.add(new DeclareParameterDependency(this.className,
									this.getTargetClassName(st.resolveBinding()),
									fullClass.getLineNumber(st.getStartPosition()), st.getStartPosition(),
									st.getLength(), node.getName().getIdentifier(), svd.getName().getIdentifier()));
						} else if (t instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) t;
							this.dependencies.add(new DeclareParameterDependency(this.className,
									this.getTargetClassName(pt.getType().resolveBinding()),
									fullClass.getLineNumber(pt.getStartPosition()), pt.getStartPosition(),
									pt.getLength(), node.getName().getIdentifier(), svd.getName().getIdentifier()));
						}
					}
				}

			}
		}

		return true;
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		ASTNode relevantParent = getRelevantParent(node);

		switch (relevantParent.getNodeType()) {
		case ASTNode.METHOD_DECLARATION:
			MethodDeclaration md = (MethodDeclaration) relevantParent;

			this.dependencies.add(new DeclareVariableDependency(this.className,
					this.getTargetClassName(node.getType().resolveBinding()),
					fullClass.getLineNumber(node.getStartPosition()), node.getType().getStartPosition(),
					node.getType().getLength(), md.getName().getIdentifier(),
					((VariableDeclarationFragment) node.fragments().get(0)).getName().getIdentifier()));

			break;
		case ASTNode.INITIALIZER:
			this.dependencies.add(new DeclareVariableDependency(this.className,
					this.getTargetClassName(node.getType().resolveBinding()),
					fullClass.getLineNumber(node.getStartPosition()), node.getType().getStartPosition(),
					node.getType().getLength(), "initializer static block",
					((VariableDeclarationFragment) node.fragments().get(0)).getName().getIdentifier()));
			break;
		}

		return true;
	}

	private String getTargetClassName(ITypeBinding type) {
		String result = "";
		if (type != null) {
			if (!type.isAnonymous() && type.getQualifiedName() != null && !type.getQualifiedName().isEmpty()) {
				result = type.getQualifiedName();
			} else if (type.isLocal() && type.getName() != null && !type.getName().isEmpty()) {
				result = type.getName();
			} else if (!type.getSuperclass().getQualifiedName().equals("java.lang.Object")
					|| type.getInterfaces() == null || type.getInterfaces().length == 0) {
				result = type.getSuperclass().getQualifiedName();
			} else if (type.getInterfaces() != null && type.getInterfaces().length == 1) {
				result = type.getInterfaces()[0].getQualifiedName();
			}

			if (result.equals("")) {
				throw new RuntimeException("AST Parser error.");
			} else if (result.endsWith("[]")) {
				result = result.substring(0, result.length() - 2);
			} else if (result.matches(".*<.*>")) {
				result = result.replaceAll("<.*>", "");
			}
		}
		return result;
	}

	

	private ASTNode getRelevantParent(final ASTNode node) {
		for (ASTNode aux = node; aux != null; aux = aux.getParent()) {
			switch (aux.getNodeType()) {
			case ASTNode.FIELD_DECLARATION:
			case ASTNode.METHOD_DECLARATION:
				// case ASTNode.INITIALIZER:
				return aux;
			}
		}
		return node;
	}
}
