import java.util.ArrayList;
 
public class PriorityQueue<E extends Comparable<? super E>>{
	int size;
	ArrayList<E> myCon;
	
	//default constructor for PriorityQueue
	public PriorityQueue(){
		size = 0;
		myCon = new ArrayList<E>();
	}
	
	//adds items into the queue based upon weight. 
	//pre: nothing
	//post: size ++ and the item is added into the queue based upon weight. 
	public void enqueue(E item){
		if(size == 0){
			myCon.add(item);
		}
		else{
			//there are already elements in this array so we need to do some comparing
			int indexToCompare = 0;
			boolean added = false;
			while(indexToCompare < myCon.size() && !added){
				E other = myCon.get(indexToCompare);
				if(item.compareTo(other) < 0){
					myCon.add(indexToCompare, item);
					added = true;
				}
				else{
					//ok here the item is either greater than or equal so we now increment indexToCompare
					indexToCompare ++;
					if(indexToCompare == myCon.size()){
						//well we got to the end and need to add to the end
						myCon.add(myCon.size(), item);
						added = true;
					}
				}
			}
		}
		size ++;
	}
	
	//method to dequeue the first element from the qeue
	//pre: size ! = 0
	public E dequeue(){
		if(size == 0)
			throw new IllegalArgumentException("Size cannot be zero when dequeueing");
		size --;
		return myCon.remove(0);
	}
	
	public int size(){
		return size;
	}

	
	public E get(int i){
		
		return (myCon.get(i));
	}
	
	public String toString(){
//		for(int i=0; i<myCon.size(); i++){
//			myCon.get(i).
//		}
		return "";
	}


}
