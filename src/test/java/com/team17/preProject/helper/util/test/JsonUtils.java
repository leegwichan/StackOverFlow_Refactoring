package com.team17.preProject.helper.util.test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static List<Class> basicClass =
            List.of(String.class, Integer.class, Long.class, LocalDateTime.class);

    public static List<Object[]> getJsonPathAndValue(Object dto) {
        try {
            return getJsonPathAndValue(dto, "$");
        } catch (Exception e){
            return null;
        }
    }

    private static List<Object[]> getJsonPathAndValue(Object dto, String jsonData) throws Exception{

        Class cls = dto.getClass();
        Field[] fields = dto.getClass().getDeclaredFields();
        List<Object[]> valueList = new ArrayList<>();

        for (Field field : fields){

            String fieldName = field.getName();
            if ( fieldName.length() > 4 && fieldName.substring(0,5).equals("this$")) continue;

            String methodName = getterMethodName(fieldName);
            Object value = cls.getDeclaredMethod(methodName).invoke(dto);
            String jsonPath = jsonData + "." + fieldName;

             if (basicClass.contains(value.getClass())){
                valueList.add(new Object[]{jsonPath, value});
             } else if (value instanceof List){
                 valueList.add(new Object[]{jsonPath, List.class});
             } else {
                valueList.addAll(getJsonPathAndValue(value, jsonPath));
             }
        }

        return valueList;
    }

    private static String getterMethodName(String fieldName) {
        if (fieldName.length() < 2){
            return "get"+ fieldName.toUpperCase();
        } else {
            return "get"+ fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        }
    }
}
