import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkWithFile {
	File file;
	Note note;
	ArrayList<Note> list;
	
	public WorkWithFile(){
		file = new File("/D/myNotes.txt");
		note = new Note();
		list = new ArrayList<Note>();
	}
	
	public WorkWithFile(String filename){
		file = new File(filename);
		note = new Note();
		list = new ArrayList<Note>();
	}
	
	public void readFile(String filename){
		Scanner sc;
        try {
            sc = new Scanner(file);
            while(sc.hasNext()){
            	String line = sc.nextLine();
            	String[] data = line.split("/");
            	note.setRecord(data[0]);
            	note.setDay(Integer.parseInt(data[1]));
            	note.setMonth(Integer.parseInt(data[2]));
            	note.setYear(Integer.parseInt(data[3]));
            	note.setImportance(Boolean.parseBoolean(data[4]));
            	list.add(note);
            	note = new Note();
            }
            
        } catch (FileNotFoundException ex) {
            System.out.println("Error on writing in file");
        }
	}
	
	public void writeInFile(Object...objects) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
						
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
					
			for (int i = 0; i < objects.length; ++i) {
				if (i == 0)
					out.print("" + objects[i]);
				else
					out.print("/" + objects[i]);
			}
			out.println();
			bw.close();		
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeInFile(String _record, int _day, int _month, int _year, boolean _param){
		try{
			if(!file.exists()){
				file.createNewFile();
			}
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(_record + "/" + _day + "/" + _month + "/" + _year + "/" + _param + "\n");
			bw.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void writeInFile(){
		try{
			if(!file.exists()){
				file.createNewFile();
			}
		
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Initial text/0/0/0/0\n");
			bw.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public File getFile(){
		return file;
	}
	
	public void setFile(File _file){
		if(_file.exists())
			file = _file;
	}
	
	public String getName(){
		return file.getName();
	}
	
	public ArrayList<Note> getList(){
		return list;
	}
}
