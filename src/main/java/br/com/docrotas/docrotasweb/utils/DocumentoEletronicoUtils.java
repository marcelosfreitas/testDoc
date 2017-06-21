package br.com.docrotas.docrotasweb.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DocumentoEletronicoUtils {
	
	private static SimpleDateFormat yyyyMMddTHHmmss = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	
	public static String formatDouble(Double valor, int qtdDecimais){
		return String.format(Locale.US,"%1$."+ String.valueOf(qtdDecimais) +"f", valor);
	}
	
	public static String formatDate(Date data){
		return yyyyMMddTHHmmss.format(data);
	}
}
