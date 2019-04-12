public class Projekt
{
    public String pbez;
    private int gkost;
    public int pat, crt = -3;

    Projekt(String ebez, int ekost, int epat)
    {
        this.pbez = ebez;

        if(epat <= 1 && !setGkost(ekost))
        {
            this.crt = -3;
        } else if(epat > 1 && !setGkost(ekost))
        {
            this.pat = epat;
            this.crt = -2;
        } else if(epat <= 1 && setGkost(ekost))
        {
            this.crt = -1;
        } else
        {
            setGkost(ekost);
            this.pat = epat;
            this.crt = 1;
        }
    }

    public int csv2Projekt(String ecsv)
    {
        String csv[];
        String delim = ";";
        try
        {
            csv = ecsv.split(delim);
            if(csv.length != 3)
            {
                return -4;
            }

            this.pbez = csv[0];
            if(setGkost(Integer.parseInt(csv[1])) && Integer.parseInt(csv[2]) > 1)
            {
                setGkost(Integer.parseInt(csv[1]));
                this.pat = Integer.parseInt(csv[2]);
                this.crt = 1;
            } else if (setGkost(Integer.parseInt(csv[1])) && !(Integer.parseInt(csv[2]) > 1))
            {
                setGkost(Integer.parseInt(csv[1]));
                this.crt = -1;
            } else if (!setGkost(Integer.parseInt(csv[1])) && (Integer.parseInt(csv[2]) > 1))
            {
                this.pat = Integer.parseInt(csv[2]);
                this.crt = -2;
            } else if (!setGkost(Integer.parseInt(csv[1])) && !(Integer.parseInt(csv[2]) > 1))
            {
                this.crt = -3;
            }
            return this.crt;
        } catch(NumberFormatException ex)
        {
            return -5;
        }
    }

    public String proAus()
    {
        String aus = this.pbez + ";" + this.getGkost() + ";" + this.pat;
        return aus;
}

    public int getGkost()
    {
        return gkost;
    }

    public boolean setGkost(int ekost)
    {
        if(ekost >= 1000)
        {
            this.gkost = ekost;
            return true;
        }

        return false;
    }
}

