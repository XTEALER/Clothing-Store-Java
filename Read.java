
import java.util.Scanner;
class Read
{
    // close after using on main with Read.reader.close
    Scanner reader = new Scanner(System.in);
    
    // read float or int method
    public double ReadDouble()
    {
        double dNumber;
        while(true)
        {
            try
            {
                dNumber = reader.nextDouble();
                reader.nextLine();
                break;
            } 
            catch(Exception err)
            {
                System.out.print("ERROR, Intente de Nuevo: ");
                reader.next();
                continue;
            }
        }
        return dNumber;
    }


    // read float or int method
    public float ReadFloat()
    {
        float fNumber;
        while(true)
        {
            try
            {
                fNumber = reader.nextFloat();
                reader.nextLine();
                break;
            } 
            catch(Exception err)
            {
                System.out.print("ERROR, Intente de Nuevo: ");
                reader.next();
                continue;
            }
        }
        return fNumber;
    }

    public int ReadInt()
    {
        int iNumber;
        while(true)
        {
            try
            {
                iNumber = reader.nextInt();
                reader.nextLine();
                break;
            } 
            catch(Exception err)
            {
                System.out.print("ERROR, Intente de Nuevo:");
                reader.next();
                continue;
            }
        }
        return iNumber;
    }

    public String ReadStr()
    {
        String xtring;
        while(true)
        {
            try
            {
                xtring = reader.nextLine(); 
                break;
            } 
            catch(Exception err)
            {
                System.out.print("ERROR, Intente de Nuevo: ");
                reader.next();
                continue;
            }
        }
        return xtring;
    }

}