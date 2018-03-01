package msdcl.main;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.sound.midi.Soundbank;

import fileManager.CommunicationMatriz;
import fileManager.InputManager;
import fileManager.OutputManager;
import msdcl.communicationExtractor.CommunicationExtractor;
import msdcl.core.CommunicateDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;
import msdcl.exception.MsDCLException;
import util.Util;

public class Run {
	public static void main(String[] args) throws MsDCLException {
		try {

//			if (args.length != 1) {
//				System.out.println("Usage:\n commextractor.jar [microservices path]");
//				return;
//			} else {
				String path = "/home/elena/Documentos/microservices/microservices/MsViz/";
			//	String path = args[0];
				Util util = new Util();
				File dir = new File(path);
				util.readFileConfig(dir);
				util.createConstraints(path);				
				
				Set<String> dynamicsExtracts = util.getExtracts();
				
				MicroservicesSystem system = getConstraints(path);
				system.setCommunications(CommunicationExtractor.getInstance().analyseAll(system));
				OutputManager.getInstance().createExtracts(system, dynamicsExtracts, path);
				CommunicationMatriz matriz = new CommunicationMatriz();
				String[][] informations = matriz.setMatriz2(system);
				matriz.updateMatriz(informations);
				String html = matriz.createHtmlTable(informations);
				OutputManager.getInstance().generateHTML(html);
				System.out.println();
				System.out.println();
				System.out.println("Extração de Comunicações realizada com Sucesso!");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// path da especificação arquitetural
	public static MicroservicesSystem getConstraints(String architecturalSpecification) throws IOException {

		InputManager inputManager = new InputManager();
		MicroservicesSystem system = inputManager.readFile(new File(architecturalSpecification + "/constraints.txt"));
		return system;
	}
	
	public static void imprimeMatriz(String[][] informations) {
		for (String[] microservices : informations) {
			System.out.println(" ");
			for(String ms : microservices) {
				System.out.print(" "+ ms);
			}
			
		}
	}

	public static void imprimeSystem(MicroservicesSystem system) {
		for (MicroserviceDefinition m : system.getMicroservices()) {
			for (CommunicateDefinition c : system.getCommunications(m)) {
				
				System.out.println(c.getMicroserviceOrigin() + " ->>>>> " + c.getMicroserviceDestin());
			}
		}
	}
}