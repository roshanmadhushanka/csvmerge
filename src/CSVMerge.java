import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVMerge {
    public static void merge(String path) throws IOException {
        //Store file readers
        ArrayList<BufferedReader> br_array = new ArrayList<>();
        //Store file names
        ArrayList<String> file_name_array = new ArrayList<>();


        File folder = new File(path);
        File[] file_array = folder.listFiles();


        for(int i=0; i<file_array.length; i++){
            if(file_array[i].isFile()){
                //Loop files
                String file_name = file_array[i].getName(); //File name
                String file_path = path + file_name;        //File path

                file_name_array.add(file_name.replace(".csv", "")); //Add file name
                br_array.add(new BufferedReader(new FileReader(file_path))); //Add file path
            }
        }

        //Create output file
        FileWriter fw= new FileWriter("merge.txt", true);

        String line = ""; //Line reade by the reader
        String row = ""; //Merge row

        boolean header = false; //Keep track of the headers

        while (line != null){

            if(!header){
                row = "t," + file_name_array.get(0); //Create header with file name
                br_array.get(0).readLine(); //Continue first line of the file
            }else{
                //first file
                row = br_array.get(0).readLine();
            }

            //Rest of the files
            for(int i=1; i<br_array.size(); i++){
                if(!header){
                    row += "," + file_name_array.get(i);
                    br_array.get(i).readLine();
                }else{
                    line = br_array.get(i).readLine();
                    if(line == null)
                        break;
                    row += "," + line.split(",")[1];
                }
            }

            //Check header state as true
            if (!header)
                header = true;

            //If row is null continue, eventually kill the loop
            if (row == null)
                continue;

            //Write line in the output file
            fw.write(row + "\n");

            //For testing purposes
            System.out.println(row);

        }

        //Close file
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        merge("/home/wso2123/Documents/MyProjects/csvmerger/csv/");
    }
}
