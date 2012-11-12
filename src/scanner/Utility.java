package scanner;
import scanner.preSet;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-11 下午2:19:46 
 * 类说明 
 */
public class Utility {
	public static TreeNode newStmtNode(preSet.stmtKind kind)
	{
		TreeNode t = new TreeNode();
		if(t==null)
			System.out.println("error");
		else
		{
			for(int i=0;i<3;i++)
				t.child[i]=null;
			t.sibling = null;
			t.sk = kind;
			t.nk = preSet.nodeKind.StmtK;
			t.lineno = preSet.lineNum;
			
		}
		return t;
	}
	
	public static TreeNode newExpNode(preSet.expKind kind)
	{
		TreeNode t = new TreeNode();
		if(t==null)
			System.out.println("error");
		else
		{
			for(int i=0;i<3;i++)
				t.child[i]=null;
			t.sibling = null;
			t.ek = kind;
			t.nk = preSet.nodeKind.ExpK;
			t.lineno = preSet.lineNum;
		}
		return t;
	}
	
	public static int indentno = 0;
	
	public static void printSpaces()
	{
		int i;
		for(i=0;i<indentno;i++)
			System.out.print(" ");
	}
	
	public static void printTree (TreeNode tree)
	{   
		System.out.println("hello");
		int i;
		indentno++;
		while(tree!=null)
		{   
			
			printSpaces();
			if(tree.nk == preSet.nodeKind.StmtK)
			{
				if(tree.sk == preSet.stmtKind.IfK)
				{
					System.out.println("If");
					break;
				}
				else if(tree.sk == preSet.stmtKind.RepeatK)
				{
					System.out.println("Repeat");
					break;
				}
				else if(tree.sk == preSet.stmtKind.AssignK)
				{
					System.out.println("Assign to:");
					System.out.println(tree.IDname);
					break;
				}
				else if(tree.sk == preSet.stmtKind.ReadK)
				{
					System.out.println("Read:");
					System.out.println(tree.IDname);
					break;
				}
				else if(tree.sk == preSet.stmtKind.WriteK)
				{
					System.out.println("Write:");
					System.out.println(tree.IDname);
					break;
				}
				else
				{
					System.out.println("Unknow ExpNode kind");
					break;
				}
			}
			
			for(int j =0 ;j<3;j++)
			{
				printTree(tree.child[j]);
			}
			tree = tree.sibling;
		}
		indentno--;
	}
	
}
