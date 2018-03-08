package New;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Kmean {
	Map<int[], Integer> map = new HashMap<int[], Integer>();
	Random Ran = new Random();
	int[][][] data;
	int[][] center;
	int K_quantity = 0;
	int iteration = 0;
	int[][] local;
	
	//建構子
	Kmean(int iteration,int[][][] data){
		K_quantity=256;
		local = new int[240000][2];
		int l = 0;
		this.iteration = iteration;
		this.data=new int[400][600][3];
		for (int y=0; y<data.length; y++){
	    	for (int x=0; x<data[y].length; x++){	
	    		this.data[y][x][0] = data[y][x][0];			
	    		this.data[y][x][1] = data[y][x][1];
	    		this.data[y][x][2] = data[y][x][2];
	    		local[l][0] = y;
	    		local[l][1] = x;
	    		map.put(local[l], 0);
	    		l++;
	    	}
	    }
		Run();
	}
	
	public void Run(){
		center = new int[K_quantity][3];//紀錄各中心之rgb
		K_point();
		Cluster(Copy(),1);
		Change();
	}
	
	//初始中心
	public void K_point(){
		for(int i = 0;i<K_quantity;i++){
			int y = Ran.nextInt(400);
			int x = Ran.nextInt(600);
			for(int j = 0;j<3;j++){
				center[i][j] = data[y][x][j];	
			}
			for(int k = 0;k<i;k++){
				if((center[i][0]==center[k][0])&&(center[i][1]==center[k][1])&&(center[i][2]==center[k][2])){
					i--;
					break;
				}
			}
			map.replace(local[y*600+x], i);
		}
	}
	
	//分群
	public void Cluster(int[][] o_center,int time){
		int l=0;
		for(int i = 0;i<400;i++){ //比較距離,分群
			for(int j = 0;j<600;j++){
				int dis = 442;
				for(int k = 0;k<K_quantity;k++){					
					int r_axis = (int)Math.pow(data[i][j][0]-o_center[k][0], 2);
					int g_axis = (int)Math.pow(data[i][j][1]-o_center[k][1], 2);
					int b_axis = (int)Math.pow(data[i][j][2]-o_center[k][2], 2);
					int d =(int) Math.sqrt(r_axis + g_axis + b_axis);
					if((d < dis)){
						dis = d;
						map.replace(local[l], k);//暫時歸類為第k群
					}
				}
				l++;
			}
		}
		Center();
		if(!End(o_center, center)&&(time < iteration)){	//
			System.out.println(time);
			time++;
			Cluster(Copy(),time);
		}
	}
	
	
	//找出中心
	public void Center(){
		
		for(int i = 0;i<K_quantity;i++){
			int count = 0;
			center[i][0]=0;
			center[i][1]=0;
			center[i][2]=0;
			for(int[] ks:map.keySet()){
				if(i == map.get(ks)){
					center[i][0] += data[ks[0]][ks[1]][0];	//r
					center[i][1] += data[ks[0]][ks[1]][1];	//g
					center[i][2] += data[ks[0]][ks[1]][2];	//b
					count++;
				}
			}
			if(count!=0){
				center[i][0] /= count;
				center[i][1] /= count;
				center[i][2] /= count;
			}
		}
	}

	//判斷是否收斂
	public boolean End(int[][] o_center,int[][] n_center){
		
		for(int i=0;i<K_quantity;i++){
			if((o_center[i][0]!=n_center[i][0])|(o_center[i][1]!=n_center[i][1])|(o_center[i][2]!=n_center[i][2])){
				return false;
			}
		}
		return true;
	}
	
	//複製
	public int[][] Copy(){
		int[][] o_center = new int[K_quantity][3];
		for(int i=0;i<K_quantity;i++){
			o_center[i][0]=center[i][0];
			o_center[i][1]=center[i][1];
			o_center[i][2]=center[i][2];
		}
		return o_center;
	}
	
	//降色
	public void Change(){
		for(int i = 0;i<K_quantity;i++){
			for(int[] ks:map.keySet()){
				if(i == map.get(ks)){
					data[ks[0]][ks[1]][0] = center[i][0];	//r
					data[ks[0]][ks[1]][1] = center[i][1];	//g
					data[ks[0]][ks[1]][2] = center[i][2];	//b
				}
			}
		}
	}
}
