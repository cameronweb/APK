package com.example.workcalendar.DataModel.Logs;

public class UserFields {

    private FieldName fieldName;
    private String field_old_value, field_new_value;


    public UserFields(FieldName field_Name, String old_value, String new_value) {
        fieldName=field_Name;
        field_new_value=new_value;
        field_old_value=old_value;
    }



    public FieldName getFieldName() {
        return fieldName;
    }

    public void setFieldName(FieldName fieldName) {
        this.fieldName = fieldName;
    }

    public String getField_old_value() {
        return field_old_value;
    }

    public void setField_old_value(String field_old_value) {
        this.field_old_value = field_old_value;
    }

    public String getField_new_value() {
        return field_new_value;
    }

    public void setField_new_value(String field_new_value) {
        this.field_new_value = field_new_value;
    }


    public enum FieldName {NAME,PHONE,BIRTHDAY;} //название полей оторые были изменены

}
