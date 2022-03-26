package main.java.com.company.creators;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Document;

public class CustomDocumentCreator {
    private static final String GENERATED_DOCUMENT_NAME = "auto.docx";

    public void saveDocument(WordprocessingMLPackage wordPackage, Document document) throws Docx4JException {
        wordPackage.getMainDocumentPart().getJaxbElement().setBody(document.getBody());
        File exportFile = new File(GENERATED_DOCUMENT_NAME);
        wordPackage.save(exportFile);
    }

    public WordprocessingMLPackage createMainDocumentPartWithParams() throws InvalidFormatException {
        try (FileInputStream properties = new FileInputStream("src/main/resources/docx4j.properties")) {
            Properties property = new Properties();
            property.load(properties);
            String pageSize = property.getProperty("docx4j.PageSize");
            String landscape = property.getProperty("docx4j.Landscape");
            if (pageSize != null) {
                return CustomCreationWordprocessingMLPackage.createPackage(PageSizePaper.valueOf(pageSize),
                    landscape != null && Boolean.getBoolean(landscape), property);
            }
            return WordprocessingMLPackage.createPackage();
        } catch (IOException e) {
            return WordprocessingMLPackage.createPackage();
        }
    }

}
