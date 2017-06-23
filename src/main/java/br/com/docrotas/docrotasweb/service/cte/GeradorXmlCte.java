package br.com.docrotas.docrotasweb.service.cte;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.entity.Empresa;
import br.com.docrotas.docrotasweb.entity.NFe;
import br.com.docrotas.docrotasweb.entity.Pessoa;
import br.com.docrotas.docrotasweb.entity.TipoEndereco;
import br.com.docrotas.docrotasweb.entity.TipoMedidas;
import br.com.docrotas.docrotasweb.entity.TipoPessoaCTe;
import br.com.docrotas.docrotasweb.utils.DocumentoEletronicoUtils;

public class GeradorXmlCte {
	
	private static final Logger log = Logger.getLogger(GeradorXmlCte.class);
	
	private static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
	private static final String MODELO_DOCUMENTO_CTE = "57";
	private static final String VERSAO_APLICACAO = "1.00";
	private static final String MODAL_RODOVIARIO = "01";
	private static final String VERSAO = "2.00";
	private static final Namespace NAMESPACE = Namespace.getNamespace("http://www.portalfiscal.inf.br/cte");
	
	public Document getDocumentXML(CTe cte) throws Exception{
		Document documentCte = null;
		
		if (cte == null) {
			throw new Exception("Cte não informado.");
		}
		
		if (!cte.temChaveAcesso()) {
			atualizarChaveAcesso(cte);
		}
		Element enviCTe = new Element("enviCTe");
		enviCTe.setAttribute("versao", VERSAO);
		Element idLote = new Element("idLote");
		idLote.addContent("11111");
		Element elementCTe = new Element("CTe");
		elementCTe.addContent(getElementInfCTe(cte));
//		elementCTe.setNamespace(NAMESPACE);
		
		enviCTe.addContent(idLote);
		enviCTe.addContent(elementCTe);

		documentCte = new Document();
		documentCte.setRootElement(enviCTe);

		log.info("XML CT-e sem assinatura: " + new XMLOutputter(Format.getPrettyFormat()).outputString(documentCte));

		return documentCte;
	}

	private void atualizarChaveAcesso(CTe cte) {
		cte.setChave(gerarChaveAcesso(cte));
	}

	private String gerarChaveAcesso(CTe cte) {
		StringBuilder stbChave = new StringBuilder();
		stbChave.append(cte.getEmpresa().getCidade().getUf().getCodIBGE().toString());
		stbChave.append(YYYY.format(cte.getDtEmissao()));
		stbChave.append(cte.getEmpresa().getCnpj());
		stbChave.append(MODELO_DOCUMENTO_CTE);
		stbChave.append(StringUtils.leftPad(cte.getSerie().toString(), 3, '0'));
		stbChave.append(StringUtils.leftPad(cte.getNumero().toString(), 9, '0'));
		stbChave.append(StringUtils.leftPad(cte.getTpEmissao().getCodigo(), 2, '0'));
		stbChave.append(StringUtils.leftPad(getNumeroAleatorio(), 8, '0'));
		stbChave.append(String.valueOf(getDigitoVerificador(stbChave.toString())));
		
		return stbChave.toString();
	}

	private String getNumeroAleatorio() {
		Double numero = Math.random() * 10000000;
		long numeroAleatorio = numero.longValue();
		return String.valueOf(numeroAleatorio);
	}

	private int getDigitoVerificador(String chave) {
		int total = 0;
		int peso = 2;

		for (int i= 0; i < chave.length(); i++){
			total += (chave.charAt((chave.length() - 1) - i) - '0') * peso;
			peso++;

			if (peso == 10) {
				peso = 2;
			}
		}
		int resto = total % 11;

		return (resto == 0 || resto == 1) ? 0 : (11 - resto);
	}
	
	private Element getElementInfCTe(CTe cte) {
		String identificacao = "CTe" + cte.getChave();
		
		Element infCTe = new Element("infCTe");

		infCTe.setAttribute("Id", identificacao);
		infCTe.setAttribute("versao", VERSAO);

		infCTe.addContent(getElementIde(cte));
		infCTe.addContent(getElementCompl(cte));
		infCTe.addContent(getElementEmit(cte.getEmpresa()));
		infCTe.addContent(getElementPessoa(TipoPessoaCTe.REMETENTE, cte.getPessoaRemetente()));
		infCTe.addContent(getElementPessoa(TipoPessoaCTe.DESTINATARIO, cte.getPessoaDestinatario()));
		infCTe.addContent(getElementVprest(cte));
		infCTe.addContent(getElementImp(cte));
		infCTe.addContent(getElementInfCteNorm(cte));

		return infCTe;
	}

