package exam;

/**
 * Das <code>ExamList</code> object represents a list of Exam's which are stored in a Arraylist.
 * @author Lukas Mendel
 */

import custom_exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

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
    * Save different exams from Arraylist into csv file on local Drive
    * @param filepath a String that contains the path to the file
    * @throws  FileNotFoundException If file couldn't be found.
    */
   public void saveDataLocal(String filepath) throws UserException
   {

      try (PrintWriter pw = new PrintWriter(new File(filepath)) )
      {
         StringBuilder builder = new StringBuilder();

         for (Exam e : exams) {

            builder.append( e.getFachnummer() +",");
            builder.append( e.getFachbezeichnung() +",");
            builder.append( e.getSemester() +",");
            builder.append( e.getDatum() +",");
            builder.append( e.getBegin() +",");
            builder.append( e.getDauer() +",");
            builder.append(  e.getBuilding() +",");
            builder.append( e.getRaumnummer() +",");
            builder.append( e.getVersuchsNummer() +",");
            builder.append( e.getNote() +",");
            builder.append( e.isBestanden() +",");
            builder.append( e.isAktuelleKlausur() +",");

            builder.append('\n');

         }
         pw.write(builder.toString());
      }
      catch (FileNotFoundException e)
      {
         throw new UserException("Datei konnte unter dem angegeben Pfad nicht gefunden werden");
      }

   }



   /**
    *the method iterates over the individual elements of the array list and checks certain parameters is set to true.
    *If yes, the element is added to the returning list.
    * @param parameter   0 = upcoming exam / 1 = passed exam / 2 = failed exam
    */
   public ObservableList<Exam> getExam(int parameter)
   {
      ObservableList<Exam> obserList = FXCollections.observableArrayList();
      for (Exam e: exams) {

         switch (parameter)
         {
            case 0:
               if(e.isAktuelleKlausur())
               {
                  obserList.add(e);
               }
               break;
            case 1:
               if(e.isBestanden())
               {
                  obserList.add(e);
               }
               break;
            case 2:
               if(!e.isBestanden())
               {
                  obserList.add(e);
               }
               break;
         }
      }
      return obserList;
   }




}
