package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.VeiculoListerner;

@Entity
@Table(name = "veiculo")
@EntityListeners(value=VeiculoListerner.class)
public class Veiculo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cod_frota", nullable = false)
	private Long codFrota;
	
	@Column(name = "placa", length = 7, nullable = false)
	private String placa;
	
	@Column(name = "renavam", length = 11, nullable = false)
	private String renavam;
	
	@Column(name = "rntrc", length = 8, nullable = false)
	private String rntrc;
	
	@Column(name = "tara", nullable = false)
	private Long tara;
	
	@Column(name = "cap_kg", nullable = false)
	private Long capKg;
	
	@Column(name = "cap_m3", nullable = false)
	private Long capCub;
	
	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_rodado", nullable = false)
	private TipoRodado rodado;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_carroceria", nullable = false)
	private TipoCarroceria carroceria;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_proprietario", nullable = false)
	private TipoProprietario proprietario;
	
	@ManyToOne
	@JoinColumn(name = "uf_id")
	private Uf uf;
	
	@OneToOne
	@JoinColumn(name = "pessoa_id")
	private Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodFrota() {
		return codFrota;
	}

	public void setCodFrota(Long codFrota) {
		this.codFrota = codFrota;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public String getRntrc() {
		return rntrc;
	}

	public void setRntrc(String rntrc) {
		this.rntrc = rntrc;
	}

	public Long getTara() {
		return tara;
	}

	public void setTara(Long tara) {
		this.tara = tara;
	}

	public Long getCapKg() {
		return capKg;
	}

	public void setCapKg(Long capKg) {
		this.capKg = capKg;
	}

	public Long getCapCub() {
		return capCub;
	}

	public void setCapCub(Long capCub) {
		this.capCub = capCub;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public Date getDtAlteracao() {
		return dtAlteracao;
	}

	public void setDtAlteracao(Date dtAlteracao) {
		this.dtAlteracao = dtAlteracao;
	}

	public TipoRodado getRodado() {
		return rodado;
	}

	public void setRodado(TipoRodado rodado) {
		this.rodado = rodado;
	}

	public TipoCarroceria getCarroceria() {
		return carroceria;
	}

	public void setCarroceria(TipoCarroceria carroceria) {
		this.carroceria = carroceria;
	}

	public TipoProprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(TipoProprietario proprietario) {
		this.proprietario = proprietario;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Veiculo [id=" + id + ", codFrota=" + codFrota + ", placa=" + placa + ", renavam=" + renavam + ", rntrc="
				+ rntrc + ", tara=" + tara + ", capKg=" + capKg + ", capCub=" + capCub + ", dtCriacao=" + dtCriacao
				+ ", dtAlteracao=" + dtAlteracao + ", rodado=" + rodado + ", carroceria=" + carroceria
				+ ", proprietario=" + proprietario + ", uf=" + uf + ", pessoa=" + pessoa + "]";
	}
	
}
