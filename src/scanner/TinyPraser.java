package scanner;
import scanner.preSet.TokenType;

import scanner.TreeNode;
import scanner.TinyScanner;
import scanner.Utility;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-6 上午12:06:02 
 * 类说明 
 */


public class TinyPraser {
 TokenType token;
 TinyScanner prase = new TinyScanner();

 public void match(TokenType expected)
 {   
	 System.out.println(token);
	 if(token==expected)
	 {
		 try {
			token =prase.getToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 else
	 {
		 System.out.println("NoMatch");
	 }
 }
 
 public TreeNode  stmtSequence() throws Exception
 {
	 TreeNode t = statement();
	 TreeNode p = t;
	 
	 while((token!=TokenType.ENDFILE)&&(token!=TokenType.END)&&(token!=TokenType.ELSE)&&(token!=TokenType.UNTIL))
	 {
		 match(TokenType.SEMI);
		 TreeNode q;
		 q= statement();
		 if(t==null)
		 {
			 t=p=q;
		 }
		 else
		 {   
			p.sibling=q;
			q=p;
		 }
	 }
	 
	return t;
 }
 
 public TreeNode  statement() throws Exception
 {   
	 //System.out.println("token");
	 //System.out.println(token);
	 TreeNode t = null;
	 //System.out.println(token);
	 if(token == TokenType.IF)
		 ifStmt();
	 else if(token == TokenType.REPEAT)
		 repeatStmt();
	 else if(token == TokenType.ID)
		 assignStmt();
	 else if(token == TokenType.READ)
		 {readStmt();}
	 else if(token == TokenType.WRITE)
		 writeStmt();
	 else
	 {
		 System.out.println("error1");
		 token = prase.getToken();
		 
		 
	 }
	 
	return t;
 }
TreeNode ifStmt() throws Exception
{
	TreeNode t = Utility.newStmtNode(preSet.stmtKind.IfK);
	match(TokenType.IF);
	if(t!=null)t.child[0] = exp();
	match(TokenType.THEN);
	if(t!=null)t.child[1] = stmtSequence();
	
	if(token== TokenType.ELSE)
	{
		match(TokenType.ELSE);
		if(t!=null)t.child[2] = stmtSequence();
	}
	match(TokenType.END);
	return t;
	
}
TreeNode repeatStmt() throws Exception
{
	TreeNode t = Utility.newStmtNode(preSet.stmtKind.RepeatK);
	match(TokenType.REPEAT);
	if(t!=null) t.child[0]= stmtSequence();
	match(TokenType.UNTIL);
	if(t!=null) t.child[1] = exp();
	return t;
	
	}
TreeNode assignStmt() throws Exception
{
	TreeNode t = Utility.newStmtNode(preSet.stmtKind.AssignK);
	if((t!=null)&&(token==TokenType.ID))
		t.IDname = preSet.returnString;
	match(TokenType.ID);
	match(TokenType.ASSIGN);
	if(t!=null)t.child[0] = exp();
	return t;
}

TreeNode readStmt()
{
	TreeNode t = Utility.newStmtNode(preSet.stmtKind.ReadK);
	match(TokenType.READ);
	if((t!=null)&&(token == TokenType.ID))
	{
		t.IDname = preSet.returnString;
		
	}
	match(TokenType.ID);
	return t;
}

TreeNode writeStmt() throws Exception
{
	TreeNode t = Utility.newStmtNode(preSet.stmtKind.WriteK);
	match(TokenType.WRITE);
	if(t!=null)
		t.child[0] = exp();
	return t;
}

TreeNode exp() throws Exception
{
	TreeNode t = simpleExp();
	if((token == TokenType.LT)||(token == TokenType.EQ))
	{
		TreeNode p = Utility.newExpNode(preSet.expKind.OpK);
		if(p!=null)
		{
			p.child[0]=t;
			p.opType = token;
			t=p;
		}
		match(token);
		if(t!=null)
			t.child[1]= simpleExp();
			
	}
	return t;
}

TreeNode simpleExp() throws Exception
{
	TreeNode t = term();
	while((token==TokenType.PLUS)||(token == TokenType.MINUS))
	{  
		TreeNode p = Utility.newExpNode(preSet.expKind.OpK);
		if(p!=null)
		{
			p.child[0]=t;
			p.opType = token;
			t=p;
		}
		match(token);
		if(t!=null)
			t.child[1]= term();
		
	}
	return t;
}

TreeNode term() throws Exception
{
	TreeNode t = factor();
	while((token==TokenType.TIMES)||(token == TokenType.OVER))
	{  
		TreeNode p = Utility.newExpNode(preSet.expKind.OpK);
		if(p!=null)
		{
			p.child[0]=t;
			p.opType = token;
			t=p;
		}
		match(token);
		if(t!=null)
			t.child[1]= factor();
		
	}
	return t;
	
}

TreeNode factor() throws Exception
{
	TreeNode t = null ;
	if(token == TokenType.NUM)
	{
		t = Utility.newExpNode(preSet.expKind.ConstK);
		if((t!=null)&&(token == TokenType.NUM))
		{   
			
			String str = preSet.returnString;
			//System.out.println(str);
			char []a =str.toCharArray();
			t.numValue = a[0]-48;
			match(TokenType.NUM);
			
		}
	}
	else if(token == TokenType.ID)
	{
		t = Utility.newExpNode(preSet.expKind.IdK);
		if((t!=null)&&(token == TokenType.ID))
		{
			t.IDname = preSet.returnString;
			match(TokenType.ID);
			
		}
	}
	else if(token == TokenType.LPAREN)
	{
		match(TokenType.LPAREN);
		t = exp();
		match(TokenType.RPAREN);
	}
	else
	{
		System.out.println("error2");
		token = prase.getToken();
	}
	return t;
}
public TreeNode parse() 
	{
		TreeNode t = new TreeNode();
		try {
			token = prase.getToken();
			//System.out.println(token);
			t=stmtSequence();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}
}
