package visualization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import msdcl.communicationChecker.ArchitecturalDrift;
import msdcl.communicationExtractor.CommunicationExtractor;
import msdcl.core.CommunicateDefinition;
import msdcl.core.ConstraintDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;
import msdcl.exception.MsDCLException;

public class MatrizManager {
	ArrayList<InformationsMatriz> info2 = new ArrayList<InformationsMatriz>();

	public MatrizManager() {

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

	
	public String createHtmlTable(String[][] violates) throws IOException {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<html>");
		htmlBuilder.append(
				"<head><link rel=\"stylesheet\" type=\"text/css\" href=\"visualizacao.css\"><title>Architectural Visualization</title></head>");
		htmlBuilder.append("<body><p>Microservices</p>");
		htmlBuilder.append("<div class=\"divTable\">");
		htmlBuilder.append("<div class=\"divTableBody\">");
		for (String[] microservices : violates) {
			htmlBuilder.append("<div class=\"divTableRow\">");
			for (String ms : microservices) {

				if (ms.equals("X")) {
					htmlBuilder.append(
							"<div class=\"divTableCell\" style=\"background-color: red;\">&nbsp;" + ms + "</div>");
				} else if (ms.contains("!")) {
					htmlBuilder.append(
							"<div class=\"divTableCell\" style=\"background-color: orange;\">&nbsp;" + ms + "</div>");

				}  else if (ms.equals("?")) {
					htmlBuilder.append(
							"<div class=\"divTableCell\" style=\"background-color: gray;\">&nbsp;" + ms + "</div>");
				}
				else {
					htmlBuilder.append(
							"<div class=\"divTableCell\" style=\"background-color: white;\">&nbsp;" + ms + "</div>");
				}

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

		HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> comunications = CommunicationExtractor.getInstance()
				.analyseAll(system);

		int tam = system.getMicroservices().size() + 1;
		String[][] informations = new String[tam][tam];

		informations[0][0] = new String("Microservices");
		info2.add(new InformationsMatriz(0, 0, "Microservices"));
		int i = 1;
		int j = 1;
		for (MicroserviceDefinition m : comunications.keySet()) {

			// System.out.println(m.getName());
			info2.add(new InformationsMatriz(0, i, m.getName()));
			info2.add(new InformationsMatriz(j, 0, m.getName()));
			informations[0][i] = new String(""+ i);
			informations[j][0] = new String(i + " - " + m.getName());

			if (i == j) {
				informations[i][j] = new String("--");
			}
			i++;
			j++;

		}

		return informations;
	}

	public String[][] getViolates(Map<MicroserviceDefinition, Set<ArchitecturalDrift>> driftsCommunications,
			String[][] informations) {

		String[][] violates = informations;

		for (MicroserviceDefinition m : driftsCommunications.keySet()) {

			for (ArchitecturalDrift a : driftsCommunications.get(m)) {

				ConstraintDefinition c = a.getViolateConstraint();

				if (c!=null && c.getMicroserviceOrigin().equals(m.getName())) {

					String[] mensage = a.getMessage().split(" ");
					String violation = mensage[0];
					String v1[] = violation.split(":");
					int pos_i = getPositionsX(mensage[1]);
					int pos_j = getPositionsY(mensage[3]);
					if (v1[0].equals("Abcence")) {
						violates[pos_i][pos_j] = new String("X");
					} 
					else if (v1[0].equals("Divergence")) {
						String value = informations[pos_i][pos_j];
						if (!value.contains("!")) {
							violates[pos_i][pos_j] = new String(value + "!");
						} else {
							String values[] = value.split("!");
							violates[pos_i][pos_j] = new String(values[0] + "!");
						}
					}
					else if(v1[0].equals("Warning")) {
						violates[pos_i][pos_j] = new String("?");
					}
				}
				else {
					continue;
				}

			}
		}
		return violates;
	}

	public String[][] setMatriz(MicroservicesSystem system) throws IOException, MsDCLException {
		String[][] informations = createMatriz(system);

		// info();
		int cont = 0;
		HashMap<MicroserviceDefinition, Set<CommunicateDefinition>> comunications = CommunicationExtractor.getInstance()
				.analyseAll(system);
		for (MicroserviceDefinition m : comunications.keySet()) {

			for (CommunicateDefinition c : comunications.get(m)) {

				String origem = c.getMicroserviceOrigin();
				String destino = c.getMicroserviceDestin();
				int pos_i = getPositionsX(origem);
				int pos_j = getPositionsY(destino);
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
				// System.out.println("N: " + i.getMsName() + " i: " + i.getPosicao_j());

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
