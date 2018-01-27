package msdcl.main;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fileManager.InputManager;
import fileManager.OutputManager;
import msdcl.communicationChecker.ArchitecturalDrift;
import msdcl.communicationChecker.CommunicationChecker;
import msdcl.communicationExtractor.CommunicationExtractor;
import msdcl.core.CommunicateDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;
import msdcl.exception.MsDCLException;
import msdcl.structuralChecker.Pi_DCL;
import pidclcheck.exception.ParseException;
import util.Util;

public class Run {
	public static void main(String[] args) throws MsDCLException, ParseException {
		try {
//			if (args.length != 3){
//				System.out.println("Usage:\n msdclcheck [dir-folder] [Name- architecturalSpecification] [Name - communications-extractor]");
//				return;
//			}
			
//			String folder = args[0];
//			String constraints = args[1];
//			String extracts = args[2];
			
		//	System.out.println("Folder Dir: " + architecturalSpecification);
			
			String pathconstraints = "/home/elena/Documents/MsDCLartigo/toyExample/constraints.txt";
			String pathCommunications = "/home/elena/Documents/MsDCLartigo/toyExample/extracts.txt";
			
			File file = new File(pathCommunications);

			//String pathViolated = args[1];
			MicroservicesSystem system  = getConstraints(pathconstraints);			
			
		//	MicroservicesSystem system  = getConstraints(architecturalSpecification);			
			
			// extrai os acessos dos microserviços
			
			
			HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> communications =Util.getInstance().analyseExtracts(file); 
			
			getComunications(communications);
			
			system.setCommunications(communications); 
			
			// verifica violações de comunicação 
			Set<ArchitecturalDrift> driftsCommunications = CommunicationChecker.getInstance().check(system);
			
			// verifica violações de projeto estrutural
			Map<MicroserviceDefinition, Collection<pidclcheck.core.DependencyConstraint.ArchitecturalDrift>> driftsStructurals  = Pi_DCL.getInstance().validateLocalArchitectures(system);
			
			// gera o arquivo com as violações
			OutputManager output = new OutputManager();
			
			//output.violate("", driftsCommunications, driftsStructurals);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	// path da especificação arquitetural
	public static MicroservicesSystem getConstraints(String architecturalSpecification) throws IOException {
		
		InputManager inputManager = new InputManager(); 
		MicroservicesSystem system = inputManager.readFile(new File(architecturalSpecification));
		return system;
	}
	public static void getComunications(HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> system) {
		 System.out.println("---------------------  Communicações -----------------");
		 System.out.println();
		for(MicroserviceDefinition ms : system.keySet()) {
			for(CommunicateDefinition com : system.get(ms)) { 
				System.out.println(com.getMicroserviceOrigin() + " communicate " +
						com.getMicroserviceDestin() + " using " + 
						com.getUsing());
				System.out.println();
			}
		}
	}
}
