import java.util.ArrayList;

public class InstructionObject {

	String subjectName = "";
	String objectName = "";
	int value = 0;
	boolean goodSyntax = true;
	Subject sub = null;
	Object obj = null;
	boolean sleep = false;
	int encodedBit = 0;
        StringBuffer commands = new StringBuffer();
        
	ArrayList<Object> objList = new ArrayList<Object>();
	ArrayList<Subject> subList = new ArrayList<Subject>();

	/*
	 * Runs different code depending on if
	 */
	public InstructionObject(int bit, ArrayList<Object> objList,ArrayList<Subject> subList) {

		this.objList = objList;
		this.subList = subList;

		//if Hal is sending a 0 then we need to create a hal obj, else create a hal obj
		if (bit == 0) {
			sub = subList.get(0);
		} 
		else if (bit == 1) {
			sub = subList.get(1);
		}
		obj = objList.get(0);

		ReferenceMonitor object = new ReferenceMonitor(this, objList, subList);
		if (bit == 0){
                    create(0); // CREATE HAL OBJ
                    commands.append("CREATE HAL OBJ\n");
                }
		object.executeCreate();

		create(1); // CREATE LYLE OBJ
		write(); // WRITE LYLE OBJ 1
		read(); // READ LYLE OBJ
		destroy(0); // DESTROY LYLE OBJ
                
		object.executeCreate();
		object.executeWrite();
		encodedBit = object.executeRead(bit);
		object.executeDestroy();

		destroy(1); // DESTROY HAL OBJ
		object.executeDestroy();
                
                //adding to strig buffer for the log file  
                commands.append("CREATE LYLE OBJ\n");
                commands.append("WRITE LYLE OBJ 1\n");
                commands.append("READ LYLE OBJ\n");
                commands.append("DESTROY LYLE OBJ\n");
                commands.append("DESTROY HAL OBJ\n");  
	}

	//
	public void create(int bit) {
		if (bit == 0) {
			subjectName = "hal";

		} else if (bit == 1) {
			subjectName = "lyle";
		}
		objectName = "obj";
		Subject sub1 = subList.get(0);
		Subject sub2 = subList.get(1);

		int level = (sub1.getName().equals(subjectName)) ? sub1.getLevel() : sub2.getLevel();
		objList.add(new Object(objectName, level));

		// System.out.println("subjectName: "+subjectName+" objectName: "+objectName);
	}

	//
	public void write() {
		subjectName = "Lyle";
		objectName = "Obj";
		value = 1;
	}

	//
	public void read() {
		subjectName = "Lyle";
		objectName = "Obj";

	}

	//
	public void destroy(int bit) {
		if (bit == 0)
			subjectName = "Lyle";
		else if (bit == 1)
			subjectName = "Hal";
		objectName = "Obj";
	}
}

