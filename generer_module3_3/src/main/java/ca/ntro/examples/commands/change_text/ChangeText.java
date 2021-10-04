package ca.ntro.examples.commands.change_text;

import ca.ntro.core.commands.Command;
import ca.ntro.core.commands.CommandTarget;

public class ChangeText extends Command {
	
	private String newText;
	
	public void setText(String newText) {
		this.newText = newText;
	}

	@Override
	protected void executeImpl(CommandTarget target) {
		((ChangeTextTarget) target).changeText(newText);
	}


}
