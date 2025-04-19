package io.lacak.clone.live.ui.model;

import java.lang.reflect.Field;
import lombok.Data;

@Data
public class FieldBase {
    public Object getVariable(Field field) {
        try {
            return field.get(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
