package exam;

/**
 * Das <code>Exam</code> object repräsentiert eine Klausur, welche am Ende eines Semesters geschrieben wird.
 * @author Lukas Mendel
 */

import java.util.Date;

public class Exam {

    private String fachnummer;
    private String fachbezeichnung;
    private String semester;
    private Date datum;
    private String begin;
    private String dauer;
    private String Raum;
    private int raumnummer;
    private int versuchsNummer;
    private double note;
    private boolean bestanden;
    private boolean aktuelleKlausur;

    public Exam()
    {

    }

    public Exam(String fachnummer)
    {
        this.fachnummer = fachnummer;
    }


    public String getFachnummer() {
        return fachnummer;
    }

    public void setFachnummer(String fachnummer) {
        this.fachnummer = fachnummer;
    }

    public String getFachbezeichnung() {
        return fachbezeichnung;
    }

    public void setFachbezeichnung(String fachbezeichnung) {
        this.fachbezeichnung = fachbezeichnung;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    public String getRaum() {
        return Raum;
    }

    public void setRaum(String raum) {
        Raum = raum;
    }

    public int getRaumnummer() {
        return raumnummer;
    }

    public void setRaumnummer(int raumnummer) {
        this.raumnummer = raumnummer;
    }

    public int getVersuchsNummer() {
        return versuchsNummer;
    }

    public void setVersuchsNummer(int versuchsNummer) {
        this.versuchsNummer = versuchsNummer;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public boolean isBestanden() {
        return bestanden;
    }

    public void setBestanden(boolean bestanden) {
        this.bestanden = bestanden;
    }

    public boolean isAktuelleKlausur() {
        return aktuelleKlausur;
    }

    public void setAktuelleKlausur(boolean aktuelleKlausur) {
        this.aktuelleKlausur = aktuelleKlausur;
    }
}
