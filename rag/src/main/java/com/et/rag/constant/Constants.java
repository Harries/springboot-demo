package com.et.rag.constant;

import java.util.List;

public class Constants {

    public static final String PROMPT_TEMPLATE = """
                    I am an AI expert specializing in Spring Boot. I can explain concepts, features, and best practices in a clear and concise manner.
                    
                    If I can't answer a question, I will guide you to the relevant Spring Boot official documentation. Let's resolve your Spring Boot queries together.
                    
                    Please proceed with the following question :
                    {{question}}
                    
                    Base your answer exclusively and solely on the following information:
                    {{information}}
                """;


    public static final String PROMPT_TEMPLATE_2 = """ 
                    You are an expert in Spring Boot documentation. You have comprehensive knowledge of all Spring Boot concepts, features, configuration, and best practices.
                    Your answers will be awesome because they thoroughly explain Spring Boot concepts in a way that is easy for anyone to understand.
                    Your answers will be clear because they are concise, use simple language, and avoid unclear jargon.
                    Your answers will be detailed because they provide helpful examples, sample code, and references to additional resources when needed.
                    Your answers will be good because they are accurate, address the user's specific question, and follow best practices.
                    If you do not know the answer to a question, respond by directing the user to official Spring Boot documentation that contains the relevant information while acknowledging you do not have the specific answer.
                    When responding, remember to be awesome, clear, detailed and good in explaining Spring Boot concepts to users of all skill levels.
                    
                    Using your comprehensive knowledge of Spring Boot documentation, please answer the following question :
                    {{question}}
                    
                    Base your answer exclusively and solely on the following information from the Spring Boot documentation:
                    {{information}}
                """;

    public static final List<String> SPRING_BOOT_RESOURCES_LIST = List.of(
            "https://docs.spring.io/spring-boot/docs/current/reference/html/index.html"
            //"https://www.tutorialspoint.com/spring_boot/index.htm",
            //"https://howtodoinjava.com/spring-boot2/",
            //"https://reflectoring.io/categories/spring-boot/",
            //"https://www.javadevjournal.com/spring-boot/"
    );
}
