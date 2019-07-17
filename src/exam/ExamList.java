package exam;

/**
 * Das <code>ExamList</code> object represents a list of Exam's which are stored in a Arraylist.
 * @author Lukas Mendel
 */

import custom_exceptions.UserException;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * Das <code>ExamList</code> object repräsentiert eine Liste von Exams, welche am Ende eines Semesters geschrieben wurden.
 * Sie beinhaltet alle grundlegen Operation zum Einfügen oder Löschen eines Dokumentes.
 * @author Lukas Mendel
 */

public class ExamList  {

   private ArrayList<Exam> exams = new ArrayList<>();

   public ExamList()
   {

   }

   /**
    * Load different exams from local Drive.
    * Each line has information about one Object.
    * When the Object is create, it will be stored in the Arraylist.
    * @param filepath a String that contains the path to the file
    * @throws  UserException If file couldn't be found.
    */
   public void loadLocalData(String filepath ) throws UserException
   {
      try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
         String current = reader.readLine();
         while (current != null) {

            Exam exam = new Exam(current);
            exams.add(exam);
            current = reader.readLine();

         }
      }
      catch(IOException exc)
      {
         throw new UserException("Datei konnte unter dem angegeben Pfad nicht gefunden werden");
      }


   }

   /**
    * Save different exams from arraylist into csv file on local Drive
    * @param filepath a String that contains the path to the file
    * @throws  FileNotFoundException If file couldn't be found.
    */
   public void saveDataLocal(String filepath)
   {


   }

   /**
    * Save different exams from arraylist into csv file on local Drive
    * @throws  FileNotFoundException If file couldn't be found.
    */
   public ObservableList<Exam> getCurrentExam()
   {
      for (Exam e: exams) {

      }

      return null;
   }

   public ObservableList<Exam> getPassedExam()
   {

      return null;
   }

   public ObservableList<Exam> getFailedExam()
   {

      return null;
   }


}
