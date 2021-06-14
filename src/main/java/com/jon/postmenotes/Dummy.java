/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jlastril
 */
public class Dummy {

    public static void main(String[] args) {
        final Pattern pattern = Pattern.compile("^-+");
        String data = "-----------------------\n"
                + "fdafasdfasdf\n"
                + "dsfasfd\n"
                + "f\n"
                + "sdfda\n"
                + "sfadsffsda\n"
                + "fdafdsafas\n"
                + "--------------------\n"
                + "dasfdsfasfd\n"
                + "fd\n"
                + "ff\n"
                + "dfafs0\n"
                + "-------------\n"
                + "1 dsafsdfa\n"
                + "2 dfafs0\n"
                + "3 dfafs02\n";
//        Matcher m = pattern.matcher(data);
//        while (m.find()) {
//            System.out.println("a");
//        }
        Stack<String> stack = new Stack<>();
        for (String string : data.split("\n")) {
            stack.push(string);
        }
        StringBuilder b = new StringBuilder();
        
        while (!stack.isEmpty()) {            
            String top = stack.pop();
            boolean terminate = pattern.matcher(top).find();
            if(!terminate){
                b.insert(0, top+"\n");
                
            }else{
                break;
            }
        }
        
//        System.out.println(b.toString());
    }
}