	private Element getElementIde(CTe cte) {
		Element elementIde = new Element("ide");
		
		Element elementCuf = new Element("cUF");
		elementCuf.addContent(String.valueOf(cte.getEmpresa().getCidade().getUf().getCodIBGE()));
		elementIde.addContent(elementCuf);
		
		Element elementCct = new Element("cCT");
		elementCct.addContent(StringUtils.leftPad(String.valueOf(cte.getId()),8,"0"));
		elementIde.addContent(elementCct);
		
		Element elementCfop = new Element("CFOP");
		elementCfop.addContent(String.valueOf(cte.getCfop().getCodigo()));
		elementIde.addContent(elementCfop);
		
		Element elementNatOp = new Element("natOp");
		elementNatOp.addContent(String.valueOf(cte.getCfop().getDescricao()));
		elementIde.addContent(elementNatOp);
		
		Element elementMod = new Element("mod");
		elementMod.addContent(MODELO_DOCUMENTO_CTE);
		elementIde.addContent(elementMod);

		Element elementSerie = new Element("serie");
		elementSerie.addContent(String.valueOf(cte.getSerie()));
		elementIde.addContent(elementSerie);
		
		Element elementNCT = new Element("nCT");
		elementNCT.addContent(String.valueOf(cte.getNumero()));
		elementIde.addContent(elementNCT);

		Element elementDhEmi = new Element("dhEmi");
		elementDhEmi.addContent(DocumentoEletronicoUtils.formatDate(cte.getDtEmissao()));
		elementIde.addContent(elementDhEmi);

		Element elementTpImp = new Element("tpImp");
		elementTpImp.addContent(cte.getEmpresa().getTipoImpressao().getCodigo());
		elementIde.addContent(elementTpImp);

		//por enquanto "1-normal" (4-EPEC SVC/5-Contingencia FSDA/7-SVC_RS/8-SVC_SP)
		Element elementTpEmis = new Element("tpEmis");
		elementTpEmis.addContent(cte.getTpEmissao().getCodigo());
		elementIde.addContent(elementTpEmis);
		
		Element elementCDV = new Element("cDV");
		elementCDV.addContent(cte.getChave().substring(43,44));
		elementIde.addContent(elementCDV);

		//--Ambiente 2-homologação (1-produção)
		Element elementTpAmb = new Element("tpAmb");
		elementTpAmb.addContent(cte.getTpAmbiente().getCodigo());
		elementIde.addContent(elementTpAmb);

		Element elementTpCte = new Element("tpCTe");
		elementTpCte.addContent(cte.getTpCte().getCodigo());
		elementIde.addContent(elementTpCte);

		//Emitido por aplicativo do contribuinte 
		Element elementProcEmi = new Element("procEmi");
		elementProcEmi.addContent("0");
		elementIde.addContent(elementProcEmi);

		Element elementVerProc = new Element("verProc");
		elementVerProc.addContent(VERSAO_APLICACAO);
		elementIde.addContent(elementVerProc);

		Element elementCMunEnv = new Element("cMunEnv");
		elementCMunEnv.addContent(String.valueOf(cte.getEmpresa().getCidade().getCodIBGE()));
		elementIde.addContent(elementCMunEnv);

		Element elementXMunEnv = new Element("xMunEnv");
		elementXMunEnv.addContent(cte.getEmpresa().getCidade().getNome());
		elementIde.addContent(elementXMunEnv);

		Element elementUfEnv = new Element("UFEnv");
		elementUfEnv.addContent(cte.getEmpresa().getCidade().getUf().getSigla());
		elementIde.addContent(elementUfEnv);

		Element elementModal = new Element("modal");
		elementModal.addContent(MODAL_RODOVIARIO);
		elementIde.addContent(elementModal);

		Element elementTpServ = new Element("tpServ");
		elementTpServ.addContent(cte.getTpServico().getCodigo());
		elementIde.addContent(elementTpServ);

		Element elementCMunIni = new Element("cMunIni");
		elementCMunIni.addContent(String.valueOf(cte.getCidadeColeta().getCodIBGE()));
		elementIde.addContent(elementCMunIni);

		Element elementXMunIni = new Element("xMunIni");
		elementXMunIni.addContent(cte.getCidadeColeta().getNome());
		elementIde.addContent(elementXMunIni);

		Element elementUfIni = new Element("UFIni");
		elementUfIni.addContent(cte.getCidadeColeta().getUf().getSigla());
		elementIde.addContent(elementUfIni);

		Element elementCMunFim = new Element("cMunFim");
		elementCMunFim.addContent(String.valueOf(cte.getCidadeEntrega().getCodIBGE()));
		elementIde.addContent(elementCMunFim);
		
		Element elementXMunFim = new Element("xMunFim");
		elementXMunFim.addContent(cte.getCidadeEntrega().getNome());
		elementIde.addContent(elementXMunFim);
		
		Element elementUfFim = new Element("UFFim");
		elementUfFim.addContent(cte.getCidadeEntrega().getUf().getSigla());
		elementIde.addContent(elementUfFim);
		
		//padrão 1... cliente não retira mercadoria
		Element elementRetira = new Element("retira");
		elementRetira.addContent("1");
		elementIde.addContent(elementRetira);
		
		//--montar rotina de verificação
		Element elementIndIEToma = new Element("indIEToma");
		elementIndIEToma.addContent("1");
		elementIde.addContent(elementIndIEToma);
		
		//--montar rotina de verificação if toma3 = remetente/expedidor/recebedor/destinatario
		Element elementToma3 = new Element("toma3");
		Element elementToma = new Element("toma");
		//--verificar se tomador é remetente/expedidor/recebedor/destinatario
		elementToma.addContent("0");
		elementToma3.addContent(elementToma);
		elementIde.addContent(elementToma3);
		
		//--else toma4(consignatário)
		//--
		//--
		//--
		
		//--montar rotina de verificação de entrada em contingêcia
		//Element elementDhCont
		//Element elementXJust
		
		return elementIde;
	}
	
