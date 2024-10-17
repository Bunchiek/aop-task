package org.example.service;

import org.example.aspect.HandlingResult;
import org.example.aspect.LogException;
import org.example.aspect.LogExecution;
import org.example.aspect.LogTracking;
import org.example.model.ExampleObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {


    @LogTracking
    public void greetings() {
        System.out.println("Hello world");
    }

    @LogExecution
    public void greetingByName(String aleksey) {
        System.out.println("Hello " + aleksey);
    }

    @LogException
    public void doException(int i) {
        if (i==0) {
            throw new IllegalArgumentException("Value is zero");
        }
        System.out.println("value is " + i);
    }

    @HandlingResult
    public ExampleObject getStrings() {
        return new ExampleObject("It's my own example");
    }




}
