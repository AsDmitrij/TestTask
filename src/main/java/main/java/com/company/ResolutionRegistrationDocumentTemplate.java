package main.java.com.company;

import static main.java.com.company.constants.DocumentConstants.ResolutionRegistrationCards.REGISTRATION_TABLE_FIELDS_LABELS;
import static main.java.com.company.constants.DocumentConstants.ResolutionRegistrationCards.REGISTRATION_TABLE_HEADER_TEXT_CONTENT;
import static main.java.com.company.constants.DocumentConstants.ResolutionRegistrationCards.RESOLUTION_TABLE_FIELDS_LABELS;
import static main.java.com.company.constants.DocumentConstants.ResolutionRegistrationCards.RESOLUTION_TABLE_HEADER_TEXT_CONTENT;
import static main.java.com.company.constants.DocumentConstants.TableColumnRelationConstants.TABLE_RELATION_THREE_ROWS_COEFFICIENT;
import static main.java.com.company.constants.DocumentConstants.TableColumnRelationConstants.TABLE_RELATION_TWO_ROW_COEFFICIENT;
import static org.docx4j.wml.JcEnumeration.CENTER;
import static org.docx4j.wml.JcEnumeration.LEFT;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.JAXBElement;
import main.java.com.company.constants.TextStyleConstants;
import main.java.com.company.handler.TableHandler;
import main.java.com.company.handler.TextHandler;
import main.java.com.company.models.PersonCard;
import main.java.com.company.models.PersonalResolutionCard;
import main.java.com.company.models.RegistrationControlCard;
import main.java.com.company.models.TableCellContent;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Body;
import org.docx4j.wml.Document;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;

