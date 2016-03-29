
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class Astar {
	private static Scanner sc;
	
	public static void main(String [] args){
		//size of grid
		int x = 32;
		int y = 18;
		
		//node
		class Node{
			boolean valid = false;
			int i, j, parenti, parentj;//nodes x and y coordinate and parents x and y coordinate
			int cost1 = Integer.MAX_VALUE;//distance from starting node with current available path
			int cost2 = Integer.MAX_VALUE;//distance from destination node
			int cost = Integer.MAX_VALUE;//combination of cost1 and cost2
			
			
		}
		
		//grid
		Node array[][] = new Node[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				array[i][j] = new Node();
				array[i][j].i = i;
				array[i][j].j = j;
			}
		}		
		
		//read file
		openFile();
		//fill up grid
		while(sc.hasNext()){
			String si = sc.next();
			String sj = sc.next();
			int i = Integer.parseInt(si.substring(1, si.indexOf(",")));
			int j = Integer.parseInt(sj.substring(0, sj.indexOf(")")));
			array[i][j].valid = true;
		}
		
		//close file
		closeFile();
		
		//start and end coordinates
		int ai = 1;int aj = 1;
		int bi = 31;int bj = 1;//B
		//int bi = 24;int bj = 17;//C
		
		//list containing nodes to be evaluated		
		ArrayList<Node> open = new ArrayList<Node>();
		//list containing nodes already evaluated
		ArrayList<Node> closed = new ArrayList<Node>();
		
		//values assigned to start point
		array[ai][aj].cost1 = 0;
		array[ai][aj].cost2 = (int)Math.sqrt(Math.pow(Math.abs(ai-bi), 2) + Math.pow(Math.abs(aj-bj), 2) )*10;
		array[ai][aj].cost = array[ai][aj].cost1 + array[ai][aj].cost2;
		array[ai][aj].parenti = ai;
		array[ai][aj].parentj = aj;
		
		//add start point to open list
		open.add(array[ai][aj]);
		
		//A*
		while(true){
			//initial assignment of current
			Node current = array[ai][aj];
			
			
			//find min cost node in open and make it the current node being looked at
			int min = Integer.MAX_VALUE;
			int minI = -1;
			for(int i = 0; i < open.size(); i++){
				if(open.get(i).cost < min){
					min = open.get(i).cost;
					current = open.get(i);
					minI = i;
				}
			}
			
			
			
			//remove current from open list and add to closed list
			open.remove(minI);
			closed.add(current);
			
			//if the node is the end node, end loop
			if(current.i == bi && current.j == bj){
				break;
			}
			
			//list of neighbors
			ArrayList<Node> neighbours = new ArrayList<Node>();
			if(current.i+1<x){
				neighbours.add(array[current.i+1][current.j]);
				if(current.j+1<y){
					neighbours.add(array[current.i+1][current.j+1]);
				}
				if(current.j-1 > -1){
					neighbours.add(array[current.i+1][current.j-1]);
				}
			}
			if(current.j+1<y){
				neighbours.add(array[current.i][current.j+1]);
			}
			if(current.j-1 > -1){
				neighbours.add(array[current.i][current.j-1]);
			}
			if(current.i-1 > -1){
				neighbours.add(array[current.i-1][current.j]);
				if(current.j+1<y){
					neighbours.add(array[current.i-1][current.j+1]);
				}
				if(current.j-1 > -1){
					neighbours.add(array[current.i-1][current.j-1]);
				}
			}
			
			
			//check neighbours
			for(Node neighbour : neighbours){
				
				//new potential costs
				int potential_cost1 = (int)Math.sqrt(Math.pow(Math.abs(current.i-neighbour.i), 2) + Math.pow(Math.abs(current.j-neighbour.j), 2) )*10;
				int potential_cost2 = (int)Math.sqrt(Math.pow(Math.abs(neighbour.i-bi), 2) + Math.pow(Math.abs(neighbour.i-bj), 2) )*10;
				int potential_cost = potential_cost1 + potential_cost2;
				
							
				
				//check if neighbour has been evaluated yet
				boolean inOpen = false; boolean inClosed = false;
				for(int i = 0; i < open.size(); i++){
					if(open.get(i).equals(neighbour)){
						inOpen = true;
					}
				}
				for(int i = 0; i < closed.size(); i++){
					if(closed.get(i).equals(neighbour)){
						inClosed = true;
					}
				}
				
				//if neighbour is invalid or has already been evaluated then skip it
				if(neighbour.valid == false || inClosed){
					continue;
				}
				
				//update potential if necessary and add to open set to be evaluated
				if(potential_cost1 < neighbour.cost1 || !inOpen){
					neighbour.cost1 = potential_cost1;
					neighbour.cost2 = potential_cost2;
					neighbour.cost = potential_cost;
					neighbour.parenti = current.i;
					neighbour.parentj = current.j;
					if(!inOpen){
						open.add(neighbour);
					}
				}
				
			
			}
		}
		
		//output
		int k = bi; int l = bj;
		while(true){
			System.out.println(k+ " " + l);
			int temp;
			temp = k;
			k = array[k][l].parenti;
			l = array[temp][l].parentj;
			if(k == ai && l == aj){
				System.out.println(k+ " " + l);
				break;
			}
			
		}
	}
	public static void openFile(){
		try{
			sc = new Scanner(new File("C:\\Users\\Mike\\Desktop\\p.txt"));
		}
		catch(Exception e){
			System.out.println("could not find file");
		}
	}
	public static void closeFile(){
		sc.close();
	}
	
	
	
	
	
	
}
