package ca.ntro.core.system.source;


public class SourceFileLocation {
	
	// FIXME: is this always / ??
	private static final String PATH_DELEMITOR = "/";
	
	private String fileName;
	private int line;

	public SourceFileLocation(String rawFilePath, int line) {
		this.fileName = extractFilename(rawFilePath);
		this.line = line;
	}
	
	private String extractFilename(String rawFilePath) {
		String[] pathSplits = rawFilePath.split(PATH_DELEMITOR);
		
		return pathSplits[pathSplits.length-1];
	}
	
	public String toString() {
		return fileName + ":" + line;
	}

}
