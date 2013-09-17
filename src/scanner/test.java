package scanner;
//master
//masterpush
//devpull
import java.io.IOException;
import scanner.Utility;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-2 上午12:44:49 
 * 类说明 
 */
public class test {
public static void main(String[] args) throws Exception{
		TinyScanner scanner = new TinyScanner();
		/*for(int i=0;i<35;i++)
		{
		//scanner.getNextChar();
		scanner.getToken();
		}*/
	TinyPraser praser = new TinyPraser();
	if(praser.parse()==null) System.out.println("failed");
    Utility.printTree(praser.parse());
    
		
	}
}
