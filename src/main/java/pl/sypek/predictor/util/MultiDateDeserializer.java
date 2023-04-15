package pl.sypek.predictor.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * https://stackoverflow.com/a/42567051/11152683
 */
public class MultiDateDeserializer extends StdDeserializer<LocalDateTime> {
    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter[] DATE_FORMATTERS = new DateTimeFormatter[]{
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("MM/d/yyyy HH:mm")
    };

    public MultiDateDeserializer() {
        this(null);
    }

    public MultiDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        final String date = node.textValue();

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDateTime.parse(date, formatter);
            } catch (Exception e) {
            }
        }
        throw new JsonParseException(jp, "Unparseable date: \"" + date + "\". Supported formats: ");
    }
}
