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
	
	private InstructionObject object ;
	
	/*
	Set up initiates actions based on inObj qualities
	*/
	ReferenceMonitor(InstructionObject inObj){
		this.object = inObj; 
		
		//if input is accepted
		if(object.goodSyntax){

			//Read the object
			if(object.isRead && object.sub.getLevel() >= object.obj.getLevel()) {
				executeRead();
			}
			//write to the object.
			else if(object.isWrite && object.sub.getLevel() <= object.obj.getLevel()) {
				executeWrite();
			} 
			//Write 0 if read unsuccessful
			else if(object.isRead) {
				badRead();
			} 
		}
		
	}
	
	/*
	Set subject value to 0
	*/
	private void badRead() {
		object.sub.setTemp(0);
	} 

	/*
	Set subject value to object value
	*/
	public void executeRead() { 
		object.sub.setTemp(object.obj.getVal());
	}
 
	/*
	Reset object value 
	*/
	public void executeWrite() { 
		object.obj.setVal(object.value);
	} 
}
