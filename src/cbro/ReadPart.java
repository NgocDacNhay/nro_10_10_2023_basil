package cbro;

import java.io.BufferedWriter;
import org.json.simple.JSONArray;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ReadPart
{
    public static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\NR_part";
    public static final String OUTPUT_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\JsonSql\\part.sql";
    
    public static void main(final String[] args) {
        final List<Part> parts = readParts("C:\\Users\\Administrator\\Desktop\\NR_part");
        final String sql = createSql(parts);
        writeSqlToFile(sql, "C:\\Users\\Administrator\\Desktop\\JsonSql\\part.sql");
        System.out.println("Done!");
    }
    
    private static List<Part> readParts(final String filePath) {
        final List<Part> parts = new ArrayList<Part>();
        try (final DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            for (int num = dis.readShort(), i = 0; i < num; ++i) {
                final int type = dis.readByte();
                int n = 0;
                if (type == 0) {
                    n = 3;
                }
                else if (type == 1) {
                    n = 17;
                }
                else if (type == 2) {
                    n = 14;
                }
                else if (type == 3) {
                    n = 2;
                }
                final JSONArray pis = new JSONArray();
                for (int k = 0; k < n; ++k) {
                    final JSONArray pi = new JSONArray();
                    pi.add((Object)dis.readShort());
                    pi.add((Object)dis.readByte());
                    pi.add((Object)dis.readByte());
                    pis.add((Object)pi);
                }
                parts.add(new Part(i, type, pis));
                System.out.println("ID Part " + i + " Type " + type + ", '" + pis.toJSONString() + "');");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }
    
    private static String createSql(final List<Part> parts) {
        final StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("USE part;\n\n");
        sqlBuilder.append("DROP TABLE IF EXISTS part;\n");
        sqlBuilder.append("CREATE TABLE part (\n");
        sqlBuilder.append("  id INT NOT NULL,\n");
        sqlBuilder.append("  type INT NOT NULL,\n");
        sqlBuilder.append("  DATA JSON NOT NULL,\n");
        sqlBuilder.append("  PRIMARY KEY (id)\n");
        sqlBuilder.append(");\n\n");
        for (final Part part : parts) {
            sqlBuilder.append("INSERT INTO part (id, type, DATA) VALUES (");
            sqlBuilder.append(part.getId()).append(", ");
            sqlBuilder.append(part.getType()).append(", ");
            sqlBuilder.append("'").append(part.getPi().toJSONString()).append("'");
            sqlBuilder.append(");\n");
        }
        return sqlBuilder.toString();
    }
    
    private static void writeSqlToFile(final String sql, final String filePath) {
        try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"))) {
            writer.write(sql);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class Part
    {
        private int id;
        private int type;
        private JSONArray pi;
        
        public Part(final int id, final int type, final JSONArray pi) {
            this.id = id;
            this.type = type;
            this.pi = pi;
        }
        
        public int getId() {
            return this.id;
        }
        
        public int getType() {
            return this.type;
        }
        
        public JSONArray getPi() {
            return this.pi;
        }
    }
}