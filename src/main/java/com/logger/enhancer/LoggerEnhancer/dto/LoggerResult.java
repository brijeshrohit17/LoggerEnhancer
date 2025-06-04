package com.logger.enhancer.LoggerEnhancer.dto;


public class LoggerResult {
    private String className;
    private String methodName;
    private int lineNumber;
    private String oldLogger;
    private String suggestedLogger;

    // Getters and setters
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

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

    public String getOldLogger() {
        return oldLogger;
    }
    public void setOldLogger(String oldLogger) {
        this.oldLogger = oldLogger;
    }

    public String getSuggestedLogger() {
        return suggestedLogger;
    }
    public void setSuggestedLogger(String suggestedLogger) {
        this.suggestedLogger = suggestedLogger;
    }
}
