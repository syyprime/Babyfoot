package com.tse.kamoulox;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.FileOutputStream;

/**
 * 描述：TODO generate pdf document

 */
 

 public class GeneratePDF {

 public static void main(String[] args) {

  //调用第一个方法，向C盘生成一个名字为ITextTest.pdf 的文件
  try {
   writeSimplePdf();
  } 
  catch (Exception e) { e.printStackTrace(); }

  
  //调用第二个方法，向C盘名字为ITextTest.pdf的文件，添加章节。
  try {
   writeCharpter();
  } 
  catch (Exception e) { e.printStackTrace(); }
 
 }
 
 public static void writeSimplePdf() throws Exception {

  Document document = new Document(PageSize.A4, 50, 50, 50, 50);

  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/Users/pc/Desktop/ITextTest.pdf"));

  document.open();

  document.add(new Paragraph("First page of the document."));
  document.add(new Paragraph("Some more text on the  first page with different color and font type.", FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new Color(255, 150, 200))));

  document.close();
 }

 
 public static void writeCharpter() throws Exception {

  Document document = new Document(PageSize.A4, 20, 20, 20, 20);

  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/Users/pc/Desktop/ITextTest.pdf"));

  document.open();
  
  /**
   * les 5 lignes suivantes servant à ajouter des info dans la description de fichier
   * ces descriptions vont afficher dans le propriété du fichier au lieu dans le text
   */
  document.addTitle("synthèse");
  document.addAuthor("kamoulox");
  document.addSubject("le résultat de jeu");
  document.addKeywords("Kamoulox_2016");
  document.addCreator("notre synthèse de jeu");

  
  
  
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("Kamoulox", FontFactory.getFont(FontFactory.defaultEncoding, 25, Font.BOLD, new Color(0, 0, 0))));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("2016_Projet_Info"));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("TÉLÉCOM-SAINT-ÉTIENNE"));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("\n"));
  
  document.add(new Paragraph("Adrien Perrau", FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new Color(0, 0, 0))));
  document.add(new Paragraph("Emile Bardou", FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new Color(0, 0, 0))));
  document.add(new Paragraph("Sixiang Xu", FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new Color(0, 0, 0))));
  document.add(new Paragraph("Yunyun Sun", FontFactory.getFont(FontFactory.defaultEncoding, 10, Font.BOLD, new Color(0, 0, 0))));
  
  Paragraph title1 = new Paragraph("RÉSULTAT", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new Color(0, 0, 0)));

  Chapter chapter1 = new Chapter(title1, 1);
  chapter1.setNumberDepth(0);
  Paragraph title11 = new Paragraph("SCORE", FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
  Section section1 = chapter1.addSection(title11);
  section1.add(new Paragraph("le score final:"));
  section1.add(new Paragraph("les détails de chaque but:etc..."));
  document.add(chapter1);
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("\n"));
  document.add(new Paragraph("etc."));
  document.add(new Paragraph("si vous voulez ajouter quelque chose"));

  document.close();
 }
 
} 
