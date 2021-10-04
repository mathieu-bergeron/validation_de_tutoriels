package ca.ntro.examples.commands.change_text;

import ca.ntro.core.commands.CommandTarget;

public interface ChangeTextTarget extends CommandTarget {
	
	void changeText(String text);

}
