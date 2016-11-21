import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wso2123 on 11/21/16.
 */
public class CSVMerge {
    public static void merge(String path) throws IOException {
        ArrayList<BufferedReader> br_array = new ArrayList<>();
        ArrayList<String> file_name_array = new ArrayList<>();

        File folder = new File(path);
        File[] file_array = folder.listFiles();
        for(int i=0; i<file_array.length; i++){
            if(file_array[i].isFile()){
                //Loop files
                String file_name = file_array[i].getName();
                String file_path = path + file_name;

                file_name_array.add(file_name.replace(".csv", ""));
                br_array.add(new BufferedReader(new FileReader(file_path)));
            }
        }


        FileWriter fw= new FileWriter("merge.txt", true);
        String line = "";
        String row = "";
        boolean header = false;

        while (line != null){
            if(!header){
                row = "t," + file_name_array.get(0);
                br_array.get(0).readLine();
            }else{
                row = br_array.get(0).readLine();
            }

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

            if (!header)
                header = true;

            if (row == null)
                continue;

            fw.write(row + "\n");
            System.out.println(row);

        }
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        merge("/home/wso2123/Documents/MyProjects/csvmerger/csv/");
    }
}