	private Element getElementCompl(CTe cte) {
		//Entrega
		Element elementCompl = new Element("compl");
		
		Element elementSemData = new Element("semData");
		Element elementTpPer = new Element("tpPer");
		//Sem data definida
		elementTpPer.addContent("0"); 
		elementSemData.addContent(elementTpPer);
		
		elementCompl.addContent(elementSemData);
		
		Element elementSemHora = new Element("semHora");
		Element elementTpHor = new Element("tpHor");
		//Sem horário definido
		elementTpHor.addContent("0");
		elementSemHora.addContent(elementTpHor);
		
		elementCompl.addContent(elementSemHora);
		
		return elementCompl;
	}
	
	private Element getElementEmit(Empresa empresa) {
		Element elementEmit = new Element("emit");
		
		Element elementCnpj = new Element("CNPJ");
		elementCnpj.addContent(empresa.getCnpj());
		elementEmit.addContent(elementCnpj);
		
		Element elementIe = new Element("IE");
		elementIe.addContent(empresa.getIe());
		elementEmit.addContent(elementIe);
		
		Element elementXnome = new Element("xNome");
		elementXnome.addContent(empresa.getRazao());
		elementEmit.addContent(elementXnome);
		
		if(empresa.getFantasia() != null){
			Element elementXfant = new Element("xFant");
			elementXfant.addContent(empresa.getFantasia());
			elementEmit.addContent(elementXfant);
		}
		
		Element elementEnderEmit = new Element("enderEmit");
		
		Element elementXlgr = new Element("xLgr");
		elementXlgr.addContent(empresa.getLogradouro());
		elementEnderEmit.addContent(elementXlgr);
		
		Element elementNro = new Element("nro");
		elementNro.addContent(String.valueOf(empresa.getNro()));
		elementEnderEmit.addContent(elementNro);
		
		if(empresa.getComplemento() != null){
			Element elementXcpl = new Element("xCpl");
			elementXcpl.addContent(empresa.getComplemento());
			elementEnderEmit.addContent(elementXcpl);
		}
		
		Element elementXbairro = new Element("xBairro");
		elementXbairro.addContent(empresa.getBairro());
		elementEnderEmit.addContent(elementXbairro);
		
		Element elementCmun = new Element("cMun");
		elementCmun.addContent(String.valueOf(empresa.getCidade().getCodIBGE()));
		elementEnderEmit.addContent(elementCmun);
		
		Element elementXmun = new Element("xMun");
		elementXmun.addContent(empresa.getCidade().getNome());
		elementEnderEmit.addContent(elementXmun);
		
		Element elementCep = new Element("CEP");
		elementCep.addContent(empresa.getCep());
		elementEnderEmit.addContent(elementCep);
		
		Element elementUf = new Element("UF");
		elementUf.addContent(empresa.getCidade().getUf().getSigla());
		elementEnderEmit.addContent(elementUf);
		
		elementEmit.addContent(elementEnderEmit);
		
		return elementEmit;
	}	
	
