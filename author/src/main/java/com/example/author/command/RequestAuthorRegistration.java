package com.example.author.command;

public class RequestAuthorRegistration {
    private String name;
    private String description;
    private String portfolio;

    // 기본 생성자
    public RequestAuthorRegistration() {}

    // 모든 필드 생성자
    public RequestAuthorRegistration(String name, String description, String portfolio) {
        this.name = name;
        this.description = description;
        this.portfolio = portfolio;
    }

    // Getter
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPortfolio() { return portfolio; }
}
