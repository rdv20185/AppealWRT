package service;


import domain.Petit;

public class PetitListWrapper {
	
	public java.util.List<Petit> getPetit() {
		return petit;
	}

	public void setPetit(java.util.List<Petit> listPetit) {
		this.petit = listPetit;
	}

	private java.util.List<Petit> petit;
}
