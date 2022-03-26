package main.java.com.company.creators;

import java.math.BigInteger;
import java.util.Properties;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageDimensions;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.DocPropsCorePart;
import org.docx4j.openpackaging.parts.DocPropsExtendedPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.SectPr.PgMar;

public class CustomCreationWordprocessingMLPackage extends WordprocessingMLPackage {

    public static WordprocessingMLPackage createPackage(PageSizePaper sz, boolean landscape, Properties property) throws
        InvalidFormatException {
        WordprocessingMLPackage wmlPack = new WordprocessingMLPackage();
        MainDocumentPart wordDocumentPart = new MainDocumentPart();
        ObjectFactory factory = Context.getWmlObjectFactory();
        Body body = factory.createBody();
        org.docx4j.wml.Document wmlDocumentEl = factory.createDocument();
        wmlDocumentEl.setBody(body);
        PageDimensions page = new PageDimensions();
        page.setPgSize(sz, landscape);
        SectPr sectPr = factory.createSectPr();
        body.setSectPr(sectPr);
        sectPr.setPgSz(page.getPgSz());
        sectPr.setPgMar(createCustomDocumentBorders(property, page.getPgMar()));
        wordDocumentPart.setJaxbElement(wmlDocumentEl);
        wmlPack.addTargetPart(wordDocumentPart);
        StyleDefinitionsPart stylesPart = new StyleDefinitionsPart();

        try {
            ((StyleDefinitionsPart) stylesPart).unmarshalDefaultStyles();
            wordDocumentPart.addTargetPart(stylesPart);
        } catch (Exception var14) {
            log.error(var14.getMessage(), var14);
        }

        DocPropsCorePart core = new DocPropsCorePart();
        org.docx4j.docProps.core.ObjectFactory coreFactory = new org.docx4j.docProps.core.ObjectFactory();
        core.setJaxbElement(coreFactory.createCoreProperties());
        wmlPack.addTargetPart(core);
        DocPropsExtendedPart app = new DocPropsExtendedPart();
        org.docx4j.docProps.extended.ObjectFactory extFactory = new org.docx4j.docProps.extended.ObjectFactory();
        app.setJaxbElement(extFactory.createProperties());
        wmlPack.addTargetPart(app);
        return wmlPack;
    }

    private static PgMar createCustomDocumentBorders(Properties property, PgMar defaultPageMargin) {
        String pageMarginTop = property.getProperty("docx4j.PageMarginTop");
        if (pageMarginTop != null) {
            defaultPageMargin.setTop(BigInteger.valueOf(Long.parseLong(pageMarginTop)));
        }
        String pageMarginBottom = property.getProperty("docx4j.PageMarginBottom");
        if (pageMarginBottom != null) {
            defaultPageMargin.setBottom(BigInteger.valueOf(Long.parseLong(pageMarginBottom)));
        }
        String pageMarginLeft = property.getProperty("docx4j.PageMarginLeft");
        if (pageMarginLeft != null) {
            defaultPageMargin.setLeft(BigInteger.valueOf(Long.parseLong(pageMarginLeft)));
        }
        String pageMarginRight = property.getProperty("docx4j.PageMarginRight");
        if (pageMarginRight != null) {
            defaultPageMargin.setRight(BigInteger.valueOf(Long.parseLong(pageMarginRight)));
        }
        String pageMarginHeader = property.getProperty("docx4j.PageMarginHeader");
        if (pageMarginHeader != null) {
            defaultPageMargin.setHeader(BigInteger.valueOf(Long.parseLong(pageMarginHeader)));
        }
        String pageMarginFooter = property.getProperty("docx4j.PageMarginFooter");
        if (pageMarginFooter != null) {
            defaultPageMargin.setFooter(BigInteger.valueOf(Long.parseLong(pageMarginFooter)));
        }
        return defaultPageMargin;
    }
}
