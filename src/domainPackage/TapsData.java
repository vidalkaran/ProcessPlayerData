package domainPackage;

import java.util.Arrays;

public class TapsData extends DataContainer
{
	boolean isSwipe;
	float timeStart;
	float timeEnd;
	String positionStart;
	String positionEnd;
	
	public boolean isSwipe() {
		return isSwipe;
	}
	public float getTimeStart() {
		return timeStart;
	}
	public float getTimeEnd() {
		return timeEnd;
	}
	public String getPositionStart() {
		return positionStart;
	}
	public String getPositionEnd() {
		return positionEnd;
	}
	
}
