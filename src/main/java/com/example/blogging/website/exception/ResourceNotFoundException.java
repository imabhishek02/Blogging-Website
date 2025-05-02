package com.example.blogging.website.exception;

import lombok.Data;


public class ResourceNotFoundException extends RuntimeException {
   private String resourceName;
   private String fieldName;
   private long fielValue;

   public ResourceNotFoundException(String resourceName,String fieldName,long fielValue){
       super(String.format("%s User not found with id %s: %s",resourceName,fieldName,fielValue));
       this.resourceName=resourceName;
       this.fieldName = fieldName;
       this.fielValue=fielValue;
   }

}
