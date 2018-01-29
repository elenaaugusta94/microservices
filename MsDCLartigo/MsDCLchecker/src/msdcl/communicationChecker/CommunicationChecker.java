package msdcl.communicationChecker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import enums.ConstraintType;
import msdcl.core.CommunicateDefinition;
import msdcl.core.ConstraintDefinition;
import msdcl.core.MicroserviceDefinition;
import msdcl.core.MicroservicesSystem;

public class CommunicationChecker {

	private final static CommunicationChecker instance = new CommunicationChecker();

	public CommunicationChecker() {

	}

	public static CommunicationChecker getInstance() {
		return instance;
	}

	// checa localmente se um microservico pode se comunicar com outro
	private Set<ClassifiedCommunicate> canCommunicateLocal(CommunicateDefinition communicate,
			MicroservicesSystem system) {
		Set<ClassifiedCommunicate> classifiedCommunications = new HashSet<>();
		MicroserviceDefinition msOrigin = system.getMicroserviceDefinition(communicate.getMicroserviceOrigin());
		for (ConstraintDefinition constraint : system.getConstraints(msOrigin)) {
			Boolean canCommunicate = constraint.canCommunicate(communicate);
			if (canCommunicate != null && canCommunicate == true) {
				classifiedCommunications.add(new ClassifiedCommunicate(true, constraint));
			} else if (canCommunicate != null && canCommunicate == false) {
				classifiedCommunications.add(new ClassifiedCommunicate(false, constraint));
			}
		}
		return classifiedCommunications;
	}

	// checa globalmente se um microservico pode se comunicar com outro
	private Set<ArchitecturalDrift> canCommunicateGlobal(CommunicateDefinition communicate,
			MicroservicesSystem system) {
		Set<ArchitecturalDrift> drifts = new HashSet<>();
		MicroserviceDefinition msOrigin = system.getMicroserviceDefinition(communicate.getMicroserviceOrigin());
		for (MicroserviceDefinition ms : system.getMicroservices()) {
			if (!ms.equals(msOrigin)) {
				for (ConstraintDefinition constraint : system.getConstraints(ms)) {
					if (constraint.getMicroserviceDestin().equalsIgnoreCase(communicate.getMicroserviceDestin())
							&& constraint.usingMatch(communicate)
							&& constraint.getConstraint().getConstraintType() == ConstraintType.ONLY_CAN_COMMUNICATE) {
						drifts.add(new DivergenceDependencyConstraint(constraint, communicate));
					}
				}
			}
		}
		return drifts;
	}

	public Set<ArchitecturalDrift> canCommunicate(CommunicateDefinition communicate, MicroservicesSystem system) {
		Set<ArchitecturalDrift> drifts = canCommunicateGlobal(communicate, system);
		Set<ClassifiedCommunicate> classifiedCommunications = canCommunicateLocal(communicate, system);
		for (ClassifiedCommunicate classifiedCommunicate : classifiedCommunications) {
			if (!classifiedCommunicate.canCommunicate) {
				drifts.add(new DivergenceDependencyConstraint(classifiedCommunicate.associatedConstraint,
						communicate));
			}
		}
		if (classifiedCommunications.size() == 0 && drifts.size() == 0) {
			drifts.add(new WarningConstraint(communicate));
		}
		return drifts;
	}

	// obtem ausencias
	public Set<ArchitecturalDrift> getAbsences(MicroserviceDefinition service, MicroservicesSystem system) {
		Set<ArchitecturalDrift> absences = new HashSet<>();
		for (ConstraintDefinition constraint : system.getConstraints(service)) {
			if (constraint.getConstraint().getConstraintType() == ConstraintType.MUST_COMMUNICATE) {
				boolean absence = true;
				for (CommunicateDefinition communicate : system.getCommunications(service)) {
					if (constraint.match(communicate)) {
						absence = false;
						break;
					}
				}
				if (absence) {
					absences.add(new AbsenceDependencyConstraint(constraint));
				}
			}
		}
		return absences; 
	}

	public Map<MicroserviceDefinition, Set<ArchitecturalDrift>> check(MicroservicesSystem system) {
		Set<ArchitecturalDrift> drifts = new HashSet<>();
		Map<MicroserviceDefinition, Set<ArchitecturalDrift>> driftsCommunications = new HashMap<>();
		ArchitecturalDrift drift;
		for (MicroserviceDefinition ms : system.getMicroservices()) {
			if(!(system.getCommunications(ms)==null)){
				for (CommunicateDefinition communicate : system.getCommunications(ms)) {
					drifts.addAll(canCommunicate(communicate, system));
					driftsCommunications.put(ms, drifts);
				}
				Set<ArchitecturalDrift> absences = getAbsences(ms, system);
				drifts.addAll(absences);
				driftsCommunications.put(ms, drifts);
			}
		}
		return driftsCommunications;
	}

}