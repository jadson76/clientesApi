package br.com.javaarquiteto.domain.services.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import br.com.javaarquiteto.domain.dtos.ClienteDto;
import br.com.javaarquiteto.domain.dtos.EnderecoDto;
import br.com.javaarquiteto.domain.exceptions.ClienteException;

public class PdfUtil {
	
	private static final Logger LOGGER = LogManager.getLogger(PdfUtil.class);
	
	 @Value("${pdf.error}")
	 private static String pdfError;
	
	public static ByteArrayInputStream clientePDFRelatorio(ClienteDto cliente) throws ClienteException, ParseException {
				
		Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        try {
        	
        	PdfWriter writer = PdfWriter.getInstance(document, out);
        	HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        	writer.setPageEvent(event);
        	
            document.open();
        	
			// Add Text to PDF file ->
			Font font16 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
			Font font14 = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph( "Relatorio de Cliente", font16);
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);
			
			Paragraph paraNomeCliente = new Paragraph("Nome: "+cliente.getNome(), font14);
			paraNomeCliente.setAlignment(Element.ALIGN_LEFT);
            document.add(paraNomeCliente);
            document.add(Chunk.NEWLINE);
            
            Paragraph paraEmailCliente = new Paragraph("Email: "+cliente.getEmail(), font14);
            paraEmailCliente.setAlignment(Element.ALIGN_LEFT);
            document.add(paraEmailCliente);
            document.add(Chunk.NEWLINE);
            
            Paragraph paraCpfCliente = new Paragraph("CPF: "+cliente.getCpf(), font14);
            paraCpfCliente.setAlignment(Element.ALIGN_LEFT);
            document.add(paraCpfCliente);
            document.add(Chunk.NEWLINE);
            
            Paragraph paraDtNascCliente = new Paragraph("Data de Nascimento: "+FormatUtil.getDataFormatada(cliente.getDataNascimento()), font14);
            paraDtNascCliente.setAlignment(Element.ALIGN_LEFT);
            document.add(paraDtNascCliente);
            document.add(Chunk.NEWLINE);  
            
            LineSeparator ls = new LineSeparator();
            ls.setOffset(-8);
            document.add(new Chunk(ls));

			PdfPTable tableEndereco = new PdfPTable(6);
			tableEndereco.setWidthPercentage(100);
			float[] columnWidths = new float[]{30f, 6f, 25f, 18f, 5f, 12f};
			tableEndereco.setWidths(columnWidths);

			Stream.of("Logradouro", "#", "Bairro", "Cidade", "UF", "CEP").forEach(headerTitle -> {
				PdfPCell headerEndereco = new PdfPCell();
				Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);				
				headerEndereco.setBackgroundColor(BaseColor.LIGHT_GRAY);
				headerEndereco.setHorizontalAlignment(Element.ALIGN_CENTER);
				headerEndereco.setBorderWidth(2);
				headerEndereco.setPhrase(new Phrase(headerTitle, headFont));
				tableEndereco.addCell(headerEndereco);
			});

			for (EnderecoDto endereco : cliente.getEnderecos()) {

				PdfPCell logradouroCell = new PdfPCell(new Phrase(endereco.getLogradouro()));
			
				logradouroCell.setPaddingLeft(4);
				logradouroCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				logradouroCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				tableEndereco.addCell(logradouroCell);

				PdfPCell numeroCell = new PdfPCell(new Phrase(String.valueOf(endereco.getNumero())));
				numeroCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				numeroCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				numeroCell.setPaddingRight(4);
				tableEndereco.addCell(numeroCell);

				PdfPCell bairroCell = new PdfPCell(new Phrase(String.valueOf(endereco.getBairro())));
				bairroCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				bairroCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				bairroCell.setPaddingRight(4);
				tableEndereco.addCell(bairroCell);

				PdfPCell cidadeCell = new PdfPCell(new Phrase(String.valueOf(endereco.getCidade())));
				cidadeCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cidadeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cidadeCell.setPaddingRight(4);
				tableEndereco.addCell(cidadeCell);

				PdfPCell ufCell = new PdfPCell(new Phrase(String.valueOf(endereco.getUf())));
				ufCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				ufCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				ufCell.setPaddingRight(4);
				tableEndereco.addCell(ufCell);

				PdfPCell cepCell = new PdfPCell(new Phrase(String.valueOf(endereco.getCep())));
				cepCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cepCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cepCell.setPaddingRight(4);
				tableEndereco.addCell(cepCell);
			}

			document.add(tableEndereco);
			document.close();
        }catch(DocumentException e) {
        	LOGGER.error(pdfError, e.getMessage());
        	throw new ClienteException(pdfError);
        }
        
		return new ByteArrayInputStream(out.toByteArray());
	}

}
