package scanner;
import java.io.*;
import java.util.*;

import scanner.preSet.*;
/** 
 * @author itzijing@gmail: 
 * @version 创建时间：2012-11-1 下午9:05:03 
 * 类说明 
 */
public class TinyScanner {
    
	static int bufferPos=0;
	static boolean ifFirst=true;
	//static StringTokenizer tokenizer;
	static char lineBuf[] = new char [260];
	static int linePos=0;
	static BufferedReader br ;
	static int bufSize=10;
	static int count=0;
	static boolean eofFlag=false;
	
	public enum status{
		INNUM,INID,INASSIGN,INCOMMENT,START,DONE;
	}
	
	public  char getNextChar() throws Exception
	{  
		if(!ifFirst)
	{
		if(!(linePos<bufSize))
		{
			if(br.read(lineBuf,0,10)!=-1)
			{
				linePos=0;
				return lineBuf[linePos++];
			}
			else
			{   
				eofFlag=true;
				br.close();
				return '!';
			}
		}
		else
		{   
			return lineBuf[linePos++];
		}
	}
	else
	{
		ifFirst=false;
		br = new BufferedReader(new FileReader("tiny.tiny"));
	    br.read(lineBuf,0,10);
	    linePos++;
		return lineBuf[0];
	}
		
	}
	
	public void unGetNextChar()
	{
		if(!eofFlag)
			linePos--;
	}
    
	/*List<Map>list = new ArrayList<Map>();
	 Map reservedWords = new HashMap();
    reservedWords.put("aa","bb");
	for(int i=0;i<8;i++)
		reservedWords[i] = new HashMap();
	reservedWords[0]*/
	
	public boolean isDigit (char ch)
	{
		int c = (int)ch;
		c=c-48;
		if(c>=0&&c<=9)
			return true;
		else
			return false;
		
	}
	
	public boolean isAlpha (char ch)
	{   
		if(ch=='\n') { return false;}
		if((ch>='a'&&ch<='z')||(ch>='A'&&ch<='Z'))
			return true;
		else
			return false;
			
	}
	
	public TokenType reservedLookup(String s)
	{   
		
		//System.out.println((int)s.charAt(4));
		int j=0;
		for(int i=0;i<s.length();i++)
		{
			if((int)s.charAt(i)==0)
				{j=i;
				
				break;
				}
			
			
				
		}
		
		s=s.substring(0, j);
		TokenType tok;
		if(s.equals("if"))
		{
			tok = TokenType.IF;
			return tok;
		}
		else if(s.equals("then"))
		{
			tok = TokenType.THEN;
			return tok;
		}
		else if(s.equals("else"))
		{
			tok = TokenType.ELSE;
			return tok;
		}
		else if(s.equals("end"))
		{   //System.out.println("zijing");
			tok = TokenType.END;
			return tok;
		}
		else if(s.equals("repeat"))
		{
			tok = TokenType.REPEAT;
			return tok;
		}
		else if(s.equals("until"))
		{
			tok = TokenType.UNTIL;
			return tok;
		}
		else if(s.equals("read"))
		{  
			tok = TokenType.READ;
			return tok;
		}
		else if(s.equals("write"))
		{
			tok = TokenType.WRITE;
			return tok;
		}
		else
			{  return TokenType.ID;}
			
	}
	public TokenType getToken() throws Exception
	{   
		char []tokenString = new char[50];
		int tokenStringIndex = 0;
		TokenType type = TokenType.ERROR;
		status state = status.START;
		boolean commentSymble;// if it's symbol in commnt ,commentSymble = false
		
		while(state != status.DONE)
		{
			char c = getNextChar();
			// System.out.println((int)c);
			//if(c=='\n') System.out.println("meimei");
			//System.out.print(c);
			commentSymble = true;
			switch(state)
			{
			case START :
				if(isDigit(c))
					state = status.INNUM;
				else if(isAlpha(c))
					state = status.INID;
				else if(c == ':')
					state = status.INASSIGN;
				else if((c==' ')||(c=='\t')||(c=='\n')||(c=='\r'))
					{commentSymble = false;}
				else if(c=='{')
				{
					commentSymble = false;
					state = status.INCOMMENT;
				}
				else
				{
					state = status.DONE;
					switch(c)
					{
					case '!':
					{
						commentSymble = false;
						type = TokenType.ENDFILE;
						break;
					}
					case '=':
					{
						type = TokenType.EQ;
						break;
					}
					case '<':
					{
						type =TokenType.LT;
						break;
					}
					case '+':
					{
						type = TokenType.PLUS;
						break;
					}
					case '-':
					{
						type = TokenType.MINUS;
						break;
					}
					case '*':
					{
						type = TokenType.TIMES;
						break;
					}
					case '/':
					{
						type = TokenType.OVER;
						break;
					}
					case '(':
					{
						type =TokenType.LPAREN;
						break;
					}
					case ')':
					{
						type = TokenType.RPAREN;
						break;
					}
					case ';':
					{
						type = TokenType.SEMI;
						break;
					}
					default :
						type = TokenType.ERROR;
						break;
						
					}
				}
				break;
			case INCOMMENT:
				commentSymble = false;
				if(c=='!')
				{
					state = status.DONE;
					type = TokenType.ENDFILE;
				}
				else if(c==')') state = status.START;
				break;
			case INASSIGN:
				state = status.DONE;
				if(c=='=')
					type = TokenType.ASSIGN;
				else
				{
					unGetNextChar();
					commentSymble = false;
					type =TokenType.ERROR;
				}
				break;
			case INNUM:
				if(!isDigit(c))
				{
					unGetNextChar();
					commentSymble = false;
					state = status.DONE;
					type = TokenType.NUM;
				}
				break;
			case INID:
				if(!isAlpha(c))
				{   
				  
					unGetNextChar();
					commentSymble = false;
					state = status.DONE;
					type = TokenType.ID;
				}
			    break;
			    default:
			    	state = status.DONE;
			    	type = TokenType.ERROR;
			    	break;
					
				
			}
			
			if(commentSymble)
				{
				tokenString[tokenStringIndex++]=c;
				//
				//System.out.println(preSet.tokenString[--tokenStringIndex]);
				}
			if(state == status.DONE)
			{   
				
				tokenString[tokenStringIndex]='\0';
				if(type.equals(TokenType.ID))
					{type = reservedLookup(String.valueOf(tokenString));}
				preSet.returnString = String.valueOf(tokenString);
				System.out.println(preSet.returnString);
				//System.out.println(tokenString);
				
			}
			//return type;
			
		}
		
		return type;
		
		
		
	}
}


/*if(!ifFirst)
{
	if(tokenizer.hasMoreTokens())
	{
		s = tokenizer.nextToken();
		System.out.println(s);
		return s;
		
	}
	else
		{
		if(str!=null)
		{
		str=br.readLine();
		bufferPos=0;
		tokenizer = new StringTokenizer(str);
		s = tokenizer.nextToken();
		return s;
		}
		else
		{
			br.close();
			s="EOF";
			System.out.println(s);
			return s;
		}
		}

	
}
else
{
ifFirst=false;
 br = new LineNumberReader(new FileReader("tiny.tiny"));
str=br.readLine();
bufferPos=0;
tokenizer = new StringTokenizer(str);
s = tokenizer.nextToken();
System.out.println(s);
return s;

}*/
