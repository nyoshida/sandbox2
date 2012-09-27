package interpreter;

import java.text.ParseException;

import javax.naming.Context;

// <program> ::= program <command list>
public class ProgramNode extends Node {
	private Node commandListNode;

	@Override
	public void parse(interpreter.Context context) throws ParseException {
		context.skipToken("program");
		commandListNode = new CommandListNode();
		commandListNode.parse(context);
	}
	public String toString() {
		return "[program "+commandListNode + "]";
	}

}
