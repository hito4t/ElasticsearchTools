package com.hito4t.elasticsearch;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class JsonObjectReader {

	public Map<String, Object> read(JsonReader reader) throws IOException, JsonParseException {
		return read(null, reader);
	}

	public Map<String, Object> read(String property, JsonReader reader) throws IOException, JsonParseException {
		reader.beginObject();

		Map<String, Object> values = new LinkedHashMap<String, Object>();
		while (reader.hasNext()) {
			String name = reader.nextName();
			Object value = readProperty(append(property, name), reader);
			values.put(name, value);
		}
		reader.endObject();

		return values;
	}

	private String append(String property, String name) {
		if (property == null) {
			return name;
		}
		return property + "." + name;
	}

    protected Object readProperty(String property, JsonReader reader) throws IOException, JsonParseException {
    	JsonToken token = reader.peek();
    	if (token.equals(JsonToken.NULL)) {
    		reader.nextNull();
    		return null;
    	}

    	if (token.equals(JsonToken.BOOLEAN)) {
    		return reader.nextBoolean();
    	}

    	if (token.equals(JsonToken.STRING)) {
    		return reader.nextString();
    	}

    	if (token.equals(JsonToken.NUMBER)) {
    		return reader.nextDouble();
    	}

    	if (token.equals(JsonToken.BEGIN_OBJECT)) {
    		return read(property, reader);
    	}

    	if (token.equals(JsonToken.BEGIN_ARRAY)) {
    		reader.beginArray();
    		int count = 0;
    		while (reader.hasNext()) {
    			readProperty(property, reader);
    			count++;
    		}
    		reader.endArray();
    		return count;
    	}

    	throw new JsonParseException("Unsupported type : " + token.name());
    }
}
