package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import msdcl.core.CommunicateDefinition;
import msdcl.core.MicroserviceDefinition;

public class Util {

	private static final Util instance = new Util();
	public Util() {}
	public static Util getInstance() {
		return instance;
	}
	public static String getExtensionOfFile(File f){
		int index = f.getName().lastIndexOf('.');
		if(index >= 0 && index < f.getName().length()){
			return f.getName().substring(index + 1);
		}else{
			return null;
		}
	}
	
	public static List<File> getAllFiles(File dir) throws IOException{
		if(!dir.getCanonicalFile().isDirectory()){
			throw new IOException("Folder " + dir.getCanonicalFile()+ " is not a folder");
		}
		List<File> anyFiles = new LinkedList<>();
		File files[] = dir.listFiles();
		String filePath = null;
		
		for(File f : files){
			if(f.isDirectory()){
				anyFiles.addAll(getAllFiles(f));
			}else{
				String extension = getExtensionOfFile(f);
				if((extension != null) && ((extension.equalsIgnoreCase("cs")) || (extension.equalsIgnoreCase("java")))){
					anyFiles.add(f);
				}
			}
		}
		return anyFiles;
	}
	
	public static String readFileToCharArray(String filePath) throws IOException {
		
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
		//	System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return  fileData.toString();	
	}
	
	public HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> analyseExtracts(File extracts) throws IOException{
		
		HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> map = new HashMap<>();
		boolean vazio = true;
		FileReader file;
		file = new FileReader(extracts);
		BufferedReader buffer = new BufferedReader(file);
		String line = "";
		Set<CommunicateDefinition> communicationsDefinitions = new HashSet<>();
		
		MicroserviceDefinition msOrigin = null;
		CommunicateDefinition communications = null;
		while (buffer.ready()) {
			line = buffer.readLine();
			String []readCommunications = line.split(" ");
			System.out.println("origin: " + readCommunications[0] + 
					" destino : " + readCommunications[2] + 
					" using : " + readCommunications[4]);
			msOrigin = new MicroserviceDefinition(readCommunications[0]);
			if(vazio){
				System.err.println("origin: " + readCommunications[0]);
				
				communications = new CommunicateDefinition(msOrigin.getName(), readCommunications[2], readCommunications[4]);
				communicationsDefinitions.add(communications);
				map.put(msOrigin,communicationsDefinitions);
				vazio = false;

			}
			else {
				for(MicroserviceDefinition m : map.keySet()) {
					if(m.getName().equals(msOrigin.getName())) {
						communications = new CommunicateDefinition(msOrigin.getName(), readCommunications[2], readCommunications[4]);
						communicationsDefinitions.add(communications);
						map.put(msOrigin,communicationsDefinitions);
						communicationsDefinitions.clear();

					}
					else {
						communications = new CommunicateDefinition(msOrigin.getName(), readCommunications[2], readCommunications[4]);
						communicationsDefinitions.add(communications);
						map.put(msOrigin,communicationsDefinitions);
					}
				}
			}
			communicationsDefinitions.clear();
			
		}
		buffer.close();
		file.close();
		return map;
		
	}
}

