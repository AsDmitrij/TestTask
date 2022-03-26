package main.java.com.company.models;

import java.time.LocalDateTime;

public class RegistrationControlCard {
    private final LocalDateTime protocolDate;
    private final String receiveIndex;
    private final String preparedBy;
    private final String documentIndex;
    private final String outgoing;
    private final LocalDateTime outgoingDate;
    private final String registeredBy;
    private final String questionContent;
    private final String documentType;
    private final String correspondents;

    public RegistrationControlCard(LocalDateTime protocolDate, String receiveIndex, String preparedBy,
                                   String documentIndex, String outgoing, LocalDateTime outgoingDate,
                                   String registeredBy, String questionContent, String documentType,
                                   String correspondents) {
        this.protocolDate = protocolDate;
        this.receiveIndex = receiveIndex;
        this.preparedBy = preparedBy;
        this.documentIndex = documentIndex;
        this.outgoing = outgoing;
        this.outgoingDate = outgoingDate;
        this.registeredBy = registeredBy;
        this.questionContent = questionContent;
        this.documentType = documentType;
        this.correspondents = correspondents;
    }

    public LocalDateTime getProtocolDate() {
        return protocolDate;
    }

    public String getReceiveIndex() {
        return receiveIndex;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public String getDocumentIndex() {
        return documentIndex;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public LocalDateTime getOutgoingDate() {
        return outgoingDate;
    }

    public String getRegisteredBy() {
        return registeredBy;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getCorrespondents() {
        return correspondents;
    }
}
