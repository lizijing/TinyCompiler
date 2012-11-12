package scanner;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-6 下午4:02:07 
 * 类说明 
 */

import scanner.preSet;
import scanner.preSet.TokenType;
public class TreeNode {
    TreeNode []child = new TreeNode[3];
    TreeNode sibling ;
	
	preSet.stmtKind sk;
	
	preSet.nodeKind nk;
	
	preSet.expKind ek;
    
	String IDname;
	preSet.TokenType opType;
	int numValue;
    int lineno;
    public TreeNode()
    {
    	for(int i=0;i<3;i++)
    		child[i]=null;
    	sibling = null;
    	sk = preSet.stmtKind.IfK;
    	nk = preSet.nodeKind.ExpK;
    	ek = preSet.expKind.ConstK;
    	IDname = "";
    	opType = TokenType.LT;
    	numValue = 0;
    	lineno =0;
    }
    
}
