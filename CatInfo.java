package Assignment3;


public class CatInfo {
	final String name;				   // name of the cat
	final int monthHired;			   // time at which the cat was hired
	final int furThickness;			   // average thickness of the cat's fur in millimeters
	final int nextGroomingAppointment; // month in which we expect the next grooming appointment
	final int expectedGroomingCost;	   // expected cost of the next grooming appointment
	
	public CatInfo(String name, int monthHired, int furThickness, int nextGroomingAppointment, int expectedGroomingCost) {
		this.name = name;
		this.monthHired = monthHired;
		this.furThickness = furThickness;
		this.nextGroomingAppointment = nextGroomingAppointment;
		this.expectedGroomingCost = expectedGroomingCost;
	}
	
	
	public String toString() {
		String result = this.name + "(" + this.monthHired + " , " + this.furThickness + ")";
		return result;
	}
	
	
	public boolean equals(CatInfo b) {
		boolean temp = this.name.equals(b.name);
		temp = temp && this.nextGroomingAppointment == b.nextGroomingAppointment;
		temp = temp && this.monthHired == b.monthHired;
		temp = temp && this.furThickness == b.furThickness;
		temp = temp && this.expectedGroomingCost == b.expectedGroomingCost;
		return temp;
	}
	
	
	
}
