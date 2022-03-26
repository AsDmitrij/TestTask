package main.java.com.company.constants;

import java.util.Arrays;
import java.util.List;

public class DocumentConstants {
    public static class ResolutionRegistrationCards {
        public static final String REGISTRATION_TABLE_HEADER_TEXT_CONTENT = "Регистрационно-контрольная карточка";
        public static final String RESOLUTION_TABLE_HEADER_TEXT_CONTENT = "Резолюция или кому направлен документ";
        public static final List<String>
            REGISTRATION_TABLE_FIELDS_LABELS = Arrays.asList("Дата протокола", "Индекс поступления",
            "Подготовил", "Индекс документа", "На исх.", "На исх. (дата)", "Зарегистрировал", "Содержание вопроса",
            "Вид документа", "Корреспонденты");
        public static final List<String>
            RESOLUTION_TABLE_FIELDS_LABELS =
            Arrays.asList("Дата поручения", "Автор", "Исполнитель", "Содержание", "Дата исполнения",
                "Отметка об исполнении");
    }

    public static class TableColumnRelationConstants {
        public static final double[] TABLE_RELATION_TWO_ROW_COEFFICIENT = {0.45, 0.55};
        public static final double[] TABLE_RELATION_THREE_ROWS_COEFFICIENT = {0.12, 0.33, 0.55};
    }
}
