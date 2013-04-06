import java.io.*;
import java.util.*;
import java.lang.*;

public class stores {
	// the set of constraints 
	private Vector<Constraint> constraints = new Vector<Constraint> (); 
	// the set of variables 
	private Vector<String> variables = new Vector<String> (); 
	// the set of domains 
	private Vector<Domain> domains = new Vector<Domain> (); 
	
	public stores(reader r) {
		Iterator<String> itStore = r.getStore().iterator();
	     
   	 	for (; itStore.hasNext(); ) {
   	 		String item = itStore.next(); 	 		

   	 		if (item.indexOf(":") == -1) {
   	 			// read in a constraint 
   	 		    Constraint c = new Constraint(item);
   	 		    constraints.add(c);
   	 		} 
   	 		else {
   	 			// read in a domain 
   	 			Domain d = new Domain(item);
   	 			domains.add(d);
   	 			variables.add(d.getName());
   	 		}
     	}
	}
	// return the variables 
	public Vector<String> getVariables () {
		return variables;
	}
	// return the domains 
	public Vector<Domain> getDomains () {
		return domains;
	}	
	// return the set of constraints 
	public Vector<Constraint> getConstraints () {
		return constraints;
	}
	// print the CSP problem 
	public void printStores() {
   	 	Iterator<Domain> it = domains.iterator();
        
   	 	for (; it.hasNext(); ) {
   	 		Domain d = it.next(); 
   	 		d.printDomain();
     	}

   	 	Iterator<Constraint> ic = constraints.iterator();
        
   	 	for (; ic.hasNext(); ) {
   	 		Constraint c = ic.next(); 
   	 		System.out.println(c.toString());
     	}
   	 	
	}

 	// checking whether a problem is "well-defined": variables 
	// occurring in the constraints should be defined 
	
	public boolean well_defined() {

   	 	Iterator<Constraint> ic = constraints.iterator();
        
   	 	for (; ic.hasNext(); ) {
   	 		Constraint c = ic.next(); 
   	 		String first = c.getFirst(); 
   	 		String second = c.getSecond();
   	 		
   	 		if (variables.contains(first) == false) return false; 
   	 		if (variables.contains(second) == false) return false; 
   	 	
     	}
		
		return true; 
	}
	
}
