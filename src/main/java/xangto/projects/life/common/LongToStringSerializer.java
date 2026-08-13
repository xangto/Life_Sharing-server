package xangto.projects.life.common;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class LongToStringSerializer extends ValueSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value != null) {
            gen.writeString(value.toString());
        }
    }
}
