package msdcl.dependencies;

import java.util.HashSet;
import java.util.Set;

public class FieldNormalAnnotationDependency extends FieldAnnotationDependency {
	private Set<MemberPair> membersValues;
	
	public FieldNormalAnnotationDependency(String nameClass1, String nameClass2, Integer position, Integer offset,
			Integer length, String nameField, String declaration, Set<MemberPair> membersValues) {
		super(nameClass1, nameClass2, position, offset, length, nameField, declaration);
		this.membersValues = new HashSet<>();
	}


	
	public String toString() {
	return "'" + this.getNameClass1() + "  contains the field " + this.nameField + " of type" + this.declaration
			+ "  that is annotated by " + this.getNameClass2() + "'" + " with name" + this.name + " and value: " + this.value + "'";
}
}
