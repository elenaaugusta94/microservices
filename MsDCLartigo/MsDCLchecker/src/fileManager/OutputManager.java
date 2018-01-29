package fileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import msdcl.communicationChecker.ArchitecturalDrift;
import msdcl.core.ConstraintDefinition;
import msdcl.core.MicroserviceDefinition;

public class OutputManager {

	public static void violates(Set<ArchitecturalDrift> drifts) throws IOException {
		FileWriter fileWriter = new FileWriter("violates.txt");
		PrintWriter writer = new PrintWriter(fileWriter);

		for (ArchitecturalDrift a : drifts) {
			System.out.println(a.getMessage());
			writer.println(a.getMessage());
		}

		fileWriter.close();

	}
	public static void violate(Map<MicroserviceDefinition, Set<ArchitecturalDrift>> driftsCommunications, String path)
			throws IOException {
		FileWriter fileWriter = new FileWriter(path + "violates.txt");
		PrintWriter writer = new PrintWriter(fileWriter);

		for (MicroserviceDefinition m : driftsCommunications.keySet()) {
			
			if(!(driftsCommunications.get(m)==null)) {
				
				for (ArchitecturalDrift a : driftsCommunications.get(m)) {
					if(!(a.getViolateConstraint() == null)){
						ConstraintDefinition c = a.getViolateConstraint();
						
						if (c.getMicroserviceOrigin().equals(m.getName())) {
							System.out.println(m.getName() + " ->> " + "\t" + a.getMessage());
							writer.println(m.getName() + " ->> " + "\t" + a.getMessage());
						}
					}
				}
			}
			System.out.println();
			writer.println();

		}
		fileWriter.close();
	}

	public static void generateHTML(String html) throws IOException {
		String texto = html;
		try {
		FileWriter fileWriter = new FileWriter("../visualizacao.html");
		fileWriter.write(texto);
		
		fileWriter.close();
		System.out.println("Arquivo HTML gerado com sucesso! ");
		}
		catch(Exception e) {
			
		}
	}
	
}
