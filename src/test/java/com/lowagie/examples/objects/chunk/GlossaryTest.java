/*
 * $Id: Glossary.java 3373 2008-05-12 16:21:24Z xlv $
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package com.lowagie.examples.objects.chunk;

import java.util.Iterator;
import java.util.TreeMap;

import org.junit.Test;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTestBase;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Demonstrates the use of the Generic PageEvent.
 * 
 * @author blowagie
 */

public class GlossaryTest extends PdfPageEventHelper {

	/** keeps a glossary of words and the pages they appear on */
	public TreeMap glossary = new TreeMap();

	/**
	 * All the text that is passed to this event, gets registered in the
	 * glossary.
	 * 
	 * @see com.lowagie.text.pdf.PdfPageEventHelper#onGenericTag(com.lowagie.text.pdf.PdfWriter,
	 *      com.lowagie.text.Document, com.lowagie.text.Rectangle,
	 *      java.lang.String)
	 */
	public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text) {
		glossary.put(text, Integer.valueOf(writer.getPageNumber()));
	}

	/**
	 * Generic page event.
	 * 
	 */
	@Test
	public void main() throws Exception {

		// step 1: creation of a document-object
		Document document = new Document();
		// step 2:
		// we create a writer that listens to the document
		PdfWriter writer = PdfWriter.getInstance(document, PdfTestBase.getOutputStream("Glossary.pdf"));
		GlossaryTest generic = new GlossaryTest();
		writer.setPageEvent(generic);

		// step 3: we open the document
		document.open();
		// step 4:
		String[] f = new String[14];
		f[0] = "Courier";
		f[1] = "Courier Bold";
		f[2] = "Courier Italic";
		f[3] = "Courier Bold Italic";
		f[4] = "Helvetica";
		f[5] = "Helvetica bold";
		f[6] = "Helvetica italic";
		f[7] = "Helvetica bold italic";
		f[8] = "Times New Roman";
		f[9] = "Times New Roman bold";
		f[10] = "Times New Roman italic";
		f[11] = "Times New Roman bold italic";
		f[12] = "Symbol";
		f[13] = "Zapfdingbats";
		Font[] fonts = new Font[14];
		fonts[0] = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL);
		fonts[1] = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD);
		fonts[2] = FontFactory.getFont(FontFactory.COURIER, 12, Font.ITALIC);
		fonts[3] = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD | Font.ITALIC);
		fonts[4] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
		fonts[5] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
		fonts[6] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC);
		fonts[7] = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD | Font.ITALIC);
		fonts[8] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL);
		fonts[9] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
		fonts[10] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.ITALIC);
		fonts[11] = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD | Font.ITALIC);
		fonts[12] = FontFactory.getFont(FontFactory.SYMBOL, 12, Font.NORMAL);
		fonts[13] = FontFactory.getFont(FontFactory.ZAPFDINGBATS, 12, Font.NORMAL);
		for (int i = 0; i < 14; i++) {
			Chunk chunk = new Chunk("This is font ", fonts[i]);
			Paragraph p = new Paragraph(chunk);
			p.add(new Phrase(new Chunk(f[i], fonts[i]).setGenericTag(f[i])));
			document.add(p);
			if (i % 4 == 3) {
				document.newPage();
			}
		}

		// we add the glossary
		document.newPage();
		for (Iterator i = generic.glossary.keySet().iterator(); i.hasNext();) {
			String key = (String) i.next();
			int page = ((Integer) generic.glossary.get(key)).intValue();
			Paragraph g = new Paragraph(key);
			g.add(" : page ");
			g.add(String.valueOf(page));
			document.add(g);
		}

		// step 5: we close the document
		document.close();
	}
}