package Assignment3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import Assignment3.CatTree.CatNode; 


public class CatTree implements Iterable <CatInfo> {
	// SOLE FIELD/ATTRIBUTE
    public CatNode root; // field that contains a REFERENCE to the root node

    // CONTRUCTORS
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    
    // METHODS THAT WHEN CALLED ON THE TREE, CALL 'CatNode' FUNCTIONS (lines 105-151) THAT WILL BE IMPLEMENTED
    
    // takes as input 'CatInfo' object and converts it to a 'CatNode' and calls 'CatNode.addCat()' with the 'CatNode' as input
    public void addCat(CatInfo c) {
        this.root = root.addCat(new CatNode(c));
    }
    
    
    public void removeCat(CatInfo c) {
        this.root = root.removeCat(c);
    }
    
    
    public int mostSenior() {
        return root.mostSenior();
    }
    
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    // ITERATOR METHOD
    public Iterator <CatInfo> iterator() {
        return new CatTreeIterator();
    }
    
    
    
    // PRIVATE NESTED CLASS FOR DEFINING NODES
    class CatNode {
        // FIELDS/ATTRIBUTES
        CatInfo data;   // 'CatInfo' object with the data for this cat
        CatNode senior; // this cat and all the children of its node have MORE seniority than the current node's cat
        CatNode same;	// this cat and all the children of its node have LESS seniority than the current node's cat
        CatNode junior; // this cat and all the children of its node have EXACTLY the same seniority than the current node's cat
        
