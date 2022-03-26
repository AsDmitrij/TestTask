package main.java.com.company.handler;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import main.java.com.company.models.TableCellContent;
import org.docx4j.jaxb.Context;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTLanguage;
import org.docx4j.wml.HpsMeasure;
import org.docx4j.wml.Jc;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

public class TextHandler {
    private static final ObjectFactory factory = Context.getWmlObjectFactory();

    public static P createText(TableCellContent textWithFonts) {
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Text text = factory.createText();
        JAXBElement<Text> textWrapped = factory.createRT(text);
        r.getContent().add(textWrapped);
        text.setValue(textWithFonts.getText());
        RPr rpr = factory.createRPr();
        r.setRPr(rpr);
        RFonts rfonts = factory.createRFonts();
        rpr.setRFonts(rfonts);
        rfonts.setAscii(textWithFonts.getFontName());
        rfonts.setHAnsi(textWithFonts.getFontName());
        rfonts.setCs(textWithFonts.getFontName());
        setTextStyle(rpr, factory.createBooleanDefaultTrue(), textWithFonts);
        HpsMeasure hpsmeasure = factory.createHpsMeasure();
        rpr.setSz(hpsmeasure);
        hpsmeasure.setVal(BigInteger.valueOf(textWithFonts.getFontSize() * 2L));
        HpsMeasure hpsmeasure2 = factory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal(BigInteger.valueOf(textWithFonts.getFontSize() * 2L));
        CTLanguage language = factory.createCTLanguage();
        rpr.setLang(language);
        language.setVal(textWithFonts.getTextLanguage());
        PPr ppr = factory.createPPr();
        p.setPPr(ppr);
        Jc jc = factory.createJc();
        ppr.setJc(jc);
        jc.setVal(textWithFonts.getAlignment());
        return p;
    }


    public static RPr setTextStyle(RPr rpr, BooleanDefaultTrue booleandefaulttrue, TableCellContent textWithFonts) {
        switch (textWithFonts.getFontStyle()) {
            case ITALIC:
                rpr.setI(booleandefaulttrue);
                return rpr;
            case BOLD:
                rpr.setB(booleandefaulttrue);
                return rpr;
            default:
                break;
        }
        return rpr;
    }

    public static ParaRPr setTextStyle(ParaRPr paragraphProperties, TableCellContent textWithFonts) {
        BooleanDefaultTrue booleandefaulttrue = factory.createBooleanDefaultTrue();
        switch (textWithFonts.getFontStyle()) {
            case ITALIC:
                paragraphProperties.setI(booleandefaulttrue);
                return paragraphProperties;
            case BOLD:
                paragraphProperties.setB(booleandefaulttrue);
                return paragraphProperties;
            default:
                break;
        }
        return paragraphProperties;
    }
}