	private Element getElementPessoa(TipoPessoaCTe tipoPessoaCTe, Pessoa pessoa) {
		
		Element elementPessoa;
	
		elementPessoa = new Element(tipoPessoaCTe.getTag());
		
		if(pessoa.getCpfCnpj().length() == 14){
			Element elementCnpj = new Element("CNPJ");
			elementCnpj.addContent(pessoa.getCpfCnpj());
			elementPessoa.addContent(elementCnpj);
			
			Element elementIe = new Element("IE");
			elementIe.addContent(pessoa.getIe());
			elementPessoa.addContent(elementIe);				
		}else{
			Element elementCpf = new Element("CPF");
			elementCpf.addContent(pessoa.getCpfCnpj());
			elementPessoa.addContent(elementCpf);			
		}
		
		Element elementXnome = new Element("xNome");
		elementXnome.addContent(pessoa.getRazao());
		elementPessoa.addContent(elementXnome);
		
		if(pessoa.getFantasia() != null){
			Element elementXfant = new Element("xFant");
			elementXfant.addContent(pessoa.getFantasia());
			elementPessoa.addContent(elementXfant);
		}
		
		Element elementEnder;
		if(tipoPessoaCTe.toString().equals("rem")){
			elementEnder = new Element("enderReme");
		}else{
			elementEnder = new Element("enderDest");
		}
		
		if(pessoa.getEnderecoPrincipal().getTipoEndereco().equals(TipoEndereco.PRINCIPAL)){
			Element elementXlgr = new Element("xLgr");
			elementXlgr.addContent(pessoa.getEnderecoPrincipal().getLogradouro());
			elementEnder.addContent(elementXlgr);
			
			Element elementNro = new Element("nro");
			elementNro.addContent(String.valueOf(pessoa.getEnderecoPrincipal().getNro()));
			elementEnder.addContent(elementNro);
			
			if(pessoa.getEnderecoPrincipal().getComplemento() != null){
				Element elementXcpl = new Element("xCpl");
				elementXcpl.addContent(pessoa.getEnderecoPrincipal().getComplemento());
				elementEnder.addContent(elementXcpl);
			}
			
			Element elementXbairro = new Element("xBairro");
			elementXbairro.addContent(pessoa.getEnderecoPrincipal().getBairro());
			elementEnder.addContent(elementXbairro);
			
			Element elementCmun = new Element("cMun");
			elementCmun.addContent(String.valueOf(pessoa.getEnderecoPrincipal().getCidade().getCodIBGE()));
			elementEnder.addContent(elementCmun);
			
			Element elementXmun = new Element("xMun");
			elementXmun.addContent(pessoa.getEnderecoPrincipal().getCidade().getNome());
			elementEnder.addContent(elementXmun);
			
			Element elementCep = new Element("CEP");
			elementCep.addContent(pessoa.getEnderecoPrincipal().getCep());
			elementEnder.addContent(elementCep);
			
			Element elementUf = new Element("UF");
			elementUf.addContent(pessoa.getEnderecoPrincipal().getCidade().getUf().getSigla());
			elementEnder.addContent(elementUf);
			
			//tabela código país BACEN
			Element elementCpais = new Element("cPais");
			elementCpais.addContent("1058");
			elementEnder.addContent(elementCpais);
			
			Element elementXpais = new Element("xPais");
			elementXpais.addContent("BRASIL");
			elementEnder.addContent(elementXpais);
			
			//Email... 
			
			elementPessoa.addContent(elementEnder);
		}
		return elementPessoa;
	}
	
	private Element getElementVprest(CTe cte) {
		Element elementVprest = new Element("vPrest");
		
		Element elementVtprest = new Element("vTPrest");
		elementVtprest.addContent(DocumentoEletronicoUtils.formatDouble(cte.getFreteTotal(), 2));
		elementVprest.addContent(elementVtprest);
		
		Element elementVrec = new Element("vRec");;
		elementVrec.addContent(DocumentoEletronicoUtils.formatDouble(cte.getFreteTotal(), 2));
		elementVprest.addContent(elementVrec);
		
		/*--Detalhamento--
 
		Comp
		xNome
		vComp
		
		*/
		
		return elementVprest;
	}
	
