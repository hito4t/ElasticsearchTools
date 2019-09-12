package com.hito4t.elasticsearch;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;

import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;


public class Json2Csv {

	public static void main(String[] args) throws Exception {
		InputStream in = System.in;
		OutputStream out = System.out;

		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-i":
				i++;
				if (i == args.length) {
					throw new IllegalArgumentException("Option -i has no value.");
				}
				in = new FileInputStream(args[i]);
				break;

			case "-o":
				i++;
				if (i == args.length) {
					throw new IllegalArgumentException("Option -o has no value.");
				}
				out = new FileOutputStream(args[i]);
				break;

			default:
				throw new IllegalArgumentException("Unknown argument : " + args[i]);
			}
		}

		Charset charset = Charset.forName("UTF8");
		try (Reader reader = new InputStreamReader(in, charset)) {
			new Json2Csv().execute(reader, new OutputStreamWriter(out, charset));
		}
	}

	public void execute(Reader reader, Writer writer) throws IOException {
		try (JsonReader jsonReader = new JsonReader(reader)) {
			try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
				execute(jsonReader, bufferedWriter);
			}
		}
	}

	private void execute(JsonReader jsonReader, BufferedWriter writer) throws IOException {
		JsonObjectReader objectReader = new JsonObjectReader() {
			private boolean first = true;

			public Map<String, Object> read(String property, JsonReader reader) throws IOException, JsonParseException {
				Map<String, Object> object = super.read(property, reader);

				if (property != null && property.equals("hits.hits._source")) {
			    	if (first) {
			    		//System.out.println(join(object.keySet()));
						writer.write(join(object.keySet()));
						writer.newLine();
			    		first = false;
			    	}
					//System.out.println(join(object.values()));

					writer.write(join(object.values()));
					writer.newLine();
				}

				return object;

			};
		    private String join(Collection<?> objects) {
		    	StringBuilder builder = new StringBuilder();
		    	boolean first = true;
		    	for (Object object : objects) {
		    		if (first) {
		    			first = false;
		    		} else {
		    			builder.append(",");
		    		}
		    		if (object != null) {
		    			builder.append(object.toString());
		    		}
		    	}
		    	return builder.toString();
		    }
		};

		objectReader.read(jsonReader);
	}

}
