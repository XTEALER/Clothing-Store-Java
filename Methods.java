import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.JSeparator;
import java.text.DecimalFormat;


public class Methods
{
    static Read R = new Read();
    static Productos P = new Productos();
    static DecimalFormat fr = new DecimalFormat("#.##");
    static double ventaDia = 0, ventaSemana = 0, ventaMes = 0, VentasTotales = 0.00, inversion = 0.00, ganancias = 0.00;
    static int x = 0, Cant = 0, Dias = 0, DiasTotales = 0;
    static String cliente, desc, art, ArgID, ArgPass;

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
                // si no coinciden
                ToReturn = 0;
            }
        }
        return ToReturn;
    }

    static public int Ventas()
    {
        String[] options = {"DAMAS", "CABALLEROS", "NIÑOS", "NIÑAS", "BABIES", "PAGAR", "VOLVER"};
        List<String> ProductList = new ArrayList<String>();
        List<Integer> SelectionsList = new ArrayList<Integer>();
        List<Integer> CantidadList = new ArrayList<Integer>();
        String ClientName = "", SelectedProduct = "";
        int y = 0, Depart = 0, Cantidad = 0, x = 0;
        int[] Range = new int[2];    
        do
        {
            Depart = JOptionPane.showOptionDialog(null, "Elija Un Departamento", "EL COSTOSO STORE",
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
            
            if(Depart == 5)
            {
                //pregunta nombre de cliente
                ClientName = JOptionPane.showInputDialog("Nombre de Cliente: ");
                System.out.println("Selection " + ClientName);

                if(ClientName == null)
                {
                    JOptionPane.showConfirmDialog(null, "CONTINUAR SIN NOMBRE DE CLIENTE?", "ADVERTENCIA", JOptionPane.OK_CANCEL_OPTION);
                }
                
                // paso de unidades a comprar
                int[] Cantidades = new int[CantidadList.size()];
                Cantidades = CantidadList.stream().mapToInt(i->i).toArray();
                CantidadList.clear();
                
                // paso de indices de productos
                int[] pindex = new int[SelectionsList.size()];
                pindex = SelectionsList.stream().mapToInt(i->i).toArray();
                SelectionsList.clear();
        
                Facturacion(Cantidades, pindex, ClientName);
                return 1;
            }
            else if(Depart == 6)
            {
                // ends program
                return 0;
            }
            else
            {
                // add selections to list
                for(y = Range[0]; y < Range[1]; y++)
                {
                    // only if there are available units in the stock and hasnt been selected
                    if(P.Stock[y] > 0 && !SelectionsList.contains(y))
                    {
                        ProductList.add(P.Nombre[y]);
                    }
                }

                // say deparment has no products available
                if(ProductList.size() == 0)
                {
                    JOptionPane.showMessageDialog(null, "SELECIONE OTRO DEPARTAMENTO...", "PRODUCTOS NO DISPONIBLES", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // paso de productos del departamento
                String[] Choices = new String[ProductList.size()];
                Choices = ProductList.toArray(Choices);
                ProductList.clear();

                // seleccion de producto
                SelectedProduct = (String) JOptionPane.showInputDialog(null, "PRODUCTOS:",
                options[Depart], JOptionPane.QUESTION_MESSAGE, null,                                                                  
                Choices, // Array of choices
                Choices[0]); // Initial choice
                
                // si el producto no es nulo, es agregado al "carrito"
                if(SelectedProduct != null)
                {
                    for(x = Range[0]; x < Range[1]; x++)
                    {
                        if(P.Nombre[x].equals(SelectedProduct))
                        {
                            SelectionsList.add(x);
                            break;
                        }
                    }
                }
                else
                {
                    // si el producto es nulo se vuelve a preguntar un departamento
                    System.out.println("Producto No Valido (null)");
                    continue;
                }

                // busqueda de seleccion en lista de productos del departamento
                System.out.println("Producto Seleccionado: " + SelectedProduct);

                do
                {
                    try
                    {
                        Cantidad = Integer.parseInt(JOptionPane.showInputDialog("UNIDADES A COMPRAR").toString());
                        System.out.println("Indice de P(x): " + x + "  Cantidades Disponibles: " + P.Stock[x]);
                        if(Cantidad > P.Stock[x])
                        {
                            JOptionPane.showMessageDialog(null, "NO SE TIENE LA CANTIDAD DE PRODUCTO SOLICITADA (" + Cantidad + ")\n UNIDADES DISPONIBLES (" + P.Stock[x] + ")", "ERROR DE STOCK", JOptionPane.INFORMATION_MESSAGE);
                        } 
                        else 
                        {
                            System.out.println("Unidades Disponibles");
                            break;
                        }
                    }
                    catch(Exception err)
                    {
                        JOptionPane.showMessageDialog(null, "NO SE PUDO LEER EL DATO INGRESADO\n\n\nINTENTE DE NUEVO...", "ERROR DE ENTRADA/SALIDA", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                while(true);
                CantidadList.add(Cantidad);
                System.out.println("Producto Selecionado: " + SelectedProduct + " (" + Cantidad + ")");
            }
        }
        while(true);
    }
    
    static public void Facturacion(int[] Cantidad, int[] Productos, String Nombre)
    {

        double Subtotal = 0, ITBMS, Total = 0, Cambio=0, Efectivo = 0;
        String Lista = "";
        int option = 0;
        
        for(x = 0; x < Cantidad.length; x++)
        {
            Lista += ("\nCodigo:          Producto:           Cantidad:           Precio:     \n" + P.Codigo[Productos[x]]+"             "+ P.Nombre[Productos[x]]+"            "+ Cantidad[x]+"              " +P.Precio[Productos[x]]+"      ");
            Subtotal += Cantidad[x] * P.Precio[Productos[x]];
            P.Stock[Productos[x]] -= Cantidad[x];
            System.out.println(Lista);
        }
        ITBMS = Subtotal*0.07;
        Total = Subtotal+ITBMS;
        
        do
        {
            try
            {
                    String respuesta = JOptionPane.showInputDialog(null,
                    "\nVendedor: "+ ArgID +"\nCliente: "+ Nombre + "\n-------------------------------------------\n" + Lista + "\n-------------------------------------------\nSUBTOTAL:         " + fr.format(Subtotal)+"\nITBMS:         " + fr.format(ITBMS)+ "\nTOTAL A PAGAR:          " + fr.format(Total) + "\n-------------------------------------------\nEFECTIVO:",
                    "CAJA",
                    JOptionPane.INFORMATION_MESSAGE);
                    if(respuesta == null)
                    {
                        JOptionPane.showMessageDialog(null, "COMPRA CANCELADA :'(", "CAJA", JOptionPane.OK_OPTION);
                        return;
                    }

                    Efectivo = Double.parseDouble(respuesta);

                    if(Efectivo >= Total)
                    {
                        break;
                    }
                    else
                    {
                        option = JOptionPane.showConfirmDialog(null, "No se puede procesar el pago...\nCancelar Compra?", "ERROR", JOptionPane.OK_CANCEL_OPTION);
                        if(option == 0 || option == -1)
                        {
                            JOptionPane.showMessageDialog(null, "COMPRA CANCELADA :'(", "CAJA", JOptionPane.OK_OPTION);
                            return;
                        }
                    }
                
                break;
            }
            catch(Exception e)
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
        }
        while(true);
        Cambio = (double) Efectivo - Total;

        System.out.println("Compra Terminada!");
        JOptionPane.showMessageDialog(null, "\nVendedor: "+ ArgID +"\nCliente: "+ Nombre +"\n\n\n"+ Lista + "\n\n\nSUBTOTAL:       "+fr.format(Subtotal)+"\nITBMS:         "+fr.format(ITBMS)+"\nTOTAL:        "+fr.format(Total)+"\n\n EFECTIVO:        "+fr.format(Efectivo)+"\n\nCAMBIO:         " +fr.format(Cambio),  "FACTURA", JOptionPane.OK_OPTION);
        
        stock(Total, false);
    }
    
    static public void stock(double Total, boolean Enday)
    {
        if(Enday)
        {
            Dias++;
            DiasTotales++;
            JOptionPane.showMessageDialog(null, "\nDiasTotales: " + DiasTotales + "\nVentas del Dia: " + fr.format(ventaDia) + "\nVentas de la Semana: " + fr.format(ventaSemana) + "\nVentas del Mes: " + fr.format(ventaMes) + "\nVentas Totales: " + fr.format(VentasTotales));
        }

        ventaDia += Total;
        ventaSemana += Total;
        ventaMes += Total;
        VentasTotales += Total;
        ganancias = ventaMes - inversion;

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
            JOptionPane.showMessageDialog(null, "GANANCIAS DEL MES: " + ganancias);
            ventaMes = 0.00;
            inversion = 0;
        }
    }
    
    static void inversiones()
    {
        while(true)
        {
            String inver = JOptionPane.showInputDialog("INVERSION PARA EL MES");
            inversion = Double.parseDouble(inver);
            if(inversion != 0)
            {
                break;
            }       
        }
    }

    // 1 para imprimir todo el inventario 2 para imprimir los productos bajos de stock y 3 para adquirir nuevos productos
    static void Inventario()
    {
        int x = 0, opc = 0, Uni = 0, option = 0;
        String BajoStock = "", c = "";
        boolean exit = false;

        do
        {
            //muestra lista de productos
            System.out.print("\n1) Ver inventario (completo)\n2) Ver inventario (solo bajo de stock)\n3) Restock de inventario\n0) Volver a panel de control\n----------------------------------------\n-> ");
            option = R.ReadInt();

            // selects option, if 0 exits from function
            switch(option)
            {
                case 1:
                    // inventory impresion
                    System.out.println("\n------------------------\n|    Inventario Dia  " + DiasTotales + " |\n------------------------");
                    for(x = 0; x < 38; x++)
                    {
                        if(P.Stock[x] > 3)
                        {
                            System.out.println("[OK] Codigo: " + P.Codigo[x] + " ++ Nombre: " + P.Nombre[x] + " ++ Cantidad: " + P.Stock[x]);
                        }
                        else
                        {
                            System.out.println("**[BAJO] Codigo: " + P.Codigo[x] + " ++ Nombre: " + P.Nombre[x] + " ++ Cantidad: " + P.Stock[x]);
                        }
                    }
                    break;
                case 2:
                    // add products to stock
                    for(x = 0; x < 38; x++)
                    {
                        if(P.Stock[x] <= 3)
                        {
                            BajoStock += "\n**[BAJO] Codigo: " + P.Codigo[x] + " ++ Nombre: " + P.Nombre[x] + " ++ Cantidad: " + P.Stock[x];
                        }
                    }
                    // imprime productos en stock con menos de 4 unidades
                    if(BajoStock != null)
                    {
                        System.out.println("\n------------------\n|   STOCK BAJO   |\n------------------" + BajoStock);
                    }
                    break;
                case 3:
                    //insercion de unidades a los productos
                    do
                    {
                        System.out.println("-------ELIJA CODIGO DE PRODUTO-------\n|            (100 - 137)            |\n-------------------------------------");
                        do
                        {
                            opc = R.ReadInt();
                            if(opc > 99 && opc < 138)
                            {
                                opc -= 100;
                                break;
                            }
                            else
                            {
                                System.out.println("CODIGO FUERA DE RANGO (" + opc + ")");
                            }
                        }
                        while(true);
                        System.out.println("PRODUCTO ELEJIDO: \n" + P.Nombre[opc] + " [" + P.Stock[opc] + "]");
                        System.out.println("\nCUANTOS UNIDADES DESEA AGREGAR");
                        Uni = R.ReadInt();
                        P.Stock[opc] += Uni;
                        System.out.println("PRODUCTO: \n" + P.Nombre[opc] + " [" + P.Stock[opc] + "]");
                        System.out.print("\nDesea Seguir Agregando Unidades? (YES/NO): ");
                        c = R.ReadStr();
                    }
                    while(c.toLowerCase().equals("yes"));
                    break;
                case 0:
                    // exits inventory
                    System.out.println("Saliendo...");
                    exit = true;
                    break;
                default:
                    // iterates again because unknown option
                    System.out.println("OPCION NO VALIDA (" + option + ")");
                    break;
            }
        }
        while(!exit);
    }
}
