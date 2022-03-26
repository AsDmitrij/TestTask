package main.java.com.company.handler;

import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBElement;
import main.java.com.company.models.TableCellContent;
import org.docx4j.jaxb.Context;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.CTHeight;
import org.docx4j.wml.CTLanguage;
import org.docx4j.wml.CTTblLayoutType;
import org.docx4j.wml.CTTblLook;
import org.docx4j.wml.CTTblPrBase;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.STBorder;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;

public class TableHandler {
    private static final ObjectFactory factory = Context.getWmlObjectFactory();
    private static final String TABLE_WIDTH_TYPE = "dxa";
    private static final String BASE_TABLE_PROPERTY_STYLE = "a3";
    private static final int DOCUMENT_TABLE_BASE_SPACING_BEFORE = 0;
    private static final int DOCUMENT_TABLE_BASE_SPACING_AFTER = 240;

    public static void createTableRow(Tbl table, List<TableCellContent> cellContents, List<Integer> widths, int rowHeight) {
        Tr tableRow = factory.createTr();
        table.getContent().add(tableRow);

        for (int i = 0; i < cellContents.size(); i++) {
            createTableCell(tableRow, cellContents.get(i), widths.get(i));
        }

        TrPr tableRowProperties = factory.createTrPr();
        tableRow.setTrPr(tableRowProperties);

        CTHeight height = factory.createCTHeight();
        JAXBElement<CTHeight> heightWrapped = factory.createCTTrPrBaseTrHeight(height);
        tableRowProperties.getCnfStyleOrDivIdOrGridBefore().add(heightWrapped);
        height.setVal(BigInteger.valueOf(rowHeight));
    }

    private static void createTableCell(Tr tableRow, TableCellContent cellContent, int width) {
        Tc tableCell = factory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tableCellWrapped = factory.createTrTc(tableCell);
        tableRow.getContent().add(tableCellWrapped);
        createTableCellContent(tableCell, cellContent, width);
    }

    private static void createTableCellContent(Tc tableCell, TableCellContent cellContent, int width) {
        P contentParameters = TextHandler.createText(cellContent);

        tableCell.getContent().add(contentParameters);
        PPr paragraphProperties = createParagraphPropertiesSameText(cellContent);
        contentParameters.setPPr(paragraphProperties);

        paragraphProperties.setSpacing(createPropertyOfBaseSpacing());

        TcPr tableCellProperties = factory.createTcPr();
        tableCell.setTcPr(tableCellProperties);

        TblWidth tableWidth = factory.createTblWidth();
        tableCellProperties.setTcW(tableWidth);
        tableWidth.setW(BigInteger.valueOf(width));
        tableWidth.setType(TABLE_WIDTH_TYPE);
    }

    private static PPr createParagraphPropertiesSameText(TableCellContent cellContent) {
        PPr paragraphProperties = factory.createPPr();
        ParaRPr paragraphArgumentProperties = factory.createParaRPr();
        paragraphProperties.setRPr(paragraphArgumentProperties);

        RFonts rfonts = factory.createRFonts();
        paragraphArgumentProperties.setRFonts(rfonts);
        rfonts.setAscii(cellContent.getFontName());
        rfonts.setHAnsi(cellContent.getFontName());
        rfonts.setCs(cellContent.getFontName());

        TextHandler.setTextStyle(paragraphArgumentProperties, cellContent);

        HpsMeasure textSizeFirst = factory.createHpsMeasure();
        paragraphArgumentProperties.setSz(textSizeFirst);
        textSizeFirst.setVal(BigInteger.valueOf(cellContent.getFontSize() * 2L));

        HpsMeasure textSizeSecond = factory.createHpsMeasure();
        paragraphArgumentProperties.setSzCs(textSizeSecond);
        textSizeSecond.setVal(BigInteger.valueOf(cellContent.getFontSize() * 2L));

        CTLanguage language = factory.createCTLanguage();
        paragraphArgumentProperties.setLang(language);
        language.setVal(cellContent.getTextLanguage());

        Jc jc = factory.createJc();
        paragraphProperties.setJc(jc);
        jc.setVal(cellContent.getAlignment());

        return paragraphProperties;
    }

    private static Spacing createPropertyOfBaseSpacing() {
        Spacing propertyOfBaseSpacing = factory.createPPrBaseSpacing();
        propertyOfBaseSpacing.setAfter(BigInteger.valueOf(DOCUMENT_TABLE_BASE_SPACING_BEFORE));
        propertyOfBaseSpacing.setLine(BigInteger.valueOf(DOCUMENT_TABLE_BASE_SPACING_AFTER));
        propertyOfBaseSpacing.setLineRule(org.docx4j.wml.STLineSpacingRule.AUTO);
        return propertyOfBaseSpacing;
    }

    private static void fixColumnSize(TblPr tableProperties) {
        CTTblLayoutType tableLayoutType = factory.createCTTblLayoutType();
        tableProperties.setTblLayout(tableLayoutType);
        tableLayoutType.setType(org.docx4j.wml.STTblLayoutType.FIXED);
    }

    public static void addBorders(Tbl table) {
        CTBorder border = new CTBorder();
        border.setColor("000000");
        border.setSz(new BigInteger("4"));
        border.setVal(STBorder.SINGLE);

        TblBorders borders = Context.getWmlObjectFactory().createTblBorders();
        borders.setBottom(border);
        borders.setLeft(border);
        borders.setRight(border);
        borders.setTop(border);
        borders.setInsideH(border);
        borders.setInsideV(border);
        table.getTblPr().setTblBorders(borders);
    }

    public static void createTableEnding(Tbl table, List<Integer> widths) {

        TblPr tableProperties = factory.createTblPr();
        fixColumnSize(tableProperties);
        table.setTblPr(tableProperties);

        CTTblPrBase.TblStyle tablePropertiesBaseTableStyle = factory.createCTTblPrBaseTblStyle();
        tableProperties.setTblStyle(tablePropertiesBaseTableStyle);
        tablePropertiesBaseTableStyle.setVal(BASE_TABLE_PROPERTY_STYLE);

        TblWidth commonTableWidth = factory.createTblWidth();
        tableProperties.setTblW(commonTableWidth);
        commonTableWidth.setW(BigInteger.valueOf(widths.stream().reduce(0, Integer::sum)));
        commonTableWidth.setType(TABLE_WIDTH_TYPE);

        CTTblLook tableLook = factory.createCTTblLook();
        tableProperties.setTblLook(tableLook);
        tableLook.setFirstRow(org.docx4j.sharedtypes.STOnOff.ONE);
        tableLook.setLastRow(org.docx4j.sharedtypes.STOnOff.ZERO);
        tableLook.setFirstColumn(org.docx4j.sharedtypes.STOnOff.ONE);
        tableLook.setLastColumn(org.docx4j.sharedtypes.STOnOff.ZERO);
        tableLook.setNoHBand(org.docx4j.sharedtypes.STOnOff.ZERO);
        tableLook.setNoVBand(org.docx4j.sharedtypes.STOnOff.ONE);
        tableLook.setVal("04A0");

        TblGrid tableGrigStyle = factory.createTblGrid();
        table.setTblGrid(tableGrigStyle);
        for (Integer width : widths) {
            TblGridCol tableGridColumnStyle = factory.createTblGridCol();
            tableGrigStyle.getGridCol().add(tableGridColumnStyle);
            tableGridColumnStyle.setW(BigInteger.valueOf(width));
        }
    }
}
