package fileManager;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import msdcl.core.CommunicateDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;

public class OutputManager {

	private static final OutputManager instance = new OutputManager();

	public OutputManager() {
	}

	public static OutputManager getInstance() {
		return instance;
	}

	public static MicroservicesSystem createExtracts(MicroservicesSystem system, Set<String> dynamicsExtracts,
			String path) throws IOException {

		FileWriter fileWriter = new FileWriter(path + "/extracts.txt");
		PrintWriter writer = new PrintWriter(fileWriter);

		for (MicroserviceDefinition ms : system.getMicroservices()) {
			for (CommunicateDefinition com : system.getCommunications(ms)) {
				String communication = com.getMicroserviceOrigin() + " communicate " + com.getMicroserviceDestin()
						+ " using " + com.getUsing();
				writer.println(communication);
			}
		}

		for (String ms : dynamicsExtracts) {
			String communication = ms;
			String[] communications = communication.split(" ");
			String origin = communications[0];
			String destin = communications[2];
			String commInterface = communications[4];
			insertMicroserviceSystem(system, origin, destin, commInterface);

			writer.println(communication);
		}
		fileWriter.close();
		return system;

	}

	public static void verifyMicroservice(String origin, MicroservicesSystem system) {

	}

	public static MicroservicesSystem insertMicroserviceSystem(MicroservicesSystem system, String origin, String destin,
			String commInterface) {

		MicroserviceDefinition msDefinitionOrigin = system.getMicroserviceDefinition(origin);
		MicroserviceDefinition msDefinitionDestin = system.getMicroserviceDefinition(destin);
		if (system.getCommunications(msDefinitionOrigin) == null) {
			system.addMicroservice(msDefinitionOrigin);
			CommunicateDefinition communication = new CommunicateDefinition(origin, destin, commInterface);
			system.addCommunication(communication);
		}
		if (system.getCommunications(msDefinitionDestin) == null) {
			MicroserviceDefinition ms = new MicroserviceDefinition(destin, " ", " ");
			CommunicateDefinition communication = new CommunicateDefinition(origin, destin, commInterface);
			system.addMicroservice(ms);
			system.addCommunication(communication);
		}
		else {
			CommunicateDefinition communication = new CommunicateDefinition(origin, destin, commInterface);
			system.addCommunication(communication);
		}

		return system;
	}

	public static void createConstraints(MicroservicesSystem constraints, String path) throws IOException {

		FileWriter fileWriter = new FileWriter(path);
		PrintWriter writer = new PrintWriter(fileWriter);
		for (MicroserviceDefinition ms : constraints.getMicroservices()) {
			writer.println(ms.getName() + ":" + " " + ms.getLink() + "; " + ms.getPath());

		}
		fileWriter.close();

	}
	public static void generateHTML(String html) throws IOException {
		String texto = html;
		try {
		FileWriter fileWriter = new FileWriter("../visualizacao.html");
		//PrintWriter writer = new PrintWriter(fileWriter);
		fileWriter.write(texto);
		
		fileWriter.close();
		System.out.println("Arquivo HTML gerado com sucesso! ");
		}
		catch(Exception e) {
			
		}
	}
	
}
