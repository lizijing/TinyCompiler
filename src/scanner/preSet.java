package scanner;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-3 下午4:26:24 
 * 类说明 
 */
public class preSet {
	public static enum TokenType{
		ENDFILE,ERROR,IF,THEN,ELSE,END,REPEAT,UNTIL,READ,WRITE,ID,NUM,
		ASSIGN,EQ,LT,PLUS,MINUS,TIMES,OVER,LPAREN,RPAREN,SEMI
	}
	public static enum stmtKind{
		IfK,RepeatK,AssignK,ReadK,WriteK
	}
	public static enum expKind{
		OpK,ConstK,IdK
	}
	public static enum nodeKind{
		StmtK,ExpK
	}
	public static int lineNum=0;
	
	public static String returnString;
	
}
