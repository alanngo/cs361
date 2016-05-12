import java.util.ArrayList;

/*
 Manages actions for subjects to objects, depending on permissions
 giving both subjects and objects associated security labels
 can't be changed after they are created
 maps subjects/objects to security labels

 Decides whether to perform the action based on BLP properties
 If the command is legal AND it follows BLP rules, then tell the object manager to perform the action
 ie check to make sure it doesn't exist in BadInstruction

 always returns an integer value to the subject: the value of the object read if the command was a legal read 0 otherwise.
 value returned is passed to the subject executing the instruction

 If the subject is performing a READ, it stores this value into its TEMP variable. Think of the
 reference monitor as a firewall around the ObjectManager. Note that the ObjectManager doesn't know 
 or care about labels or security; it just performs simple accesses.
 */
public class ReferenceMonitor {

	public InstructionObject object;
	ArrayList<Object> objList = new ArrayList<Object>();
	ArrayList<Subject> subList = new ArrayList<Subject>();

	/*
	 * Set up initiates actions based on inObj qualities
	 */
	ReferenceMonitor(InstructionObject inObj, ArrayList<Object> objList, ArrayList<Subject> subList) {

		this.object = inObj;
		this.objList = objList;
		this.subList = subList;
	}

	/*
	 * Searches through objects to see if the file exists.
	 * if file doesn't exist create it with the security level of the subject writing it
	 * if the file does exist, do nothing
	 */
	public void executeCreate() {

		//helper method that returns null if is exists, an object if it doesn't
		Object foundObject = search(objList);

		boolean foundSubject = false;
		int level = 0;
		//loop over subject list to find if it exists and get its level
		for (int i = 0; i < subList.size() && !foundSubject; i++) {
			if (subList.get(i).getName().equals(object.subjectName)) {
				level = subList.get(i).getLevel();
				foundSubject = true;
			}
		}
		if (foundObject == null) {
			objList.add(new Object(object.objectName, level));
			Object obj3 = new Object("OBJ", level);
		}
		// System.out.println(":::::executeCreate");
	}

	/*
	 * If the object exists and the subject has write access, destroy the object
	 * if either clause fails, do nothing
	 */
	public void executeDestroy() {

		Object foundObject = search(objList);

		Subject foundSubject = null;
		int level = 0;
		for (int i = 0; i < subList.size(); i++) {
			if (subList.get(i).getName().equals(object.subjectName)) {
				foundSubject = subList.get(i);
			}
		}
		if (foundObject != null && foundObject.getLevel() >= foundSubject.getLevel()) {
			objList.remove(new Object(object.objectName, level));
		}
		// System.out.println(":::::executeDestroy");
	}

	/*
	 * if the object level dominates the subject level, read the value in the object
	 * returns 0 or 1 based on whether or not LYLE was able to write to the file or not
	 */
	public int executeRead(int tempRead) {
		if (object.sub.getLevel() <= object.obj.getLevel()) {
			object.sub.setTemp(object.obj.getVal());
			// System.out.println(":::::executeRead");
			return 0;
		} else
			return 1;
	}

	/*
	 * if the object level dominates the subject level, write the value to the object.
	 */
	public void executeWrite() {
		if (object.sub.getLevel() <= object.obj.getLevel())
			object.obj.setVal(object.value);
		// System.out.println(":::::executeWrite");
	}

	
	/*
	 * Helper method: Returns null if not found. else returns object
	 */
	public Object search(ArrayList<Object> objList) {
		boolean foundObject = false;
		Object temp = null;
		for (int i = 0; i < objList.size() && !foundObject; i++) {
			if (objList.get(i).getName().equals(object.objectName)) {
				foundObject = true;
				temp = objList.get(i);
			}
		}
		return temp;
	}
}
