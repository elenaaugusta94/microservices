package fileManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import msdcl.communicationExtractor.CommunicationExtractor;
import msdcl.core.CommunicateDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;
import msdcl.exception.MsDCLException;

public class CommunicationMatriz {

	ArrayList<InformationsMatriz> info2 = new ArrayList<InformationsMatriz>();

	public CommunicationMatriz() {
		super();
	}

	public String[][] updateMatriz(String[][] violates) {
		int i = 0;
		int j = 0;

		for (i = 0; i < violates.length; i++) {
			for (j = 0; j < violates[i].length; j++) {
				if (violates[i][j] == null) {
					violates[i][j] = "\t";
				}
			}

		}
		return violates;

	}

	public String createHtmlTable(String[][] informations) throws IOException {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>");
		htmlBuilder.append(
				"<head><link rel=\"stylesheet\" type=\"text/css\" href=\"visualizacao.css\"><title>Architectural Visualization</title></head>");
		htmlBuilder.append("<body><p>Microservices</p>");
		htmlBuilder.append("<div class=\"divTable\">");
		htmlBuilder.append("<div class=\"divTableBody\">");
		for (String[] microservices : informations) {
			htmlBuilder.append("<div class=\"divTableRow\">");
			for (String ms : microservices) {
				htmlBuilder.append(
						"<div class=\"divTableCell\" style=\"background-color: white;\">&nbsp;" + ms + "</div>");

			}
			htmlBuilder.append("</div>");
		}
		htmlBuilder.append("</div>");
		htmlBuilder.append("</div>");
		htmlBuilder.append("</body>");
		htmlBuilder.append("</html>");

		String html = htmlBuilder.toString();
		return html;
	}

	public String[][] createMatriz(MicroservicesSystem system) throws IOException, MsDCLException {

		int tam = system.getMicroservices().size() + 1;
		String[][] informations = new String[tam][tam];

		informations[0][0] = new String("Microservices");
		info2.add(new InformationsMatriz(0, 0, "Microservices"));
		int i = 1;
		int j = 1;
		for (MicroserviceDefinition m : system.getMicroservices()) {

			// System.out.println(m.getName());
			info2.add(new InformationsMatriz(0, i, m.getName()));
			info2.add(new InformationsMatriz(j, 0, m.getName()));
			informations[0][i] = new String("" + i);
			informations[j][0] = new String(i + " - " + m.getName());

			if (i == j) {
				informations[i][j] = new String("--");
			}
			i++;
			j++;

		}

		return informations;
	}

	public String[][] setMatriz2(MicroservicesSystem system) throws IOException, MsDCLException {

		String[][] informations = createMatriz(system);
		int cont = 0;

		for (MicroserviceDefinition m : system.getMicroservices()) {
			for (CommunicateDefinition c : system.getCommunications(m)) {
				String origem = c.getMicroserviceOrigin();
				String destino = c.getMicroserviceDestin();
				int pos_j = getPositionsY(origem);
				int pos_i = getPositionsX(destino);

				String v = informations[pos_i][pos_j];
				if (v != null) {
					int valor = Integer.parseInt(v);
					valor += 1;
					informations[pos_i][pos_j] = "" + valor;
				} else {
					int valor = 1;
					informations[pos_i][pos_j] = "" + valor;
				}

			}

		}

		return informations;

	}

	public void info() {
		for (InformationsMatriz i : info2) {
			System.out.println("[ " + i.getPosicao_i() + "]" + "[" + i.getPosicao_j() + "]" + " = " + i.getMsName());
		}
	}

	public int getPositionsX(String name) {
		for (InformationsMatriz i : info2) {
			if (i.getMsName().equals(name)) {
				return i.getPosicao_j();
			}
		}
		return -1;

	}

	public int getPositionsY(String name) {
		for (InformationsMatriz i : info2) {
			if (i.getMsName().equals(name)) {
				int pos_J = i.getPosicao_j();
				return pos_J;
			}
		}
		return -1;

	}

	public ArrayList<InformationsMatriz> getInfo2() {
		return info2;
	}
}
