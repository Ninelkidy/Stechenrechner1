import javax.swing.*;
import java.util.ArrayList;

public class Berechnen {


    public static JLabel ergebnisLableUeberstunden;
    public int endStunden, endMinuten;
    ArrayList<Integer> ueberstundenRueckgabe = new ArrayList<>();

    public ArrayList<Integer> berechneFeierabendZeit(ArrayList<Integer> stunden, JTextField ankunftsStundenField, JTextField ankunftsMinutenField, JTextField bleibZeitField, JTextField pausenZeitField, JLabel ergebnisLabel) {
        int ankunftsStunden = Integer.parseInt(ankunftsStundenField.getText());
        int ankunftsMinuten = Integer.parseInt(ankunftsMinutenField.getText());
        double bleibZeit = Double.parseDouble(bleibZeitField.getText());
        double pausenZeit = Double.parseDouble(pausenZeitField.getText());
        double gesamtZeit = bleibZeit + pausenZeit;
        int gesamtStunden = (int) gesamtZeit;
        int gesamtMinuten = (int) ((gesamtZeit - gesamtStunden) * 60);
        int endMinuten = ankunftsMinuten + gesamtMinuten;
        int extraStunden = endMinuten / 60;
        endMinuten = endMinuten % 60;
        int endStunden = ankunftsStunden + gesamtStunden + extraStunden;

        if (endStunden <=24) {
            ergebnisLabel.setText("Feierabend um " + endStunden + ":" + (endMinuten < 10 ? "0" + endMinuten : endMinuten) + " Uhr.");
        }
        else {
            ergebnisLabel.setText("Check deine Eingabe");
        }
        stunden.add(endStunden);
        stunden.add(endMinuten);
        return stunden;
    }
    public void ueberstunden(ArrayList<Integer> stunden, JTextField bleibZeitField, JLabel ueberstundenLabel) {
        double vorgegebeneArbeitszeit = 7.6 * 60;
        double gesamteArbeitszeit = Double.parseDouble(bleibZeitField.getText());
        int arbeitszeitInMinuten = (int) (gesamteArbeitszeit * 60);
        int ueberstundenInMinuten = (int) (arbeitszeitInMinuten - vorgegebeneArbeitszeit);
        int endstundis = stunden.get(0);
        if (endstundis <=24) {
            System.out.println(endStunden);
            if (ueberstundenInMinuten > 0){
                ueberstundenLabel.setText("Ueberstunden: " + ueberstundenInMinuten + " Minuten");
            }else {
                ueberstundenLabel.setText("Du hast keine Ueberstunden");
            }}else {
            ueberstundenLabel.setText(":(");
        }
        ueberstundenRueckgabe.add(ueberstundenInMinuten);
    }
    public void ueberstundenBerechnen(JTextField ueberstundenAnzahlField, JTextField ueberstundenAnzahlMinutenField, JTextField davonverwendenField, JTextField davonverwendenFieldMinuten, JTextField wannHeuteGehenField, JTextField wannHeuteGehenFieldMinute, JLabel ergebnisLableUeberstunden) {

        int gesamtUeberStunden = (Integer.parseInt(ueberstundenAnzahlField.getText()))*60; // Wie viele überstunden man hat
        int gesamtUeberMinuten = Integer.parseInt(ueberstundenAnzahlMinutenField.getText());
        int UeberEndMinuten = gesamtUeberStunden + gesamtUeberMinuten;

        int abziehenStunden = (Integer.parseInt(davonverwendenField.getText()))*60;  //Wie viele man abziehen will
        int abziehenMinuten = Integer.parseInt(davonverwendenFieldMinuten.getText());
        int abziehenEndMinuten = abziehenStunden + abziehenMinuten;

        int uebrigeMinuten = UeberEndMinuten - abziehenEndMinuten;
        int uebrigeStundenInt = uebrigeMinuten / 60;
        double uebrigeStunden = (double) uebrigeMinuten / 60;
        double zwischenStunden = uebrigeStundenInt;
        double ueberstudenEndMinuten = (uebrigeStunden - zwischenStunden) * 60;
        int ueberstudenEndMinutenInt = (int) Math.round(ueberstudenEndMinuten);

        int arbeitAusStunden = (Integer.parseInt(wannHeuteGehenField.getText()))*60;   //Wann man laut Stechenrechner gehen darf
        int arbeitAusMinuten = Integer.parseInt(wannHeuteGehenFieldMinute.getText());
        int arbeitAusZeit = arbeitAusStunden + arbeitAusMinuten;

        int endArbeitAusZeit = arbeitAusZeit - abziehenEndMinuten;
        int arbeitAusInt = endArbeitAusZeit / 60;
        double arbeitAusStundenDouble = (double) endArbeitAusZeit / 60;
        double zwischenArbeitAusStunden = arbeitAusInt;
        double arbeitAusEndMinuten = (arbeitAusStundenDouble - zwischenArbeitAusStunden) * 60;
        int arbeitAusEndMinutenInt = (int) Math.round(arbeitAusEndMinuten);

        if (arbeitAusInt <=24) {
            ergebnisLableUeberstunden.setText("Feierabend ist um: " + arbeitAusInt + " Uhr " + arbeitAusEndMinutenInt);
        }
        else{
            System.err.println("Du Bastard");
        }}
}
