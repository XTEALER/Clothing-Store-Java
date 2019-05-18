import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JSeparator;
import java.text.DecimalFormat;


public class Methods
{
    static Read R = new Read();
    static Productos P = new Productos();
    static String cliente, desc, art, ArgID, ArgPass;
    static double ventaDia = 0, ventaSemana = 0, ventaMes = 0, ventaTodo;
    static int x = 0, cant = 0, Dias = 0, DiasTotales = 0;
    //static int[] Pindex = new int[] {0,7,8,14,15,21,22,30,31,37};
    // Verifica datos del usuario y de existir en el registro retorna true
    static private boolean Users(String ID, String Pass) 
    {
        int x;
        boolean check = false;
        String[] Names = {"ANGEL", "HUMBERTO"};
        String[] IDs = {"110", "111"};
        String[] Passwords = {"Ventas110", "Ventas111"};
        // comparacion de datos
        if(!(ID.equals("")) && !(Pass.equals("")))
        {
            for(x = 0; x < 2; x++) 
            {
                if(Names[x].equals(ID) || IDs[x].equals(ID))
                {
                    if(Passwords[x].equals(Pass))
                    {
                        check = true;
                        if(IDs[x].equals(ID))
                        {
                            ArgID = Names[x];
                        }
                        break;
                    }
                }
            }
        }

        if(!check) 
        {
            // si no hay coincidencias se retorna false
            JOptionPane.showMessageDialog(null, "Usuario/ID o Contraseña no validos!\nIntente de Nuevo.");
            check = false;
        }
        return check;
    }

    static public int vendedor()
    {
        JPanel MainPanel = new JPanel();
        JTextField Pass = new JTextField(5);
        JTextField ID = new JTextField(10);
        boolean check = false;
        int ToReturn = 0;
        // se jodio algo con el audio de mi laptop y si hablamos por discord?
        MainPanel.setLayout(new BoxLayout(MainPanel,BoxLayout.PAGE_AXIS));
        MainPanel.add(new JSeparator(JSeparator.VERTICAL));
        MainPanel.add(Box.createVerticalStrut(15));
        MainPanel.add(new JLabel("Nombre/ID de vendedor: "));
        MainPanel.add(ID);
        MainPanel.add(new JLabel("Contraseña: "));
        MainPanel.add(Pass);
        MainPanel.add(Box.createVerticalStrut(15));
        
        int option = JOptionPane.showConfirmDialog(null, 
        MainPanel, 
        "Inicio de Sesion", 
        JOptionPane.OK_CANCEL_OPTION);

        ArgID = ID.getText().toUpperCase();
        ArgPass = Pass.getText();

        System.out.println("Opcion: " + option);

        // si se selecionan las opciones cancelar o cerrar se vuelve al menu de cerrar programa
        if(option == -1 || option == 2) 
        {
            ToReturn = -1;
        } else 
        {
            // si coinciden los datos ingresados con el registro se retorna 1
            check = Users(ArgID, ArgPass);
            if(check == true) 
            {
                ToReturn = 1;
            } else 
            {
                ToReturn = 0;
            }
        }
        return ToReturn;
    }

