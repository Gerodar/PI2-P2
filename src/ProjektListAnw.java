import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;

public class ProjektListAnw
{
    public static void main(String[] args)
    {
        LinkedList<Projekt> proList = new LinkedList<Projekt>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)), br1;
        PrintWriter pw1;
        int auswahl = -1, ergebnis = -1, eingabeInt = -1, wert = -1;
        String eingabe;

        try
        {
            FileWriter fw1 = new FileWriter("PROJEKT5.txt");
            pw1 = new PrintWriter(fw1);
            FileReader fr1 = new FileReader("PROJEKT3.txt");
            br1 = new BufferedReader(fr1);
            proList = dat2ProList(br1);

            anzeige(proList);

            proList = proListSort(proList);

            anzeige(proList);

            do
            {
                ergebnis = -1;
                Projekt pro = new Projekt();
                System.out.println("\n(1): Neues Projekt anlegen");
                System.out.println("(2): Projekt loeschen");
                System.out.println("(3): Projekt aendern");
                System.out.println("(-1): Programm beenden");
                try
                {
                    auswahl = Integer.parseInt(in.readLine());
                } catch (NumberFormatException ex)
                {
                    System.out.println("Der eingegebene Wert war keine Nummer");
                }

                switch (auswahl)
                {
                    case (1):
                        do
                        {
                            System.out.println("Bitte einen Projekt-CSV im Format PBPB;GGGG;PAPA angeben");
                            eingabe = in.readLine();
                            pro.csv2Projekt(eingabe);
                        } while (pro.crt != 1);

                        ergebnis = proListEinfueg(proList, pro);
                        if (ergebnis != -1)
                        {
                            System.out.println("Das neue Projekt wurde an Stelle " + ergebnis + " eingefuegt");
                        } else
                        {
                            System.out.println("Das Projekt wurde nicht erfolgreich eingefuegt");
                        }
                        break;
                    case (2):
                        do
                        {
                            System.out.println("Bitte Position des zu loeschenden Projekts angeben");
                            try
                            {
                                eingabeInt = Integer.parseInt(in.readLine());
                            } catch (NumberFormatException ex)
                            {
                                System.out.println("Eingegebener Wert war keine Zahl");
                            }
                        } while (eingabeInt == -1);

                        ergebnis = proListLoesch(proList, eingabeInt);
                        if (ergebnis != -1)
                        {
                            System.out.println("Projekt an Stelle " + eingabeInt + " wurde entfernt");
                        } else
                        {
                            System.out.println("Das Projekt wurde nicht erfolgreich entfernt");
                        }
                        break;
                    case (3):
                        int position = -1;
                        do
                        {
                            position = -1;
                            System.out.println("Bitte Position des zu aendernden Projekts angeben");
                            try
                            {
                                position = Integer.parseInt(in.readLine());
                            } catch (NumberFormatException ex)
                            {
                                System.out.println("Fuer die Position wurde kein integer-Wert angegeben");
                            }
                        } while (position == -1);
                        do
                        {


                            System.out.println("Bitte angeben, ob gkost oder pat geaendert werden soll");
                            System.out.println("(1): gkost");
                            System.out.println("(2): pat");
                            try
                            {
                                eingabeInt = Integer.parseInt(in.readLine());
                            } catch (NumberFormatException ex)
                            {
                                System.out.println("Eingegebener Wert für w war keine Zahl");
                            }
                        } while (eingabeInt != 1 && eingabeInt != 2);

                        if (eingabeInt == 1 || eingabeInt == 2)
                        {
                            do
                            {
                                System.out.println("Bitte den neuen Wert eingeben");
                                try
                                {
                                    wert = Integer.parseInt(in.readLine());
                                } catch (NumberFormatException ex)
                                {
                                    System.out.println("Es wurde kein integer-Wert fuer pat eingegeben");
                                }
                            } while (wert == -1);

                            ergebnis = proListUpdate(proList, position, eingabeInt, wert);
                            if (ergebnis == 1)
                            {
                                System.out.println("Update war erfolgreich");
                            } else if (ergebnis == -1)
                            {
                                System.out.println("Der Wert fuer Pat entsprach nicht den Anforderungen");
                            } else if (ergebnis == -2)
                            {
                                System.out.println("Der Wert fuer Gkost war nicht hoch genug");
                            } else if (ergebnis == -7)
                            {
                                System.out.println("Pos war nicht innerhalb der Liste");
                            }
                        }

                        break;
                    case (-1):
                        for (Projekt ausgabe : proList)
                        {
                            pw1.println(ausgabe.proAus());
                        }

                        br1.close();
                        fr1.close();
                        in.close();
                        pw1.close();
                        fw1.close();
                        return;
                }

                anzeige(proList);

            } while (auswahl != -1);

        } catch (IOException ex)
        {
            System.out.println("IO-Fehler");
            ex.printStackTrace();
        }

    }

    private static void anzeige(LinkedList<Projekt> proList)
    {
        System.out.println();
        for (int i = 0; i < proList.size(); i++)
        {
            System.out.println("Projekt " + i + ": " + proList.get(i).proAus());
        }
    }

    private static int proListUpdate(LinkedList<Projekt> proList, int position, int eingabeInt, int wert)
    {
        try
        {
            if (eingabeInt == 1)
            {
                if (proList.get(position).setGkost(wert))
                {
                    proList.get(position).setGkost(wert);
                    return 1;
                } else
                {
                    return -2;
                }
            } else if (eingabeInt == 2)
            {
                if (proList.get(position).setPat(wert) == 1)
                {
                    proList.get(position).setPat(wert);
                    return 1;
                } else
                {
                    return -1;
                }
            }
            return 0;
        } catch (IndexOutOfBoundsException ex)
        {
            return -7;
        }
    }

    private static int proListLoesch(LinkedList<Projekt> proList, int eingabeInt)
    {
        try
        {
            proList.remove(eingabeInt);
            return 1;
        } catch (IndexOutOfBoundsException ex)
        {
            System.out.println("eingabeInt war groesser als die Liste");
            ex.printStackTrace();
            return -1;
        }
    }

    private static int proListEinfueg(LinkedList<Projekt> proList, Projekt pro)
    {
        proList.add(pro);
        proList = proListSort(proList);
        for (int i = 0; i < proList.size(); i++)
        {
            if (proList.get(i).pbez.compareTo(pro.pbez) == 0)
            {
                return i;
            }
        }
        return -1;
    }

    private static LinkedList<Projekt> proListSort(LinkedList<Projekt> proList)
    {
        proList.sort(Comparator.naturalOrder());
        return proList;
    }

    private static LinkedList<Projekt> dat2ProList(BufferedReader br1) throws IOException
    {
        LinkedList<Projekt> temp = new LinkedList<Projekt>();
        String in, delim = ";";

        in = br1.readLine();
        while (in != null)
        {
            Projekt pro = new Projekt();
            pro.csv2Projekt(in);
            if (pro.crt == 1)
            {
                temp.add(pro);
            }

            in = br1.readLine();
        }
        return temp;
    }
}
