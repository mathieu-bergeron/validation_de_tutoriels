package ca.ntro.java;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.PromiseSync;
import ca.ntro.core.system.file.Resource;

public class ResourceSwing implements Resource {
	
	private InputStream stream;
	
	public ResourceSwing(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public Promise<String> getString() {

		Promise<String> result = null;
		
		StringBuilder builder = new StringBuilder();
		
		InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
		
		int c;
		try {

			while((c = reader.read()) > 0) {
				builder.append((char) c);
			}

			result = new PromiseSync<String>(builder.toString());

		} catch (Exception e) {
			
			result = new PromiseSync<String>(null);

		} 
		
		return result;
	}

}