    static public int Ventas()
    {
        String[] options = {"DAMAS", "CABALLEROS", "NIÑOS", "NIÑAS", "BABIES"};
        List<String> ProductList = new ArrayList<String>();
        int[] Range = new int[2];
        String ClientName;
        int y;
        
        int Depart = JOptionPane.showOptionDialog(null, "Elija Un Departamento", "EL COSTOSO STORE",
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        
        System.out.println("Selection: " + Depart);

        switch(Depart) 
        {
            case 4:
                Range[0] = 0;
                Range[1] = 7 + 1;
                break;
            case 2:
                Range[0] = 8;
                Range[1] = 14 + 1;
                break;
            case 3:
                Range[0] = 15;
                Range[1] = 21 + 1;
                break;
            case 1:
                Range[0] = 22;
                Range[1] = 30 + 1;
                break;
            case 0:
                Range[0] = 31;
                Range[1] = 37 + 1;
                break;
            case -1:
                System.out.println("Exiting Ventas...");
                return -1;
        }

        // adds deparment products to list
        for(y = Range[0]; y < Range[1]; y++)
        {
            ProductList.add(P.Nombre[y]);
        }

        String[] Choices = new String[ProductList.size()];
        Choices = ProductList.toArray(Choices);
        int[] Cantidad = new int[2];
        Cantidad[0] = 2;
        Cantidad[1] = 1;
        String input = (String) JOptionPane.showInputDialog(null, "Escoja Un Producto:",
        "Departamento de " + options[Depart], JOptionPane.QUESTION_MESSAGE, null,                                                                     
        Choices, // Array of choices
        null); // Initial choice

        System.out.println("Input: " + input);

        if(input == null)
        {
            return -2;
        }

        int[] pindex = {0, 1};
        ClientName = "Lee";
        Facturacion(Cantidad, pindex, ClientName);
        return 0;
    }
    
    static public void Facturacion(int[] Cantidad, int[] Productos, String Nombre)
    {
        String Lista = "";
        double Subtotal = 0, ITBMS, Total = 0, Cambio=0, Efectivo = 0;
        DecimalFormat fr = new DecimalFormat("#.##");
        
        for(x = 0; x < Cantidad.length; x++)
        {
            Lista += ("\nProducto: " + P.Nombre[Productos[x]] + " Codigo: "+ P.Codigo[Productos[x]] + " Cantidad: " + Cantidad[x] + " Precio: " + P.Precio[Productos[x]]);
            Subtotal += Cantidad[x] * P.Precio[x];
            System.out.println(Lista);
        }
        ITBMS = Subtotal*0.07;
        Total = Subtotal+ITBMS;
        
        JOptionPane.showMessageDialog(null, "Productos: \n" + Lista + "\nSubtotal: "+fr.format(Subtotal)+"\nITBMS: "+fr.format(ITBMS)+"\n\nTotal: "+fr.format(Total),  "FACTURACION", JOptionPane.OK_OPTION);
        try
        {
        String respuesta = JOptionPane.showInputDialog(null, "total a pagar"+Total+"\n\n Monto a Pagar","CAJA",JOptionPane.INFORMATION_MESSAGE);
        Efectivo = Double.parseDouble(respuesta);
        }catch(Exception e)
        {
            if (e instanceof IOException)
            {
                JOptionPane.showMessageDialog(null,"Error de Entrada", "ERROR",JOptionPane.WARNING_MESSAGE);
            }
            else if (e instanceof NumberFormatException)
            {
            JOptionPane.showMessageDialog(null,"Error de Entrada", "ERROR",JOptionPane.WARNING_MESSAGE);
            }
        }
        
        Cambio = (double)Efectivo - Total;

        JOptionPane.showMessageDialog(null, "Productos: \n" + Lista + "\n\nSubtotal: "+fr.format(Subtotal)+"ITBMS: "+fr.format(ITBMS)+"Total: "+fr.format(Total)+"\n\n\n Monto pagado: "+fr.format(Efectivo)+"Cambio: " +fr.format(Cambio),  "FACTURA", JOptionPane.OK_OPTION);
        stock(Total, true);
    }
    
    static public void stock(double Total, boolean Enday)
    {   
        //double Inversion = 0;
        // System.out.println("Ingrese La Inversion Para El Mes: ");
        // Inversion = R.ReadDouble();
        
        Dias++;
        DiasTotales++;
        ventaDia += Total;
        ventaSemana += Total;
        ventaMes += Total;
        ventaTodo += Total;
        System.out.println("DiasTotales: " + DiasTotales);
        System.out.println("Ventas del Dia: " + ventaDia);
        System.out.println("Ventas de la Semana: " + ventaSemana);
        System.out.println("Ventas del Mes: " + ventaMes);
        System.out.println("Ventas Totales: " + ventaTodo);

        if(DiasTotales%8 != 0 && Enday)
        {
            if(Dias <= 2)
            {
                // reinicia las ventas del dia
                ventaDia = 0.00;
            }
            if(Dias == 2)
            {
                // reinicia las ventas de la semana
                ventaSemana = 0.00;
                Dias = 0;
            }
        }
        else if(Enday)
        {
            // reinicia las ventas del mes
            ventaMes = 0.00;
        }
    
        ventaTodo += Total;
        System.out.println("DiasTotales: " + DiasTotales);
        System.out.println("Ventas del Dia: " + ventaDia);
        System.out.println("Ventas de la Semana: " + ventaSemana);
        System.out.println("Ventas del Mes: " + ventaMes);
        System.out.println("Ventas Totales: " + ventaTodo);

        if(DiasTotales%8 != 0 && Enday)
        {
            if(Dias <= 2)
            {
                // reinicia las ventas del dia
                ventaDia = 0.00;
            }
            if(Dias == 2)
            {
                // reinicia las ventas de la semana
                ventaSemana = 0.00;
                Dias = 0;
            }
        }
        else if(Enday)
        {
            // reinicia las ventas del mes
            ventaMes = 0.00;
        }
    
        ventaTodo += Total;
        System.out.println("DiasTotales: " + DiasTotales);
        System.out.println("Ventas del Dia: " + ventaDia);
        System.out.println("Ventas de la Semana: " + ventaSemana);
        System.out.println("Ventas del Mes: " + ventaMes);
        System.out.println("Ventas Totales: " + ventaTodo);

        if(DiasTotales%8 != 0 && Enday)
        {
            if(Dias <= 2)
            {
                // reinicia las ventas del dia
                ventaDia = 0.00;
            }
            if(Dias == 2)
            {
                // reinicia las ventas de la semana
                ventaSemana = 0.00;
                Dias = 0;
            }
        }
        else if(Enday)
        {
            // reinicia las ventas del mes
            ventaMes = 0.00;
        }
    }
}
// activare control de versiones