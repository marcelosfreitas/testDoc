package br.com.docrotas.docrotasweb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.log4j.Logger;

import br.com.docrotas.docrotasweb.service.cte.CTeService;

public class DocumentoEletronicoUtils {
	
	private static final Logger log = Logger.getLogger(DocumentoEletronicoUtils.class);
	
	private static SimpleDateFormat yyyyMMddTHHmmssXXX = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");	
	private static SimpleDateFormat yyyyMMddTHHmmss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public static String formatDouble(Double valor, int qtdDecimais){
		return String.format(Locale.US,"%1$."+ String.valueOf(qtdDecimais) +"f", valor);
	}
	
	public static String formatDateComFusoHorario(Date data){
		return yyyyMMddTHHmmssXXX.format(data);
	}
	
	public static Date getDate(String strData) {
		Date data = null;

		try {
			data = yyyyMMddTHHmmss.parse(strData);
		} catch (ParseException e) {
			log.warn("Não foi possível formata a data : '" + strData + "'.\n");
		}

		return data;
	}
}
