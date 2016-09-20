package Node;
import java.util.Vector;


public class ArrayNode {

	private String Chrom = "";
	private int Start = 0;
	private int Stop = 0;
	private Vector<TagNode> Data = new Vector<TagNode>();
	private Vector<MyTagNode> List = new Vector<MyTagNode>();
		
	public ArrayNode(){
			
	}
	public ArrayNode(String chr,int start,int stop,Vector<TagNode> data,Vector<MyTagNode> list){
		Start = start;
		Stop = stop;
		Chrom = chr;
		Data = data;
		List = list;
	}
		public void setTemp(Vector<TagNode> data){
			Data = data;
		}
		
		public Vector<TagNode> getData(){
			return Data;
		}
		
		public void setList(Vector<MyTagNode> list){
			List = list;
		}
		public Vector<MyTagNode> getList(){
			return List;
		}
		
		
		public int getStart(){
			return Start;
		}
		public void setStart(int start){
			Start = start;
		}
		
		public int getStop(){
			return Stop;
		}
		public void setStop(int stop){
			Stop = stop;
		}
		
		public String getChrom(){
			return Chrom;
		}
		public void setChrom(String chr){
			Chrom = chr;
		}
	
	

}