        // CONSTRUCTOR
        public CatNode(CatInfo data) { // NOTE: each 'CatNode' is associated with data stored in a 'CatInfo' object
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        
        // TO STRING METHOD TO PRINT OUT OBJECT
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        
        
        // METHODS:
        
        // given 'CatNode c' and a 'CatNode catToAdd', "c.addCat(catToAdd)" adds the 'CatNode catToAdd' in the tree with root 'c'
        public CatNode addCat(CatNode c) {
        	// BASE CASE --> if the node was already added, return and break recursion
        	if(this.equals(c)) {
        		return this;
        	}

        	// if MORE SENIOR than cat at root 'c'...
        	if(c.data.monthHired < this.data.monthHired) {
        		// ...first check if the cat "senior" to this root is null
        		// set 'this.senior', i.e. the root's senior, to be 'c'
        		if(this.senior == null) {
        			this.senior = c;
        		}
        		
        		// otherwise, just add the cat 'c' as the senior to 'this' object
        		this.senior.addCat(c);
        	}

        	// if SAME SENIORITY than cat at root 'c' --> check fur thickness
        	if(c.data.monthHired == this.data.monthHired) {
        		// if this' same is null, then just set 'this.same' to be 'c'
        		if(this.same == null) {
        			this.same = c;
        		}
        		
        		// then check to see if cat to add's fur is thicker than root...
        		// if fur is thicker --> swap data between root and 'c' and add the old root to the new root with data 'c'
        		if(c.data.furThickness > this.data.furThickness) {
        			CatInfo temp = this.data;
        			this.data = c.data;
        			c.data = temp;
        		}
        		
        		// if fur is not thicker, call "addCat(c)" again but this time on SAME
        		this.same.addCat(c);
        	}
        	
        	// if LESS SENIOR than cat at root 'c'...
        	if(c.data.monthHired > this.data.monthHired) {
        		// ...first check if the cat "junior" to this root is null, if so (bc input CatNode is more junior to root CatNode), set 'this.junior', i.e. the root's junior, to be 'c'
        		if(this.junior == null) {
        			this.junior = c;
        		}
        		
        		// otherwise, just add the cat 'c' as the junior to 'this' object
        		this.junior.addCat(c);
        	}

            return this; 
        }
        
        
        
        
        // removes CatNode that has matching data "c" and returns CatNode function was called on
        public CatNode removeCat(CatInfo c) {
        	// return NULL if there is nothing in the root and so nothing in the tree that function is being called on
        	if(this.data == null) {
        		return null;
        	}
        	
        	
        	if(!this.data.equals(c)) {        		
        		// if the thing to be removed is SENIOR to the node --> go iterate through the SENIOR SIDE of the tree
            	if(c.monthHired < this.data.monthHired) {
            		if(this.senior != null) {
            			this.senior = this.senior.removeCat(c);
            		}
           		}
            		
            	// if the thing to be removed is JUNIOIR to the node --> go iterate through the JUNIOR SIDE of the tree
            	else if(c.monthHired > this.data.monthHired) {
            		if(this.junior != null) {            			
            			this.junior = this.junior.removeCat(c);
            		}
           		}
            	
            	else if(c.monthHired == this.data.monthHired) {
            		if(c.furThickness < this.data.furThickness) {
            			if(this.same != null) {
            				this.same = this.same.removeCat(c);
            			}
            		}        		
            	}
        	}
        	

        	// otherwise, the root/node is equal to 'c'
        	else {
        		// CASE 1: SAME has data
        		if(this.same != null) {
        			this.data = this.same.data;
        			this.same = this.same.same;
        		}
        		
        		// CASE 2: SAME is NULL but SENIOR is not... update THIS using SENIOR data
        		else if(this.same == null && this.senior != null) {
        			// update NODE
        			this.data = this.senior.data;        			
        			
        			// update JUNIOR
        			CatNode newNode = this.junior;
        			this.junior = this.senior.junior;
        			if(this.junior != null) {
        				this.junior.addCat(newNode);
        			}
        			
        			// update SAME
        			this.same = this.senior.same;
        			
        			// update SENIOR
        			this.senior = this.senior.senior;
        		}
        		        		
        		else if(this.same == null && this.senior == null) {
        			// CASE 3: SAME is NULL and SENIOR is NULL... update THIS using JUNIOR data
        			if(this.junior != null) {
        				// update NODE
        				this.data = this.junior.data;
        				
        				// update JUNIOR
        				this.junior = this.junior.junior;	
        			}
        			
        			// CASE 4: at a leaf
        			else {
        				return null;
        			}
        		}
        		
        	}
        		
        	// return the node that the function was called on
            return this;
        }
    
        
    
        
        // returns the month when the most experienced "CatNode" in the tree with root 'c' was hired
        // MAIN IDEA: because our tree is ORDERED, we know that the objects on the LEFT, or SENIOR, part of the tree are more senior -->
        //		 	  therefore we only need to recursively traverse the SENIOR part of the tree until we reach the leaf of the SENIOR
        //		 	  subtree, i.e., when what is most senior to the node we're at is NULL
        public int mostSenior() {
        	// if the node we're at has nothing, return 0
        	if(this.data == null) {
        		return 0;
        	}
        	
        	// if we're at the left most leaf of the CatTree, i.e. the most senior of cats, return the value from that node
        	if(this.senior == null) {
        		return this.data.monthHired;
        	}
        	
        	// otherwise, recursively traverse through the tree until we reach the left-most and farthest down node
        	else {
        		return this.senior.mostSenior();
        	}
        }
        
        
        
        
        
        // returns the integer value of the CatNode with the thickest fur in the tree associated with the root input
        // MAIN IDEA: because there is no real ordering around "furThickness", we have to compare, similarly to if we used a for loop -->
        //			  so we create a variable that will eventually hold the cat with the largest "furThickness" when compared to Cats in
        //			  the tree
        //			  BUT, again, there is no order, so we make two other variables for the SENIOR and JUNIOR max that we will then compare
        //			  to the ULTIMATE max and return that
        public int fluffiest() {
        	// if at the end/there is nothing, return 0
            if(this.data == null) {
            	return 0;
            }
            
            int fluffiest = this.data.furThickness;
            int seniorFluffiest, juniorFluffiest;
            
            // as long as we do not go past the leaf...
            if(this.senior != null) {
            	// set max SENIOR variable value to be the value of whatever is LEFT of THIS
            	seniorFluffiest = this.senior.fluffiest();
            	if(seniorFluffiest > fluffiest) { // compare the two values --> if SENIOR is more fluffy, make that new max, otherwise leave it
            		fluffiest = seniorFluffiest;
            	}
            }
            
            // as long as we do not go past the leaf...
            if(this.junior != null) {
            	// set max JUNIOR variable value to be the value of whatever is RIGHT of THIS
            	juniorFluffiest = this.junior.fluffiest();
            	if(juniorFluffiest > fluffiest) { // compare the two values --> if JUNIOR is more fluffy, make that new max, otherwise leave it
            		fluffiest = juniorFluffiest;
            	}
            }
            
            return fluffiest; // return whatever is stored as the highest value
        }
        
        
        
        
        // returns the number of cats hired from month 'monthMin' to 'monthMax' within the tree being looked at
        public int hiredFromMonths(int monthMin, int monthMax) {
        	// if the indexing is wrong or we are past the leaf, return 0
            if(monthMin > monthMax || this.data == null) {
            	return 0;
            }
            
            // counters for cats traversed that are in the input bounds
            int totalHired = 0;
            int seniorHired, juniorHired;
            
            // if the root/node we're at is in bounds --> add 1 to the TOTAL counter, then check if the following cases arise...
            // 1) if there is a value at SAME --> recursively call next SAME and add 1 every time we call it
            // 2) if there is a value at JUNIOR --> recursively call next JUNIOR and add 1 every time we call it
            // 3) if there is a value at SENIOR --> recursively call next SENIOR and add 1 every time we call it
            if(this.data.monthHired >= monthMin && this.data.monthHired <= monthMax) {
            	totalHired += 1;
            	
            	if(this.same != null) {
            		totalHired += this.same.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.junior != null) {
            		totalHired += this.junior.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	if(this.senior != null) {
            		totalHired += this.senior.hiredFromMonths(monthMin, monthMax);
            	}
            	
            	
            }
            
            // if not in the bounds, check to see if we are out of LOWER BOUNDS --> if so, adjust senior by one to the right and 
            // recursive call to add to total
            if(this.data.monthHired < monthMin && this.junior != null) {
        		seniorHired = this.junior.hiredFromMonths(monthMin, monthMax);
        		totalHired += seniorHired;
        	}
            
            // if not in the bounds, check to see if we are out of UPPER BOUNDS --> if so, adjust junior by one to the left and 
            // recursive call to add to total
            if(this.data.monthHired > monthMax && this.senior != null){
        		juniorHired = this.senior.hiredFromMonths(monthMin, monthMax);
        		totalHired += juniorHired;
        	}
            
            // return total found
            return totalHired;
        }
        
        
        
        
        // return CatInfo linked to the cat with the THICKEST FUR, hired in the month input --> if no cat found, return NULL
        public CatInfo fluffiestFromMonth(int month) {
        	CatInfo fluffiestFromMonth = null;
        	
        	if(this.data == null) {
        		return null;
        	}
        	
        	
            // if the node we're at is the correct month, check to see if there's a SAME value or not
            if(this.data.monthHired == month) {
            	// if there is a SAME, return the object with greater fur length
            	if(this.same != null) {
            		if(this.same.data.furThickness > this.data.furThickness) {
            			fluffiestFromMonth = this.same.data;
            		}
            	}
            	
            	// otherwise, return THIS node we're at
            	fluffiestFromMonth = this.data;
            }
            
            
            // otherwise, we are not at the right month, so figure out if the month is too big or too small to narrow down which side
            // of the tree we traverse
            else if(this.data.monthHired != month){
            	// if value too small, move over to the right, JUNIOR, side
                if(this.data.monthHired < month) {
                	if(this.junior != null) {
                		fluffiestFromMonth = this.junior.fluffiestFromMonth(month);
                	}
            	}
                
                // otherwise, the value is too big, so move over to the left, SENIOR, side
                else {
                	if(this.senior != null) {                		
                		fluffiestFromMonth = this.senior.fluffiestFromMonth(month);
                	}
                }
                
            }    
            
            return fluffiestFromMonth;
        }
        
        
        
        
        // when called on a CatNode 'c', the array "c.costPlanning(n)", which should have length n, should have in its
        // i-th cell, how much you should spend on month (243 + i) for all scheduled grooming appointments over this
        // time period, in the tree with root c
        public int[] costPlanning(int nbMonths) {
        	int[] costsPerMonth = new int[nbMonths];
        	int baseMonth = 243;
        	CatTree costTree = new CatTree(this);

        	for(int i=0; i<costsPerMonth.length; i++) {
            	Iterator<CatInfo> iter = costTree.iterator();
        		int monthCosts = 0;
        		CatInfo currentCatInfo;
        		
        		while(iter.hasNext()) {
        			currentCatInfo = iter.next();
        			if(currentCatInfo.nextGroomingAppointment == i + baseMonth) {
            			monthCosts += currentCatInfo.expectedGroomingCost;	
        			}
        		}
        		
        		costsPerMonth[i] = monthCosts;
        	}
        	
            return costsPerMonth;
        }
        
        
        
        
        
    }

  
    
        
    
    // NESTED CLASS THAT ALLOWS US TO ITERATE THROUGH THE TREE OF OBJECTS
    // returns a "CatTreeIterator" object that can be used to iterate through all the cats in the tree and should access the cats from MOST SENIOR to
    // MOST JUNIOR
    private class CatTreeIterator implements Iterator<CatInfo> {
    	// ITERATOR FIELDS/ATTRIBUTES
    	private ArrayList<CatInfo> catTreeNodes;
		private CatInfo currentCat;
    	
		
    	// ITERATOR METHOD/CONSTRUCTOR
        public CatTreeIterator() {
        	// initialize variables
        	catTreeNodes = new ArrayList<CatInfo>(); 		 // initializes "catTreeNodes" to be an ArrayList
    		catTreeNodes = this.inOrder(root, catTreeNodes); // fills up the "catTreeNodes" ArrayList in the correct order
    		currentCat = catTreeNodes.get(0);
        }
        
        
        
        // helper to travel through the node
        private ArrayList<CatInfo> inOrder(CatNode root, ArrayList<CatInfo> catTree) {
        	if(root == null) {
        		return null;
        	}
        	
        	if(root.senior != null) {
    			inOrder(root.senior, catTree);
    		}
    		
    		if(root.same != null) {
    			if(root.same.data.furThickness < root.data.furThickness) {
    				inOrder(root.same, catTree);
    			}
    		}
    		
    		catTree.add(root.data);
			
    		if(root.junior != null) {
    			inOrder(root.junior, catTree);
    		}
        	
        	return catTree;
        }
        
        
        
        
        public CatInfo next() {
        	CatInfo currentCatToReturn = currentCat;
        	
        	if(catTreeNodes.indexOf(currentCat) < catTreeNodes.size() - 1) {
            	currentCat = catTreeNodes.get(catTreeNodes.indexOf(currentCat) + 1);            	
            	return currentCatToReturn;
        	}
        	
        	currentCat = null;
        	return currentCatToReturn;
        }
        
        
        
        public boolean hasNext() {
        	return (currentCat != null);
        }
        
    }
    
  
    
    
    
}

