package com.team17.preProject.helper.util.docs;

import lombok.NoArgsConstructor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;

public class JsonDocumentUtils {

    private static List<Class> numberClass =
            List.of(Integer.class, Long.class, int.class, long.class);
    private static List<Class> stringClass =
            List.of(String.class, LocalDateTime.class);
    private static List<Class> arrayClass = 
            List.of(List.class, ArrayList.class);

    private static List<Class> basicClass =
            List.of(String.class, Integer.class, Long.class, LocalDateTime.class, int.class, long.class);

    public static List<FieldDescriptor> getResponseFieldData(Object dto){
        try {
            return getResponseFieldData(dto,"", null);
        } catch (Exception e){
            return null;
        }

    }

    public static List<FieldDescriptor> getResponseFieldData(Object dto, Map<Option, String[]> optionInfo){
        try {
            return getResponseFieldData(dto,"", optionInfo);
        } catch (Exception e){
            return null;
        }
        
    }

    private static List<FieldDescriptor> getResponseFieldData(Object dto, String previousJsonPath,
                                                              Map<Option, String[]> optionInfo) throws Exception{
        Class cls = dto.getClass();
        Field[] fields = cls.getDeclaredFields();
        List<FieldDescriptor> valueList = new ArrayList<>();

        for (Field field : fields){

            String fieldName = field.getName();
            if ( fieldName.length() > 4 && fieldName.substring(0,5).equals("this$")) continue;
            
            Class fieldClass = field.getType();
            JsonFieldType type = getJsonFieldType(fieldClass);
            String describe = DocumentFieldDescription.getFieldDescriptions().get(fieldName);
            String jsonPath = getJsonPath(fieldName, previousJsonPath);
            // describe 예외 처리 실시 해야됨
            Object fieldValue = cls.getDeclaredMethod(getterMethodName(fieldName)).invoke(dto);


            if (basicClass.contains(fieldClass)){
                valueList.add(makeFieldDescriptor(fieldName, jsonPath, type, optionInfo));
            } else if (fieldValue instanceof List){
                valueList.add(makeFieldDescriptor(fieldName, jsonPath, type, optionInfo));
                List values = (List) fieldValue;
                valueList.addAll(getResponseFieldData(values.get(0), jsonPath + "[]", optionInfo));
            } else {
                valueList.add(makeFieldDescriptor(fieldName, jsonPath, type, optionInfo));
                valueList.addAll(getResponseFieldData(fieldValue, jsonPath, optionInfo));
            }
        }
        return valueList;
    }

    private static JsonFieldType getJsonFieldType(Class fieldClass) {

        if (numberClass.contains(fieldClass)){
            return JsonFieldType.NUMBER;
        } else if (stringClass.contains(fieldClass)){
            return JsonFieldType.STRING;
        } else if(arrayClass.contains(fieldClass)){
            return JsonFieldType.ARRAY;
        } else {
            return JsonFieldType.OBJECT;
        }
    }
    
    private static String getJsonPath(String fieldName, String previousJsonPath){
        if (previousJsonPath.equals("")){
            return fieldName;
        } else {
            return previousJsonPath + "." + fieldName;
        }
    }

    private static String getterMethodName(String fieldName) {
        if (fieldName.length() < 2){
            return "get"+ fieldName.toUpperCase();
        } else {
            return "get"+ fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        }
    }

    private static FieldDescriptor makeFieldDescriptor(String fieldName, String jsonPath, JsonFieldType type,
                                                       Map<Option, String[]> optionInfo){

        String describe = DocumentFieldDescription.getFieldDescriptions().get(fieldName);
        FieldDescriptor fieldDescriptor = fieldWithPath(jsonPath).type(type).description(describe);

        if (optionInfo == null) return fieldDescriptor;
        if (optionInfo.get(Option.OPTIONAL) != null && IsInArray(optionInfo.get(Option.OPTIONAL), fieldName)) {
            fieldDescriptor = fieldDescriptor.optional();
        }
        if (optionInfo.get(Option.IGNORED) != null && IsInArray(optionInfo.get(Option.IGNORED), fieldName)) {
            fieldDescriptor = fieldDescriptor.ignored();
        }

        return fieldDescriptor;
    }

    private static boolean IsInArray(String[] array, String element){
        for (String ele : array){
            if (ele.equals(element)) return true;
        }
        return false;
    }

    @NoArgsConstructor
    public enum Option {
        OPTIONAL,
        IGNORED
    }


}
