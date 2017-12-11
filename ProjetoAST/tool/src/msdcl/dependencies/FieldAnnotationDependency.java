package msdcl.dependencies;

public class FieldAnnotationDependency  extends AnnotationDependency{
	private String nameField ; 
	public FieldAnnotationDependency(String nameClass1, String nameClass2, Integer position, Integer offset,
			Integer length, String nameField) {
		super(nameClass1, nameClass2, position, offset, length);
		this.nameField = nameField;
	}
	public FieldAnnotationDependency(String nameClass2, Integer position, Integer offset,
			Integer length, String nameField) {
		super( nameClass2, position, offset, length);
		this.nameField = nameField;
	}
	public String getNameField() {
		return nameField;
	}
	
	public String toString() {
		return "'"+this.getNameClass1() + "  contains the field " + this.nameField + 
				"  that is annotated by " + this.getNameClass2() + "'";
	}
	
}
