package br.com.docrotas.docrotasweb.service.cte;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import br.com.docrotas.docrotasweb.entity.CTe;
import br.com.docrotas.docrotasweb.entity.Empresa;
import br.com.docrotas.docrotasweb.entity.NFe;
import br.com.docrotas.docrotasweb.entity.Pessoa;
import br.com.docrotas.docrotasweb.entity.TipoMedidas;
import br.com.docrotas.docrotasweb.entity.TipoPessoaCTe;
import br.com.docrotas.docrotasweb.utils.DocumentoEletronicoUtils;

public class GeradorXmlCte {
	
	public Document getDocumentXML(CTe cte) throws Exception{
		Document documentCte = null;
		
		if(cte == null) {
			throw new Exception("Cte não informado.");
		}
		
		Element infCte = new Element("infCte");
		infCte.addContent(getElementIde(cte));
		infCte.addContent(getElementCompl(cte));
		infCte.addContent(getElementEmit(cte.getEmpresa()));
		infCte.addContent(getElementPessoa(TipoPessoaCTe.REMETENTE, cte.getPessoaRemetente()));
		infCte.addContent(getElementPessoa(TipoPessoaCTe.DESTINATARIO, cte.getPessoaDestinatario()));
		infCte.addContent(getElementVprest(cte));
		infCte.addContent(getElementImp(cte));
		infCte.addContent(getElementInfCteNorm(cte));
		
		documentCte = new Document();
		documentCte.setRootElement(infCte);
		
		return documentCte;
	}

	private Element getElementIde(CTe cte) {
		Element elementIde = new Element("ide");
		
		Element elementCuf = new Element("cUF");
		elementCuf.addContent(String.valueOf(cte.getEmpresa().getCidade().getUf().getCodIBGE()));
		elementIde.addContent(elementCuf);
		
		Element elementCct = new Element("cCT");
		elementCct.addContent(StringUtils.leftPad(String.valueOf(cte.getId()),8,"0"));
		elementIde.addContent(elementCct);
		
		/*Element elementCfop = new Element("CFOP");
		elementCfop.addContent(String.valueOf(cte.getCfop().getCodigo()));
		elementIde.addContent(elementCfop);
		
		Element elementNatOp = new Element("natOp");
		elementNatOp.addContent(String.valueOf(cte.getCfop().getDescricao()));
		elementIde.addContent(elementNatOp);*/
		
		Element elementMod = new Element("mod");
		elementMod.addContent("57");
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
		
		//Emissão 0-Normal (1-Complemento/2-Anulação/3-Substituto)
		Element elementTpCte = new Element("tpCTe");
		elementTpCte.addContent(cte.getTpCte().getCodigo());
		elementIde.addContent(elementTpCte);
		
		//Emitido por aplicativo do contribuinte 
		Element elementProcEmi = new Element("procEmi");
		elementProcEmi.addContent("0");
		elementIde.addContent(elementProcEmi);
		
		//Versão da aplicação
		Element elementVerProc = new Element("verProc");
		elementVerProc.addContent("1.00");
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
		
		//Rodoviário
		Element elementModal = new Element("modal");
		elementModal.addContent("01");
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
		
		//--Verificar como resolver multiplos enderecos -- por enquanto "principal"
		if(pessoa.getEnderecos().size() > 0){
			Element elementXlgr = new Element("xLgr");
			elementXlgr.addContent(pessoa.getEnderecos().get(0).getLogradouro());
			elementEnder.addContent(elementXlgr);
			
			Element elementNro = new Element("nro");
			elementNro.addContent(String.valueOf(pessoa.getEnderecos().get(0).getNro()));
			elementEnder.addContent(elementNro);
			
			if(pessoa.getEnderecos().get(0).getComplemento() != null){
				Element elementXcpl = new Element("xCpl");
				elementXcpl.addContent(pessoa.getEnderecos().get(0).getComplemento());
				elementEnder.addContent(elementXcpl);
			}
			
			Element elementXbairro = new Element("xBairro");
			elementXbairro.addContent(pessoa.getEnderecos().get(0).getBairro());
			elementEnder.addContent(elementXbairro);
			
			Element elementCmun = new Element("cMun");
			elementCmun.addContent(String.valueOf(pessoa.getEnderecos().get(0).getCidade().getCodIBGE()));
			elementEnder.addContent(elementCmun);
			
			Element elementXmun = new Element("xMun");
			elementXmun.addContent(pessoa.getEnderecos().get(0).getCidade().getNome());
			elementEnder.addContent(elementXmun);
			
			Element elementCep = new Element("CEP");
			elementCep.addContent(pessoa.getEnderecos().get(0).getCep());
			elementEnder.addContent(elementCep);
			
			Element elementUf = new Element("UF");
			elementUf.addContent(pessoa.getEnderecos().get(0).getCidade().getUf().getSigla());
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
		
		Element elementInfQ = new Element("infQ");
		
		Map<TipoMedidas, Double> totalMedidasCTe = new HashMap<TipoMedidas, Double>();
		
		/*for (NFe nfe : cte.getNFes()) {
			for (TipoMedidas tipo : nfe.totalMedidas().keySet()) {
				Double valorNFe = nfe.totalMedidas().get(tipo);

				Double valorCTe = totalMedidasCTe.get(tipo);
				
				if (valorCTe == null) {
					valorCTe = Double.valueOf(0);
				}

				valorCTe = valorCTe + valorNFe;

				totalMedidasCTe.put(tipo, valorCTe);
			}
		}*/
		
		for (TipoMedidas tipo : totalMedidasCTe.keySet()) {
			System.out.println("Tipo: " + tipo.toString() + " - " + totalMedidasCTe.get(tipo).toString());
		}
		
		return elementInfCteNorm;
	}	
	
}
