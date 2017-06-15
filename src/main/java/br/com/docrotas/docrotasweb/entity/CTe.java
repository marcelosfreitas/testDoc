package br.com.docrotas.docrotasweb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.docrotas.docrotasweb.listerner.CTeListerner;

@Entity
@Table(name = "cte")
@EntityListeners(value=CTeListerner.class)
public class CTe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "serie")
	private Integer serie;
	
	@Column(name = "numero")
	private Long numero;
	
	@Column(name = "dt_emissao")
	private Date dtEmissao;

	@Column(name = "dt_criacao")
	private Date dtCriacao;

	@Column(name = "dt_alteracao")
	private Date dtAlteracao;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_emissao")
	private TipoEmissao tpEmissao;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_ambiente")
	private TipoAmbienteEmissao tpAmbiente;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_servico")
	private TipoServico tpServico;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tp_cte")
	private TipoCTe tpCte;
	
	@Column(name = "frete_total")
	private Double freteTotal;
	
	@Column(name = "base_calculo")
	private Double baseCalculo;
	
	@Column(name = "icms")
	private Double icms;
	
	@Column(name = "aliquota")
	private Double aliquota;
	
	@Column(name = "vlr_mercadoria")
	private Double vlrMercadoria;
	
	//descrição do produto a ser transportado
	@Column(name = "produto")
	private String produto;
	
	@Column(name = "chave")
	private String chave;
	
	@Column(name = "situacao")
	private Integer situacao;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@ManyToOne
	@JoinColumn(name = "coleta_cidade_id")
	private Cidade cidadeColeta;
	
	@ManyToOne
	@JoinColumn(name = "entrega_cidade_id")
	private Cidade cidadeEntrega;
	
	@ManyToOne
	@JoinColumn(name = "remetente_pessoa_id")
	private Pessoa pessoaRemetente;
	
	@ManyToOne
	@JoinColumn(name = "destinatario_pessoa_id")
	private Pessoa pessoaDestinatario;
	
	@ManyToOne
	@JoinColumn(name = "tomador_pessoa_id")
	private Pessoa pessoaTomador;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "nfe_cte", 
		joinColumns = {@JoinColumn(name = "cte_id")},
		inverseJoinColumns = {@JoinColumn(name = "nfe_id")})
	private List<NFe> NFes;
	
	@ManyToOne
	@JoinColumn(name = "cfop")
	private Cfop cfop;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "cst_codigo")
	private TipoCST cst;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Cfop getCfop() {
		return cfop;
	}

	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}

	public Date getDtEmissao() {
		return dtEmissao;
	}

	public void setDtEmissao(Date dtEmissao) {
		this.dtEmissao = dtEmissao;
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

	public TipoEmissao getTpEmissao() {
		return tpEmissao;
	}

	public void setTpEmissao(TipoEmissao tpEmissao) {
		this.tpEmissao = tpEmissao;
	}

	public TipoAmbienteEmissao getTpAmbiente() {
		return tpAmbiente;
	}

	public void setTpAmbiente(TipoAmbienteEmissao tpAmbiente) {
		this.tpAmbiente = tpAmbiente;
	}

	public TipoServico getTpServico() {
		return tpServico;
	}

	public void setTpServico(TipoServico tpServico) {
		this.tpServico = tpServico;
	}

	public Double getFreteTotal() {
		return freteTotal;
	}

	public void setFreteTotal(Double freteTotal) {
		this.freteTotal = freteTotal;
	}

	public Double getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Double getIcms() {
		return icms;
	}

	public void setIcms(Double icms) {
		this.icms = icms;
	}

	public Double getAliquota() {
		return aliquota;
	}

	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}

	public Double getVlrMercadoria() {
		return vlrMercadoria;
	}

	public void setVlrMercadoria(Double vlrMercadoria) {
		this.vlrMercadoria = vlrMercadoria;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Cidade getCidadeColeta() {
		return cidadeColeta;
	}

	public void setCidadeColeta(Cidade cidadeColeta) {
		this.cidadeColeta = cidadeColeta;
	}

	public Cidade getCidadeEntrega() {
		return cidadeEntrega;
	}

	public void setCidadeEntrega(Cidade cidadeEntrega) {
		this.cidadeEntrega = cidadeEntrega;
	}

	public Pessoa getPessoaRemetente() {
		return pessoaRemetente;
	}

	public void setPessoaRemetente(Pessoa pessoaRemetente) {
		this.pessoaRemetente = pessoaRemetente;
	}

	public Pessoa getPessoaDestinatario() {
		return pessoaDestinatario;
	}

	public void setPessoaDestinatario(Pessoa pessoaDestinatario) {
		this.pessoaDestinatario = pessoaDestinatario;
	}

	public Pessoa getPessoaTomador() {
		return pessoaTomador;
	}

	public void setPessoaTomador(Pessoa pessoaTomador) {
		this.pessoaTomador = pessoaTomador;
	}

	public List<NFe> getNFes() {
		return NFes;
	}

	public void setNFes(List<NFe> nFes) {
		NFes = nFes;
	}	

	public TipoCST getCst() {
		return cst;
	}

	public void setCst(TipoCST cst) {
		this.cst = cst;
	}	

	public TipoCTe getTpCte() {
		return tpCte;
	}

	public void setTpCte(TipoCTe tpCte) {
		this.tpCte = tpCte;
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
		CTe other = (CTe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CTe [id=" + id + ", serie=" + serie + ", numero=" + numero + ", dtEmissao=" + dtEmissao + ", dtCriacao="
				+ dtCriacao + ", dtAlteracao=" + dtAlteracao + ", tpEmissao=" + tpEmissao + ", tpAmbiente=" + tpAmbiente
				+ ", tpServico=" + tpServico + ", tpCte=" + tpCte + ", freteTotal=" + freteTotal + ", baseCalculo="
				+ baseCalculo + ", icms=" + icms + ", aliquota=" + aliquota + ", vlrMercadoria=" + vlrMercadoria
				+ ", produto=" + produto + ", chave=" + chave + ", situacao=" + situacao + ", empresa=" + empresa
				+ ", cidadeColeta=" + cidadeColeta + ", cidadeEntrega=" + cidadeEntrega + ", pessoaRemetente="
				+ pessoaRemetente + ", pessoaDestinatario=" + pessoaDestinatario + ", pessoaTomador=" + pessoaTomador
				+ ", NFes=" + NFes + ", cfop=" + cfop + ", cst=" + cst + "]";
	}
	
}
