package br.com.javaarquiteto.domain.services.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent  extends PdfPageEventHelper{
	
	  public void onStartPage(PdfWriter writer, Document document) {
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Cliente API"), 34, 800, 0);
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Curso JAVA ARQUITETO"), 520, 800, 0);
	    }

	    public void onEndPage(PdfWriter writer, Document document) {
	    	String hoje = getDataAtual();
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Relatorio de Cliente, gerado em: "+hoje), 114, 30, 0);
	        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
	    }
	    
	    private String getDataAtual() {
	    	LocalDate localDate = LocalDate.now();
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    	return localDate.format(formatter);
	    }

}
