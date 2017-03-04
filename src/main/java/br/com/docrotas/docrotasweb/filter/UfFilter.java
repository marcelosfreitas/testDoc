package br.com.docrotas.docrotasweb.filter;

import java.util.Date;

public class UfFilter implements Filter{

	private String descricao;

	private String sigla;

	private Long codIBGEMaior;

	private Long codIBGEMenor;

	private Date dtCriacaoMaior;

	private Date dtCriacaoMenor;

	private Date dtAlteracaoMaior;

	private Date dtAlteracaoMenor;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Long getCodIBGEMaior() {
		return codIBGEMaior;
	}

	public void setCodIBGEMaior(Long codIBGEMaior) {
		this.codIBGEMaior = codIBGEMaior;
	}

	public Long getCodIBGEMenor() {
		return codIBGEMenor;
	}

	public void setCodIBGEMenor(Long codIBGEMenor) {
		this.codIBGEMenor = codIBGEMenor;
	}

	public Date getDtCriacaoMaior() {
		return dtCriacaoMaior;
	}

	public void setDtCriacaoMaior(Date dtCriacaoMaior) {
		this.dtCriacaoMaior = dtCriacaoMaior;
	}

	public Date getDtCriacaoMenor() {
		return dtCriacaoMenor;
	}

	public void setDtCriacaoMenor(Date dtCriacaoMenor) {
		this.dtCriacaoMenor = dtCriacaoMenor;
	}

	public Date getDtAlteracaoMaior() {
		return dtAlteracaoMaior;
	}

	public void setDtAlteracaoMaior(Date dtAlteracaoMaior) {
		this.dtAlteracaoMaior = dtAlteracaoMaior;
	}

	public Date getDtAlteracaoMenor() {
		return dtAlteracaoMenor;
	}

	public void setDtAlteracaoMenor(Date dtAlteracaoMenor) {
		this.dtAlteracaoMenor = dtAlteracaoMenor;
	}
	
	@Override
	public void validaFilter() throws FilterException {
		if ((descricao == null || descricao.isEmpty()) &&
			(sigla == null || sigla.isEmpty()) &&
			(codIBGEMenor == null) &&
			(codIBGEMaior == null) &&
			(dtCriacaoMenor == null) &&
			(dtCriacaoMaior == null) &&
			(dtAlteracaoMenor == null) &&
			(dtAlteracaoMaior == null)) {
			throw new FilterException("Filtro Uf vazio");
		}
		
	}

	@Override
	public String toString() {
		return "UfFilter [descricao=" + descricao + ", sigla=" + sigla + ", codIBGEMaior=" + codIBGEMaior
				+ ", codIBGEMenor=" + codIBGEMenor + ", dtCriacaoMaior=" + dtCriacaoMaior + ", dtCriacaoMenor="
				+ dtCriacaoMenor + ", dtAlteracaoMaior=" + dtAlteracaoMaior + ", dtAlteracaoMenor=" + dtAlteracaoMenor
				+ "]";
	}
}
