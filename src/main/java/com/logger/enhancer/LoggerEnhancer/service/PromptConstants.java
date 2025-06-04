package com.logger.enhancer.LoggerEnhancer.service;

public class PromptConstants {
    public static final String PROMPT_CONSTANT="Analyze and enhance logger usage for each Java class and method: " +
            "Review the logging practices used in this Java project and suggest improvements to enhance debugging, " +
            "code readability, thread traceability, and performance monitoring. Specifically: Suggest appropriate use of log levels " +
            "(INFO, DEBUG, ERROR, etc.) to reduce noise while retaining critical context. Advise on making log messages meaningful and " +
            "context-rich (e.g., include class, method, and business context). Explain how to include thread identifiers (such as thread name or ID) " +
            "to enable easier tracing of concurrent executions or async tasks. Propose ways to measure and log method execution times or transactions " +
            "for detecting performance bottlenecks. Recommend any logging frameworks (e.g., SLF4J, Logback, Log4j2) or " +
            "configuration tips (e.g., MDC for per-request tracing, log rotation, external aggregation). Include best practices for keeping logs clean, " +
            "actionable, and production-ready without overwhelming developers with excessive data. Output format: For each logger found or suggested, " +
            "return only a list of structured JSON object without any plain text with: json Copy Edit { \"className\": \"com.example.MyClass\", \"methodName\": \"myMethod\", \"lineNumber\": 42, \"oldLogger\": \"logger.info(\\\"Starting process\\\")\", \"suggestedLogger\": \"logger.debug(\\\"[MyClass::myMethod] Starting process for user={} in thread={}\\\", userId, Thread.currentThread().getName())\" } If no logger exists in a method but one is recommended, oldLogger should be null. Please be concise but complete. Only output this structured comparison for valid .java source files and methods. Do not hallucinate classes/methods."+
            "\n"+" Below is our java code"
            +"\n";
}
