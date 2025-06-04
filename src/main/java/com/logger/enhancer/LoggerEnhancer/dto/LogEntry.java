package com.logger.enhancer.LoggerEnhancer.dto;

public class LogEntry {
    private String className;
    private String methodName;
    private int lineNumber;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getExistingLog() {
        return existingLog;
    }

    public void setExistingLog(String existingLog) {
        this.existingLog = existingLog;
    }

    public String getSuggestedLog() {
        return suggestedLog;
    }

    public void setSuggestedLog(String suggestedLog) {
        this.suggestedLog = suggestedLog;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    private String existingLog;
    private String suggestedLog;
}
