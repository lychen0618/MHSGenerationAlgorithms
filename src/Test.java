import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Test {
    public static void main(String[] args) throws IOException {
        String fileName="./src/com/lychen/SHD/data/test.txt";
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(fileName));
        osw.write("\n");
        osw.write("10");
        osw.close();
    }
}