	private Element getElementImp(CTe cte) {
		//por enquanto ICMS00
		Element elementImp = new Element("imp");
		
		Element elementIcms = new Element("ICMS");
		
		Element elementIcms00 = new Element("ICMS00");
		
		//conforme CST do CTe
		Element elementCst = new Element("CST");
		elementCst.addContent("00");
		elementIcms00.addContent(elementCst);
		
		Element elementVbc = new Element("vBC");
		elementVbc.addContent(DocumentoEletronicoUtils.formatDouble(cte.getBaseCalculo(),2));
		elementIcms00.addContent(elementVbc);
		
		Element elementPicms = new Element("pICMS");
		elementPicms.addContent(DocumentoEletronicoUtils.formatDouble(cte.getAliquota(), 2));
		elementIcms00.addContent(elementPicms);
		
		Element elementVicms = new Element("vICMS");
		elementVicms.addContent(DocumentoEletronicoUtils.formatDouble(cte.getIcms(), 2));
		elementIcms00.addContent(elementVicms);
		
		elementIcms.addContent(elementIcms00);
		elementImp.addContent(elementIcms);
		
		return elementImp;
	}	
	
	private Element getElementInfCteNorm(CTe cte) {
		Element elementInfCteNorm = new Element("infCTeNorm");
		
		Element elementInfCarga = new Element("infCarga");
		
		Element elementVcarga = new Element("vCarga");
		elementVcarga.addContent(DocumentoEletronicoUtils.formatDouble(cte.getVlrMercadoria(),2));
		elementInfCarga.addContent(elementVcarga);
		
		Element elementProPred = new Element("proPred");
		elementProPred.addContent(cte.getProduto());
		elementInfCarga.addContent(elementProPred);
		
		
		Map<TipoMedidas, Double> totalMedidasCTe = new HashMap<TipoMedidas, Double>();
		
		for (NFe nfe : cte.getNfes()) {
			for (TipoMedidas tipo : nfe.totalMedidas().keySet()) {
				Double valorNFe = nfe.totalMedidas().get(tipo);
				Double valorCTe = totalMedidasCTe.get(tipo);
				
				if (valorCTe == null) {
					valorCTe = Double.valueOf(0);
				}

				valorCTe = valorCTe + valorNFe;
				totalMedidasCTe.put(tipo, valorCTe);
			}
		}
		
		for (TipoMedidas tipo : totalMedidasCTe.keySet()) {
			Element elementInfQ = new Element("infQ");
			
			Element elementCunid = new Element("cUnid");
			elementCunid.addContent(tipo.getCodigo());
			elementInfQ.addContent(elementCunid);
			
			Element elementTpMed = new Element("tpMed");
			elementTpMed.addContent(tipo.toString());
			elementInfQ.addContent(elementTpMed);
			
			Element elementQcarga = new Element("qCarga");
			elementQcarga.addContent(DocumentoEletronicoUtils.formatDouble(totalMedidasCTe.get(tipo).doubleValue(),4));
			elementInfQ.addContent(elementQcarga);
			
			elementInfCarga.addContent(elementInfQ);
			//System.out.println("Tipo: " + tipo.toString() + " - " + totalMedidasCTe.get(tipo).toString());
		}
		elementInfCteNorm.addContent(elementInfCarga);
		
		Element elementInfdoc = new Element("infDoc");
		for (NFe nfe : cte.getNfes()) {
			//-- Verificar outros documentos
			Element elementInfnfe = new Element("infNFe");			
			Element elementChave = new Element("chave");
			elementChave.addContent(nfe.getChave());
			elementInfnfe.addContent(elementChave);
			elementInfdoc.addContent(elementInfnfe);
		}
		elementInfCteNorm.addContent(elementInfdoc);
		
		
		Element elementInfmodal = new Element("infModal");
		Attribute atributeVersao = new Attribute("versaoModal", "3.00");
		elementInfmodal.setAttribute(atributeVersao);
		
		//Modal Rodovíario
		Element elementRodo = new Element("rodo");
		
		Element elementRntrc = new Element("RNTRC");
		elementRntrc.addContent(cte.getEmpresa().getRntrc());
		elementRodo.addContent(elementRntrc);
		
		elementInfmodal.addContent(elementRodo);
		
		elementInfCteNorm.addContent(elementInfmodal);
		
		return elementInfCteNorm;
	}	
	
}
