package br.com.docrotas.docrotasweb.service.cte;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Document;
import org.jdom2.Element;

import br.com.docrotas.docrotasweb.entity.CTe;

public class GeradorXmlCte {
	
	private static SimpleDateFormat yyyyMMddTHHmmss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public Document getDocumentXML(CTe cte) throws Exception{
		Document documentCte = null;
		
		if(cte == null) {
			throw new Exception("Cte não informado.");
		}
		
		Element infCte = new Element("infCte");
		infCte.addContent(getElementIde(cte));
		infCte.addContent(getElementCompl(cte));
		
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
		
		Element elementCfop = new Element("CFOP");
		elementCfop.addContent(String.valueOf(cte.getCfop().getCodigo()));
		elementIde.addContent(elementCfop);
		
		Element elementNatOp = new Element("natOp");
		elementNatOp.addContent(String.valueOf(cte.getCfop().getDescricao()));
		elementIde.addContent(elementNatOp);
		
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
		elementDhEmi.addContent(yyyyMMddTHHmmss.format(cte.getDtEmissao()));
		elementIde.addContent(elementDhEmi);
		
		Element elementTpImp = new Element("tpImp");
		elementTpImp.addContent(cte.getEmpresa().getTipoImpressao().getCodigo());
		elementIde.addContent(elementTpImp);
		
		//por enquanto "1-normal" (4-EPEC SVC/5-Contingencia FSDA/7-SVC_RS/8-SVC_SP)
		Element elementTpEmis = new Element("tpEmis");
		elementTpEmis.addContent("1");
		elementIde.addContent(elementTpEmis);
		
		Element elementCDV = new Element("cDV");
		elementCDV.addContent(cte.getChave().substring(43, 1));
		elementIde.addContent(elementCDV);
		
		//--Ambiente 2-homologação (1-produção)
		Element elementTpAmb = new Element("tpAmb");
		//elementTpAmb.addContent(cte.getEmpresa().getTipoAmbienteCte().getCodigo());
		elementTpAmb.addContent("2");
		elementIde.addContent(elementTpAmb);
		
		//Emissão 0-Normal (1-Complemento/2-Anulação/3-Substituto)
		Element elementTpCte = new Element("tpCTe");
		elementTpCte.addContent("0");
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
		elementTpServ.addContent("0");
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
		
		return null;
	}	
}
