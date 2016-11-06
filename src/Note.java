import java.util.Calendar;

public class Note {
	private String record;
	private int day;
	private int month;
	private int year;
	private boolean important;
	
	public Note(){
		record = "";
		day = 1;
		month = 1;
		year = 1;
		important = false;
	}
	
	public Note(String _record, int _day, int _month, int _year, boolean _important){
		record = _record;
		day = _day;
		month = _month;
		year = _year;
		important = _important;
	}
	
	public String getRecord(){
		return record;
	}
	
	public int getDay(){
		return day;
	}
	
	public int getMonth(){
		return month;
	}
	
	public int getYear(){
		return year;
	}
	
	public boolean getImportance(){
		return important;
	}
	
	public void setRecord(String _record){
		if(!_record.equals(null))
		record = _record;
	}
	
	public void setDay(int _day){
		if(_day > 0 && _day <= 31)
			if(_day > day)
				day = _day;
	}
	
	public void setMonth(int _month){
		if(_month > 0 && _month <= 12 )
			month = _month;
	}
	
	public void setYear(int _year){
		if(_year >= (Calendar.getInstance().get(Calendar.DAY_OF_YEAR)))
			year = _year;
	}
	
	public void setImportance(boolean _imp){
		if(_imp == true)
			important = !important;
	}

	@Override
	public String toString() {
		return "Note [record=" + record + ", day=" + day + ", month=" + month + ", year=" + year + ", important="
				+ important + "]";
	}
}
