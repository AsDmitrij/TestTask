package main.java.com.company.models;

import java.time.LocalDateTime;

public class PersonalResolutionCard {
    private String registrationCardIndex;
    private final LocalDateTime assignmentDate;
    private final int authorId;
    private final int executorId;
    private final String content;
    private final LocalDateTime executionDate;
    private final String executionMark;

    public PersonalResolutionCard(LocalDateTime assignmentDate, int authorId, int executorId, String content,
                                  LocalDateTime executionDate, String executionMark) {
        this.assignmentDate = assignmentDate;
        this.authorId = authorId;
        this.executorId = executorId;
        this.content = content;
        this.executionDate = executionDate;
        this.executionMark = executionMark;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public String getExecutionMark() {
        return executionMark;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getExecutorId() {
        return executorId;
    }

    public String getRegistrationCardIndex() {
        return registrationCardIndex;
    }

    public void setRegistrationCardIndex(String registrationCardIndex) {
        this.registrationCardIndex = registrationCardIndex;
    }
}
