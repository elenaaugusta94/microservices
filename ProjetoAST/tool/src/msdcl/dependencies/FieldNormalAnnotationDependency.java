package msdcl.dependencies;

import java.util.ArrayList;
import java.util.List;

public class FieldNormalAnnotationDependency extends FieldAnnotationDependency {
	private String name;
	private String value;
	
	public FieldNormalAnnotationDependency(String nameClass1, String nameClass2, Integer position, Integer offset,
			Integer length, String nameField, String declaration, String name, String value) {
		super(nameClass1, nameClass2, position, offset, length, nameField, declaration);
		this.name = name;
		this.value = value;
	}


	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	public String toString() {
	return "'" + this.getNameClass1() + "  contains the field " + this.nameField + " of type" + this.declaration
			+ "  that is annotated by " + this.getNameClass2() + "'" + " with name" + this.name + " and value: " + this.value + "'";
}
}
