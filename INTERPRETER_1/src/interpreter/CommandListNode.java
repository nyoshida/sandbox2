package interpreter;

import java.text.ParseException;
import java.util.Vector;

public class CommandListNode extends Node {
	private Vector<CommanNode>list = new Vector<CommanNode>();

	@Override
	public void parse(Context context) throws ParseException {
		while(true){
			if ( context.currentToken() == null ){
				throw new ParseException("Missing 'end'", 0);
			} else if ( context.currentToken().equals("end")){
				context.skipToken("end");
				break;
			}
			else {
				Node commandNode = new CommanNode();
				commandNode.parse(context);
				list.add((CommanNode) commandNode);
			}
		}
	}
	public String toString() {
		return ""+list;
	}

}
