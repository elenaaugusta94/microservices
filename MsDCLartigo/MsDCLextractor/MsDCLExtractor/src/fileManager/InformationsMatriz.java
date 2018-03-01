package fileManager;

public class InformationsMatriz {
	private int posicao_i;
	private int posicao_j;
	private String msName;
	public InformationsMatriz(int posicao_i, int posicao_j, String msName) {
		this.posicao_i = posicao_i;
		this.posicao_j = posicao_j;
		this.msName = msName;
	}
	
	public InformationsMatriz() {}
	public int getPosicao_i() {
		return posicao_i;
	}
	public void setPosicao_i(int posicao_i) {
		this.posicao_i = posicao_i;
	}
	public int getPosicao_j() {
		return posicao_j;
	}
	public void setPosicao_j(int posicao_j) {
		this.posicao_j = posicao_j;
	}
	public String getMsName() {
		return msName;
	}
	public void setMsName(String msName) {
		this.msName = msName;
	}
	
}
