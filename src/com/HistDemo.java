/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import model.DBHand;

/**
 *
 * @author me-aydin
 */
public class HistDemo {
    
    /**
     *
     * @return
     */
    int nums[]; 
    
    public HistDemo(){
        nums = new int[100];
        Random random = new Random();
        
        for(int i=0;i<100;i++) 
            nums[i] = random.nextInt(100);
        
    }
    
    public int[] retrieve() throws FileNotFoundException, IOException {
       
        try {
            FileReader file = new FileReader("/Users/me-aydin/Downloads/workdr/nums.txt");
            BufferedReader br = new BufferedReader(file);

            for(int i = 0; i<nums.length;i++) {
                String str = br.readLine().trim();
                try {
                    nums[i]= Integer.parseInt(str);
                } 
                catch(NumberFormatException ex) {
                    System.out.println("Error, "+nums[i]+" isn't a whole number!");
                }
                 //   System.out.println(str+" "+nums[i])
            }
            br.close();
        }
        catch(IOException ie ) {
            System.out.println(ie);
        }

        return nums;
    }
    
    public int insert(){
        DBHand jdbc = new DBHand();
        int count = 0;
        
         boolean connectionSet = jdbc.setConnection("meaydin","aydinme");
         
         if (connectionSet) {
            for(int i=0;i<100;i++){ 
                count += jdbc.insert("insert into grades (GradeID,grade) values ('1',"+nums[i]+")");
            }
            
         }
        else 
             System.out.println("ERROR establishing connection! Please consult stack trace.");   

        
        return count;
    }
    
    public int[] retrieve(String query){
        //int[] nums;
        
        List list = new ArrayList();
        
        DBHand jdbc = new DBHand();
        
         boolean connectionSet = jdbc.setConnection("meaydin","aydinme");
         if (connectionSet) {
            list = jdbc.getNumbers(query);
         }
        else 
             System.out.println("ERROR establishing connection! Please consult stack trace.");   
        
         nums = new int[list.size()];
         Iterator itr = list.iterator();
         int i = 0;
         while(itr.hasNext()) {
             nums[i++] = (int)itr.next();
         }
         
        return nums;
    }
    
    public int[] getNums(){
         return nums;
    }
    
 
    public static void main(String[] str) throws FileNotFoundException, IOException{
        HistDemo demo = new HistDemo(); 
        
       // int[] nums = demo.getNums();
       // int[] nums = demo.retrieve();
      /*   */
        int[] nums = demo.retrieve("select * from meaydin.grades"); 
        
        Histogram hist = new Histogram(nums, 10);
        
        System.out.println(hist.getHistogram());
     
      
      // System.out.println(demo.insert());
    }
    
}
