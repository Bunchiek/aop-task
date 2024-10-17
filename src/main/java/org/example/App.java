package org.example;

import org.example.service.MainService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan
@EnableAspectJAutoProxy
public class App {

    public static void main( String[] args ) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(App.class);

        MainService mainService = context.getBean(MainService.class);

        mainService.getStrings();

        mainService.greetings();

        mainService.greetingByName("Aleksey");

        mainService.doException(0);

    }
}