public class ResolutionRegistrationDocumentTemplate {
    private static final ObjectFactory factory = Context.getWmlObjectFactory();
    private static final String DOCUMENT_FONT = "Times New Roman";
    private static final String DOCUMENT_LANGUAGE = "ru-RU";
    private static final int DOCUMENT_FONT_SIZE = 14;
    private static final int DOCUMENT_TABLE_ROW_HEIGHT = 416;
    private static final String EMPTY_CELL_SIGN = " - ";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static Document createResolutionRegistrationDocumentBody(WordprocessingMLPackage mainWordprocessingMLPackage,
                                                                    RegistrationControlCard registrationControlCard,
                                                                    List<PersonalResolutionCard> personalResolutionCards,
                                                                    List<PersonCard> personCards) {
        Document document = factory.createDocument();
        Body body = factory.createBody();
        document.setBody(body);

        List<String> registrationControlCardFields = getRegistrationControlCardObjectFields(registrationControlCard);

        P firstTableHeader = TextHandler.createText(
            new TableCellContent(REGISTRATION_TABLE_HEADER_TEXT_CONTENT, DOCUMENT_FONT, TextStyleConstants.BOLD,
                DOCUMENT_FONT_SIZE, CENTER, DOCUMENT_LANGUAGE));
        P emptyString = TextHandler.createText(
            new TableCellContent("", DOCUMENT_FONT, TextStyleConstants.NONE, DOCUMENT_FONT_SIZE, LEFT,
                DOCUMENT_LANGUAGE));
        body.getContent().add(firstTableHeader);
        body.getContent().add(emptyString);

        Tbl firstTable = factory.createTbl();
        JAXBElement<Tbl> tableWrapped = factory.createBodyTbl(firstTable);

        body.getContent().add(tableWrapped);

        TableCellContent cellContentFirstRow =
            new TableCellContent(DOCUMENT_FONT, TextStyleConstants.ITALIC, DOCUMENT_FONT_SIZE - 1, JcEnumeration.LEFT,
                DOCUMENT_LANGUAGE);
        TableCellContent cellContentSecondRow =
            new TableCellContent(EMPTY_CELL_SIGN, DOCUMENT_FONT, TextStyleConstants.NONE, DOCUMENT_FONT_SIZE - 1,
                JcEnumeration.LEFT, DOCUMENT_LANGUAGE);

        int writableWidthTwips = mainWordprocessingMLPackage.getDocumentModel()
                                                            .getSections()
                                                            .get(0).getPageDimensions()
                                                            .getWritableWidthTwips();

        List<Integer> widths = new ArrayList<>();
        for (double c : TABLE_RELATION_TWO_ROW_COEFFICIENT) {
            widths.add((int) (writableWidthTwips * c * 0.9));
        }

        for (int i = 0; i < REGISTRATION_TABLE_FIELDS_LABELS.size(); i++) {
            cellContentFirstRow.setText(REGISTRATION_TABLE_FIELDS_LABELS.get(i));
            cellContentSecondRow.setText(registrationControlCardFields.get(i));
            TableHandler.createTableRow(firstTable, Arrays.asList(cellContentFirstRow, cellContentSecondRow), widths,
                DOCUMENT_TABLE_ROW_HEIGHT);
        }
        TableHandler.createTableEnding(firstTable, widths);
        body.getContent().add(emptyString);
        P secondTableHeader = TextHandler.createText(
            new TableCellContent(RESOLUTION_TABLE_HEADER_TEXT_CONTENT, DOCUMENT_FONT, TextStyleConstants.BOLD,
                DOCUMENT_FONT_SIZE, CENTER, DOCUMENT_LANGUAGE));
        body.getContent().add(secondTableHeader);

        List<Integer> widthsThreeRows = new ArrayList<>();
        for (double c : TABLE_RELATION_THREE_ROWS_COEFFICIENT) {
            widthsThreeRows.add((int) (writableWidthTwips * c * 0.9));
        }

        for (PersonalResolutionCard personalResolutionCard : personalResolutionCards) {
            List<String> personalResolutionCardObjectFields =
                getPersonalResolutionCardObjectFields(personalResolutionCard, personCards);
            Tbl cardTableFirstPart = factory.createTbl();
            JAXBElement<Tbl> cardTableFirstPartWrapped = factory.createBodyTbl(cardTableFirstPart);
            body.getContent().add(emptyString);
            body.getContent().add(cardTableFirstPartWrapped);
            cellContentFirstRow.setText(RESOLUTION_TABLE_FIELDS_LABELS.get(0));
            cellContentFirstRow.setAlignment(JcEnumeration.RIGHT);
            TableCellContent cellContentNumericRow =
                new TableCellContent(personalResolutionCardObjectFields.get(0), DOCUMENT_FONT, TextStyleConstants.NONE,
                    DOCUMENT_FONT_SIZE - 1,
                    JcEnumeration.LEFT, DOCUMENT_LANGUAGE);
            cellContentSecondRow.setText(personalResolutionCardObjectFields.get(1));
            TableHandler.createTableRow(cardTableFirstPart,
                Arrays.asList(cellContentNumericRow, cellContentFirstRow, cellContentSecondRow), widthsThreeRows,
                DOCUMENT_TABLE_ROW_HEIGHT);
            TableHandler.createTableEnding(cardTableFirstPart, widthsThreeRows);

            Tbl cardTableSecondPart = factory.createTbl();
            JAXBElement<Tbl> cardTableSecondPartWrapped = factory.createBodyTbl(cardTableSecondPart);
            body.getContent().add(cardTableSecondPartWrapped);
            for (int j = 1; j < RESOLUTION_TABLE_FIELDS_LABELS.size(); j++) {
                cellContentFirstRow.setText(RESOLUTION_TABLE_FIELDS_LABELS.get(j));
                cellContentSecondRow.setText(personalResolutionCardObjectFields.get(j + 1));
                TableHandler.createTableRow(cardTableSecondPart,
                    Arrays.asList(cellContentFirstRow, cellContentSecondRow), widths,
                    DOCUMENT_TABLE_ROW_HEIGHT);
            }
            TableHandler.createTableEnding(cardTableSecondPart, widths);
            TableHandler.addBorders(cardTableFirstPart);
            TableHandler.addBorders(cardTableSecondPart);
        }
        TableHandler.addBorders(firstTable);
        return document;
    }

    private static List<String> getRegistrationControlCardObjectFields(RegistrationControlCard registrationControlCard) {
        return new ArrayList<>(Arrays.asList(registrationControlCard.getProtocolDate().format(FORMATTER),
            registrationControlCard.getReceiveIndex(),
            registrationControlCard.getPreparedBy(),
            registrationControlCard.getDocumentIndex(),
            registrationControlCard.getOutgoing(),
            registrationControlCard.getOutgoingDate().format(FORMATTER),
            registrationControlCard.getRegisteredBy(),
            registrationControlCard.getQuestionContent(),
            registrationControlCard.getDocumentType(),
            registrationControlCard.getCorrespondents()
        ));
    }

    private static List<String> getPersonalResolutionCardObjectFields(PersonalResolutionCard personalResolutionCard,
                                                                      List<PersonCard> personCards) {
        return new ArrayList<>(
            Arrays.asList(
                personalResolutionCard.getRegistrationCardIndex(),
                personalResolutionCard.getAssignmentDate().format(FORMATTER),
                findExecutorName(personalResolutionCard.getAuthorId(), personCards),
                findExecutorName(personalResolutionCard.getExecutorId(), personCards),
                personalResolutionCard.getContent(),
                personalResolutionCard.getExecutionDate().format(FORMATTER),
                personalResolutionCard.getExecutionMark()
            ));
    }

    private static String findExecutorName(int id, List<PersonCard> personCards) {
        for (PersonCard p : personCards) {
            if (p.getId() == id) {
                return p.getPerson().getFullName();
            }
        }
        return "Unknown person";
    }
}
