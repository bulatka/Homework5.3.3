import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        openZip("D://Games/savegames/zipsaves.zip", "D://Games/savegames");

        GameProgress openProgress = openProgress("D://Games/savegames/save1.dat");
        System.out.println(openProgress);
        openProgress = openProgress("D://Games/savegames/save2.dat");
        System.out.println(openProgress);
        openProgress = openProgress("D://Games/savegames/save3.dat");
        System.out.println(openProgress);
    }

    public static void openZip(String zipPath, String filePath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String name;

            while ((entry = zin.getNextEntry()) != null) {
                name = filePath + "/" + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static GameProgress openProgress(String savePath) {
        GameProgress savedGameProgress = new GameProgress();
        try (FileInputStream fis = new FileInputStream(savePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            savedGameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedGameProgress;
    }
}