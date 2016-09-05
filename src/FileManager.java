import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hans on 9/5/16.
 */
public class FileManager {

    private PaintFrame frame;

    FileManager(PaintFrame frame) {
        this.frame = frame;
    }

    public void save() {
        JFileChooser fileSaver = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".pb Painting Board File", "pb");
        fileSaver.setFileFilter(filter);
        fileSaver.setSelectedFile(new File("Untitled.pb"));
        boolean done = false;
        do {
            if (fileSaver.showSaveDialog(frame.getRootPane()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File saveFile = fileSaver.getSelectedFile();
                    if (saveFile.exists()) {
                        if (JOptionPane.showConfirmDialog(frame.getRootPane(), "File "
                                        + saveFile.getName() + " already exists.\n"
                                        + "Do you want to overwrite it?", "Overwrite?",
                                JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
                            continue;
                    }
                    FileOutputStream saverFileStream = new FileOutputStream(saveFile);
                    ObjectOutputStream saverObjectStream = new ObjectOutputStream(saverFileStream);
                    saverObjectStream.writeObject(PaintPanel.buffer);
                    saverObjectStream.writeObject(frame.getPen());
                    saverObjectStream.close();
                    done = true;
                } catch (Exception fe) {
                    JOptionPane.showMessageDialog(frame.getRootPane(), "Error saving file!\n"
                            + fe.toString(), "Save failed", JOptionPane.ERROR_MESSAGE);
                }
            }
            done = true;
        } while (!done);
    }

    public void load() {
        JFileChooser fileLoader = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ".pb Painting Board File", "pb");
        fileLoader.setFileFilter(filter);
        boolean done = false;
        do {
            if (fileLoader.showOpenDialog(frame.getRootPane()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File loadFile = fileLoader.getSelectedFile();
                    if (!loadFile.exists()) {
                        JOptionPane.showMessageDialog(frame.getRootPane(), "File "
                                        + loadFile.getName() + " does not exist!",
                                "File does not exist", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    FileInputStream loadFileStream = new FileInputStream(loadFile);
                    ObjectInputStream loadObjectStream = new ObjectInputStream(loadFileStream);
                    Object obj1 = loadObjectStream.readObject();
                    Object obj2 = loadObjectStream.readObject();
                    loadObjectStream.close();
                    if (!(obj1 instanceof CopyOnWriteArrayList<?> && obj2 instanceof PenPoint)) {
                        JOptionPane.showMessageDialog(frame.getRootPane(), "Resolving file "
                                        + loadFile.getName() + " failed!",
                                "File broken", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    PaintPanel.buffer = (CopyOnWriteArrayList<Path>) obj1;
                    frame.getPaintPanel().loadToNetwork();
                    frame.setPen((PenPoint) obj2);
                    done = true;
                } catch (java.io.StreamCorruptedException streamEx) {
                    JOptionPane.showMessageDialog(frame.getRootPane(), "Resolving file failed!\n"
                                    + "Exception message:\n" + streamEx.toString(),
                            "File broken", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException classEx) {
                    JOptionPane.showMessageDialog(frame.getRootPane(), "Resolving file failed!\n"
                                    + "Exception message:\n" + classEx.toString(),
                            "File broken", JOptionPane.ERROR_MESSAGE);
                } catch (java.io.IOException ioEx) {
                    JOptionPane.showMessageDialog(frame.getRootPane(), "Input/Output exception caught!\n"
                                    + "Exception message:\n" + ioEx.toString(),
                            "Exception", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame.getRootPane(), "Unknown exception caught!\n"
                                    + "Exception message:\n" + ex.toString(),
                            "Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
            done = true;
        } while (!done);
    }
}
